package br.com.onetec.application.views.main.relatorios.service;

import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.nimbusds.jose.shaded.gson.Gson;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MidiaPrintExportService {

    private final OrcamentoService orcamentoService;
    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final ClientesService clientesService;
    private final OrdemServicoService ordemServicoService;
    private final ServicosOrcamentoService servicosOrcamentoService;
    private final ServicoService servicoService;
    private final EnderecoService enderecoService;
    private final SituacaoCadastroService situacaoService;
    private boolean abreviaverificacao = false;

    @Autowired
    public MidiaPrintExportService(OrcamentoService orcamentoService1, ContratoService contratoService1,
                                   FuncionarioService funcionarioService1, ClientesService clientesService1,
                                   OrdemServicoService ordemServicoService1,
                                   ServicosOrcamentoService servicosOrcamentoService1, ServicoService servicoService1, EnderecoService enderecoService, SituacaoCadastroService situacaoService) {
        this.orcamentoService = orcamentoService1;
        this.contratoService = contratoService1;
        this.funcionarioService = funcionarioService1;
        this.clientesService = clientesService1;
        this.ordemServicoService = ordemServicoService1;
        this.servicosOrcamentoService = servicosOrcamentoService1;
        this.servicoService = servicoService1;
        this.enderecoService = enderecoService;
        this.situacaoService = situacaoService;
    }

    public void imprimirRelatorio(Grid<SetOrcamento> grid, Checkbox abreviarCheckbox)  {

        // 1) coleta o que está filtrado
        List<SetOrcamento> contratos = grid.getGenericDataView()
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

    private String montarHtmlDeImpressao(List<SetOrcamento> contratos, boolean abreviar) {
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
                .append("<h2>Relatório de Mídias</h2>")
                .append("<table><thead><tr>")
                .append("<th>Cliente</th><th>Cidade</th><th>Bairro</th>")
                .append("<th>Numero Proposta</th><th>Data</th><th>Serviços</th>")
                .append("<th>Negociação</th><th>Hora Ligação</th>")
                .append("</tr></thead><tbody>");

        for (SetOrcamento c : contratos) {
            html.append("<tr>");
            // Nome cliente
            SetCliente cl = clientesService.findById(c.getId_cliente());
            html.append("<td>")
                    .append(cl != null ? cl.getNome_cliente() : "")
                    .append("</td>");
            // Cidade
            SetEnderecos ed = enderecoService.findById(c.getId_endereco());
            html.append("<td>")
                    .append(cl != null ? ed.getCidade_imovel() : "")
                    .append("</td>");
            // bairro
            html.append("<td>")
                    .append(cl != null ? ed.getBairro_imovel() : "")
                    .append("</td>");
            html.append("<td>").append(c.getId_orcamento()).append("</td>");
            // Data formatada
            String data = c.getData_orcamento() != null
                    ? UtilitySystemConfigService.getDataFormatada(c.getData_orcamento().atStartOfDay()).toString()
                    : "";
            html.append("<td>").append(data).append("</td>");
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
            // Tipo
            SetSituacaoCadastro tipo = situacaoService.fidById(c.getId_situacao());
            html.append("<td>").append(tipo.getDescricao_situacaocadastro()).append("</td>");

            // Horário
            html.append("<td>").append(c.getHorario_inspecao()).append("</td>");



            html.append("</tr>");
        }

        html.append("</tbody></table></body></html>");
        return html.toString();
    }


    private String montarHtmlDeGrafico(List<SetOrcamento> contratos) {
        Map<SetSituacaoCadastro, Map<String, Integer>> resultado = new LinkedHashMap<>();

        for (SetOrcamento orc : contratos) {
            SetSituacaoCadastro situacao = situacaoService.fidById(orc.getId_situacao());

            // IDs dos serviços deste orçamento
            Set<Integer> ids = servicosOrcamentoService.listByOrcamento(orc.getId_orcamento())
                    .stream().map(SetServicosOrcamento::getId_servico).collect(Collectors.toSet());

            // Combinação textual dos serviços (ordenada, para padronizar)
            List<String> nomesServicos = servicoService.listAll().stream()
                    .filter(s -> ids.contains(s.getId_servico()))
                    .map(SetServico::getDescricao_servico)
                    .sorted()
                    .collect(Collectors.toList());

            String combinacao = String.join(", ", nomesServicos);

            resultado.computeIfAbsent(situacao, k -> new LinkedHashMap<>());
            Map<String, Integer> mapaInterno = resultado.get(situacao);
            mapaInterno.put(combinacao, mapaInterno.getOrDefault(combinacao, 0) + 1);
        }

        // Gerar HTML com tabela e gráfico
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='utf-8'>")
                .append("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>")
                .append("<style>body{font-family:sans-serif;padding:20px} table{border-collapse:collapse;width:100%}th,td{border:1px solid #ccc;padding:6px}</style>")
                .append("</head><body><h1>Relatório Resumido</h1>");

        List<String> chartLabels = new ArrayList<>();
        List<Integer> chartData = new ArrayList<>();

        for (Map.Entry<SetSituacaoCadastro, Map<String, Integer>> entry : resultado.entrySet()) {
            SetSituacaoCadastro situacao = entry.getKey();
            Map<String, Integer> combinacoes = entry.getValue();

            int total = combinacoes.values().stream().mapToInt(i -> i).sum();

            // Tabela com combinações (mantida como está)
            html.append("<h3>").append(total).append(" ").append(situacao.getDescricao_situacaocadastro()).append("</h3><ul>");
            for (Map.Entry<String, Integer> combo : combinacoes.entrySet()) {
                html.append("<li>").append(combo.getKey())
                        .append(" = ").append(String.format("%02d", combo.getValue()))
                        .append("</li>");
            }
            html.append("</ul>");

            // Gráfico: só adiciona uma vez por situação
            chartLabels.add(situacao.getDescricao_situacaocadastro());
            chartData.add(total);
        }

        // Gráfico
        html.append("<canvas id='grafico' width='800' height='400'></canvas>")
                .append("<script>")
                .append("const ctx = document.getElementById('grafico').getContext('2d');")
                .append("new Chart(ctx, {type:'bar', data:{labels:")
                .append(new Gson().toJson(chartLabels)) // use Gson ou monte o JSON manualmente
                .append(", datasets:[{label:'Orçamentos por Situação', data:")
                .append(chartData.toString())
                .append(", backgroundColor:'rgba(54, 162, 235, 0.6)'}]}, options:{indexAxis:'y'}});")
                .append("</script>");

        html.append("</body></html>");

        // NOVO BLOCO: Contagem por Serviço

            // Map para contar serviços
        Map<SetServico, Integer> contagemServicos = new LinkedHashMap<>();

        for (SetOrcamento orc : contratos) {
            // IDs dos serviços deste orçamento
            Set<Integer> ids = servicosOrcamentoService.listByOrcamento(orc.getId_orcamento())
                    .stream().map(SetServicosOrcamento::getId_servico).collect(Collectors.toSet());

            // Para cada id, atualiza a contagem
            for (SetServico servico : servicoService.listAll()) {
                if (ids.contains(servico.getId_servico())) {
                    contagemServicos.put(servico, contagemServicos.getOrDefault(servico, 0) + 1);
                }
            }
        }

        // Título da seção
        html.append("<hr><h2>Gráfico e Detalhes por Serviço</h2><ul>");

        List<String> chartLabelsServicos = new ArrayList<>();
        List<Integer> chartDataServicos = new ArrayList<>();

        for (Map.Entry<SetServico, Integer> entry : contagemServicos.entrySet()) {
            String nome = entry.getKey().getDescricao_servico();
            Integer count = entry.getValue();
            html.append("<li>").append(nome)
                    .append(" = ").append(String.format("%02d", count))
                    .append("</li>");

            chartLabelsServicos.add(nome);
            chartDataServicos.add(count);
        }
        html.append("</ul>");

            // Segundo gráfico
        html.append("<canvas id='graficoServicos' width='800' height='400'></canvas>")
                .append("<script>")
                .append("const ctx2 = document.getElementById('graficoServicos').getContext('2d');")
                .append("new Chart(ctx2, {type:'bar', data:{labels:")
                .append(new Gson().toJson(chartLabelsServicos))
                .append(", datasets:[{label:'Ocorrências por Serviço', data:")
                .append(chartDataServicos.toString())
                .append(", backgroundColor:'rgba(255, 99, 132, 0.6)'}]}, options:{indexAxis:'y'}});")
                .append("</script>");
        return html.toString();
    }

    public void imprimirGrafico(Grid<SetOrcamento> grid, Checkbox abreviarCheckbox)  {

        // 1) coleta o que está filtrado
        List<SetOrcamento> contratos = grid.getGenericDataView()
                .getItems()
                .collect(Collectors.toList());
        boolean abreviar = abreviarCheckbox.getValue();

        // 2) monta o HTML
        String html = montarHtmlDeGrafico(contratos);

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
}