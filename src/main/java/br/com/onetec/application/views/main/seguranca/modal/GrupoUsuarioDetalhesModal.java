package br.com.onetec.application.views.main.seguranca.modal;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
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

@Component
@UIScope
public class GrupoUsuarioDetalhesModal extends Dialog {


    @Autowired
    GrupoUsuarioService grupoUsuarioService;

    @Autowired
    PermissaoService permissaoService;

    UtilitySystemConfigService service;


    @Autowired
    @Lazy
    GrupoUsuariosDiv grupoUsuariosDiv;
    VerticalLayout permissoes = new VerticalLayout();
    List<RadioButtonGroup<String>> radioGroupList = new ArrayList<>();
    SetGrupoUsuario grupoUsuario;
    List<SetPermissao> setPermissaoList;
    private Button saveButton;
    private Button cancelButton;
    private Button deleteButton;
    private TextField descricao_grupousuario;
    private Span usernameStrengthText;


    @Autowired
    public GrupoUsuarioDetalhesModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            deleteButton = new Button("Excluir", event -> {
                deleta(grupoUsuario);
            });
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            Div contentTabs = new Div(createFormCadastro());
            contentTabs.setSizeFull();
            addThemeVariants(DialogVariant.LUMO_NO_PADDING);
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
            getFooter().add(saveButton, cancelButton, deleteButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }

    private void deleta(SetGrupoUsuario grupoUsuario) {

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle(
                String.format("Deletar usuário \"%s\"?", grupoUsuario.getDescricao_grupousuario()));
        dialog.add("Você tem certeza que deseja excluir este grupo permanentemente ?");

        // tag::snippet1[]
        Button deleteButton = new Button("Delete", (e) -> excluir(grupoUsuario, dialog));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(deleteButton);

        Button cancelButton = new Button("Cancel", (e) -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getFooter().add(cancelButton);
        // Verifica se existe um ClickListener registrado anteriormente e o remove
        dialog.open();
    }

    private void excluir(SetGrupoUsuario grupoUsuario, Dialog dialog) {
        try {
            grupoUsuarioService.delete(grupoUsuario);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            dialog.close();
            close();
            grupoUsuariosDiv.refreshGrid();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
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
        SetGrupoUsuario dto = grupoUsuario;
        dto.setDescricao_grupousuario(descricao_grupousuario.getValue());
        dto.setAtivo("S");
        dto.setData_alteracao(LocalDateTime.now());

        try {
            SetGrupoUsuario grupoUsuario = grupoUsuarioService.update(dto);
            List<SetPermissao> listaPermissao = new ArrayList<>();
            setPermissaoList.forEach(p -> {
                radioGroupList.forEach(stringRadioButtonGroup -> {
                    if (p.getNome_tela().equals(stringRadioButtonGroup.getAriaLabel().get())) {
                        if (stringRadioButtonGroup.getValue().equals("Sim")) {
                            p.setLeitura(1);
                        } else {
                            p.setLeitura(0);
                        }
                    }
                });
                p.setData_alteracao(LocalDateTime.now());
                p.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                p.setId_grupousuario(grupoUsuario.getId_grupousuario());
                listaPermissao.add(p);
            });
            permissaoService.updateAll(listaPermissao);
            grupoUsuariosDiv.refreshGrid();
            descricao_grupousuario.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void SetGrupo(SetGrupoUsuario item) {
        this.grupoUsuario = item;
        descricao_grupousuario.setValue(item.getDescricao_grupousuario());
        this.setPermissaoList = permissaoService.findAllById(item.getId_grupousuario());
        setPermissaoList.forEach(p -> {
            radioGroupList.forEach(stringRadioButtonGroup -> {
                if (p.getNome_tela().equals(stringRadioButtonGroup.getAriaLabel().get())) {
                    if (p.getLeitura() == 1) {
                        stringRadioButtonGroup.setValue("Sim");
                    } else {
                        stringRadioButtonGroup.setValue("Não");
                    }
                }
            });
        });
    }
}