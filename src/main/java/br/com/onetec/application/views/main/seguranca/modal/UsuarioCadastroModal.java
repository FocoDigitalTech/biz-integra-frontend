package br.com.onetec.application.views.main.seguranca.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.grupousuarioservice.GrupoUsuarioService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.seguranca.div.UsuariosDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;


@Component
@UIScope
public class UsuarioCadastroModal extends Dialog {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GrupoUsuarioService grupoUsuarioService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    @Lazy
    UsuariosDiv usuariosDiv;

    private Button saveButton;
    private Button cancelButton;

    private ComboBox <SetFuncionario> id_funcionario;
    private ComboBox <SetGrupoUsuario> id_grupousuario;
    private EmailField emailField;
    private PasswordField passwordField;
    private PasswordField entradaPassword;
    private TextField nome_usuario;




    @Autowired
    public UsuarioCadastroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try { save();}
                catch (Exception e) {}
            });
            cancelButton = new Button("Cancelar", event -> close());
            Div contentTabs = new Div(createFormCadastro());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }

    private Span passwordStrengthText;
    private Span usernameStrengthText;

    private Div createFormCadastro() {
        service = new UtilitySystemConfigService();
        //id_classificacaoproduto = new ComboBox<String>("Nome ou Descrição");

        nome_usuario = new TextField("Nome de Acesso do Usuario");

        Div usernameStrength = new Div();
        usernameStrengthText = new Span();
        usernameStrength.add(new Text("Disponibilidade de nome de usuário: "),
                usernameStrengthText);
        nome_usuario.setHelperComponent(usernameStrength);
        nome_usuario.addBlurListener(event -> validaUserName(event));
        nome_usuario.setMinLength(5);
        nome_usuario.setMaxLength(20);
        nome_usuario.setErrorMessage("O nome de usuário deve ter no minimo 5 caracteres");


        emailField = new EmailField();
        emailField.setLabel("E-mail Usuario");
        emailField.getElement().setAttribute("name", "email");
        emailField.setErrorMessage("Insira um endereço de e-mail válido");
        emailField.setClearButtonVisible(true);

        passwordField = new PasswordField();
        passwordField.setLabel("Senha");
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setMinLength(6);
        passwordField.setMaxLength(12);
        passwordField.setErrorMessage("A senha deve ter no minimo 6 caracteres");
        passwordField.setPrefixComponent(VaadinIcon.LOCK.create());
        passwordField.setHelperText(
                "A senha deve ter de 6 a 12 caracteres. Entre letras, numeros e caracters especiais.");

        entradaPassword = new PasswordField();
        entradaPassword.setLabel("Confirme sua Senha");
        entradaPassword.setRequiredIndicatorVisible(true);
        entradaPassword.setMinLength(6);
        entradaPassword.setMaxLength(12);
        entradaPassword.setErrorMessage("A senha deve ter no minimo 6 caracteres");
        entradaPassword.setPrefixComponent(VaadinIcon.LOCK.create());
        entradaPassword.setHelperText(
                "Confirme a senha do usuário");
        Div passwordStrength = new Div();
        passwordStrengthText = new Span();
        passwordStrength.add(new Text("Verificação de senha: "),
                passwordStrengthText);
        entradaPassword.setHelperComponent(passwordStrength);
        entradaPassword.setValueChangeMode(ValueChangeMode.EAGER);


        entradaPassword.addValueChangeListener(event -> {
            String value = event.getValue();
            if (!value.equals(passwordField.getValue())) {
                passwordStrengthText.setText("As Senhas não coincidem");
                passwordStrengthText.getStyle().set("color",
                        "var(--lumo-error-color)");
            } else {
                passwordStrengthText.setText("Sucesso");
                passwordStrengthText.getStyle().set("color",
                        "var(--lumo-success-color)");
            }
        });



        id_funcionario = new ComboBox<>("Funcionario");
        id_grupousuario = new ComboBox<>("Grupo Usuário");

        id_funcionario.setItems(funcionarioService.listAll());
        id_funcionario.setItemLabelGenerator(SetFuncionario::getNome_funcionario);
        id_grupousuario.setItems(grupoUsuarioService.listAll());
        id_grupousuario.setItemLabelGenerator(SetGrupoUsuario::getDescricao_grupousuario);



        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_funcionario, id_grupousuario,emailField,
                passwordField, nome_usuario, entradaPassword);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void validaUserName(BlurNotifier.BlurEvent<TextField> event) {
        String value = event.getSource().getValue();
        boolean usernameAvaliable = usuarioService.checkUserNameAvaliable(value);
        if (usernameAvaliable){
            usernameStrengthText.setText("Disponivel para uso");
            usernameStrengthText.getStyle().set("color",
                    "var(--lumo-success-color)");
        } else {
            usernameStrengthText.setText("Este nome de usuário já está sendo utilizado");
            usernameStrengthText.getStyle().set("color",
                    "var(--lumo-error-color)");
        }
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetUsuarios dto = new SetUsuarios();
        dto.setEmail_usuario(emailField.getValue());
        dto.setSenha_usuario(verificaSenha(entradaPassword.getValue(), passwordField.getValue()));
        dto.setNome_usuario(nome_usuario.getValue());

        SetFuncionario funcionario = id_funcionario.getValue();
        if (funcionario != null) {
            dto.setId_funcionario(funcionario.getId_funcionario());
        }
        dto.setId_grupousuario(getIdGrupoUsuario());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());

        try {
            usuarioService.save(dto);
            usuariosDiv.refreshGrid();
            id_funcionario.clear();
            id_grupousuario.clear();
            emailField.clear();
            passwordField.clear();
            entradaPassword.clear();
            nome_usuario.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    private String verificaSenha(String value, String value1) throws Exception {
        if (value.equals(value1)){
            return value;
        } else {
            service.notificaErro(ModalMessageConst.ERROR_USER_GROUP);
            throw new Exception();
        }
    }

    private int getIdGrupoUsuario() throws Exception {
        SetGrupoUsuario grupoUsuario = id_grupousuario.getValue();
        if (grupoUsuario != null){
            return grupoUsuario.getId_grupousuario();
        } else {
            service.notificaErro(ModalMessageConst.ERROR_USER_GROUP);
            throw new Exception();
        }
    }
}
