package br.com.onetec.application.views.main.login;

import br.com.onetec.domain.usecase.securityusecase.imp.UseCaseSecurityImp;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Route("login")
@PageTitle("Login Sistema")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final CustomLoginForm loginForm;



    public LoginView() {

        addClassName("login-view");
        Image logo = new Image("https://nagasakidedetizacao.com.br/wp-content/uploads/2020/08/Logo-Nagasaki.png", "Logo");
        loginForm = new CustomLoginForm();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setAction("login");
        loginForm.addLoginListener(this::onLogin);
        add(loginForm);
        add(logo, new H3("Seja bem vindo !"), loginForm);
    }

    private void onLogin(AbstractLogin.LoginEvent event) {
        String username = event.getUsername();
        String password = event.getPassword();

        // Adicione a lógica personalizada aqui
        try {
            //User usuario = useCase.getUserByUserName(username);
            // Crie um token de autenticação com as credenciais fornecidas
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            // Autentique o usuário usando o AuthenticationManager
            //Authentication authentication = authenticationManager.authenticate(token);

            // Defina a autenticação no contexto de segurança
            //SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException e) {
            // Tratamento de erro de autenticação
            Notification.show("Erro de autenticação: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}

