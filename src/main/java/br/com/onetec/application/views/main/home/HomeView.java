package br.com.onetec.application.views.main.home;

import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.model.SetOrdemServico;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Route(value = "",layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {


    private OrdemServicoService ordemServicoService;


    private FuncionarioService funcionarioService;


    private EnderecoService enderecoService;

    @Autowired
    public void initServices(OrdemServicoService ordemServicoServicev1, FuncionarioService funcionarioService1,
                             EnderecoService enderecoService1) {
        this.enderecoService = enderecoService1;
        this.ordemServicoService = ordemServicoServicev1;
        this.funcionarioService = funcionarioService1;
    }

    @Autowired
    public HomeView(OrdemServicoService ordemServicoServicev1, FuncionarioService funcionarioService1,
                    EnderecoService enderecoService1) {
        this.enderecoService = enderecoService1;
        this.ordemServicoService = ordemServicoServicev1;
        this.funcionarioService = funcionarioService1;
        UI.getCurrent().access(() -> {
//        // Logo


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
        });
    }

    private Accordion getlist1(List<SetOrdemServico> listaPorFuncionario) {
        Accordion accordion = new Accordion();

        listaPorFuncionario.forEach(ordem -> {
            Span name = new Span("OS: " + ordem.getId_ordemservico().toString());
            Span email = new Span(ordem.getHorarioinicio_ordemservico().toString());
            Span pontoFocal = new Span (ordem.getNome_pontofocal());
            Span phone = new Span(ordem.getOcorrencias_ordemservico());
            VerticalLayout personalInformationLayout = new VerticalLayout(name,
                    email,pontoFocal, phone);
            personalInformationLayout.setSpacing(false);
            personalInformationLayout.setPadding(false);

            SetEnderecos enderecos = enderecoService.findAllById(ordem.getId_endereco());

            AccordionPanel personalInfoPanel = accordion.add(enderecos.getEndereco_imovel(),
                    personalInformationLayout);
            personalInfoPanel.addThemeVariants(DetailsVariant.SMALL);
        });

        return accordion;
    }


    private HorizontalLayout createFooter() {

        LocalDate a = LocalDate.now();
        Paragraph footerText = new Paragraph("Mapa de Programação Diario : " + a.getDayOfMonth() + "/" + a.getMonthValue() + "/" +
                a.getYear());

        HorizontalLayout footer = new HorizontalLayout(footerText);
        footer.setWidthFull();
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        footer.addClassName("footer-section");
        return footer;
    }


    private HorizontalLayout createCardSection() {
        List<VerticalLayout> cards = new ArrayList<>();
        List<SetOrdemServico> listaOrdens = ordemServicoService.findAll();

        List<SetOrdemServico> novaLista = new ArrayList<>();
        List<SetFuncionario> listaAssistente = funcionarioService.listAll();

        addClassName("carousel-section");

        listaOrdens.forEach(ordem -> {
            if (ordem.getDatainicio_ordemservico().equals(LocalDate.now())) {
                novaLista.add(ordem);
            }
        });
        if (novaLista.size() > 0) {
            listaAssistente.forEach(assistente -> {
                List<SetOrdemServico> listaPorFuncionario = new ArrayList<>();
                Boolean existe;
                SetFuncionario assist = assistente;
                novaLista.forEach(ordem -> {
                    if (assist.getId_funcionario().equals(ordem.getId_funcionariotecnico())) {
                        listaPorFuncionario.add(ordem);
                    }
                });

                if (!listaPorFuncionario.isEmpty()) {
                    cards.add(createCard1(listaPorFuncionario, assist));
                }
            });

//        novaLista.forEach(ordem -> {
//            cards.add(createCard1(ordem));
//        });


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
        } else {
            return new HorizontalLayout(new Div());
        }
    }


    private VerticalLayout createCard1(List<SetOrdemServico> listaPorFuncionario, SetFuncionario assistente) {
        SetFuncionario funcionario = funcionarioService.findById(listaPorFuncionario.get(0).getId_funcionarioassistente());
        String title = assistente.getNome_funcionario() + " e " + funcionario.getNome_funcionario();
        String description = "Abaixo programação :";
        VerticalLayout card = new VerticalLayout(new H3(title), new Paragraph(description), getlist1(listaPorFuncionario));
        card.addClassName("clickable-card");
        // Criando o modal (Dialog)
        Dialog modal = createModal(assistente, listaPorFuncionario);

        // Adicionando listener para abrir o modal ao clicar no card
        card.addClickListener(event -> {
            modal.open();  // Abre o modal
        });

        return card;
    }

    private Dialog createModal(SetFuncionario assistente, List<SetOrdemServico> listaPorFuncionario) {
        // Criando o dialog/modal
        Dialog modal = new Dialog();
        modal.setWidth("600px");
        modal.setHeight("500px");

        // Criando as abas
        Tab tab1 = new Tab("Informações");
        Tab tab2 = new Tab("Mapa");

        Tabs tabs = new Tabs(tab1, tab2);

        // Conteúdo da primeira aba (informações do funcionário)
        VerticalLayout infoLayout = new VerticalLayout(new H3("Detalhes do Funcionário: " + assistente.getNome_funcionario()));
        listaPorFuncionario.forEach(ordem -> {
            Span osId = new Span("OS: " + ordem.getId_ordemservico());
            Span horarioInicio = new Span("Início: " + ordem.getHorarioinicio_ordemservico().toString());
            Span pontoFocal = new Span("Ponto Focal: " + ordem.getNome_pontofocal().toString());
            Span ocorrencia = new Span("Ocorrência: " + ordem.getOcorrencias_ordemservico());
            infoLayout.add(new VerticalLayout(osId, horarioInicio,pontoFocal, ocorrencia));
        });

        // Conteúdo da segunda aba (OpenStreetMap com Leaflet)
        Div mapDiv = new Div();
        mapDiv.setId("map");  // Definimos o ID para poder referenciar no JavaScript
        mapDiv.setWidth("100%");
        mapDiv.setHeight("300px");

        // Layouts para cada aba
        Div infoContent = new Div(infoLayout);
        Div mapContent = new Div(mapDiv);

        // Exibir conteúdo com base na aba selecionada
        Div content = new Div(infoContent, mapContent);
        content.setSizeFull();

        mapContent.setVisible(false);  // Inicialmente, mostrar apenas as informações

        SetEnderecos end = enderecoService.findAllById(listaPorFuncionario.get(0).getId_endereco());

        // Listener para troca de abas
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab.equals(tab1)) {
                infoContent.setVisible(true);
                mapContent.setVisible(false);
            } else if (selectedTab.equals(tab2)) {
                infoContent.setVisible(false);
                mapContent.setVisible(true);
                String endereco1 = "Rua da Consolação, 1234, São Paulo, SP, Brasil";
                if (Objects.nonNull(end)){
                    endereco1 = end.getEndereco_imovel();
                }
                String endereco2 = endereco1;
                loadOpenStreetMap(endereco1,endereco2);  // Carregar o mapa quando a aba for selecionada
            }
        });

        // Botão de fechar
        Button closeButton = new Button("Fechar", event -> modal.close());

        // Adicionando tudo no modal
        VerticalLayout modalLayout = new VerticalLayout(tabs, content, closeButton);
        modal.add(modalLayout);

        return modal;
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


    private void loadOpenStreetMap(String endereco1, String endereco2) {
        try {
            // Obter coordenadas dos dois endereços
            double[] coords1 = getCoordinates(endereco1);
            double[] coords2 = getCoordinates(endereco2);

            // Carregar o mapa com base nas coordenadas do primeiro endereço
            getUI().ifPresent(ui -> {
                ui.getPage().executeJs(
                        "var map = L.map('map').setView([" + coords1[0] + ", " + coords1[1] + "], 13);" +  // Coordenadas e zoom inicial
                                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {" +
                                "    attribution: '© OpenStreetMap contributors'" +
                                "}).addTo(map);" +
                                "L.marker([" + coords1[0] + ", " + coords1[1] + "]).addTo(map).bindPopup('Endereço 1: " + endereco1 + "').openPopup();" +  // Primeiro endereço
                                "L.marker([" + coords2[0] + ", " + coords2[1] + "]).addTo(map).bindPopup('Endereço : " + endereco2 + "').openPopup();"  // Segundo endereço
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public double[] getCoordinates(String address) throws Exception {
        // Codificar o endereço para URL
        String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
        String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Processar a resposta JSON
        JSONArray jsonArray = new JSONArray(response.toString());
        if (jsonArray.length() > 0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");
            return new double[] {lat, lon};
        }

        throw new Exception("Não foi possível encontrar coordenadas para o endereço: " + address);
    }



}