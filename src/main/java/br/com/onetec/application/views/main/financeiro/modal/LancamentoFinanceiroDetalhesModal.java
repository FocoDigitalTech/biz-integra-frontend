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
import br.com.onetec.cross.constants.FinanceiroDataConst;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@UIScope
public class LancamentoFinanceiroDetalhesModal extends Dialog {

    private ComboBox<SetEventoFinanceiro> id_eventofinanceiro;
    private ComboBox<SetFornecedor> id_fornecedor;
    //private ComboBox<> id_tipopagamento;
    private TextField nome_fluxorecebimentopagamento;
    private DatePicker data_lancamento;
    private TextField valor_lancamento;
    private DatePicker data_pagamento;
    private TextField valor_pagamento;
    private TextField numero_documento;
    private TextField numero_parcela;
    private TextField valor_previsto;
    private DatePicker datahora_lancamento;
    private TextField id_funcionariolancamento;
    private ComboBox<String> status_pagamento;
    private TextArea descricao_lancamento;

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

    private Button saveButton;
    private Button baixaButton;
    private Button cancelButton;
    private Button excluirButton;

    private SetFluxoRecebimentoPagamento fluxoRecebimentoPagamento;

    @Autowired
    public void initServices( UtilitySystemConfigService service) {
        this.service = service;
        //configurações dos fields:
        UI.getCurrent().access(() -> {
            service.configuraCalendario(data_lancamento);
            service.configuraCalendario(data_pagamento);
            service.configuraCalendario(datahora_lancamento);
        });
    }



    public LancamentoFinanceiroDetalhesModal() {
        UI.getCurrent().access(() -> {
            service = new UtilitySystemConfigService();
            saveButton = new com.vaadin.flow.component.button.Button("Atualizar", eventbe -> {
                try {
                    save();}
                catch (Exception e) {
                    service.notificaErro(ModalMessageConst.ERROR_CREATE);
                }
            });
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            excluirButton = new Button("Excluir", event -> {
                deleta(fluxoRecebimentoPagamento);
            });
            excluirButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            baixaButton = new Button("Baixar Pagamento", event -> {
                Dialog modalBaixaPagamento = carregaBaixaPagamento();
                modalBaixaPagamento.open();
            });
            baixaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);;
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(baixaButton,excluirButton, saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            H2 title = new H2("Lançamento Financeiro");
            getHeader().add(title);
            add(layout);
        });
    }

