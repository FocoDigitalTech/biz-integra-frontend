package br.com.onetec.application.views.main.home;

import br.com.onetec.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@Route(value = "",layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView() {
//        // Logo
//        Image logo = new Image("https://nagasakidedetizacao.com.br/wp-content/uploads/2020/08/Logo-Nagasaki.png", "Nagazaki Logo");
//        logo.setHeight("100px");
//
//        // Título
//        H1 title = new H1("Bem-vindo à Nagasaki! "+ UsuarioAutenticadoConfig.getUser().getNome_usuario());
//
//        // Botões de navegação
//        Button button1 = new Button("Funcionalidade 1");
//        Button button2 = new Button("Funcionalidade 2");
//
//        // Container para os botões
//        Div buttonContainer = new Div(button1, button2);
//        buttonContainer.addClassName("button-container");
//
//        // Adicionando os componentes ao layout principal
//        add(logo, title, buttonContainer);
//
//        // Estilizando o layout
//        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setHeightFull();


        // Info Sections
        //VerticalLayout infoSection = createInfoSection();

        // Card Section
        HorizontalLayout cardSection = createCardSection();

        // Footer
        HorizontalLayout footer = createFooter();

        // Add all sections to the main layout
        add(cardSection, footer);

        // Full width and alignment
        setWidthFull();
        setAlignItems(Alignment.CENTER);
    }



    private VerticalLayout createInfoSection() {
        H2 infoTitle = new H2("Why Choose Us?");
        Paragraph infoText = new Paragraph("We offer the best services in the industry...");

        VerticalLayout infoSection = new VerticalLayout(infoTitle, infoText);
        infoSection.setAlignItems(Alignment.CENTER);
        infoSection.addClassName("info-section");
        return infoSection;
    }

//    private HorizontalLayout createCardSection() {
//
//        Image cardImage1 = new Image("https://via.placeholder.com/150", "Card Image 1");
//        Image cardImage2 = new Image("https://via.placeholder.com/150", "Card Image 2");
//        Image cardImage3 = new Image("https://via.placeholder.com/150", "Card Image 3");
//
//        // Card 1
//        VerticalLayout card1 = new VerticalLayout(cardImage1, new H3("Service 1"), new Paragraph("Description for service 1."), getlist());
//        card1.addClassName("clickable-card");
//        card1.addClickListener(event -> {
//            // Ação ao clicar no card 1
//            System.out.println("Card 1 clicked!");
//            // Você pode abrir um modal, redirecionar, ou executar qualquer outra ação aqui
//        });
//
//        // Card 2
//        VerticalLayout card2 = new VerticalLayout(cardImage2, new H3("Service 2"), new Paragraph("Description for service 2."), getlist());
//        card2.addClassName("clickable-card");
//        card2.addClickListener(event -> {
//            // Ação ao clicar no card 2
//            System.out.println("Card 2 clicked!");
//        });
//
//        // Card 3
//        VerticalLayout card3 = new VerticalLayout(cardImage3, new H3("Service 3"), new Paragraph("Description for service 3."), getlist());
//        card3.addClassName("clickable-card");
//        card3.addClickListener(event -> {
//            // Ação ao clicar no card 3
//            System.out.println("Card 3 clicked!");
//        });
//
//        HorizontalLayout cardSection = new HorizontalLayout( card1, card2, card3);
//        cardSection.setSpacing(true);
//        cardSection.addClassName("card-section");
//        return cardSection;
//    }

    private Accordion getlist() {
        Accordion accordion = new Accordion();

        Span name = new Span("Sophia Williams");
        Span email = new Span("sophia.williams@company.com");
        Span phone = new Span("(501) 555-9128");

        VerticalLayout personalInformationLayout = new VerticalLayout(name,
                email, phone);
        personalInformationLayout.setSpacing(false);
        personalInformationLayout.setPadding(false);

        AccordionPanel personalInfoPanel = accordion.add("Personal information",
                personalInformationLayout);
        personalInfoPanel.addThemeVariants(DetailsVariant.SMALL);

        Span street = new Span("4027 Amber Lake Canyon");
        Span zipCode = new Span("72333-5884 Cozy Nook");
        Span city = new Span("Arkansas");

        VerticalLayout billingAddressLayout = new VerticalLayout();
        billingAddressLayout.setSpacing(false);
        billingAddressLayout.setPadding(false);
        billingAddressLayout.add(street, zipCode, city);

        AccordionPanel billingAddressPanel = accordion.add("Billing address",
                billingAddressLayout);
        billingAddressPanel.addThemeVariants(DetailsVariant.SMALL);

        Span cardBrand = new Span("Mastercard");
        Span cardNumber = new Span("1234 5678 9012 3456");
        Span expiryDate = new Span("Expires 06/21");

        VerticalLayout paymentLayout = new VerticalLayout();
        paymentLayout.setSpacing(false);
        paymentLayout.setPadding(false);
        paymentLayout.add(cardBrand, cardNumber, expiryDate);

        AccordionPanel paymentPanel = accordion.add("Payment", paymentLayout);
        paymentPanel.addThemeVariants(DetailsVariant.SMALL);
        return accordion;
    }

    private HorizontalLayout createFooter() {
        Paragraph footerText = new Paragraph("© 2024 Your Company. All Rights Reserved.");

        HorizontalLayout footer = new HorizontalLayout(footerText);
        footer.setWidthFull();
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        footer.addClassName("footer-section");
        return footer;
    }

    private HorizontalLayout createCardSection() {
        List<VerticalLayout> cards = new ArrayList<>();

        addClassName("carousel-section");

        // Criação dos cards
        cards.add(createCard("Service 1", "Description for service 1.", "https://via.placeholder.com/150"));
        cards.add(createCard("Service 2", "Description for service 2.", "https://via.placeholder.com/150"));
        cards.add(createCard("Service 3", "Description for service 3.", "https://via.placeholder.com/150"));
        cards.add(createCard("Service 4", "Description for service 4.", "https://via.placeholder.com/150"));
        cards.add(createCard("Service 5", "Description for service 5.", "https://via.placeholder.com/150"));
        cards.add(createCard("Service 6", "Description for service 6.", "https://via.placeholder.com/150"));

        // Criar sessões de até 3 cards cada
        List<HorizontalLayout> sessions = new ArrayList<>();
        for (int i = 0; i < cards.size(); i += 3) {
            HorizontalLayout session = new HorizontalLayout();
            session.setSpacing(true);
            session.addClassName("card-section");

            // Adicionar até 3 cards por sessão
            for (int j = i; j < i + 3 && j < cards.size(); j++) {
                session.add(cards.get(j));
            }

            sessions.add(session);
        }

        // Contêiner para as sessões com transição
        Div sessionContainer = new Div();
        sessionContainer.setWidthFull();
        sessionContainer.addClassName("session-container");

        // Inicialmente, exibe a primeira sessão
        Div sessionSlide = new Div(sessions.get(0));
        sessionSlide.addClassName("session-slide");
        sessionContainer.add(sessionSlide);

        // Botões de navegação
        Button previousButton = new Button("Anterior", event -> switchSession(sessions, sessionSlide, -1));
        Button nextButton = new Button("Próximo", event -> switchSession(sessions, sessionSlide, 1));

        HorizontalLayout navigation = new HorizontalLayout(previousButton, nextButton);
        navigation.setJustifyContentMode(JustifyContentMode.CENTER);

        // Layout final
        VerticalLayout cardSectionLayout = new VerticalLayout(sessionContainer, navigation);
        cardSectionLayout.setAlignItems(Alignment.CENTER);
        cardSectionLayout.setWidthFull();

        return new HorizontalLayout(cardSectionLayout);
    }



    private VerticalLayout createCard(String title, String description, String imageUrl) {
        Image cardImage = new Image(imageUrl, title);
        VerticalLayout card = new VerticalLayout(cardImage, new H3(title), new Paragraph(description), getlist());
        card.addClassName("clickable-card");
        card.addClickListener(event -> {
            System.out.println(title + " clicked!");
        });
        return card;
    }

    private void switchSession(List<HorizontalLayout> sessions, Div sessionSlide, int direction) {
        // Obtenha a sessão atual exibida
        HorizontalLayout currentSession = (HorizontalLayout) sessionSlide.getComponentAt(0);
        int currentIndex = sessions.indexOf(currentSession);
        int newIndex = currentIndex + direction;

        // Garantir que o índice seja válido
        if (newIndex >= 0 && newIndex < sessions.size()) {
            // Remova as classes de transição anteriores
            sessionSlide.removeClassName("slide-left");
            sessionSlide.removeClassName("slide-right");
            sessionSlide.removeClassName("slide-enter");

            // Defina a direção do deslizamento
            if (direction > 0) {
                sessionSlide.addClassName("slide-left");
            } else {
                sessionSlide.addClassName("slide-right");
            }

            // Aguarde a animação antes de trocar a sessão
            sessionSlide.getUI().ifPresent(ui -> {
                ui.access(() -> {
                    // Remova a sessão atual
                    sessionSlide.removeAll();
                    // Adicione a nova sessão
                    sessionSlide.add(sessions.get(newIndex));
                    sessionSlide.addClassName("slide-enter");
                });
            });
        }
    }




}