package br.com.onetec.application.views.principal.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;

public class CustomLoginForm extends LoginForm {

    public CustomLoginForm() {
        setI18n(createPortugueseI18n());
    }

    private LoginI18n createPortugueseI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Login");
        i18nForm.setUsername("Usuário");
        i18nForm.setPassword("Senha");
        i18nForm.setSubmit("Entrar");
        i18nForm.setForgotPassword("Esqueceu a senha?");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Usuário/senha inválidos");
        i18nErrorMessage.setMessage("Verifique seu usuário e senha e tente novamente.");
        i18n.setErrorMessage(i18nErrorMessage);

        return i18n;
    }
}

