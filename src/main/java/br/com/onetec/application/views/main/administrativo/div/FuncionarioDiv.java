package br.com.onetec.application.views.main.administrativo.div;

import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.main.administrativo.modal.FuncionarioCadastroModal;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class FuncionarioDiv extends Div {

    private Grid<SetFuncionario> funcionarioGrid;

    private FiltersFuncionario filtersFuncionario;


    private final FuncionarioService funcionarioService;


    private final DepartamentoService departamentoService;

    @Autowired
    private final FuncionarioCadastroModal funcionarioCadastroModal;

    Button btnExcluir;

    @Autowired
    public FuncionarioDiv(FuncionarioService funcionarioService, DepartamentoService departamentoService, FuncionarioService funcionarioService1, DepartamentoService departamentoService1, FuncionarioCadastroModal funcionarioCadastroModal) {
        this.funcionarioService = funcionarioService1;
        this.departamentoService = departamentoService1;
        this.funcionarioCadastroModal = funcionarioCadastroModal;
        add(telaDiv());

    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        filtersFuncionario = new FuncionarioDiv.FiltersFuncionario(() -> refreshGridFuncionario());
        VerticalLayout layout = new VerticalLayout(createMobileFiltersFuncionario(), filtersFuncionario, createGridFuncionario());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }

    public void refreshGridFuncionario() {
        funcionarioGrid.getDataProvider().refreshAll();
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
            if (filtersFuncionario.getClassNames().contains("visible")) {
                filtersFuncionario.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filtersFuncionario.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    private Component createGridFuncionario() {
        //departamentoService.list(null,null);
        funcionarioGrid = new Grid<>(SetFuncionario.class, false);
        funcionarioGrid.addColumn(SetFuncionario::getId_funcionario)
                .setHeader("ID")
                .setSortable(true)
                .setAutoWidth(true);

        funcionarioGrid.addColumn(SetFuncionario::getNome_funcionario)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);

        funcionarioGrid.addColumn(SetFuncionario::getData_admissao)
                .setHeader("Data de Admissão")
                .setSortable(true)
                .setAutoWidth(true);

        funcionarioGrid.addColumn(SetFuncionario::getEndereco_funcionario)
                .setHeader("Endereço")
                .setSortable(true)
                .setAutoWidth(true);

        funcionarioGrid.addColumn(SetFuncionario::getBairro_funcionario)
                .setHeader("Bairro")
                .setSortable(true)
                .setAutoWidth(true);

        funcionarioGrid.addColumn(SetFuncionario::getCep_funcionario)
                .setHeader("CEP")
                .setSortable(true)
                .setAutoWidth(true);

//        funcionarioGrid.addColumn(funcionario -> {
//            SetDepartamento departamento = departamentoService.findById(funcionario.getId_departamento());
//            return departamento != null ? departamento.getDescricao_departamento() : "N/A";
//        })
//                .setHeader("Departamento")
//                .setSortable(true)
//                .setAutoWidth(true);



        // Adiciona o listener de clique nos itens da grade
        funcionarioGrid.addItemClickListener(event -> openDetalhesFuncionarioModal(event.getItem()));


        funcionarioGrid.setItems(query -> funcionarioService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filtersFuncionario).stream());

        funcionarioGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        funcionarioGrid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return funcionarioGrid;
    }
    private void openDetalhesFuncionarioModal(SetFuncionario item) {
        btnExcluir = new Button();
        btnExcluir.setVisible(true);
        btnExcluir.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    public class FiltersFuncionario extends Div implements Specification<SetFuncionario> {

        private final com.vaadin.flow.component.textfield.TextField name = new com.vaadin.flow.component.textfield.TextField("Código");
        private final com.vaadin.flow.component.textfield.TextField phone = new TextField("Descrição");
        private final MultiSelectComboBox<String> occupations = new MultiSelectComboBox<>("Responsável");

        public FiltersFuncionario(Runnable onSearch) {

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
            com.vaadin.flow.component.button.Button createBtn = createFuncionarioCadastroButton();
            com.vaadin.flow.component.button.Button searchBtn = new com.vaadin.flow.component.button.Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());



            Div actions = new Div(resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name, phone, occupations, actions);
        }



        @Override
        public Predicate toPredicate(Root<SetFuncionario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            java.util.List<Predicate> predicates = new ArrayList<>();


            if (!name.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(name.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_funcionario"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!phone.isEmpty()) {
                String databaseColumn = "nome_funcionario";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, phone.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            }


            if (!occupations.isEmpty()) {
                String databaseColumn = "bairro_funcionario";
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

    private Button createFuncionarioCadastroButton() {
        Button cadastroButton = new Button("Cadastrar", event -> openCadastroFuncionarioModal());
        return cadastroButton;
    }


    private void openCadastroFuncionarioModal() {
        funcionarioCadastroModal.open();
    }
}
