package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.lancamentoservice.LancamentoService;
import br.com.onetec.application.service.setoratuacaoservice.SetorAtuacaoService;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.LancamentoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@UIScope
public class LancamentoFinanceiroModal extends Dialog {

    private static SetFuncionario funcionario;
    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    EventoFinanceiroService eventoFinanceiroService;
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    ContaCorrenteService contaCorrenteService;
    @Autowired
    TipoEventoFinanceiroService tipoEventoFinanceiroService;
    @Autowired
    @Lazy
    LancamentoFinanceiroDiv lancamentoFinanceiroDiv;
    @Autowired
    LancamentoService lancamentoService;
    @Autowired
    EstadoService estadoService1;
    @Autowired
    SetorAtuacaoService setorAtuacaoService1;
    UtilitySystemConfigService service;
    private ComboBox<SetEventoFinanceiro> id_eventofinanceiro;
    //private ComboBox<SetContaCorrente> id_contacorrente;
    private ComboBox<SetFornecedor> id_fornecedor;
    private ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro;
    //private ComboBox<> id_tipopagamento;
    private TextField nome_fluxorecebimentopagamento;
    private IntegerField quantidade_parcelas;
    private IntegerField quantidade_intervalo;
    private DatePicker data_lancamento;
    private TextField valor_lancamento;
    //private DatePicker data_contabil;
    //private TextField valor_contabil;
    //private DatePicker data_pagamento;
    private TextField valor_pagamento;
    private TextField numero_documento;
    //private TextField numero_parcela;
    private TextField valor_previsto;
    private DatePicker datahora_lancamento;
    private TextField id_funcionariolancamento;
    private ComboBox<String> status_pagamento;
    private TextArea descricao_lancamento;
    private Button saveButton;
    private Button cancelButton;


