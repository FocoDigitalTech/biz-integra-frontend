package br.com.onetec.application.views.home;

import br.com.onetec.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "",layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView() {
        // Logo
        Image logo = new Image("https://nagasakidedetizacao.com.br/wp-content/uploads/2020/08/Logo-Nagasaki.png", "Nagazaki Logo");
        logo.setHeight("100px");

        // Título
        H1 title = new H1("Bem-vindo à Nagazaki!");

        // Botões de navegação
        Button button1 = new Button("Funcionalidade 1");
        Button button2 = new Button("Funcionalidade 2");

        // Container para os botões
        Div buttonContainer = new Div(button1, button2);
        buttonContainer.addClassName("button-container");

        // Adicionando os componentes ao layout principal
        add(logo, title, buttonContainer);

        // Estilizando o layout
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
    }
}
