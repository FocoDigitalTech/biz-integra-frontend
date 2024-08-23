package br.com.onetec.application.views.main.configuracoessistema.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.tipomidiaservice.TipoMidiaService;
import br.com.onetec.application.views.main.configuracoessistema.div.TipoMidiaDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.Servicos;
import br.com.onetec.infra.db.model.SetTipoMidia;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TipoMidiaCadastroModal extends Dialog {

    private TextField decricaoField;

    @Autowired
    TipoMidiaService tipoMidiaService;

    @Autowired
    @Lazy
    TipoMidiaDiv tipoMidiaDiv;
    private Button saveButton;
    private Button cancelButton;



    public TipoMidiaCadastroModal() {
        addClassName("cadastro-modal");
        saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
            try {
                save();
            } catch (Exception e) {}
        });
        cancelButton = new Button("Cancelar", event -> close());
        Div contentTabs = new Div(createFormCadastroEmpresa());
        contentTabs.setSizeFull();
        VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
        add(layout);
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


    Servicos service;

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetTipoMidia dto = new SetTipoMidia();
        dto.setDescricao_tipomidia(decricaoField.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new Servicos();
        try {
            tipoMidiaService.save(dto);
            tipoMidiaDiv.refreshGrid();
            decricaoField.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}