    public LancamentoFinanceiroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            H2 title = new H2("Cadastro Lançamento Financeiro");
            getHeader().add(title);
            add(layout);
        });
    }

    @Autowired
    public void initServices(UtilitySystemConfigService service) {
        this.service = service;
        //configurações dos fields:
        UI.getCurrent().access(() -> {
            service.configuraCalendario(data_lancamento);
            //service.configuraCalendario(data_contabil);
            //service.configuraCalendario(data_pagamento);
            service.configuraCalendario(datahora_lancamento);
        });
    }

    private Div createFormCadastroEmpresa() {

        nome_fluxorecebimentopagamento = new TextField("Nome");
        id_eventofinanceiro = new ComboBox<>("SubConta (Evento Financeiro)");
        //id_contacorrente = new ComboBox<>("Conta Corrente");
        id_fornecedor = new ComboBox<>("Fornecedor");
        nome_fluxorecebimentopagamento = new TextField("Nome Lançamento (Histórico)");
        quantidade_parcelas = new IntegerField("Parcelas");
        quantidade_intervalo = new IntegerField("Intervalo de Dias");
        data_lancamento = new DatePicker("Data 1° Vencimento");
        valor_lancamento = new TextField("Valor Lançamento");
        //data_contabil = new DatePicker("Data Contabil");
        //valor_contabil = new TextField("Valor Contabil");
        //data_pagamento = new DatePicker("Data Pagamento");
        valor_pagamento = new TextField("Valor Pagamento");
        numero_documento = new TextField("Numero Documento");
        //numero_parcela = new TextField("Numero Parcela");
        valor_previsto = new TextField("Valor Previsto");
        datahora_lancamento = new DatePicker("Data Lançamento");
        id_funcionariolancamento = new TextField("Responsável Lançamento");
        status_pagamento = new ComboBox<>("Status Atual Pagamento");
        id_tipoeventofinanceiro = new ComboBox<>("Nome Conta (Tipo Evento Financeiro)");
        descricao_lancamento = new TextArea("Descrição");


        id_eventofinanceiro.setRequired(true);
        id_fornecedor.setRequired(true);
        id_tipoeventofinanceiro.setRequired(true);
        nome_fluxorecebimentopagamento.setRequired(true);
        quantidade_parcelas.setRequired(true);
        quantidade_intervalo.setRequired(true);
        valor_lancamento.setRequired(true);
        status_pagamento.setRequired(true);
        data_lancamento.setRequired(true);

        //Configura campos obrigatórios
        UtilitySystemConfigService configService = new UtilitySystemConfigService();

        configService.setRequiredField(id_tipoeventofinanceiro);
        configService.setRequiredField(id_eventofinanceiro);
        configService.setRequiredField(id_fornecedor);
        configService.setRequiredField(nome_fluxorecebimentopagamento);
        configService.setRequiredField(quantidade_parcelas);
        configService.setRequiredField(quantidade_intervalo);
        configService.setRequiredField(valor_lancamento);
        configService.setRequiredField(status_pagamento);
        configService.setRequiredField(data_lancamento);


        quantidade_parcelas.setStepButtonsVisible(true);
        quantidade_parcelas.setMin(1);
        quantidade_intervalo.setStepButtonsVisible(true);
        quantidade_intervalo.setMin(1);

        status_pagamento.setItems(List.of("Previsão (P)", "Real (R)"));

        datahora_lancamento.setValue(LocalDate.now());
        datahora_lancamento.setReadOnly(true);

        valor_lancamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_lancamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_lancamento));
        valor_lancamento.setPlaceholder("R$ 0,00");

        /*valor_contabil.setValueChangeMode(ValueChangeMode.EAGER);
        valor_contabil.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_contabil));
        valor_contabil.setPlaceholder("R$ 0,00");*/

        valor_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_pagamento));
        valor_pagamento.setPlaceholder("R$ 0,00");

        valor_previsto.setValueChangeMode(ValueChangeMode.EAGER);
        valor_previsto.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_previsto));
        valor_previsto.setPlaceholder("R$ 0,00");


        id_eventofinanceiro.setItemLabelGenerator(SetEventoFinanceiro::getNome_eventofinanceiro);
        HorizontalLayout eventofinanceirolayout =
                new CustomizedComboBox().customizeEventoFinanceiro(id_eventofinanceiro, eventoFinanceiroService,
                        tipoEventoFinanceiroService);

        /*id_contacorrente.setItems(contaCorrenteService.findAll());
        id_contacorrente.setItemLabelGenerator(SetContaCorrente::getNome_contacorrente);
        HorizontalLayout contacorrentelayout =
                new CustomizedComboBox().customizeContaCorrente(id_contacorrente,contaCorrenteService);*/

        id_fornecedor.setItems(fornecedorService.findAll());
        id_fornecedor.setItemLabelGenerator(SetFornecedor::getNomefantasia_fornecedor);
        HorizontalLayout fornecedorlayout =
                new CustomizedComboBox().customizeFornecedor
                        (id_fornecedor, fornecedorService, estadoService1, setorAtuacaoService1);


        id_tipoeventofinanceiro.setItems(tipoEventoFinanceiroService.findAll());
        id_tipoeventofinanceiro.setItemLabelGenerator(SetTipoEventoFinanceiro::getNome_tipoeventofinanceiro);
        HorizontalLayout tipoeventofinanceirolayout =
                new CustomizedComboBox().customizeTipoEventoFinanceiro(id_tipoeventofinanceiro, tipoEventoFinanceiroService);
        id_tipoeventofinanceiro.addValueChangeListener(event -> {
            SetTipoEventoFinanceiro selecionado = event.getValue();
            if (selecionado != null) {
                id_eventofinanceiro.setItems(eventoFinanceiroService.
                        findByIdTipoEventoFinanceiro(selecionado.getId_tipoeventofinanceiro()));
            }
        });

        //id_funcionariolancamento.setEnabled(false);
        funcionario = funcionarioService.findById(UsuarioAutenticadoConfig.getUser().getId_funcionario());
        id_funcionariolancamento.setReadOnly(true);
        if (Objects.isNull(funcionario.getNome_funcionario())) {
            id_funcionariolancamento.setValue("Usuário sem funcionario associado");
        } else {
            id_funcionariolancamento.setValue(funcionario.getNome_funcionario());
        }


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(tipoeventofinanceirolayout,
                eventofinanceirolayout, status_pagamento,
                fornecedorlayout,
                nome_fluxorecebimentopagamento,
                quantidade_parcelas,
                descricao_lancamento,
                quantidade_intervalo,
                data_lancamento,
                valor_lancamento,
                valor_pagamento,
                numero_documento,
                valor_previsto,
                datahora_lancamento,
                id_funcionariolancamento);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void save() throws Exception {
        try {

            if (Objects.isNull(id_tipoeventofinanceiro.getValue()) ||
                    Objects.isNull(id_eventofinanceiro.getValue()) ||
                    Objects.isNull(id_fornecedor.getValue()) ||
                    Objects.isNull(quantidade_parcelas.getValue()) ||
                    Objects.isNull(quantidade_intervalo.getValue()) ||
                    Objects.isNull(valor_lancamento.getValue()) ||
                    Objects.isNull(status_pagamento.getValue()) ||
                    Objects.isNull(data_lancamento.getValue()) ||
                    Objects.isNull(nome_fluxorecebimentopagamento.getValue())) {
                service.notificaErro(ModalMessageConst.FIELD_ERROR);

            } else {

                if (quantidade_parcelas.getValue() > 0) {
                    LocalDate datacorrente = data_lancamento.getValue();
                    int parcela = 1;
                    for (int i = 0; i < quantidade_parcelas.getValue(); i++) {
                        service = new UtilitySystemConfigService();

                        SetFluxoRecebimentoPagamento dto = new SetFluxoRecebimentoPagamento();

                        if (Objects.nonNull(id_eventofinanceiro.getValue().getId_eventofinanceiro())) {
                            dto.setId_eventofinanceiro(id_eventofinanceiro.getValue().getId_eventofinanceiro());
                        }
                        if (Objects.nonNull(id_tipoeventofinanceiro.getValue().getId_tipoeventofinanceiro())) {
                            dto.setId_tipoeventofinanceiro(id_tipoeventofinanceiro.getValue().getId_tipoeventofinanceiro());
                        }
                        if (Objects.nonNull(status_pagamento.getValue())) {
                            dto.setStatus_pagamento(status_pagamento.getValue());
                        }
                        /*if (Objects.nonNull(id_contacorrente.getValue().getId_contacorrente())) {
                            dto.setId_contacorrente(id_contacorrente.getValue().getId_contacorrente());
                        }  */
                        if (id_fornecedor.getValue() != null) {
                            dto.setId_fornecedor(id_fornecedor.getValue().getId_fornecedor());
                        }
                        if (Objects.nonNull(funcionario)) {
                            dto.setId_funcionariolancamento(funcionario.getId_funcionario());
                        }
                        dto.setData_vencimento(datacorrente);
                        datacorrente = datacorrente.plusDays(quantidade_intervalo.getValue());

                        dto.setNome_fluxorecebimentopagamento(nome_fluxorecebimentopagamento.getValue());
                        dto.setQuantidade_parcelas(String.valueOf(quantidade_parcelas.getValue()));
                        dto.setQuantidade_intervalo(String.valueOf(quantidade_intervalo.getValue()));
                        dto.setData_lancamento(data_lancamento.getValue().atStartOfDay());
                        dto.setValor_lancamento(service.getValorBigDecimal(valor_lancamento.getValue()));
                        dto.setNumero_parcela(String.valueOf(parcela));
                        //dto.setData_contabil(data_contabil.getValue().atStartOfDay());
                        /*dto.setValor_contabil(service.getValorBigDecimal(valor_contabil.getValue()));
                        dto.setData_pagamento(data_pagamento.getValue().atStartOfDay());*/
                        dto.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
                        dto.setNumero_documento(numero_documento.getValue());
                        //dto.setNumero_parcela(numero_parcela.getValue());
                        dto.setValor_previsto(service.getValorBigDecimal(valor_previsto.getValue()));
                        dto.setDescricao_fluxorecebimentopagamento(descricao_lancamento.getValue());
                        dto.setDatahora_lancamento(LocalDateTime.now());
                        dto.setAtivo("S");
                        dto.setData_inclusao(LocalDateTime.now());
                        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                        service = new UtilitySystemConfigService();
                        lancamentoService.save(dto);
                        parcela++;
                    }
                    lancamentoFinanceiroDiv.refreshGrid();
                    id_eventofinanceiro.clear();
                    //id_contacorrente.clear();
                    id_fornecedor.clear();
                    nome_fluxorecebimentopagamento.clear();
                    quantidade_parcelas.clear();
                    quantidade_intervalo.clear();
                    data_lancamento.clear();
                    valor_lancamento.clear();
                    //data_contabil.clear();
                    /*valor_contabil.clear();
                    data_pagamento.clear();*/
                    valor_pagamento.clear();
                    numero_documento.clear();
                    //numero_parcela.clear();
                    valor_previsto.clear();
                    datahora_lancamento.clear();
                    id_funcionariolancamento.clear();
                    service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
                    close();
                }
            }
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}
