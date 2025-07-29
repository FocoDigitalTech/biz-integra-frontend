package br.com.onetec.application.views.main.relatorios;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.relatorios.div.RelatorioAgendamentoDiv;
import br.com.onetec.application.views.main.relatorios.div.RelatorioMidiasDiv;
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
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Route(value = "relatorios", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.REPORT_NAV_TITLE)
@PermitAll
@Component
@UIScope
public class RelatoriosView extends VerticalLayout {



        private RelatorioAgendamentoDiv agendamentoDiv;
        private RelatorioMidiasDiv relatorioMidiasDiv;




        @Autowired
        public void initServices(RelatorioAgendamentoDiv agendamentoDiv1,
                                 RelatorioMidiasDiv relatorioMidiasDiv1){

                this.agendamentoDiv = agendamentoDiv1;
                this.relatorioMidiasDiv = relatorioMidiasDiv1;
        }

        @Autowired
        public RelatoriosView(){
                UI.getCurrent().access(() -> {
                        setSizeFull();
                        TabSheet tabSheet = new TabSheet();
                        tabSheet.setSizeFull();

                        // Corrigir a adição da MetricsView retornando um componente
                        tabSheet.add("Overview", MetricsView());

                        // Adicionar o overviewDiv na aba de Clientes
                        tabSheet.add("Agendamentos", agendamentoDiv);
                         tabSheet.add("Relatório de Midias", relatorioMidiasDiv);

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

                HorizontalLayout chartLayout = new HorizontalLayout();
                chartLayout.setWidthFull();
                chartLayout.setSpacing(true);

                // Container do gráfico de pizza
                Div pieChartContainer = new Div();
                pieChartContainer.setId("pieChartContainer");
                pieChartContainer.getElement().setProperty("innerHTML", "<canvas id='pieChart'></canvas>");
                pieChartContainer.setWidth("50%");

                // Container do gráfico de colunas
                Div barChartContainer = new Div();
                barChartContainer.setId("barChartContainer");
                barChartContainer.getElement().setProperty("innerHTML", "<canvas id='barChart'></canvas>");
                barChartContainer.setWidth("50%");

                // Adiciona os gráficos ao layout
                chartLayout.add(pieChartContainer, barChartContainer);

                // Adiciona o layout à view principal
                //add(chartLayout);

                // Cria os gráficos
                createPieChart();
                createBarChart();

                // Adicionar o layout dos cards ao layout principal
                metricsLayout.add(cardsLayout,chartLayout);


                return metricsLayout; // Retornar o layout contendo os cards
        }

        private void createPieChart() {
                getElement().executeJs(
                        "window.createPieChart('pieChart', $0, $1)",
                        getPieChartData(), getChartOptions()
                );
        }

        private void createBarChart() {
                getElement().executeJs(
                        "window.createBarChart('barChart', $0, $1)",
                        getBarChartData(), getChartOptions()
                );
        }

        private JsonObject getPieChartData() {
                JsonObject data = Json.createObject();

                JsonArray labels = Json.createArray();
                labels.set(0, "Category A");
                labels.set(1, "Category B");
                labels.set(2, "Category C");
                data.put("labels", labels);

                JsonArray datasetData = Json.createArray();
                datasetData.set(0, 12);
                datasetData.set(1, 19);
                datasetData.set(2, 3);

                JsonObject dataset = Json.createObject();
                dataset.put("label", "Categories");
                dataset.put("backgroundColor", createColorArray());
                dataset.put("data", datasetData);

                JsonArray datasets = Json.createArray();
                datasets.set(0, dataset);
                data.put("datasets", datasets);

                return data;
        }

        private JsonObject getBarChartData() {
                JsonObject data = Json.createObject();

                JsonArray labels = Json.createArray();
                labels.set(0, "January");
                labels.set(1, "February");
                labels.set(2, "March");
                labels.set(3, "April");
                labels.set(4, "May");
                data.put("labels", labels);

                JsonArray datasetData = Json.createArray();
                datasetData.set(0, 30);
                datasetData.set(1, 20);
                datasetData.set(2, 50);
                datasetData.set(3, 40);
                datasetData.set(4, 60);

                JsonObject dataset = Json.createObject();
                dataset.put("label", "Monthly Sales");
                dataset.put("backgroundColor", "#36A2EB");
                dataset.put("data", datasetData);

                JsonArray datasets = Json.createArray();
                datasets.set(0, dataset);
                data.put("datasets", datasets);

                return data;
        }

        private JsonArray createColorArray() {
                JsonArray colors = Json.createArray();
                colors.set(0, "#FF6384");
                colors.set(1, "#36A2EB");
                colors.set(2, "#FFCE56");
                return colors;
        }

        private JsonObject getChartOptions() {
                JsonObject options = Json.createObject();
                options.put("responsive", true);
                return options;
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