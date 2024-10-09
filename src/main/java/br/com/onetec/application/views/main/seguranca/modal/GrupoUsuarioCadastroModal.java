package br.com.onetec.application.views.main.seguranca.modal;


import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.grupousuarioservice.GrupoUsuarioService;
import br.com.onetec.application.service.permissaoservice.PermissaoService;
import br.com.onetec.application.views.main.seguranca.div.GrupoUsuariosDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetGrupoUsuario;
import br.com.onetec.infra.db.model.SetPermissao;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@UIScope
public class GrupoUsuarioCadastroModal extends Dialog{


    @Autowired
    GrupoUsuarioService grupoUsuarioService;

    @Autowired
    PermissaoService permissaoService;

    UtilitySystemConfigService service;


    @Autowired
    @Lazy
    GrupoUsuariosDiv grupoUsuariosDiv;

    private Button saveButton;
    private Button cancelButton;


    private TextField descricao_grupousuario;

    private Span usernameStrengthText;

    VerticalLayout permissoes = new VerticalLayout();
    List<RadioButtonGroup<String>> radioGroupList = new ArrayList<>();


    @Autowired
    public GrupoUsuarioCadastroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try { save();}
                catch (Exception e) {}
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            Div contentTabs = new Div(createFormCadastro());
            contentTabs.setSizeFull();
            addThemeVariants(DialogVariant.LUMO_NO_PADDING);
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }

    private Div createFormCadastro() {
        service = new UtilitySystemConfigService();


        ViewsTitleConst.NAMES_VIEWS.forEach(tela -> {
            RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
            radioGroup.setLabel("Tela : " + tela);
            radioGroup.setItems("Sim", "Não");
            radioGroup.setValue("Não");
            radioGroup.setAriaLabel(tela);
            radioGroupList.add(radioGroup);
            permissoes.add(radioGroup);
        });
        //permissoes.add((com.vaadin.flow.component.Component) radioGroupList);
        descricao_grupousuario = new TextField("Nome do Grupo de usuários");
        Div usernameStrength = new Div();
        usernameStrengthText = new Span();
        usernameStrength.add(new Text("Disponibilidade de nome de usuário: "),
                usernameStrengthText);
        //descricao_grupousuario.setHelperComponent(usernameStrength);
        //descricao_grupousuario.addBlurListener(event -> validaUserName(event));
        descricao_grupousuario.setMinLength(5);
        descricao_grupousuario.setMaxLength(30);
        descricao_grupousuario.setErrorMessage("O nome do grupo deve ter no minimo 5 caracteres");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(descricao_grupousuario, permissoes);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    private void save() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetGrupoUsuario dto = new SetGrupoUsuario();
        dto.setDescricao_grupousuario(descricao_grupousuario.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());

        try {
            SetGrupoUsuario grupoUsuario = grupoUsuarioService.save(dto);
            List<SetPermissao> listaPermissao = new ArrayList<>();
            radioGroupList.forEach(radio -> {
                SetPermissao permissao = new SetPermissao();
                permissao.setNome_tela(radio.getAriaLabel().get());
                permissao.setTela_view(radio.getAriaLabel().get());
                permissao.setAtivo("S");
                if (radio.getValue().equals("Sim")){
                    permissao.setLeitura(1);
                } else {
                    permissao.setLeitura(0);
                }
                permissao.setData_inclusao(LocalDateTime.now());
                permissao.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                permissao.setId_grupousuario(grupoUsuario.getId_grupousuario());
                listaPermissao.add(permissao);
            });
            permissaoService.saveAll(listaPermissao);
            grupoUsuariosDiv.refreshGrid();
            descricao_grupousuario.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

}
