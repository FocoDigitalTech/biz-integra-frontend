package br.com.onetec.application.views.main.configuracoessistema.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.execucaoservico.ExecucaoServicoService;
import br.com.onetec.application.service.fornecedorcontatoservice.FornecedorContatoService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.setoratuacaoservice.SetorAtuacaoService;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.application.views.main.configuracoessistema.div.ExecucaoServicoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ExecucaoServicoDetalheModal extends Dialog {


    private TextArea decricaoField;
    private TextField nomeField;

    @Autowired
    ExecucaoServicoService execucaoServicoService;

    @Autowired
    @Lazy
    ExecucaoServicoDiv execucaoServicoDiv;

    private com.vaadin.flow.component.button.Button saveButton;
    private com.vaadin.flow.component.button.Button cancelButton;

    UtilitySystemConfigService service;


    @Autowired
    public ExecucaoServicoDetalheModal() {
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
        nomeField = new TextField("Nome");
        decricaoField = new TextArea("Descrição");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nomeField,decricaoField);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetExecucaoServico dto = setExecucaoservico;
        dto.setDescricao_execucaoservico(decricaoField.getValue());
        dto.setNome_execucaoservico(nomeField.getValue());
        dto.setAtivo("S");
        dto.setData_alteracao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            execucaoServicoService.update(dto);
            execucaoServicoDiv.refreshGrid();
            decricaoField.clear();
            nomeField.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    SetExecucaoServico setExecucaoservico;

    public void setExecucaoservico(SetExecucaoServico item) {
        this.setExecucaoservico = item;
        decricaoField.setValue(item.getDescricao_execucaoservico());
        nomeField.setValue(item.getNome_execucaoservico());
    }
}