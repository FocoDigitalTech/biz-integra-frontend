package br.com.onetec.application.views.main.home;

import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import jakarta.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {
    private final DashboardElement dashboard = new DashboardElement();
    private final OrdemServicoService ordemServicoService;
    private final OrcamentoService orcamentoService;
    private final ClientesService clientesService;
    private final FuncionarioService funcionarioService;
    private final EnderecoService enderecoService;

    public HomeView(OrdemServicoService os, OrcamentoService orcamento, ClientesService clientes,
                    FuncionarioService funcionarios, EnderecoService enderecos) {
        this.ordemServicoService = os;
        this.orcamentoService = orcamento;
        this.clientesService = clientes;
        this.funcionarioService = funcionarios;
        this.enderecoService = enderecos;
        setSizeFull(); setPadding(false); setSpacing(false); add(dashboard);
    }

    @Override protected void onAttach(AttachEvent event) { super.onAttach(event); dashboard.setData(loadData()); }

    private JsonObject loadData() {
        LocalDate today = LocalDate.now();
        List<SetOrdemServico> orders = ordemServicoService.findAll().stream()
                .filter(x -> today.equals(x.getDatainicio_ordemservico())).toList();
        List<SetOrcamento> inspections = orcamentoService.findAllBySituacaoId(1).stream()
                .filter(x -> today.equals(x.getData_inspecao())).toList();
        JsonObject root = Json.createObject();
        root.put("dateLabel", today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JsonArray teams = Json.createArray(); int teamIndex = 0;

        for (SetFuncionario technician : funcionarioService.listAll()) {
            List<SetOrdemServico> teamOrders = orders.stream().filter(x -> Objects.equals(technician.getId_funcionario(), x.getId_funcionariotecnico())).toList();
            List<SetOrcamento> teamInspections = inspections.stream().filter(x -> Objects.equals(technician.getId_funcionario(), x.getId_funcionarioinspecao())).toList();
            if (teamOrders.isEmpty() && teamInspections.isEmpty()) continue;
            String assistantName = teamOrders.stream().findFirst().map(x -> funcionarioService.findById(x.getId_funcionarioassistente()))
                    .map(SetFuncionario::getNome_funcionario).orElse("Equipe de apoio");
            JsonObject team = Json.createObject(); team.put("title", safe(technician.getNome_funcionario()) + " e " + safe(assistantName));
            JsonArray appointments = Json.createArray(); int itemIndex = 0;
            for (SetOrdemServico order : teamOrders) appointments.set(itemIndex++, orderJson(order));
            for (SetOrcamento inspection : teamInspections) appointments.set(itemIndex++, inspectionJson(inspection));
            team.put("appointments", appointments); teams.set(teamIndex++, team);
        }
        root.put("teams", teams); return root;
    }

    private JsonObject orderJson(SetOrdemServico x) {
        SetCliente client = clientesService.findById(x.getId_cliente()); SetEnderecos address = enderecoService.findById(x.getId_endereco());
        return appointment("OS", x.getId_ordemservico(), String.valueOf(x.getHorarioinicio_ordemservico()),
                client == null ? null : client.getNome_cliente(), x.getNome_pontofocal(), x.getOcorrencias_ordemservico(), address);
    }

    private JsonObject inspectionJson(SetOrcamento x) {
        SetCliente client = clientesService.findById(x.getId_cliente()); SetEnderecos address = enderecoService.findById(x.getId_endereco());
        return appointment("INSPEÇÃO", x.getId_orcamento(), String.valueOf(x.getHorario_inspecao()), client == null ? null : client.getNome_cliente(),
                client == null ? null : client.getResponsavel_cliente(), x.getDescricao_problema(), address);
    }

    private JsonObject appointment(String type, Object number, String time, String customer, String contact, String description, SetEnderecos address) {
        JsonObject json = Json.createObject(); json.put("type", type); json.put("number", String.valueOf(number)); json.put("time", safe(time));
        json.put("customer", safe(customer)); json.put("contact", safe(contact)); json.put("description", safe(description));
        json.put("address", address == null ? "" : safe(address.getEnderecoImovel()));
        // Se a entidade possuir latitude/longitude, inclua-as aqui. O JS só cria marcadores para coordenadas válidas.
        return json;
    }
    private String safe(Object value) { return value == null ? "" : value.toString(); }

    @Tag("home-dashboard") @JsModule("./components/home-dashboard.js")
    @NpmPackage(value = "leaflet", version = "1.9.4")
    public static class DashboardElement extends com.vaadin.flow.component.Component {
        void setData(JsonObject value) { getElement().setPropertyJson("data", value); }
    }
}

