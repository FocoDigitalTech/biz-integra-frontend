package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
@UIScope
public class OrdemServicoDadosModal extends Dialog {

    SetOrdemServico ordemServico;

    private Div cadastroOrcamantosDadosFinanceiros;
    private Button saveButton;
    private Button cancelButton;

    // Formulario
    private ComboBox<SetOrcamento> id_orcamento;
    private ComboBox<SetSituacaoCadastro> id_situacaoservico;
    private DatePicker datainicio_ordemservico;
    private TextField diasemanainicio_ordemservico;
    private TimePicker horarioinicio_ordemservico;
    private TextField quantidade_ordemservico;
    private TextField intervalo_ordemservico;
    private TextField nome_pontofocal;
    private ComboBox<SetFuncionario> id_funcionarioassistente;
    private ComboBox<SetFuncionario> id_funcionariotecnico;
    private TextArea ocorrencias_ordemservico;
    private ComboBox<SetEnderecos> localTratamentoOrcamento;
    final CheckboxGroup<String> confirmado_ordemservico = new CheckboxGroup<>("Confirmado ?");

    private UtilitySystemConfigService service;


    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private SituacaoCadastroService situacaoCadastroService;

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private OrcamentoService orcamentoService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;


    @Autowired
    public void initServices (){
    }

