package br.com.onetec.application.views.main.relatorios;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.relatorios.div.RelatorioClienteDiv;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "relatorios", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.REPORT_NAV_TITLE)
@PermitAll
public class RelatoriosView extends VerticalLayout {



        private RelatorioClienteDiv overviewDiv;




        @Autowired
        public void initServices(RelatorioClienteDiv overviewDiv1){
                this.overviewDiv=overviewDiv1;
        }

        @Autowired
        public RelatoriosView(){
                UI.getCurrent().access(() -> {
                        setSizeFull();
                        TabSheet tabSheet = new TabSheet();

                        // Corrigir a adição da MetricsView retornando um componente
                        tabSheet.add("Overview", MetricsView());

                        // Adicionar o overviewDiv na aba de Clientes
                        tabSheet.add("Clientes", overviewDiv);

                        // Adicionar estilos ao TabSheet
                        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);

                        add(tabSheet);
                });
        }

        public VerticalLayout MetricsView() {
                // Criar um layout para conter os cards
                VerticalLayout metricsLayout = new VerticalLayout();
                metricsLayout.setAlignItems(Alignment.CENTER);
                metricsLayout.setSpacing(true);

                HorizontalLayout cardsLayout = new HorizontalLayout();
                cardsLayout.setSpacing(true);
                cardsLayout.setWidthFull();
                cardsLayout.setJustifyContentMode(JustifyContentMode.CENTER);

                // Criar e adicionar os cards com accordion e delay de animação
                Div card1 = createMetricCard("Vendas", "R$ 15.000", "20% a mais que ontem",
                        List.of("Meta Mensal: R$ 50.000", "Meta Semanal: R$ 12.000"), 1);
                card1.getStyle().set("animation-delay", "0.1s");

                Div card2 = createMetricCard("Novos Clientes", "150", "10% a mais que ontem",
                        List.of("Meta Mensal: 500", "Meta Semanal: 120"), 2);
                card2.getStyle().set("animation-delay", "0.2s");

                Div card3 = createMetricCard("Receitas", "R$ 35.000", "Crescimento estável",
                        List.of("Meta Mensal: R$ 100.000", "Meta Semanal: R$ 25.000"), 3);
                card3.getStyle().set("animation-delay", "0.3s");

                Div card4 = createMetricCard("Faturamento", "R$ 70.000", "5% a menos que ontem",
                        List.of("Meta Mensal: R$ 200.000", "Meta Semanal: R$ 50.000"), 4);
                card4.getStyle().set("animation-delay", "0.4s");

                cardsLayout.add(card1, card2, card3, card4);

                // Adicionar o layout dos cards ao layout principal
                metricsLayout.add(cardsLayout);

                return metricsLayout; // Retornar o layout contendo os cards
        }


        private Div createMetricCard(String title, String value, String trend, List<String> details, int colorIndex) {
                Div card = new Div();
                card.addClassName("metric-card");

                // Alterna entre as classes de fundo com gradientes suaves
                switch (colorIndex % 4) {
                        case 1:
                                card.addClassName("card-bg-1");
                                break;
                        case 2:
                                card.addClassName("card-bg-2");
                                break;
                        case 3:
                                card.addClassName("card-bg-3");
                                break;
                        default:
                                card.addClassName("card-bg-4");
                                break;
                }

                Div titleDiv = new Div();
                titleDiv.setText(title);
                titleDiv.addClassName("metric-title");

                Div valueDiv = new Div();
                valueDiv.setText(value);
                valueDiv.addClassName("metric-value");

                Div trendDiv = new Div();
                trendDiv.setText(trend);
                trendDiv.addClassName("metric-trend");

                // Accordion para os detalhes numéricos
                Details detailsAccordion = new Details();
                detailsAccordion.setSummaryText("Ver Detalhes");
                detailsAccordion.addClassName("details-summary");

                // Conteúdo do accordion (detalhes)
                VerticalLayout detailsContent = new VerticalLayout();
                details.forEach(detail -> {
                        Div detailDiv = new Div();
                        detailDiv.setText(detail);
                        detailDiv.addClassName("details-content");
                        detailsContent.add(detailDiv);
                });
                detailsAccordion.setContent(detailsContent);
                detailsAccordion.setOpened(false); // Colapsado inicialmente

                card.add(titleDiv, valueDiv, trendDiv, detailsAccordion);
                return card;
        }



}