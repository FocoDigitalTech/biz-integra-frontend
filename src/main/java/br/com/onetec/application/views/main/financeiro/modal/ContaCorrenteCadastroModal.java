package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.views.main.financeiro.div.CondicaoPagamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetContaCorrente;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@UIScope
public class ContaCorrenteCadastroModal extends Dialog {


    private TextField nome_contacorrente;
    private TextField banco_contacorrente;
    private TextField agencia_contacorrente;
    private TextField numero_contacorrente;
    private NumberField limete_contacorrente;
    private DatePicker ultimolancamento_contacorrente;

    @Autowired
    ContaCorrenteService contaCorrenteService;

    @Autowired
    @Lazy
    CondicaoPagamentoDiv condicaoPagamentoDiv;
    private Button saveButton;
    private Button cancelButton;



    public ContaCorrenteCadastroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            cancelButton = new Button("Cancelar", event -> close());
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }


    private Div createFormCadastroEmpresa() {
        service = new UtilitySystemConfigService();

        nome_contacorrente = new TextField("Nome");
        banco_contacorrente = new TextField("Nome");
        agencia_contacorrente = new TextField("Agencia");
        numero_contacorrente = new TextField("Numero");
        limete_contacorrente = new NumberField("Limite");
        ultimolancamento_contacorrente = new DatePicker("Ultimo Lançamento");

        limete_contacorrente.setValueChangeMode(ValueChangeMode.EAGER);
        limete_contacorrente.addValueChangeListener(event -> service.formataMoedaBrasileiraNumberField(limete_contacorrente));
        Div dollarPrefix = new Div();
        dollarPrefix.setText("R$");
        limete_contacorrente.setPrefixComponent(dollarPrefix);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_contacorrente,
                banco_contacorrente,
                agencia_contacorrente,
                numero_contacorrente,
                limete_contacorrente,
                ultimolancamento_contacorrente);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetContaCorrente dto = new SetContaCorrente();
        dto.setNome_contacorrente(nome_contacorrente.getValue());
        dto.setBanco_contacorrente(banco_contacorrente.getValue());
        dto.setAgencia_contacorrente(agencia_contacorrente.getValue());
        dto.setNumero_contacorrente(numero_contacorrente.getValue());
        dto.setLimete_contacorrente(BigDecimal.valueOf(limete_contacorrente.getValue()));
        dto.setUltimolancamento_contacorrente(ultimolancamento_contacorrente.getValue().atStartOfDay());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            contaCorrenteService.save(dto);
            condicaoPagamentoDiv.refreshGrid();
            nome_contacorrente.clear();
            banco_contacorrente.clear();
            agencia_contacorrente.clear();
            numero_contacorrente.clear();
            limete_contacorrente.clear();
            ultimolancamento_contacorrente.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}
