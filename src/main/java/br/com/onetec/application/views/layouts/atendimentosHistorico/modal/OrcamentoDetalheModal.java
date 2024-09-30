package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.OrcamentoTransiction;
import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@UIScope
public class OrcamentoDetalheModal extends Dialog {

    private SetCliente cliente;
    private Div cadastroOrcamantosDadosFinanceiros;
    private Button saveButton;
    private Button cancelButton;

    // Formulario Orçamento
    private TextField clienteNomeOrcamento;
    private ComboBox<SetEnderecos> localTratamentoOrcamento;
    private TextArea problemaOrcamento;
    private DatePicker dataOrcamento;
    private ComboBox<SetFuncionario> atendenteOrcamento;
    private ComboBox<SetSituacaoCadastro> situacaoOrcamento;
    private DatePicker dataInspecaoOrcamento;
    private TimePicker horarioOrcamento;
    private ComboBox<SetFuncionario> consultorOrcamento;
    private ComboBox<SetCondicaoPagamento> condicaoOrcamento;
    private TextField garantiaOrcamento;
    private TextField valorOrcamento;
    private final CheckboxGroup<SetServico> servicoOrcamentoChekBox = new CheckboxGroup<>("Serviço");
    private UtilitySystemConfigService service;
    private SetOrcamento orcamento;


    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private SituacaoCadastroService situacaoCadastroService;

    @Autowired
    private CondicaoPagamentoService condicaoPagamentoService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private OrcamentoService orcamentoService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;

    private void loadClienteData(SetCliente cliente) {
        // Lógica para carregar os dados do cliente usando o objeto cliente
    }

    public OrcamentoDetalheModal() {
        UI.getCurrent().access(() -> {
            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão
            cliente = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");
            orcamento = (SetOrcamento) UI.getCurrent().getSession().getAttribute("cliente");
            if (cliente != null) {
                loadClienteData(cliente);
            } else {
                // Tratar caso o objeto cliente não esteja presente na sessão
            }

            saveButton = new Button("Salvar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> close());
            cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();

            Div content = new Div(cadastroOrcamantosDadosFinanceiros);
            content.setSizeFull();
            cadastroOrcamantosDadosFinanceiros.setVisible(true);
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(content);
            add(layout);
        });
    }

    private Div createFormCadastroOrcamantosDadosFinanceiros() {

        orcamento = new SetOrcamento();
        orcamento = OrcamentoTransiction.getOrcamento();
        service = new UtilitySystemConfigService();
        clienteNomeOrcamento = new TextField("Nome Cliente");
        clienteNomeOrcamento.isReadOnly();
        clienteNomeOrcamento.setValue(cliente.getNome_cliente());

        List<SetEnderecos> enderecolista = enderecoService.findAllClienteId(cliente.getId_cliente());

        localTratamentoOrcamento = new ComboBox<>("Local Tratamento");
        localTratamentoOrcamento.setItems
                (enderecolista);
        localTratamentoOrcamento.setItemLabelGenerator(SetEnderecos::getEndereco_imovel);
        localTratamentoOrcamento.setValue(enderecolista.stream()
                .filter(renderer -> Objects.equals(renderer.getId_endereco(), orcamento.getId_endereco()))
                .findFirst().orElse(null));

        problemaOrcamento = new TextArea("Problema");
        problemaOrcamento.setValue(orcamento.getDescricao_problema());

        dataOrcamento = new DatePicker("Data");
        service.configuraCalendario(dataOrcamento);
        dataOrcamento.setValue(orcamento.getData_orcamento());

        List<SetFuncionario> atendenteOrcamentoLista = funcionarioService.listAll();
        atendenteOrcamento = new ComboBox<>("Atendente");
        atendenteOrcamento.setItems(funcionarioService.listAll());
        atendenteOrcamento.setItemLabelGenerator(SetFuncionario::getNome_funcionario);
        atendenteOrcamento.setValue(atendenteOrcamentoLista.stream()
                .filter(renderer -> Objects.equals(renderer.getId_funcionario(), orcamento.getId_funcionarioatendimento()))
                .findFirst().orElse(null));

        situacaoOrcamento = new ComboBox<>("Situação");
        situacaoOrcamento.setItems(situacaoCadastroService.listAll());
        situacaoOrcamento.setItemLabelGenerator(SetSituacaoCadastro::getDescricao_situacaocadastro);

        dataInspecaoOrcamento = new DatePicker("Data Inspeção");
        service.configuraCalendario(dataInspecaoOrcamento);
        dataInspecaoOrcamento.setValue(orcamento.getData_inspecao());

        horarioOrcamento = new TimePicker("Horário");
        horarioOrcamento.setValue(orcamento.getHorario_inspecao());

        consultorOrcamento = new ComboBox<>("Consultor");
        consultorOrcamento.setItems(funcionarioService.listAll());
        consultorOrcamento.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        condicaoOrcamento = new ComboBox<>("Condição");
        condicaoOrcamento.setItems(condicaoPagamentoService.listAll());
        condicaoOrcamento.setItemLabelGenerator(SetCondicaoPagamento::getDescricao_condicaopagamento);


        garantiaOrcamento = new TextField("Garantia");
        //garantiaOrcamento.setValue(orcamento.getGarantia_orcamento());

        valorOrcamento = new TextField("Valor Orçamento");
        valorOrcamento.setValueChangeMode(ValueChangeMode.EAGER);
        valorOrcamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valorOrcamento));
        valorOrcamento.setPlaceholder("R$ 0,00");
        //valorOrcamento.setValue(orcamento.getValor_orcamento().toString());