    private Dialog carregaBaixaPagamento() {
        service = new UtilitySystemConfigService();
        if (fluxoRecebimentoPagamento.getStatus_pagamento().equals(FinanceiroDataConst.STATUS_CONSOLIDADO.getValor())){
            Dialog modalPagamento = new Dialog();
            TextField valorLancado = new TextField("Valor do Lançamento");
            DatePicker dataVencimentoParcela = new DatePicker("Vencimento Pagamento");
            DatePicker dataPagamentomodal = new DatePicker("Data Baixa");
            TextField valorPagamentoModal = new TextField("Valor Baixa");

            dataVencimentoParcela.isReadOnly();
            dataPagamentomodal.isReadOnly();
            valorPagamentoModal.isReadOnly();
            valorLancado.isReadOnly();

            service.formataMoedaBrasileira(valorLancado);
            service.configuraCalendario(dataVencimentoParcela);
            service.configuraCalendario(dataPagamentomodal);

            valorLancado.setValue(fluxoRecebimentoPagamento.getValor_lancamento().toString());  // Valor inicial sem máscara

            service.formataMoedaBrasileira(valorLancado);  // Formatar como moeda brasileira

            dataPagamentomodal.setValue(fluxoRecebimentoPagamento.getDatahora_baixa().toLocalDate());

            valorPagamentoModal.setValue(fluxoRecebimentoPagamento.getValor_baixa().toString());
            service.formataMoedaBrasileira(valorPagamentoModal);

            dataVencimentoParcela.setValue(fluxoRecebimentoPagamento.getData_vencimento());

            valorLancado.setValueChangeMode(ValueChangeMode.EAGER);
            valorLancado.setPlaceholder("R$ 0,00");

            valorPagamentoModal.setValueChangeMode(ValueChangeMode.EAGER);
            valorPagamentoModal.setPlaceholder("R$ 0,00");

            // Botões de ação
            Button pagamentoButon = new Button("Cancelar Baixa ?", event -> {
                try {
                    BigDecimal valorTots = service.getValorBigDecimal(valorPagamentoModal.getValue());  // Converter o valor total para BigDecimal
                    fluxoRecebimentoPagamento.setStatus_pagamento(FinanceiroDataConst.STATUS_REAL.getValor());
                    fluxoRecebimentoPagamento.setData_pagamento(null);
                    fluxoRecebimentoPagamento.setValor_pagamento(null);
                    fluxoRecebimentoPagamento.setDatahora_baixa(null);
                    fluxoRecebimentoPagamento.setValor_baixa(null);
                    fluxoRecebimentoPagamento.setId_funcionariobaixa(UsuarioAutenticadoConfig.getUser().getId_funcionario());
                    fluxoRecebimentoPagamento.setData_alteracao(LocalDateTime.now());
                    lancamentoService.update(fluxoRecebimentoPagamento);
                    service.notificaSucesso("Cancelado com sucesso !");
                    lancamentoFinanceiroDiv.refreshGrid();
                    close();
                    modalPagamento.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    service.notificaErro(ModalMessageConst.ERROR_CREATE);
                    modalPagamento.close();
                }
            });
            pagamentoButon.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Button cancelar = new Button("Cancelar", event -> service.askForConfirmation(modalPagamento));
            cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
            VerticalLayout layout = new VerticalLayout(valorLancado,
                    dataVencimentoParcela,
                    dataPagamentomodal,
                    valorPagamentoModal);
            modalPagamento.setHeaderTitle("Baixa de Pagamento");
            modalPagamento.add(layout);
            modalPagamento.getFooter().add(pagamentoButon, cancelar);
            return modalPagamento;

        } else {
            Dialog modalPagamento = new Dialog();
            TextField valorLancado = new TextField("Valor do Lançamento");
            valorLancado.isReadOnly();
            DatePicker dataVencimentoParcela = new DatePicker("Vencimento Pagamento");
            dataVencimentoParcela.isReadOnly();
            DatePicker dataPagamentomodal = new DatePicker("Data Pagamento (Baixa)");
            TextField valorPagamentoModal = new TextField("Valor Pagamento (Baixa)");
            service.formataMoedaBrasileira(valorLancado);

            service.configuraCalendario(dataVencimentoParcela);
            service.configuraCalendario(dataPagamentomodal);

            valorLancado.setValue(valor_lancamento.getValue());  // Valor inicial sem máscara

            service.formataMoedaBrasileira(valorLancado);  // Formatar como moeda brasileira

            dataPagamentomodal.setValue(LocalDate.now());

            valorPagamentoModal.setValue(valor_lancamento.getValue());
            service.formataMoedaBrasileira(valorPagamentoModal);

            dataVencimentoParcela.setValue(data_lancamento.getValue());

            valorLancado.setValueChangeMode(ValueChangeMode.EAGER);
            valorLancado.setPlaceholder("R$ 0,00");

            valorPagamentoModal.setValueChangeMode(ValueChangeMode.EAGER);
            valorPagamentoModal.setPlaceholder("R$ 0,00");

            // Botões de ação
            Button pagamentoButon = new Button("Confirmar Baixa de Pagamento", event -> {
                try {
                    BigDecimal valorTots = service.getValorBigDecimal(valorPagamentoModal.getValue());  // Converter o valor total para BigDecimal
                    fluxoRecebimentoPagamento.setStatus_pagamento(FinanceiroDataConst.STATUS_CONSOLIDADO.getValor());
                    fluxoRecebimentoPagamento.setData_pagamento(dataPagamentomodal.getValue().atStartOfDay());
                    fluxoRecebimentoPagamento.setValor_pagamento(valorTots);
                    fluxoRecebimentoPagamento.setDatahora_baixa(LocalDateTime.now());
                    fluxoRecebimentoPagamento.setValor_baixa(valorTots);
                    fluxoRecebimentoPagamento.setId_funcionariobaixa(UsuarioAutenticadoConfig.getUser().getId_funcionario());
                    fluxoRecebimentoPagamento.setData_alteracao(LocalDateTime.now());
                    lancamentoService.update(fluxoRecebimentoPagamento);
                    service.notificaSucesso("Pagamento Baixado com sucesso !");
                    lancamentoFinanceiroDiv.refreshGrid();
                    close();
                    modalPagamento.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    service.notificaErro(ModalMessageConst.ERROR_CREATE);
                    modalPagamento.close();
                }
            });
            pagamentoButon.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Button cancelar = new Button("Cancelar", event -> service.askForConfirmation(modalPagamento));
            cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
            VerticalLayout layout = new VerticalLayout(valorLancado,
                    dataVencimentoParcela,
                    dataPagamentomodal,
                    valorPagamentoModal);
            modalPagamento.setHeaderTitle("Baixa de Pagamento");
            modalPagamento.add(layout);
            modalPagamento.getFooter().add(pagamentoButon, cancelar);
            return modalPagamento;
        }
    }

