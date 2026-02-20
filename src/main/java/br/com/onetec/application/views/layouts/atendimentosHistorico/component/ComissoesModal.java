package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.comissoesservice.ComissoesService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetComissoes;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ComissoesModal {

    public static void openModalComissoes(SetComissoes item, ComissoesService comissoesService,
                                          FuncionarioService funcionarioService, TextField valor_nagasaki) {
        Dialog dialog = new Dialog();
        ComboBox<SetFuncionario> id_funcionario = new ComboBox<>("Funcionário");
        ComboBox<String> parcelas_comissoes = new ComboBox<>("Pagamento Comissão ?");
        TextField porcentagem_comissoes = new TextField("Porcentagem");
        DatePicker data_comissao = new DatePicker("Data");
        TextField valor_comissao = new TextField("Valor Comissão");
        IntegerField parcela_comisao = new IntegerField("Parcela Vigente");
        IntegerField totalparcelas_comissao = new IntegerField("Total de Parcelas");
        DatePicker datapagamento_comissao = new DatePicker("Data de Pagamento");
        TextArea descricao_comissao = new TextArea("Observações");

        UtilitySystemConfigService service = new UtilitySystemConfigService();

        parcelas_comissoes.setItems
                (List.of("A Vista", "Parcelado"));
        id_funcionario.setItems
                (funcionarioService.listAll());
        id_funcionario.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        service.configuraCalendario(data_comissao);
        service.configuraCalendario(datapagamento_comissao);

        valor_comissao.setValueChangeMode(ValueChangeMode.EAGER);
        valor_comissao.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_comissao));
        valor_comissao.setPlaceholder("R$ 0,00");

        porcentagem_comissoes.setValueChangeMode(ValueChangeMode.EAGER);
        porcentagem_comissoes.addValueChangeListener(event -> service.formataPorcentagem(porcentagem_comissoes));
        porcentagem_comissoes.setPlaceholder("% 0,00");

        totalparcelas_comissao.setValue(1);
        totalparcelas_comissao.setStepButtonsVisible(true);
        totalparcelas_comissao.setMin(1);
        totalparcelas_comissao.addValueChangeListener(event -> {
            parcela_comisao.setMax(totalparcelas_comissao.getValue());
        });

        parcela_comisao.setValue(1);
        parcela_comisao.setStepButtonsVisible(true);
        parcela_comisao.setMin(1);
        parcela_comisao.setMax(totalparcelas_comissao.getValue());

        porcentagem_comissoes.addValueChangeListener(event -> {
            BigDecimal valorsomado = service.getValorBigDecimal(valor_nagasaki.getValue());
            BigDecimal valorporcentagem = service.extrairPorcentagem(valorsomado,
                    service.getValorBigDecimal(porcentagem_comissoes.getValue()));
            valor_comissao.setValue(valorporcentagem.toString());
        });

        //config form
        id_funcionario.setValue(funcionarioService.listAll().stream()
                .filter(objeto -> objeto.getId_funcionario().equals(item.getId_funcionario()))
                .findFirst().orElse(null));
        parcelas_comissoes.setValue(item.getParcelas_comissoes());
        porcentagem_comissoes.setValue(String.valueOf(item.getPorcentagem_comissoes()));
        data_comissao.setValue(item.getData_comissao());
        valor_comissao.setValue(String.valueOf(item.getValor_comissao()));
        parcela_comisao.setValue(item.getParcela_comisao());
        totalparcelas_comissao.setValue(item.getTotalparcelas_comissao());
        datapagamento_comissao.setValue(item.getDatapagamento_comissao());
        descricao_comissao.setValue(item.getDescricao_comissao());

        //botoes
        Button saveBtn = new Button("Atualizar", eventbe -> {
            item.setId_funcionario(id_funcionario.getValue().getId_funcionario());
            item.setParcelas_comissoes(parcelas_comissoes.getValue());
            item.setPorcentagem_comissoes(service.getValorBigDecimal(porcentagem_comissoes.getValue()));
            item.setData_comissao(data_comissao.getValue());
            item.setValor_comissao(service.getValorBigDecimal(valor_comissao.getValue()));
            item.setParcela_comisao(parcela_comisao.getValue());
            item.setTotalparcelas_comissao(totalparcelas_comissao.getValue());
            item.setDatapagamento_comissao(datapagamento_comissao.getValue());
            item.setDescricao_comissao(descricao_comissao.getValue());
            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                comissoesService.update(item);
                dialog.close();
                service.notificaSucesso("Atualizado com sucesso !");
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.addDialogCloseActionListener(event -> service.askForConfirmation(dialog));
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_funcionario,
                parcelas_comissoes,
                porcentagem_comissoes,
                data_comissao,
                valor_comissao,
                parcela_comisao,
                totalparcelas_comissao,
                datapagamento_comissao,
                descricao_comissao
        );

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