        localTratamentoOrcamento.setWidth("auto");
        atendenteOrcamento.setWidth("auto");
        situacaoOrcamento.setWidth("auto");
        consultorOrcamento.setWidth("auto");
        condicaoOrcamento.setWidth("auto");

        valorOrcamento.setWidth("auto");

        List<String> items = new ArrayList<>();
        servicoService.listAll().forEach(obj -> {
            items.add(obj.getDescricao_servico());
        });
        List<SetServico> teste = servicoService.listAll();

        //servicoOrcamentoChekBox.setItems("Cupins", "Insetos Rasteiros", "Roedores", "Vazamentos", "Desobstrução", "Limp.Cx D'água", "Outros");
        servicoOrcamentoChekBox.setItems(servicoService.listAll());
        servicoOrcamentoChekBox.setItemLabelGenerator(SetServico::getDescricao_servico);
        servicoOrcamentoChekBox.addClassName("double-width");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();


        formLayout.add(clienteNomeOrcamento,localTratamentoOrcamento,problemaOrcamento,
                dataOrcamento,atendenteOrcamento,situacaoOrcamento,dataInspecaoOrcamento,
                horarioOrcamento,consultorOrcamento,condicaoOrcamento,garantiaOrcamento,
                valorOrcamento,servicoOrcamentoChekBox);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }



    private void save() {
        // Lógica para salvar o cadastro
        SetOrcamento dto = new SetOrcamento();

        try {
            if (atendenteOrcamento.getValue() != null) {
                dto.setId_funcionarioatendimento(atendenteOrcamento.getValue().getId_funcionario());
            }
            if (consultorOrcamento.getValue() != null) {
                dto.setId_funcionarioconsultor(consultorOrcamento.getValue().getId_funcionario());
            }
            if (situacaoOrcamento.getValue() != null) {
                dto.setId_situacao(situacaoOrcamento.getValue().getId_situacaocadastro());
            }
            if (condicaoOrcamento.getValue() != null) {
                dto.setId_condicaopagamento(condicaoOrcamento.getValue().getId_condicaopagamento());
            }
            if (localTratamentoOrcamento.getValue() != null) {
                dto.setId_endereco(localTratamentoOrcamento.getValue().getId_endereco());
            }

            dto.setId_cliente(cliente.getId_cliente());
            dto.setDescricao_problema(problemaOrcamento.getValue());
            dto.setData_orcamento(dataOrcamento.getValue());
            dto.setData_inspecao(dataInspecaoOrcamento.getValue());
            dto.setHorario_inspecao(horarioOrcamento.getValue());
            dto.setGarantia_orcamento(garantiaOrcamento.getValue());
            dto.setValor_orcamento(service.getValorBigDecimal(valorOrcamento.getValue()));
            dto.setData_inclusao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            dto.setAtivo("S");
            service = new UtilitySystemConfigService();
            orcamentoService.save(dto);
            orcamentoDiv.refreshGrid();
            localTratamentoOrcamento.clear();
            problemaOrcamento.clear();
            dataOrcamento.clear();
            atendenteOrcamento.clear();
            situacaoOrcamento.clear();
            dataInspecaoOrcamento.clear();
            horarioOrcamento.clear();
            consultorOrcamento.clear();
            condicaoOrcamento.clear();
            garantiaOrcamento.clear();
            valorOrcamento.clear();
            servicoOrcamentoChekBox.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }

    }


}
