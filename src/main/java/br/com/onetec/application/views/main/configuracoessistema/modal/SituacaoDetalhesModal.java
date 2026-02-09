package br.com.onetec.application.views.main.configuracoessistema.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.views.main.configuracoessistema.div.SituacaoCadastroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class SituacaoDetalhesModal extends Dialog {

    @Autowired
    SituacaoCadastroService situacaoCadastroService;
    @Autowired
    @Lazy
    SituacaoCadastroDiv situacaoCadastroDiv;
    UtilitySystemConfigService service;
    SetSituacaoCadastro setSituacaoCadastro;
    private TextField decricaoField;
    private Button saveButton;
    private Button cancelButton;


    public SituacaoDetalhesModal() {
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
        decricaoField = new TextField("Nome ou Descrição");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(decricaoField);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetSituacaoCadastro dto = setSituacaoCadastro;
        dto.setDescricao_situacaocadastro(decricaoField.getValue());
        dto.setAtivo("S");
        dto.setData_alteracao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            situacaoCadastroService.update(dto);
            situacaoCadastroDiv.refreshGrid();
            decricaoField.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void setSituacaoCadastro(SetSituacaoCadastro item) {
        this.setSituacaoCadastro = item;
        decricaoField.setValue(item.getDescricao_situacaocadastro());
    }
}
