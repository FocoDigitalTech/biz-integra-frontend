package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetNotaFiscal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotaFiscalModal {
    public static void openModalNota(SetNotaFiscal item, NotaFiscalService notaFiscalService,
                                     UtilitySystemConfigService service) {

        Dialog dialog = new Dialog();
        TextField numero_notafiscal = new TextField("Numero");
        DatePicker dataemissao_notafiscal = new DatePicker("Data Emissão");
        TextField valorunitario_notafiscal = new TextField("Valor Unitario");
        TextField valortotal_notafiscal = new TextField("Valor Total");
        TextField descricao_notafiscal = new TextField("Descrição");
        DatePicker datavencimento_notafiscal = new DatePicker("Data Vencimento");


        valorunitario_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valorunitario_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valorunitario_notafiscal));
        valorunitario_notafiscal.setPlaceholder("R$ 0,00");

        valortotal_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valortotal_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valortotal_notafiscal));
        valortotal_notafiscal.setPlaceholder("R$ 0,00");


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        //config form
        numero_notafiscal.setValue
                (item.getNumero_notafiscal() != null ? item.getNumero_notafiscal() : "");

        dataemissao_notafiscal.setValue(
                item.getDataemissao_notafiscal() != null ? item.getDataemissao_notafiscal() : LocalDate.now()
        );
        datavencimento_notafiscal.setValue(
                item.getDatavencimento_notafiscal() != null ? item.getDatavencimento_notafiscal() : LocalDate.now()
        );
        valorunitario_notafiscal.setValue(
                item.getValorunitario_notafiscal() != null ? String.valueOf(item.getValorunitario_notafiscal()) : "0"
        );
        valortotal_notafiscal.setValue(item.getValortotal_notafiscal() != null ? String.valueOf(item.getValortotal_notafiscal()) : "0"
        );
        descricao_notafiscal.setValue(item.getDescricao_notafiscal() != null ? item.getDescricao_notafiscal() : "");

        Button saveBtn = new Button("Atualizar", eventbe -> {

            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setNumero_notafiscal(numero_notafiscal.getValue());
                item.setDataemissao_notafiscal(dataemissao_notafiscal.getValue());
                item.setDatavencimento_notafiscal(datavencimento_notafiscal.getValue());
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

        service.configuraCalendario(datavencimento_notafiscal);
        service.configuraCalendario(dataemissao_notafiscal);

        formLayout.setWidthFull();
        formLayout.add(numero_notafiscal,
                dataemissao_notafiscal, datavencimento_notafiscal,
                valorunitario_notafiscal,
                valortotal_notafiscal,
                descricao_notafiscal);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
