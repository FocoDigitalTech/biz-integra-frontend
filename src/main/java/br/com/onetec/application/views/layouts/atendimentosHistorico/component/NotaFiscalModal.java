package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetNotaFiscal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class NotaFiscalModal {
    public static void openModalNota(SetNotaFiscal item, NotaFiscalService notaFiscalService,
                                     UtilitySystemConfigService service) {

        Dialog dialog = new Dialog();
        TextField numero_notafiscal = new TextField("Numero");
        TextField serie_notafiscal = new TextField("Série");
        DatePicker dataemissao_notafiscal = new DatePicker("Data Emissão");
        TextField natureza_notafiscal = new TextField("Natureza");
        TextField unidade_notafiscal = new TextField("Unidade");
        IntegerField quantidade_notafiscal = new IntegerField("Quantidade");
        TextField valorunitario_notafiscal = new TextField("Valor Unitario");
        TextField valortotal_notafiscal = new TextField("Valor Total");
        TextField descricao_notafiscal = new TextField("Descrição");


        valorunitario_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valorunitario_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valorunitario_notafiscal));
        valorunitario_notafiscal.setPlaceholder("R$ 0,00");

        valortotal_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valortotal_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valortotal_notafiscal));
        valortotal_notafiscal.setPlaceholder("R$ 0,00");

        quantidade_notafiscal.setStepButtonsVisible(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        //config form
        numero_notafiscal.setValue
                (item.getNumero_notafiscal() != null? item.getNumero_notafiscal() : "");
        serie_notafiscal.setValue(
                item.getSerie_notafiscal() != null? item.getSerie_notafiscal() : ""
        );
        dataemissao_notafiscal.setValue(
                item.getDataemissao_notafiscal() != null? item.getDataemissao_notafiscal() : LocalDate.now()
        );
        natureza_notafiscal.setValue(
                item.getNatureza_notafiscal() != null? item.getNatureza_notafiscal() : "0"
        );
        unidade_notafiscal.setValue(
                item.getUnidade_notafiscal() != null? item.getUnidade_notafiscal() : ""
        );
        quantidade_notafiscal.setValue(
                item.getQuantidade_notafiscal() != null? Integer.parseInt(item.getQuantidade_notafiscal()) : 0
        );
        valorunitario_notafiscal.setValue(
                item.getValorunitario_notafiscal() != null? String.valueOf(item.getValorunitario_notafiscal()) : "0"
        );
        valortotal_notafiscal.setValue(item.getValortotal_notafiscal() != null? String.valueOf(item.getValortotal_notafiscal()) : "0"
        );
        descricao_notafiscal.setValue(item.getDescricao_notafiscal() != null? item.getDescricao_notafiscal() : "");

        Button saveBtn = new Button("Atualizar", eventbe -> {

            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setNumero_notafiscal(numero_notafiscal.getValue());
                item.setSerie_notafiscal(serie_notafiscal.getValue());
                item.setDataemissao_notafiscal(dataemissao_notafiscal.getValue());
                item.setNatureza_notafiscal(natureza_notafiscal.getValue());
                item.setUnidade_notafiscal(unidade_notafiscal.getValue());
                item.setQuantidade_notafiscal(String.valueOf(quantidade_notafiscal.getValue()));
                item.setValorunitario_notafiscal(service.getValorBigDecimal(valorunitario_notafiscal.getValue()));
                item.setValortotal_notafiscal(service.getValorBigDecimal(valortotal_notafiscal.getValue()));
                item.setDescricao_notafiscal(descricao_notafiscal.getValue());
                notaFiscalService.update(item);
                dialog.close();
                service.notificaSucesso("Atualizado com sucesso !");
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        formLayout.setWidthFull();
        formLayout.add(numero_notafiscal,
                serie_notafiscal,
                dataemissao_notafiscal,
                natureza_notafiscal,
                unidade_notafiscal,
                quantidade_notafiscal,
                valorunitario_notafiscal,
                valortotal_notafiscal,
                descricao_notafiscal);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
