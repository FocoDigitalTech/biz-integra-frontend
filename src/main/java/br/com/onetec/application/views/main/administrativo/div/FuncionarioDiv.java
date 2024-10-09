package br.com.onetec.application.views.main.administrativo.div;

import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.main.administrativo.modal.FuncionarioCadastroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
@UIScope
public class FuncionarioDiv extends Div {

    List<SetDepartamento> departamentoLista = new ArrayList<>();
    private Grid<SetFuncionario> funcionarioGrid;

    private FiltersFuncionario filtersFuncionario;

    private  FuncionarioService funcionarioService;

    private  DepartamentoService departamentoService;

    private  FuncionarioCadastroModal funcionarioCadastroModal;

    Button btnExcluir;


    @Autowired
    public void initServices(FuncionarioCadastroModal funcionarioCadastroModal,
                             DepartamentoService departamentoService1,
                             FuncionarioService funcionarioService) {
        this.funcionarioCadastroModal = funcionarioCadastroModal;
        this.departamentoService = departamentoService1;
        this.funcionarioService = funcionarioService;
        funcionarioCadastroModal.addDialogCloseActionListener(event -> {
            // Código para atualizar a AdministrativoView
            refreshGridFuncionario();
        });
    }


    @Autowired
    public FuncionarioDiv( ) {
        UI.getCurrent().access(() -> {
        add(telaDiv());
        });

    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        Component gridFuncionario = createGridFuncionario();
        filtersFuncionario = new FuncionarioDiv.FiltersFuncionario(() -> refreshGridFuncionario());
        HorizontalLayout mobileFiltersFuncionario = createMobileFiltersFuncionario();

        VerticalLayout layout = new VerticalLayout(mobileFiltersFuncionario, filtersFuncionario, gridFuncionario);
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
                //.setSortable(true)
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

        funcionarioGrid.addColumn(funcionario -> {
            SetDepartamento departamento = departamentoService.findById(funcionario.getId_departamento());
            return departamento == null ? "N/A" : departamento.getDescricao_departamento();
        })
                .setHeader("Departamento")
                .setSortable(true)
                .setAutoWidth(true);





        // Adiciona o listener de clique nos itens da grade
        funcionarioGrid.addItemClickListener(event -> {
            // Configura o botão "Deletar" para deletar o item clicado
            btnExcluir.addClickListener(event1 -> deleta(event.getItem()));

            // Torna o botão "Deletar" visível
            btnExcluir.setVisible(true);
        });


        funcionarioGrid.setItems(query -> funcionarioService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filtersFuncionario).stream());


        funcionarioGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        funcionarioGrid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return funcionarioGrid;
    }
    public void refreshGrid() {
        funcionarioGrid.getDataProvider().refreshAll();
    }

    private void deleta(SetFuncionario item) {
        UtilitySystemConfigService service = new UtilitySystemConfigService();
        try {
            funcionarioService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private void openDetalhesFuncionarioModal(SetFuncionario item) {
        btnExcluir = new Button();
        btnExcluir.setVisible(true);
        btnExcluir.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }



    public class FiltersFuncionario extends Div implements Specification<SetFuncionario> {


        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Nome");
        private final MultiSelectComboBox<SetDepartamento> departamento = new MultiSelectComboBox<>("Departamento");


        public FiltersFuncionario(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            id.setPlaceholder("Código");

            //adcionar lista de funcionarios abaixo
            departamento.setItems(departamentoLista);
            departamento.setItemLabelGenerator(SetDepartamento::getDescricao_departamento);



            // Action buttons
            com.vaadin.flow.component.button.Button resetBtn = new com.vaadin.flow.component.button.Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                id.clear();
                nome.clear();
                departamento.clear();
                onSearch.run();
            });
            com.vaadin.flow.component.button.Button createBtn = createFuncionarioCadastroButton();
            com.vaadin.flow.component.button.Button searchBtn = new com.vaadin.flow.component.button.Button("Buscar");
            btnExcluir = new Button("Excluir");
            btnExcluir.setVisible(false);
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());



            Div actions = new Div(btnExcluir,resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(id, nome, departamento, actions);
        }



        @Override
        public Predicate toPredicate(Root<SetFuncionario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_funcionario"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "nome_funcionario";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, nome.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            }


            if (!departamento.isEmpty()) {
                String databaseColumn = "id_departamento";
                List<Predicate> occupationPredicates = new ArrayList<>();
                for (SetDepartamento occupation : departamento.getValue()) {
                    occupationPredicates
                            .add(criteriaBuilder.equal(criteriaBuilder.literal(occupation.getId_departamento()), root.get(databaseColumn)));
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