    public OrdemServicoDadosModal() {
        UI.getCurrent().access(() -> {
            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão


            saveButton = new Button("Salvar", eventbe -> save());
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

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

        service = new UtilitySystemConfigService();


        localTratamentoOrcamento = new ComboBox<>("Local Tratamento");
        localTratamentoOrcamento.setItems
                (enderecoService.findAllClienteId(1));
        localTratamentoOrcamento.setItemLabelGenerator(SetEnderecos::getEndereco_imovel);

        id_orcamento = new ComboBox<>("Id Orçamento");
        id_orcamento.setItems
                (orcamentoService.findAllClienteId(1));
        id_orcamento.setItemLabelGenerator(item -> item.getId_orcamento().toString());

        id_situacaoservico = new ComboBox<>("Situação");
        id_situacaoservico.setItems
                (situacaoCadastroService.listAll());
        id_situacaoservico.setItemLabelGenerator(SetSituacaoCadastro::getDescricao_situacaocadastro);

        datainicio_ordemservico = new DatePicker("Data Inicio");
        service.configuraCalendario(datainicio_ordemservico);


        diasemanainicio_ordemservico = new TextField("Dia da Semana");
        diasemanainicio_ordemservico.isReadOnly();
        datainicio_ordemservico.addValueChangeListener(event -> {
            DayOfWeek a = event.getValue().getDayOfWeek();
            diasemanainicio_ordemservico.setValue(a.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        });

        horarioinicio_ordemservico = new TimePicker("Horario");
        quantidade_ordemservico = new TextField("Quantidade");
        intervalo_ordemservico = new TextField("Intervalo");
        nome_pontofocal = new TextField("Responsável no Local");

        id_funcionarioassistente = new ComboBox<>("Assistente");
        id_funcionarioassistente.setItems
                (funcionarioService.listAll());
        id_funcionarioassistente.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        id_funcionariotecnico = new ComboBox<>("Técnico");
        id_funcionariotecnico.setItems
                (funcionarioService.listAll());
        id_funcionariotecnico.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        ocorrencias_ordemservico = new TextArea("Ocorrências");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        confirmado_ordemservico.setItems(List.of("SIM","NÃO"));

        formLayout.add(id_orcamento,localTratamentoOrcamento,
                id_situacaoservico,
                datainicio_ordemservico,
                diasemanainicio_ordemservico,
                horarioinicio_ordemservico,
                quantidade_ordemservico,
                intervalo_ordemservico,
                nome_pontofocal,
                id_funcionarioassistente,id_funcionariotecnico,
                confirmado_ordemservico,
                ocorrencias_ordemservico);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }



    private void save() {
        // Lógica para salvar o cadastro
        SetOrdemServico dto = new SetOrdemServico();

        try {
            if (id_orcamento.getValue() != null) {
                dto.setId_orcamento(id_orcamento.getValue().getId_orcamento());
            }
            if (id_funcionarioassistente.getValue() != null) {
                dto.setId_funcionarioassistente(id_funcionarioassistente.getValue().getId_funcionario());
            }
            if (id_situacaoservico.getValue() != null) {
                dto.setId_situacaoservico(id_situacaoservico.getValue().getId_situacaocadastro());
            }

            dto.setId_cliente(1);
            dto.setOcorrencias_ordemservico(ocorrencias_ordemservico.getValue());
            dto.setDatainicio_ordemservico(datainicio_ordemservico.getValue());
            dto.setHorarioinicio_ordemservico(horarioinicio_ordemservico.getValue());
            //dto.setConfirmado_ordemservico(confirmado_ordemservico.getValue().getClass);
            //dto.setDiasemanainicio_ordemservico(diasemanainicio_ordemservico.getValue());
            dto.setNome_pontofocal(nome_pontofocal.getValue());
            dto.setQuantidade_ordemservico(quantidade_ordemservico.getValue());
            dto.setIntervalo_ordemservico(intervalo_ordemservico.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            dto.setAtivo("S");
            service = new UtilitySystemConfigService();
            ordemServicoService.save(dto);
            orcamentoDiv.refreshGrid();
            localTratamentoOrcamento.clear();
            id_orcamento.clear();
            id_situacaoservico.clear();
            datainicio_ordemservico.clear();
            //diasemanainicio_ordemservico.clear();
//            horarioinicio_ordemservico.clear();
//            quantidade_ordemservico.clear();
//            intervalo_ordemservico.clear();
//            nome_pontofocal.clear();
//            id_funcionarioassistente.clear();
//            confirmado_ordemservico.clear();
//            ocorrencias_ordemservico.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
            System.out.println(e.getMessage().toString());
        }

    }

    public void setOrdemServico(SetOrdemServico item) {
    this.ordemServico = item;

        List<SetOrcamento> listorcamento = orcamentoService.findAllClienteId(ordemServico.getId_cliente());
        List<SetSituacaoCadastro> listasituacao = situacaoCadastroService.listAll();
        List<SetFuncionario> listafuncionario = funcionarioService.listAll();
        id_orcamento.setValue(listorcamento.stream()
                .filter(midia -> midia.getId_orcamento().equals(ordemServico.getId_orcamento()))
                .findFirst().orElse(null));

        id_situacaoservico.setValue(listasituacao.stream()
                .filter(midia -> midia.getId_situacaocadastro().equals(ordemServico.getId_situacaoservico()))
                .findFirst().orElse(null));
        id_funcionarioassistente.setValue(listafuncionario.stream()
                .filter(midia -> midia.getId_funcionario().equals(ordemServico.getId_funcionarioassistente()))
                .findFirst().orElse(null));
        id_funcionariotecnico.setValue(listafuncionario.stream()
                .filter(midia -> midia.getNome_funcionario().equals(ordemServico.getId_funcionariotecnico()))
                .findFirst().orElse(null));

        datainicio_ordemservico.setValue(ordemServico.getDatainicio_ordemservico());
        diasemanainicio_ordemservico.setValue(ordemServico.getDiasemanainicio_ordemservico());
        horarioinicio_ordemservico.setValue(ordemServico.getHorarioinicio_ordemservico());
        quantidade_ordemservico.setValue(ordemServico.getQuantidade_ordemservico());
        intervalo_ordemservico.setValue(ordemServico.getIntervalo_ordemservico());
        nome_pontofocal.setValue(ordemServico.getNome_pontofocal());
        ocorrencias_ordemservico.setValue(ordemServico.getOcorrencias_ordemservico());
        //localTratamentoOrcamento.setValue(ordemServico.getId_endereco());
        //confirmado_ordemservico.setValue(ordemServico.getConfirmado_ordemservico());

    }
}