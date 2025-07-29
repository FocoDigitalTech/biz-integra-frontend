package br.com.onetec.application.views.main.relatorios.service;

import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.relatorios.div.RelatorioAgendamentoDiv;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendamentoPrintExportService {

    private final OrcamentoService orcamentoService;
    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final ClientesService clientesService;
    private final OrdemServicoService ordemServicoService;
    private final ServicosOrcamentoService servicosOrcamentoService;
    private final ServicoService servicoService;
    private boolean abreviaverificacao = false;

    @Autowired
    public AgendamentoPrintExportService(OrcamentoService orcamentoService1, ContratoService contratoService1,
                                         FuncionarioService funcionarioService1, ClientesService clientesService1,
                                         OrdemServicoService ordemServicoService1,
                                         ServicosOrcamentoService servicosOrcamentoService1, ServicoService servicoService1) {
        this.orcamentoService = orcamentoService1;
        this.contratoService = contratoService1;
        this.funcionarioService = funcionarioService1;
        this.clientesService = clientesService1;
        this.ordemServicoService = ordemServicoService1;
        this.servicosOrcamentoService = servicosOrcamentoService1;
        this.servicoService = servicoService1;
    }

    public void imprimirRelatorio(Grid<SetContrato> grid, ClickEvent<Button> e, Checkbox abreviarCheckbox) {

            // 1) coleta o que está filtrado
            List<SetContrato> contratos = grid.getGenericDataView()
                    .getItems()
                    .collect(Collectors.toList());
            boolean abreviar = abreviarCheckbox.getValue();

            // 2) monta o HTML
            String html = montarHtmlDeImpressao(contratos, abreviar);

            // 3) cria o StreamResource
            StreamResource resource = new StreamResource("relatorio.html", () ->
                    new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8))
            );
            resource.setContentType("text/html");
            resource.setCacheTime(0); // sem cache

            // 4) registra e obtém a URI
            String uri = VaadinSession.getCurrent()
                    .getResourceRegistry()
                    .registerResource(resource)
                    .getResourceUri()
                    .toString();

            // 5) abre numa aba nova — o onload do HTML dispara window.print()
            UI.getCurrent().getPage().open(uri, "_blank");

    }

    private String montarHtmlDeImpressao(List<SetContrato> contratos, boolean abreviar) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='utf-8'>")
                // impressão paisagem
                .append("<style>@media print{@page{size: landscape;}}")
                // estilos de tabela
                .append("table{width:100%;border-collapse:collapse}")
                .append("th,td{border:1px solid #000;padding:4px}")
                .append("th{background:#eee}")
                .append("</style></head>")
                // onload dispara a caixa de diálogo
                .append("<body onload='window.print()'>")
                .append("<h2>Relatório de Agendamentos</h2>")
                .append("<table><thead><tr>")
                .append("<th>Data</th><th>Nº Orçamento</th><th>Nº Contrato</th>")
                .append("<th>Cliente</th><th>Horário</th><th>Tipo</th>")
                .append("<th>Técnico</th><th>Serviços</th>")
                .append("</tr></thead><tbody>");

        for (SetContrato c : contratos) {
            html.append("<tr>");
            // Data formatada
            String data = c.getDatainicio_execucao() != null
                    ? UtilitySystemConfigService.getDataFormatada(c.getDatainicio_execucao().atStartOfDay()).toString()
                    : "";
            html.append("<td>").append(data).append("</td>")
                    .append("<td>").append(c.getId_orcamento()).append("</td>")
                    .append("<td>").append(c.getId_contrato()).append("</td>");

            // Nome cliente
            SetCliente cl = clientesService.findById(c.getId_cliente());
            html.append("<td>")
                    .append(cl != null ? cl.getNome_cliente() : "")
                    .append("</td>");

            // Horário atendimento
            var optionalHorario = ordemServicoService.findAllByOrcamentoId(c.getId_orcamento()).stream()
                    .max(Comparator.comparing(SetOrdemServico::getDatainicio_ordemservico))
                    .map(SetOrdemServico::getHorarioinicio_ordemservico);
            String horario = "N/D";
            if (optionalHorario.isPresent()){
                horario = optionalHorario.get().toString();
            }

            html.append("<td>").append(horario).append("</td>");

            // Tipo
            String tipo = "SIM".equals(c.getAplicacoes_periodicas())
                    ? "Aplicações Periódicas" : "Suporte";
            html.append("<td>").append(tipo).append("</td>");

            // Técnico
            String tecnico = ordemServicoService.findAllByOrcamentoId(c.getId_orcamento()).stream()
                    .min(Comparator.comparing(SetOrdemServico::getData_inclusao))
                    .map(o -> funcionarioService.findById(o.getId_funcionariotecnico()))
                    .map(SetFuncionario::getNome_funcionario)
                    .orElse("N/D");
            html.append("<td>").append(tecnico).append("</td>");

            // Serviços
            Set<Integer> ids = servicosOrcamentoService.listByOrcamento(c.getId_orcamento())
                    .stream().map(SetServicosOrcamento::getId_servico).collect(Collectors.toSet());
            StringJoiner sj = new StringJoiner(", ");
            servicoService.listAll().stream()
                    .filter(s -> ids.contains(s.getId_servico()))
                    .forEach(s -> {
                        String desc = s.getDescricao_servico();
                        if (abreviar) {
                            String abb = Arrays.stream(desc.split(" "))
                                    .map(w -> w.substring(0, 1).toUpperCase())
                                    .collect(Collectors.joining());
                            sj.add(abb);
                        } else {
                            sj.add(desc);
                        }
                    });
            html.append("<td>").append(sj.toString()).append("</td>");

            html.append("</tr>");
        }

        html.append("</tbody></table></body></html>");
        return html.toString();
    }
}