    private void deleta(SetFluxoRecebimentoPagamento item) {
        try {
            lancamentoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            lancamentoFinanceiroDiv.refreshGrid();
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro;

    private Div createFormCadastroEmpresa() {

        nome_fluxorecebimentopagamento = new TextField("Nome Lançamento (Histórico)");
        id_eventofinanceiro = new ComboBox<>("SubConta (Evento Financeiro)");
        id_fornecedor = new ComboBox<>("Fornecedor");
        data_lancamento = new DatePicker("Data Vencimento");
        valor_lancamento = new TextField("Valor Lançamento");
        data_pagamento = new DatePicker("Data Pagamento");
        valor_pagamento = new TextField("Valor Pagamento");
        numero_documento = new TextField("Numero Documento");
        numero_parcela = new TextField("Parcela");
        valor_previsto = new TextField("Valor Previsto");
        datahora_lancamento = new DatePicker("Data Lançamento");
        id_funcionariolancamento = new TextField("Responsável Lançamento");
        status_pagamento = new ComboBox<>("Status Atual Pagamento");
        id_tipoeventofinanceiro = new ComboBox<>("Nome Conta (Tipo Evento Financeiro)");
        descricao_lancamento = new TextArea("Descrição");

        status_pagamento.setItems(List.of("Previsão (P)", "Real (R)"));


        datahora_lancamento.setValue(LocalDate.now());
        datahora_lancamento.setReadOnly(true);

        valor_lancamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_lancamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_lancamento));
        valor_lancamento.setPlaceholder("R$ 0,00");

        valor_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_pagamento));
        valor_pagamento.setPlaceholder("R$ 0,00");

        valor_previsto.setValueChangeMode(ValueChangeMode.EAGER);
        valor_previsto.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_previsto));
        valor_previsto.setPlaceholder("R$ 0,00");

        id_funcionariolancamento.setReadOnly(true);
        numero_parcela.setReadOnly(true);

        id_eventofinanceiro.setItems(eventoFinanceiroService.findAll());
        id_eventofinanceiro.setItemLabelGenerator(SetEventoFinanceiro::getNome_eventofinanceiro);
        HorizontalLayout eventofinanceirolayout =
                new CustomizedComboBox().customizeEventoFinanceiro(id_eventofinanceiro,eventoFinanceiroService,
                                                                    tipoEventoFinanceiroService);


        id_fornecedor.setItems(fornecedorService.findAll());
        id_fornecedor.setItemLabelGenerator(SetFornecedor::getNomefantasia_fornecedor);
        HorizontalLayout fornecedorlayout =
                new CustomizedComboBox().customizeFornecedor
                        (id_fornecedor,fornecedorService,estadoService1,setorAtuacaoService1);

        id_tipoeventofinanceiro.setItems(tipoEventoFinanceiroService.findAll());
        id_tipoeventofinanceiro.setItemLabelGenerator(SetTipoEventoFinanceiro::getNome_tipoeventofinanceiro);
        HorizontalLayout tipoeventofinanceirolayout =
                new CustomizedComboBox().customizeTipoEventoFinanceiro(id_tipoeventofinanceiro,tipoEventoFinanceiroService);
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
        if (Objects.nonNull(funcionario.getNome_funcionario())) {
            id_funcionariolancamento.setValue(funcionario.getNome_funcionario());
        }

        service.setRequiredField(id_tipoeventofinanceiro);
        service.setRequiredField(id_eventofinanceiro);
        service.setRequiredField(id_fornecedor);
        service.setRequiredField(nome_fluxorecebimentopagamento);
        service.setRequiredField(valor_lancamento);
        service.setRequiredField(status_pagamento);
        service.setRequiredField(data_lancamento);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(tipoeventofinanceirolayout,
                eventofinanceirolayout,status_pagamento,
                fornecedorlayout,
                nome_fluxorecebimentopagamento,
                data_lancamento,
                valor_lancamento,
                data_pagamento,
                valor_pagamento,
                numero_documento,
                numero_parcela,
                valor_previsto,
                descricao_lancamento,
                datahora_lancamento,
                id_funcionariolancamento);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        if (Objects.isNull(id_tipoeventofinanceiro.getValue())||
                Objects.isNull(id_eventofinanceiro.getValue())||
                Objects.isNull(id_fornecedor.getValue())||
                Objects.isNull(valor_lancamento.getValue())||
                Objects.isNull(status_pagamento.getValue())||
                Objects.isNull(data_lancamento.getValue())||
                Objects.isNull(nome_fluxorecebimentopagamento.getValue())) {
            service.notificaErro(ModalMessageConst.FIELD_ERROR);
        } else {
            // Lógica para salvar o cadastro
            SetFluxoRecebimentoPagamento dto = fluxoRecebimentoPagamento;

            if (Objects.nonNull(id_tipoeventofinanceiro.getValue().getId_tipoeventofinanceiro())) {
                dto.setId_tipoeventofinanceiro(id_tipoeventofinanceiro.getValue().getId_tipoeventofinanceiro());
            }
            if (Objects.nonNull(id_eventofinanceiro.getValue().getId_eventofinanceiro())) {
                dto.setId_eventofinanceiro(id_eventofinanceiro.getValue().getId_eventofinanceiro());
            }
            if (id_fornecedor.getValue() != null) {
                dto.setId_fornecedor(id_fornecedor.getValue().getId_fornecedor());
            }
            if (Objects.nonNull(funcionario)) {
                dto.setId_funcionariolancamento(funcionario.getId_funcionario());
            }


            dto.setNome_fluxorecebimentopagamento(nome_fluxorecebimentopagamento.getValue());
            dto.setData_vencimento(data_lancamento.getValue());
            dto.setValor_lancamento(service.getValorBigDecimal(valor_lancamento.getValue()));
            if (Objects.nonNull(data_pagamento.getValue())) {
                dto.setData_pagamento(data_pagamento.getValue().atStartOfDay());
            }
            if (Objects.nonNull(valor_pagamento.getValue())) {
                dto.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
            }
            dto.setNumero_documento(numero_documento.getValue());

            if (Objects.nonNull(valor_previsto.getValue())) {
                dto.setValor_previsto(service.getValorBigDecimal(valor_previsto.getValue()));
            }
            dto.setDescricao_fluxorecebimentopagamento(descricao_lancamento.getValue());
            dto.setDatahora_lancamento(LocalDateTime.now());

            dto.setAtivo("S");
            dto.setData_alteracao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            service = new UtilitySystemConfigService();
            try {
                lancamentoService.update(dto);
                lancamentoFinanceiroDiv.refreshGrid();
                id_eventofinanceiro.clear();
                id_fornecedor.clear();
                nome_fluxorecebimentopagamento.clear();
                data_lancamento.clear();
                valor_lancamento.clear();
                data_pagamento.clear();
                valor_pagamento.clear();
                numero_documento.clear();
                numero_parcela.clear();
                valor_previsto.clear();
                datahora_lancamento.clear();
                id_funcionariolancamento.clear();
                service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
                close();
            } catch (Exception e) {
                service.notificaErro(ModalMessageConst.ERROR_CREATE);
            }
        }
    }

    public void setFluxoRecebimentoPagamento(SetFluxoRecebimentoPagamento item) {
        UI.getCurrent().access(() -> {
            this.fluxoRecebimentoPagamento = item;

            if (fluxoRecebimentoPagamento.getStatus_pagamento().equals
                    (FinanceiroDataConst.STATUS_CONSOLIDADO.getValor())){
                baixaButton.setText("Cancelar Baixa");
            } else {
                baixaButton.setText("Baixar Pagamento");
            }

            List<SetEventoFinanceiro> eventoFinancelista = eventoFinanceiroService.findAll();
            List<SetTipoEventoFinanceiro> tipoEventoFinanceiros = tipoEventoFinanceiroService.findAll();
            List<SetFornecedor> fornecedorSerlista = fornecedorService.findAll();
            if (Objects.nonNull(item.getId_tipoeventofinanceiro())) {
                id_tipoeventofinanceiro.setValue(tipoEventoFinanceiros.stream()
                        .filter(objeto -> objeto.getId_tipoeventofinanceiro().equals(item.getId_tipoeventofinanceiro()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getId_eventofinanceiro())) {
                id_eventofinanceiro.setValue(eventoFinancelista.stream()
                        .filter(objeto -> objeto.getId_eventofinanceiro().equals(item.getId_eventofinanceiro()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getId_fornecedor())) {
                id_fornecedor.setValue(fornecedorSerlista.stream()
                        .filter(objeto -> objeto.getId_fornecedor().equals(item.getId_fornecedor()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getStatus_pagamento())) {
                status_pagamento.setValue(item.getStatus_pagamento());
            }

            if (Objects.nonNull(item.getId_funcionariolancamento())) {
                funcionario = funcionarioService.findById(item.getId_funcionariolancamento());
                id_funcionariolancamento.setValue(funcionario.getNome_funcionario());
            }

            if (Objects.nonNull(item.getNumero_parcela())&&Objects.nonNull(item.getQuantidade_parcelas())){
                numero_parcela.setValue(item.getNumero_parcela()+"/"+item.getQuantidade_parcelas());
            }

            if (Objects.nonNull(item.getNome_fluxorecebimentopagamento()))
            nome_fluxorecebimentopagamento.setValue(item.getNome_fluxorecebimentopagamento());
            if (Objects.nonNull(item.getData_vencimento()))
            data_lancamento.setValue(LocalDate.from(item.getData_vencimento()));
            if (Objects.nonNull(item.getValor_lancamento()))
            valor_lancamento.setValue(item.getValor_lancamento().toString());
            if (Objects.nonNull(item.getData_pagamento()))
            data_pagamento.setValue(LocalDate.from(item.getData_pagamento()));
            if (Objects.nonNull(item.getValor_pagamento()))
            valor_pagamento.setValue(item.getValor_pagamento().toString());
            if (Objects.nonNull(item.getNumero_documento()))
            numero_documento.setValue(item.getNumero_documento());
            if (Objects.nonNull(item.getValor_previsto()))
            valor_previsto.setValue(String.valueOf(item.getValor_previsto()));
            if (Objects.nonNull(item.getDatahora_lancamento()))
            datahora_lancamento.setValue(LocalDate.from(item.getDatahora_lancamento()));

            if (Objects.nonNull(item.getDescricao_fluxorecebimentopagamento()))
            descricao_lancamento.setValue(item.getDescricao_fluxorecebimentopagamento());
        });
    }
}
