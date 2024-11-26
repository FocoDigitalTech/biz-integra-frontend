package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.lancamentoservice.LancamentoService;
import br.com.onetec.application.views.main.financeiro.div.LancamentoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
public class LancamentoFinanceiroDetalhesModal extends Dialog {

    private ComboBox<SetEventoFinanceiro> id_eventofinanceiro;
    private ComboBox<SetContaCorrente> id_contacorrente;
    private ComboBox<SetFornecedor> id_fornecedor;
    //private ComboBox<> id_tipopagamento;
    private TextField nome_fluxorecebimentopagamento;
    private TextField quantidade_parcelas;
    private TextField quantidade_intervalo;
    private DatePicker data_lancamento;
    private TextField valor_lancamento;
    private DatePicker data_contabil;
    private TextField valor_contabil;
    private DatePicker data_pagamento;
    private TextField valor_pagamento;
    private TextField numero_documento;
    private TextField numero_parcela;
    private TextField valor_previsto;
    private DatePicker datahora_lancamento;
    private TextField id_funcionariolancamento;

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
    @Lazy
    LancamentoFinanceiroDiv lancamentoFinanceiroDiv;

    @Autowired
    LancamentoService lancamentoService;

    private Button saveButton;
    private Button cancelButton;
    private Button excluirButton;

    private SetFluxoRecebimentoPagamento fluxoRecebimentoPagamento;

    @Autowired
    public void initServices( UtilitySystemConfigService service) {
        this.service = service;
        //configurações dos fields:
        UI.getCurrent().access(() -> {
            service.configuraCalendario(data_lancamento);
            service.configuraCalendario(data_contabil);
            service.configuraCalendario(data_pagamento);
            service.configuraCalendario(datahora_lancamento);
        });
    }



    public LancamentoFinanceiroDetalhesModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Atualizar", eventbe -> {
                try { save();}
                catch (Exception e) {}
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            excluirButton = new Button("Excluir", event -> {
                deleta(fluxoRecebimentoPagamento);
            });
            excluirButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(excluirButton, saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }

