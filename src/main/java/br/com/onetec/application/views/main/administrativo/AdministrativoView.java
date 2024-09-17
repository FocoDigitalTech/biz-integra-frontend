package br.com.onetec.application.views.main.administrativo;


import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.application.views.main.administrativo.div.FuncionarioDiv;
import br.com.onetec.application.views.main.administrativo.modal.DepartamentoCadastroModal;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessDeniedErrorRouter;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Route(value = "administrativo", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ADMINISTRATIVO_NAV_TITLE)
@PermitAll
@org.springframework.stereotype.Component
@UIScope
public class AdministrativoView extends Div {


//    @RolesAllowed("USER")
//    @AccessDeniedErrorRouter(rerouteToError = NotFoundException.class)

    private  FuncionarioDiv funcionarioDiv;

    private FornecedorDiv fornecedorDiv;

    private Grid<SetDepartamento> departamentoGrid;


    private FiltersDepartamento filtersDepartamento;


    private DepartamentoService departamentoService;


    private FuncionarioService funcionarioService;


    private DepartamentoCadastroModal departamentoCadastroModal;

    @Autowired
    public void initServices(FuncionarioDiv funcionarioDiv,
                             FornecedorDiv fornecedorDiv1,
                             DepartamentoService departamentoService,
                             FuncionarioService funcionarioService,
                             DepartamentoCadastroModal departamentoCadastroModal) {
        this.fornecedorDiv = fornecedorDiv1;
        this.funcionarioDiv = funcionarioDiv;
        this.departamentoService = departamentoService;
        this.funcionarioService = funcionarioService;
        this.departamentoCadastroModal = departamentoCadastroModal;
    }


    @Autowired
    public AdministrativoView(FuncionarioDiv funcionarioDiv1,
                                FornecedorDiv fornecedorDiv1) {

        this.funcionarioDiv = funcionarioDiv1;
        this.fornecedorDiv = fornecedorDiv1;
        UI.getCurrent().access(() -> {
        setSizeFull();
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Departamentos",
                departamentosDiv());
        tabSheet.add("Funcionarios",
                funcionarioDiv);
        tabSheet.add("Fornecedores",
                fornecedorDiv);
        tabSheet.add("Compras",
                new Div(new Text("This is the Shipping tab content")));
        tabSheet.add("Tabelas de Serviço",
                new Div(new Text("This is the Shipping tab content")));
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
        add(tabSheet);
        });
    }



    private Div departamentosDiv() {
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        filtersDepartamento = new FiltersDepartamento(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filtersDepartamento, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }

    public void refreshGrid() {
        departamentoGrid.getDataProvider().refreshAll();
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
            if (filtersDepartamento.getClassNames().contains("visible")) {
                filtersDepartamento.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filtersDepartamento.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }




    private Component createGrid() {
        //departamentoService.list(null,null);
        departamentoGrid = new Grid<>(SetDepartamento.class, false);
        departamentoGrid.addColumn(SetDepartamento::getId_departamento)
                .setHeader("ID")
                .setSortable(true)
                .setAutoWidth(true);

        departamentoGrid.addColumn(SetDepartamento::getDescricao_departamento)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);


        departamentoGrid.addColumn(departamento -> {
            SetFuncionario funcionario = funcionarioService.findById(departamento.getId_funcionario());
            return funcionario != null ? funcionario.getNome_funcionario() : "N/A";

        })
                .setHeader("Responsável")
                .setSortable(true)
                .setAutoWidth(true);



        // Adiciona o listener de clique nos itens da grade
        departamentoGrid.addItemClickListener(event -> openDetalhesClienteModal(event.getItem()));


        departamentoGrid.setItems(query -> departamentoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filtersDepartamento).stream());

        departamentoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        departamentoGrid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return departamentoGrid;
    }

    private void openDetalhesClienteModal(SetDepartamento item) {

    }




    public class FiltersDepartamento extends Div implements Specification<SetDepartamento> {

        private final com.vaadin.flow.component.textfield.TextField name = new com.vaadin.flow.component.textfield.TextField("Código");
        private final com.vaadin.flow.component.textfield.TextField phone = new TextField("Descrição");
        private final MultiSelectComboBox<String> occupations = new MultiSelectComboBox<>("Responsável");

        public FiltersDepartamento(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            name.setPlaceholder("Código");

            //adcionar lista de funcionarios abaixo
            occupations.setItems("Danilo", "Mortarman", "Coil Cleaner", "Scale Attendant");


            // Action buttons
            com.vaadin.flow.component.button.Button resetBtn = new com.vaadin.flow.component.button.Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                name.clear();
                phone.clear();
                occupations.clear();
                onSearch.run();
            });
            com.vaadin.flow.component.button.Button createBtn = createCadastroButton();
            com.vaadin.flow.component.button.Button searchBtn = new com.vaadin.flow.component.button.Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name, phone, occupations, actions);
        }



        @Override
        public Predicate toPredicate(Root<SetDepartamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            java.util.List<Predicate> predicates = new ArrayList<>();


            if (!name.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(name.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_departamento"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!phone.isEmpty()) {
                String databaseColumn = "descricao_departamento";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, phone.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            }


            if (!occupations.isEmpty()) {
                String databaseColumn = "id_funcionario";
                List<Predicate> occupationPredicates = new ArrayList<>();
                for (String occupation : occupations.getValue()) {
                    occupationPredicates
                            .add(criteriaBuilder.equal(criteriaBuilder.literal(occupation), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(occupationPredicates.toArray(Predicate[]::new)));
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

    private com.vaadin.flow.component.button.Button createCadastroButton() {
        com.vaadin.flow.component.button.Button cadastroButton = new com.vaadin.flow.component.button.Button("Cadastrar", event -> openCadastroModal());
        return cadastroButton;
    }


    private void openCadastroModal() {
        departamentoCadastroModal.open();
    }
}


