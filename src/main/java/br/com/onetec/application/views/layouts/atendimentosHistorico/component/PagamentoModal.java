package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.pagamentoservice.PagamentoService;
import br.com.onetec.application.service.situacaopagamentoservice.SituacaoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetPagamento;
import br.com.onetec.infra.db.model.SetSituacaoPagamento;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PagamentoModal {
    public static void openModalPagamento(SetPagamento item, PagamentoService pagamentoService, TipoPagamentoService tipoPagamentoService,
                                          SituacaoPagamentoService situacaoPagamentoService, Grid<SetPagamento> gridPagamento) {
        Dialog dialog = new Dialog();
        IntegerField numeroparcela_pagamento = new IntegerField("Parcela Pagamento");
        IntegerField totalparcela_pagamento = new IntegerField("Total Parcelas");
        DatePicker vencimento_pagamento = new DatePicker("Vencimento");
        TextField valor_pagamento = new TextField("Valor Pagamento");
        DatePicker data_pagamento = new DatePicker("Data Pagamento");
        TextField valorpago_pagamento = new TextField("Valor Pago");
        TextField numerodocumento_pagamento = new TextField("N° Doc/Comprovante");
        ComboBox<SetTipoPagamento> id_tipopagamento = new ComboBox<>("Tipo de Pagamento");
        ComboBox<SetSituacaoPagamento> id_situacaopagamento = new ComboBox<>("Status Pagamento");
        TextArea descricao_pagamento = new TextArea("Observações");


        List<SetSituacaoPagamento> situacaoLista = situacaoPagamentoService.listAll();
        List<SetTipoPagamento> tipoPagamentoLista = tipoPagamentoService.listAll();

        id_situacaopagamento.setItems(situacaoLista);
        id_situacaopagamento.setItemLabelGenerator(SetSituacaoPagamento::getNome_situacaopagamento);

        HorizontalLayout id_situacaopagamentoLayout =
                new CustomizedComboBox()
                        .customizeSituacaoPagamento(id_situacaopagamento,situacaoPagamentoService);

        id_tipopagamento.setItems(tipoPagamentoLista);
        id_tipopagamento.setItemLabelGenerator(SetTipoPagamento::getNome_tipopagamento);
        id_tipopagamento.setRequiredIndicatorVisible(true);
        id_tipopagamento.addValueChangeListener(event -> {
            if (id_tipopagamento.isEmpty()) {
                id_tipopagamento.setErrorMessage("Campo obrigatório");
                id_tipopagamento.setInvalid(true);
            } else {
                id_tipopagamento.setInvalid(false);
            }
        });

        id_situacaopagamento.setRequiredIndicatorVisible(true);
        id_situacaopagamento.addValueChangeListener(event -> {
            if (id_situacaopagamento.isEmpty()) {
                id_situacaopagamento.setErrorMessage("Campo obrigatório");
                id_situacaopagamento.setInvalid(true);
            } else {
                id_situacaopagamento.setInvalid(false);
            }
        });

        HorizontalLayout id_tipopagamentolayout =
                new CustomizedComboBox().customizeTipoPagamento(id_tipopagamento,tipoPagamentoService);

        valor_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        UtilitySystemConfigService service= new UtilitySystemConfigService();
        valor_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_pagamento));
        valor_pagamento.setPlaceholder("R$ 0,00");

        valorpago_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valorpago_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valorpago_pagamento));
        valorpago_pagamento.setPlaceholder("R$ 0,00");

        //config form
        numeroparcela_pagamento.setValue
                (item.getNumeroparcela_pagamento() != null? item.getNumeroparcela_pagamento() : 0);
        totalparcela_pagamento.setValue(
                item.getTotalparcela_pagamento() != null? item.getTotalparcela_pagamento() : 0
                );
        vencimento_pagamento.setValue(
                item.getVencimento_pagamento() != null? item.getVencimento_pagamento() : LocalDate.now()
                );
        valor_pagamento.setValue(
                item.getValor_pagamento() != null? String.valueOf(item.getValor_pagamento()) : "0"
                );
        data_pagamento.setValue(
                item.getData_pagamento() != null? item.getData_pagamento() : LocalDate.now()
             );
        valorpago_pagamento.setValue(
                item.getValorpago_pagamento() != null? String.valueOf(item.getValorpago_pagamento()) : "0"
                );
        numerodocumento_pagamento.setValue(
                item.getNumerodocumento_pagamento() != null? item.getNumerodocumento_pagamento() : ""
                );
        id_tipopagamento.setValue(tipoPagamentoLista.stream()
                .filter(objeto -> objeto.getId_tipopagamento().equals(item.getId_tipopagamento()))
                .findFirst().orElse(null));
        id_situacaopagamento.setValue(situacaoLista.stream()
                .filter(objeto -> objeto.getId_situacaopagamento().equals(item.getId_situacaopagamento()))
                .findFirst().orElse(null));
        descricao_pagamento.setValue(
                item.getDescricao_pagamento() != null? item.getDescricao_pagamento() : ""
               );

        Button saveBtn = new Button("Atualizar", eventbe -> {


            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                if (id_situacaopagamento.isEmpty()) {
                    id_situacaopagamento.setRequiredIndicatorVisible(true);
                    id_situacaopagamento.setErrorMessage("Campo obrigatório");
                    id_situacaopagamento.setInvalid(true);
                    service.notificaErro(ModalMessageConst.FIELD_ERROR);
                } else if (id_tipopagamento.isEmpty()) {
                    id_tipopagamento.setRequiredIndicatorVisible(true);
                    id_tipopagamento.setErrorMessage("Campo obrigatório");
                    id_tipopagamento.setInvalid(true);
                    service.notificaErro(ModalMessageConst.FIELD_ERROR);
                } else {
                    item.setNumeroparcela_pagamento(numeroparcela_pagamento.getValue());
                    item.setTotalparcela_pagamento(totalparcela_pagamento.getValue());
                    item.setVencimento_pagamento(vencimento_pagamento.getValue());
                    item.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
                    item.setData_pagamento(data_pagamento.getValue());
                    item.setValorpago_pagamento(service.getValorBigDecimal(valorpago_pagamento.getValue()));
                    item.setNumerodocumento_pagamento(numerodocumento_pagamento.getValue());
                    if (Objects.nonNull(id_tipopagamento.getValue().getId_tipopagamento())) {
                        item.setId_tipopagamento(id_tipopagamento.getValue().getId_tipopagamento());
                    }
                    if (Objects.nonNull(id_situacaopagamento.getValue().getId_situacaopagamento())) {
                        item.setId_situacaopagamento(id_situacaopagamento.getValue().getId_situacaopagamento());
                    }
                    item.setDescricao_pagamento(descricao_pagamento.getValue());
                    pagamentoService.update(item);
                    List<SetPagamento> listaPagamentos = pagamentoService.findAllByOrcamentoId(item.getId_orcamento());
                    service.notificaSucesso("Atualizado com sucesso !");
                    gridPagamento.setItems(listaPagamentos);
                    dialog.close();
                }
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
                dialog.close();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        Button btnBaixar  = new Button("Baixar Pagamento", eventbe -> {


            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setNumeroparcela_pagamento(numeroparcela_pagamento.getValue());
                item.setTotalparcela_pagamento(totalparcela_pagamento.getValue());
                item.setVencimento_pagamento(vencimento_pagamento.getValue());
                item.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
                item.setData_pagamento(LocalDate.now());
                item.setValorpago_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
                item.setNumerodocumento_pagamento(numerodocumento_pagamento.getValue());
                item.setBaixado("S");
                item.setId_tipopagamento(tipoPagamentoLista.get(0).getId_tipopagamento());
                item.setId_situacaopagamento(situacaoLista.get(0).getId_situacaopagamento());
                item.setDescricao_pagamento(descricao_pagamento.getValue());
                item.setId_usuariobaixa(UsuarioAutenticadoConfig.getUser().getId_usuario());
                pagamentoService.update(item);
                dialog.close();
                service.notificaSucesso("Baixado com sucesso !");
                List<SetPagamento> listaPagamentos = pagamentoService.findAllByOrcamentoId(item.getId_orcamento());
                gridPagamento.setItems(listaPagamentos);

            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });

        btnBaixar.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_TERTIARY);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(numeroparcela_pagamento,
                totalparcela_pagamento,
                vencimento_pagamento,
                valor_pagamento,
                data_pagamento,
                valorpago_pagamento,
                numerodocumento_pagamento,
                id_tipopagamentolayout,
                id_situacaopagamentoLayout,
                descricao_pagamento);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn,btnBaixar);
        dialog.open();

    }
}