    private void deleta(SetFluxoRecebimentoPagamento item) {
        try {
            lancamentoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            lancamentoFinanceiroDiv.refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    private Div createFormCadastroEmpresa() {

        nome_fluxorecebimentopagamento = new TextField("Nome");
        id_eventofinanceiro = new ComboBox<>("SubConta (Evento Financeiro)");
        id_contacorrente = new ComboBox<>("Conta Corrente");
        id_fornecedor = new ComboBox<>("Fornecedor");
        nome_fluxorecebimentopagamento = new TextField("Nome Lançamento");
        quantidade_parcelas = new TextField("Parcelas");
        quantidade_intervalo = new TextField("Intervalo");
        data_lancamento = new DatePicker("Data");
        valor_lancamento = new TextField("Valor");
        data_contabil = new DatePicker("Data Contabil");
        valor_contabil = new TextField("Valor Contabil");
        data_pagamento = new DatePicker("Data Pagamento");
        valor_pagamento = new TextField("Valor Pagamento");
        numero_documento = new TextField("Numero Documento");
        numero_parcela = new TextField("Numero Parcela");
        valor_previsto = new TextField("Valor Previsto");
        datahora_lancamento = new DatePicker("Data Lançamento");
        id_funcionariolancamento = new TextField("Responsável Lançamento");

        datahora_lancamento.setValue(LocalDate.now());
        datahora_lancamento.setReadOnly(true);

        valor_lancamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_lancamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_lancamento));
        valor_lancamento.setPlaceholder("R$ 0,00");

        valor_contabil.setValueChangeMode(ValueChangeMode.EAGER);
        valor_contabil.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_contabil));
        valor_contabil.setPlaceholder("R$ 0,00");

        valor_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_pagamento));
        valor_pagamento.setPlaceholder("R$ 0,00");

        valor_previsto.setValueChangeMode(ValueChangeMode.EAGER);
        valor_previsto.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_previsto));
        valor_previsto.setPlaceholder("R$ 0,00");

        id_eventofinanceiro.setItems(eventoFinanceiroService.findAll());
        id_eventofinanceiro.setItemLabelGenerator(SetEventoFinanceiro::getNome_eventofinanceiro);

        id_contacorrente.setItems(contaCorrenteService.findAll());
        id_contacorrente.setItemLabelGenerator(SetContaCorrente::getNome_contacorrente);

        id_fornecedor.setItems(fornecedorService.findAll());
        id_fornecedor.setItemLabelGenerator(SetFornecedor::getNomefantasia_fornecedor);

        //id_funcionariolancamento.setEnabled(false);
        funcionario = funcionarioService.findById(UsuarioAutenticadoConfig.getUser().getId_funcionario());
        id_funcionariolancamento.setReadOnly(true);
        id_funcionariolancamento.setValue(funcionario.getNome_funcionario());



        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_eventofinanceiro,
                id_contacorrente,
                id_fornecedor,
                nome_fluxorecebimentopagamento,
                quantidade_parcelas,
                quantidade_intervalo,
                data_lancamento,
                valor_lancamento,
                data_contabil,
                valor_contabil,
                data_pagamento,
                valor_pagamento,
                numero_documento,
                numero_parcela,
                valor_previsto,
                datahora_lancamento,
                id_funcionariolancamento);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetFluxoRecebimentoPagamento dto = fluxoRecebimentoPagamento;

        if (Objects.nonNull(id_eventofinanceiro.getValue().getId_eventofinanceiro())) {
            dto.setId_eventofinanceiro(id_eventofinanceiro.getValue().getId_eventofinanceiro());
        }  if (Objects.nonNull(id_contacorrente.getValue().getId_contacorrente())) {
            dto.setId_contacorrente(id_contacorrente.getValue().getId_contacorrente());
        }  if (id_fornecedor.getValue() != null) {
            dto.setId_fornecedor(id_fornecedor.getValue().getId_fornecedor());
        }  if (Objects.nonNull(funcionario)) {
            dto.setId_funcionariolancamento(funcionario.getId_funcionario());
        }


        dto.setNome_fluxorecebimentopagamento(nome_fluxorecebimentopagamento.getValue());
        dto.setQuantidade_parcelas(quantidade_parcelas.getValue());
        dto.setQuantidade_intervalo(quantidade_intervalo.getValue());
        dto.setData_lancamento(data_lancamento.getValue().atStartOfDay());
        dto.setValor_lancamento(service.getValorBigDecimal(valor_lancamento.getValue()));
        dto.setData_contabil(data_contabil.getValue().atStartOfDay());
        dto.setValor_contabil(service.getValorBigDecimal(valor_contabil.getValue()));
        dto.setData_pagamento(data_pagamento.getValue().atStartOfDay());
        dto.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
        dto.setNumero_documento(numero_documento.getValue());
        dto.setNumero_parcela(numero_parcela.getValue());
        dto.setValor_previsto(service.getValorBigDecimal(valor_previsto.getValue()));
        dto.setDatahora_lancamento(LocalDateTime.now());
        //dto.setNome_grupoeventofinanceiro(nome_grupoeventofinanceiro.getValue());
        //dto.setDescricao_grupoeventofinanceiro(descricao_grupoeventofinanceiro.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            lancamentoService.update(dto);
            lancamentoFinanceiroDiv.refreshGrid();
            id_eventofinanceiro.clear();
            id_contacorrente.clear();
            id_fornecedor.clear();
            nome_fluxorecebimentopagamento.clear();
            quantidade_parcelas.clear();
            quantidade_intervalo.clear();
            data_lancamento.clear();
            valor_lancamento.clear();
            data_contabil.clear();
            valor_contabil.clear();
            data_pagamento.clear();
            valor_pagamento.clear();
            numero_documento.clear();
            numero_parcela.clear();
            valor_previsto.clear();
            datahora_lancamento.clear();
            id_funcionariolancamento.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void setFluxoRecebimentoPagamento(SetFluxoRecebimentoPagamento item) {
        UI.getCurrent().access(() -> {
            this.fluxoRecebimentoPagamento = item;

            List<SetEventoFinanceiro> eventoFinancelista = eventoFinanceiroService.findAll();
            List<SetContaCorrente> contaCorrentelista = contaCorrenteService.findAll();
            List<SetFornecedor> fornecedorSerlista = fornecedorService.findAll();
            if (Objects.nonNull(item.getId_eventofinanceiro())) {
                id_eventofinanceiro.setValue(eventoFinancelista.stream()
                        .filter(objeto -> objeto.getId_eventofinanceiro().equals(item.getId_eventofinanceiro()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getId_contacorrente())) {
                id_contacorrente.setValue(contaCorrentelista.stream()
                        .filter(objeto -> objeto.getId_contacorrente().equals(item.getId_contacorrente()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getId_fornecedor())) {
                id_fornecedor.setValue(fornecedorSerlista.stream()
                        .filter(objeto -> objeto.getId_fornecedor().equals(item.getId_fornecedor()))
                        .findFirst().orElse(null));
            }

            if (Objects.nonNull(item.getNome_fluxorecebimentopagamento()))
            nome_fluxorecebimentopagamento.setValue(item.getNome_fluxorecebimentopagamento());
            if (Objects.nonNull(item.getQuantidade_parcelas()))
            quantidade_parcelas.setValue(item.getQuantidade_parcelas());
            if (Objects.nonNull(item.getQuantidade_intervalo()))
            quantidade_intervalo.setValue(item.getQuantidade_intervalo());
            if (Objects.nonNull(item.getData_lancamento()))
            data_lancamento.setValue(LocalDate.from(item.getData_lancamento()));
            if (Objects.nonNull(item.getValor_lancamento()))
            valor_lancamento.setValue(String.valueOf(item.getValor_lancamento()));
            if (Objects.nonNull(item.getData_contabil()))
            data_contabil.setValue(LocalDate.from(item.getData_contabil()));
            if (Objects.nonNull(item.getValor_contabil()))
            valor_contabil.setValue(String.valueOf(item.getValor_contabil()));
            if (Objects.nonNull(item.getData_pagamento()))
            data_pagamento.setValue(LocalDate.from(item.getData_pagamento()));
            if (Objects.nonNull(item.getValor_pagamento()))
            valor_pagamento.setValue(String.valueOf(item.getValor_pagamento()));
            if (Objects.nonNull(item.getNumero_documento()))
            numero_documento.setValue(item.getNumero_documento());
            if (Objects.nonNull(item.getNumero_parcela()))
            numero_parcela.setValue(item.getNumero_parcela());
            if (Objects.nonNull(item.getValor_previsto()))
            valor_previsto.setValue(String.valueOf(item.getValor_previsto()));
            if (Objects.nonNull(item.getDatahora_lancamento()))
            datahora_lancamento.setValue(LocalDate.from(item.getDatahora_lancamento()));
            if (Objects.nonNull(item.getId_funcionariolancamento()))
            id_funcionariolancamento.setValue(String.valueOf(item.getId_funcionariolancamento()));

        });
    }
}
