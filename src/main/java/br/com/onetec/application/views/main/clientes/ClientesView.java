package br.com.onetec.application.views.main.clientes;

import br.com.onetec.application.service.clientesservice.*;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.clientes.modal.CadastroClientesModal;
import br.com.onetec.application.views.main.clientes.modal.DadosClienteModal;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.infra.db.model.SetCliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@Route(value = "clientes",layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
@PageTitle(ViewsTitleConst.CLIENTES_NAV_TITLE)
@org.springframework.stereotype.Component
public class ClientesView extends Div {

    private Grid<SetCliente> grid;

    private Filters filters;

    @Autowired
    ClientesService clientesService;

    @Autowired
    EstadoService estadoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ResponsavelCobrancaService responsavelCobrancaService;

    @Autowired
    ResponsavelAgendamentoService responsavelAgendamentoService;

    @Autowired
    ResponsavelAprovacaoService responsavelAprovacaoService;

    public ClientesView() {
        setSizeFull();
        addClassNames("telarelatorios-view");

        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
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
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    private Button createCadastroButton() {
        Button cadastroButton = new Button("Cadastrar", event -> openCadastroModal());
        return cadastroButton;
    }



    public class Filters extends Div implements Specification<SetCliente> {

        /*
        *

Contato
Fone
Endereço
Internet
Contato
Aprovação
Cobrança
Fax
Usuário
* */
        private final TextField name = new TextField("Nome");
        private final TextField phone = new TextField("Telefone");
        private final DatePicker startDate = new DatePicker("Ultimo Orçamento");
        private final DatePicker endDate = new DatePicker();
        private final MultiSelectComboBox<String> occupations = new MultiSelectComboBox<>("Occupation");
        private final CheckboxGroup<String> roles = new CheckboxGroup<>("Situação");

        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            name.setPlaceholder("First or last name");

            occupations.setItems("Insurance Clerk", "Mortarman", "Beer Coil Cleaner", "Scale Attendant");

            roles.setItems("Pendente", "Aprovado");
            roles.addClassName("double-width");

            // Action buttons
            Button resetBtn = new Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                name.clear();
                phone.clear();
                startDate.clear();
                endDate.clear();
                occupations.clear();
                roles.clear();
                onSearch.run();
            });
            Button createBtn = createCadastroButton();
            Button searchBtn = new Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name, phone, createDateRangeFilter(), occupations, roles, actions);
        }

        private Component createDateRangeFilter() {
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
        public Predicate toPredicate(Root<SetCliente> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!name.isEmpty()) {
                String lowerCaseFilter = name.getValue().toLowerCase();
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("nome_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            if (!phone.isEmpty()) {
                String databaseColumn = "celular_cliente";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, phone.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            }
            if (startDate.getValue() != null) {
                String databaseColumn = "data_inclusao";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "data_inclusao";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
            }
            if (!occupations.isEmpty()) {
                String databaseColumn = "complemento_cliente";
                List<Predicate> occupationPredicates = new ArrayList<>();
                for (String occupation : occupations.getValue()) {
                    occupationPredicates
                            .add(criteriaBuilder.equal(criteriaBuilder.literal(occupation), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(occupationPredicates.toArray(Predicate[]::new)));
            }
            if (!roles.isEmpty()) {
                String databaseColumn = "ativo";
                List<Predicate> rolePredicates = new ArrayList<>();
                for (String role : roles.getValue()) {
                    rolePredicates.add(criteriaBuilder.equal(criteriaBuilder.literal(role), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(rolePredicates.toArray(Predicate[]::new)));
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

    private Component createGrid() {
        grid = new Grid<>(SetCliente.class, false);
        grid.addColumn(SetCliente::getData_inclusao)
                .setHeader("Ult. Orçamento")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getNome_cliente)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getCelular_cliente)
                .setHeader("Contato")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getEmail_cliente)
                .setHeader("E-mail")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getNome_fantasia_cliente)
                .setHeader("Nome Fantasia")
                .setSortable(true)
                .setAutoWidth(true);

        // Adiciona o listener de clique nos itens da grade
        grid.addItemClickListener(event -> openDetalhesClienteModal(event.getItem()));


        grid.setItems(query -> clientesService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private void openDetalhesClienteModal(SetCliente cliente) {
        DadosClienteModal detalhesClienteModal = new DadosClienteModal(cliente,
                                                                        clientesService,
                                                                        estadoService,
                                                                        usuarioService,
                                                                        enderecoService,
                                                                        responsavelCobrancaService,
                                                                        responsavelAgendamentoService,
                                                                        responsavelAprovacaoService);
        detalhesClienteModal.open();
    }



    private void openCadastroModal() {
        CadastroClientesModal cadastroModal = new CadastroClientesModal(clientesService,
                estadoService,
                usuarioService,
                enderecoService,
                responsavelCobrancaService,
                responsavelAgendamentoService,
                responsavelAprovacaoService);
        cadastroModal.open();
    }
}

