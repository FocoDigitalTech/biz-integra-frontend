package br.com.onetec.application.views.telas.AtendimentosHistorico;

import br.com.onetec.application.data.Clientes;
import br.com.onetec.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route(value = "atendimentos_historico",layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class AtendimentoHistoricoView extends Div {

    private Clientes cliente;


    private Tabs tabs;
    private Div cadastroOrcamantosDadosFinanceiros;
    private Div cadastroServicosExecutados;
    private Div cadastroOutros;
    private Button saveButton;
    private Button cancelButton;

    // Formulario Orçamento
    private TextField numeroOrcamento;
    private TextField clienteNomeOrcamento;
    private ComboBox localTratamentoOrcamento;
    private TextField problemaOrcamento;
    private DatePicker dataOrcamento;
    private ComboBox atendenteOrcamento;
    private ComboBox situacaoOrcamento;
    private TextField dataInspecaoOrcamento;
    private TimePicker horarioOrcamento;
    private ComboBox consultorOrcamento;
    private ComboBox condicaoOrcamento;
    private TextField garantiaOrcamento;
    private TextField valorOrcamento;
    private Button buttonAdcionar;
    private Button buttonRemover;
    private Button buttonImprimir;
    private final CheckboxGroup<String> servicoOrcamentoChekBox = new CheckboxGroup<>("Serviço");

        private void loadClienteData(Clientes cliente) {
            // Lógica para carregar os dados do cliente usando o objeto cliente
        }

        public AtendimentoHistoricoView() {
            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão
            cliente = (Clientes) UI.getCurrent().getSession().getAttribute("cliente");

            if (cliente != null) {
                loadClienteData(cliente);
            } else {
                // Tratar caso o objeto cliente não esteja presente na sessão
            }

            addClassName("cadastro-modal");
            saveButton = new Button("Salvar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> close());
            tabs = new Tabs();
            Tab tab1 = new Tab("Orçamentos e Dados Financeiros");
            Tab tab2 = new Tab("Serviços Executados");
            Tab tab3 = new Tab("Outros");

            tabs.add(tab1, tab2, tab3);
//            cadastroOrcamantosDadosFinanceiros = createFormCadastroEmpresa();
//            cadastroServicosExecutados = createFormCadastroAgendamento();
//            cadastroOutros = createFormCadastroAprovacaoeCobranca();
//            cadastroEnderecos = createFormEnderecos();

            cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();
            cadastroServicosExecutados = createFormCadastroServicosExecutados();
            cadastroOutros = createFormCadastroOutros();


            Div content = new Div(cadastroOrcamantosDadosFinanceiros,
                    cadastroServicosExecutados, cadastroOutros);
            content.setSizeFull();
            cadastroOrcamantosDadosFinanceiros.setVisible(true);
            cadastroServicosExecutados.setVisible(false);
            cadastroOutros.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroOrcamantosDadosFinanceiros.setVisible(false);
                cadastroServicosExecutados.setVisible(false);
                cadastroOutros.setVisible(false);

                Tab selectedTab = tabs.getSelectedTab();
                if (selectedTab.equals(tab1)) {
                    cadastroOrcamantosDadosFinanceiros.setVisible(true);
                } else if (selectedTab.equals(tab2)) {
                    cadastroServicosExecutados.setVisible(true);
                } else if (selectedTab.equals(tab3)) {
                    cadastroOutros.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroOrcamantosDadosFinanceiros,
                    cadastroServicosExecutados, cadastroOutros);
            contentTabs.setSizeFull();

            VerticalLayout layout = new VerticalLayout(tabs,contentTabs, saveButton, cancelButton);
            add(layout);
        }

    private Div createFormCadastroOutros() {
            return new Div();
    }

    private Div createFormCadastroServicosExecutados() {
            return new Div();
    }

    private Div createFormCadastroOrcamantosDadosFinanceiros() {

        numeroOrcamento = new TextField("N° Orçamento");
        numeroOrcamento.isReadOnly();
        clienteNomeOrcamento = new TextField("Nome Cliente");
        clienteNomeOrcamento.isReadOnly();

        localTratamentoOrcamento = new ComboBox("Local Tratamento");
        problemaOrcamento = new TextField("Problema");
        dataOrcamento = new DatePicker("Data");
        atendenteOrcamento = new ComboBox("Atendente");
        situacaoOrcamento = new ComboBox("Situação");
        dataInspecaoOrcamento = new TextField("Dt Inspeção");
        horarioOrcamento = new TimePicker("Horário");
        consultorOrcamento = new ComboBox("Consultor");
        condicaoOrcamento = new ComboBox("Condição");
        garantiaOrcamento = new TextField("Garantia");
        valorOrcamento = new TextField("Valor Orçamento");

        localTratamentoOrcamento.setWidth("auto");
        atendenteOrcamento.setWidth("auto");
        situacaoOrcamento.setWidth("auto");
        consultorOrcamento.setWidth("auto");
        condicaoOrcamento.setWidth("auto");

        valorOrcamento.setWidth("auto");

        buttonAdcionar = new Button("Adcionar");
        buttonRemover = new Button("Remover");
        buttonImprimir = new Button("Imprimir");

        servicoOrcamentoChekBox.setItems("Cupins", "Insetos Rasteiros", "Roedores", "Vazamentos", "Desobstrução", "Limp.Cx D'água", "Outros");
        servicoOrcamentoChekBox.addClassName("double-width");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        FormLayout formLayout1 = new FormLayout();
        formLayout1.setWidthFull();

        Details details = new Details("Orçamentos e Dados Financeiros", formLayout1);
        details.setOpened(false);

        formLayout.add(numeroOrcamento,clienteNomeOrcamento,localTratamentoOrcamento,problemaOrcamento,dataOrcamento,atendenteOrcamento,situacaoOrcamento,dataInspecaoOrcamento,horarioOrcamento,consultorOrcamento,condicaoOrcamento,garantiaOrcamento,valorOrcamento,buttonAdcionar,buttonRemover,buttonImprimir,servicoOrcamentoChekBox,details);

        Div div = new Div(formLayout);
        div.setSizeFull();



        return div;
    }

    private void close() {
        UI.getCurrent().navigate("clientes");
        removeAll();
    }

    private void save() {
        // Lógica para salvar o cadastro

    }
}
