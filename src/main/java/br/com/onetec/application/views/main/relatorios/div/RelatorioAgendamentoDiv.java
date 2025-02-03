package br.com.onetec.application.views.main.relatorios.div;

import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
public class RelatorioAgendamentoDiv extends Div {


    private final DatePicker startDate = new DatePicker("Data");
    private final DatePicker endDate = new DatePicker();
    private final ComboBox<String> tipoContrato = new ComboBox<>("Tipo");//tipo
    private final RadioButtonGroup abreviaradio = new RadioButtonGroup("Abreviar nome dos serviços ?");//abrevia ?
    private Checkbox abreviarCheckbox;
    private Button btnImprimir = new Button("Imprimir");//imprimir
    private UtilitySystemConfigService service;
    private Grid<SetContrato> grid;
    private RelatorioAgendamentoDiv.Filter filter;
    private UsuarioService usuarioService;
    private final OrcamentoService orcamentoService;
    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final ClientesService clientesService;
    private final OrdemServicoService ordemServicoService;
    private final ServicosOrcamentoService servicosOrcamentoService;
    private final ServicoService servicoService;
    private boolean abreviaverificacao = false;

    @Autowired
    public RelatorioAgendamentoDiv(OrcamentoService orcamentoService1, ContratoService contratoService1,
                                   FuncionarioService funcionarioService1, ClientesService clientesService1,
                                   OrdemServicoService ordemServicoService1,
                                   ServicosOrcamentoService servicosOrcamentoService1, ServicoService servicoService1) {
        this.orcamentoService = orcamentoService1;
        this.contratoService = contratoService1;
        this.funcionarioService = funcionarioService1;
        this.clientesService = clientesService1;
        this.ordemServicoService = ordemServicoService1;
        this.servicosOrcamentoService = servicosOrcamentoService1;
        this.servicoService = servicoService1;
        UI.getCurrent().access(() -> {
            add(telaDiv());

        });
    }

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1) {
        this.service = service1;
        this.usuarioService = usuarioService1;
    }

    private Div telaDiv() {
        // Configura o tamanho total do componente principal
        setSizeFull();
        addClassNames("telarelatorios-view");

        // Criação dos componentes da tela
        com.vaadin.flow.component.Component gridFuncionario = createGrid();
        filter = new RelatorioAgendamentoDiv.Filter(this::refreshGrid);
        HorizontalLayout mobileFiltersFuncionario = createMobileFiltersFuncionario();

        // Configuração do layout principal
        VerticalLayout layout = new VerticalLayout(mobileFiltersFuncionario, filter, gridFuncionario);
        layout.setSizeFull(); // Define o layout para ocupar todo o espaço disponível
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH); // Garante que os itens ocupem todo o espaço horizontal

        Div div = new Div(layout);
        div.setSizeFull(); // Garante que o div ocupe toda a tela

        return div;
    }

    public void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private HorizontalLayout createMobileFiltersFuncionario() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filter.getClassNames().contains("visible")) {
                filter.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filter.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    private com.vaadin.flow.component.Component createGrid() {

        //departamentoService.list(null,null);
        grid = new Grid<>(SetContrato.class, false);
        grid.addColumn(SetContrato::getDatainicio_execucao)
                .setHeader("Data")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetContrato::getId_orcamento)
                .setHeader("Numero Orçamento")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetContrato::getId_contrato)
                .setHeader("Numero Contrato")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(cliente -> {
            SetCliente setCliente = clientesService.findById(cliente.getId_cliente());
            return setCliente.getNome_cliente();
        })
                .setHeader("Nome Cliente")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(cliente -> {
            List<SetOrdemServico> orcamento = ordemServicoService.findAllByOrcamentoId(cliente.getId_orcamento());
            Optional<SetOrdemServico> orcamentoMaisRecente = orcamento.stream()
                    .max(Comparator.comparing(SetOrdemServico::getDatainicio_ordemservico));
            return orcamentoMaisRecente.isPresent() ? orcamentoMaisRecente.get().getHorarioinicio_ordemservico() : "N/A";
        })
                .setHeader("Horario Atendimento")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(contrato ->
                contrato.getAplicacoes_periodicas().equals("SIM") ?
                        "Aplicações Periodicas" : "Suporte")
                .setHeader("Tipo")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(cliente -> {
            List<SetOrdemServico> orcamento = ordemServicoService.findAllByOrcamentoId(cliente.getId_orcamento());
            Optional<SetOrdemServico> orcamentoMaisRecente = orcamento.stream()
                    .min(Comparator.comparing(SetOrdemServico::getData_inclusao));
            if (orcamentoMaisRecente.isPresent()) {
                SetFuncionario funcionario = funcionarioService.findById(orcamentoMaisRecente.get().getId_funcionariotecnico());
                return funcionario.getNome_funcionario();
            } else {
                return "N/D";
            }
        })
                .setHeader("Tecnico")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(item -> {
            List<SetServicosOrcamento> servicosListOrcamneto = servicosOrcamentoService.listByOrcamento(item.getId_orcamento());
            List<SetServico> servicos = servicoService.listAll();

            Set<Integer> idsServicosOrcamento = servicosListOrcamneto.stream()
                    .map(SetServicosOrcamento::getId_servico)  // Extrair os IDs de servicosListOrcamento
                    .collect(Collectors.toSet());

            List<SetServico> servicosFiltrados = servicos.stream()
                    .filter(servico -> idsServicosOrcamento.contains(servico.getId_servico()))
                    .collect(Collectors.toList());

            StringBuilder servicosfraseBuilder = new StringBuilder();

            servicosFiltrados.forEach(setServico -> {
                String descricao = setServico.getDescricao_servico();

                if (abreviaverificacao) {
                    // Abreviação: Pega somente as iniciais das palavras
                    String abreviado = Arrays.stream(descricao.split(" ")) // Divide em palavras
                            .map(palavra -> palavra.substring(0, 1).toUpperCase()) // Pega a inicial e deixa em maiúsculo
                            .collect(Collectors.joining("")); // Junta as iniciais
                    servicosfraseBuilder.append(abreviado).append(", ");
                } else {
                    // Sem abreviação: Adiciona a descrição completa
                    servicosfraseBuilder.append(descricao).append(", ");
                }

            });

            // Remove a última vírgula e espaço, se necessário
            String servicosfrase = servicosfraseBuilder.toString().replaceAll(", $", "");
            return servicosfrase;
        })
                .setHeader("Serviços")
                .setSortable(true)
                .setAutoWidth(true);


        grid.setItems(query -> contratoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);


        return grid;
    }

    public class Filter extends Div implements Specification<SetContrato> {


        public Filter(Runnable onSearch) {


            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            // Action buttons
            com.vaadin.flow.component.button.Button resetBtn = new com.vaadin.flow.component.button.Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                startDate.clear();
                endDate.clear();
                onSearch.run();
                tipoContrato.clear();
                abreviaradio.clear();
            });
            //com.vaadin.flow.component.button.Button createBtn = createFuncionarioCadastroButton();
            com.vaadin.flow.component.button.Button searchBtn = new com.vaadin.flow.component.button.Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            btnImprimir = new Button("Imprimir", click -> {
                //exportGridToPDF("relatorio_agendamentos.pdf");
            });
            btnImprimir.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            abreviarCheckbox = new Checkbox("Abreviar nome dos serviços?");
            abreviarCheckbox.addValueChangeListener(event -> {
                abreviaverificacao = event.getValue(); // Marca a opção de abreviar
                refreshGrid();  // Atualiza o Grid quando a opção mudar
            });

            tipoContrato.setItems(List.of("Aplicações Periodicas", "Suporte"));
            tipoContrato.addValueChangeListener(event -> {
                if ("Aplicações Periodicas".equals(event.getValue())) {

                } else if ("Suporte".equals(event.getValue())) {
                }
            });


            Div actions = new Div(resetBtn, searchBtn, btnImprimir);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");


            add(createDateRangeFilter(), tipoContrato, abreviarCheckbox, actions);
        }

        private com.vaadin.flow.component.Component createDateRangeFilter() {
            startDate.setPlaceholder("De");

            endDate.setPlaceholder("Até");

            // For screen readers
            startDate.setAriaLabel("Data Inicio");
            endDate.setAriaLabel("Data Fim");

            FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }


        @Override
        public Predicate toPredicate(Root<SetContrato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if ("Aplicações Periodicas".equals(tipoContrato.getValue())) {
                String databaseColumn = "aplicacoes_periodicas";
                String ignore = "- ()";

                String lowerCaseFilter = "SIM";
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            } else if ("Suporte".equals(tipoContrato.getValue())) {
                String databaseColumn = "aplicacoes_periodicas";
                String ignore = "- ()";

                String lowerCaseFilter = "NÃO";
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);
            }


            if (startDate.getValue() != null) {
                String databaseColumn = "datainicio_execucao";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "datainicio_execucao";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }

        private String ignoreCharacters(String characters, String in) {
            String result = in;
            for (int i = 0; i < characters.length(); i++) {
                result = result.replace("" + characters.charAt(i), "");
            }
            return result;
        }

        private Expression<String> ignoreCharacters(String characters, CriteriaBuilder criteriaBuilder,
                                                    Expression<String> inExpression) {
            Expression<String> expression = inExpression;
            for (int i = 0; i < characters.length(); i++) {
                expression = criteriaBuilder.function("replace", String.class, expression,
                        criteriaBuilder.literal(characters.charAt(i)), criteriaBuilder.literal(""));
            }
            return expression;
        }

    }

    public void exportGridToPDF(String filePath) {
        try {
            // Criação do documento PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Título do relatório
            Paragraph title = new Paragraph("Relatório de Agendamentos")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Espaço após o título
            document.add(new Paragraph("\n"));

            // Configuração da tabela (baseada no número de colunas do grid)
            Table table = new Table(grid.getColumns().size());
            table.setWidth(100f);

            // Adicionando cabeçalhos ao PDF
            grid.getColumns().forEach(column -> {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(column.getHeaderText()))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold());
            });

            // Adicionando os dados do Grid ao PDF
            grid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>())
                    .forEach(item -> {
                        grid.getColumns().forEach(column -> {
                            // Exemplo simples, adaptado dependendo do seu modelo de dados
                            Object value = item.getId_orcamento(); // ou algo similar para o seu item
                            table.addCell(new Cell().add(new Paragraph(value != null ? value.toString() : "N/A")));
                        });
                    });

            // Adicionando a tabela ao documento
            document.add(table);

            // Fechando o documento
            document.close();

            System.out.println("PDF gerado com sucesso em: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
