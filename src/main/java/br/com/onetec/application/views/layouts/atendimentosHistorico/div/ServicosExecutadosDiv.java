package br.com.onetec.application.views.layouts.atendimentosHistorico.div;

import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.layouts.atendimentosHistorico.modal.OrdemServicoCadastroModal;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.model.SetOrdemServico;
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
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ServicosExecutadosDiv extends Div {

    List<SetDepartamento> departamentoLista = new ArrayList<>();
    private Grid<SetOrdemServico> setOrdemServicoGrid;

    private ServicosExecutadosDiv.FiltersFuncionario filtersordemsservico;

    private OrdemServicoService ordemServicoService;

    private ClientesService clientesService;

    private OrdemServicoCadastroModal ordemServicoCadastroModal;

    Button btnExcluir;


    @Autowired
    public void initServices(OrdemServicoCadastroModal ordemServicoCadastroModal1,
                             ClientesService departamentoService1,
                             OrdemServicoService funcionarioService) {
        this.ordemServicoCadastroModal = ordemServicoCadastroModal1;
        this.clientesService = departamentoService1;
        this.ordemServicoService = funcionarioService;
        ordemServicoCadastroModal1.addDialogCloseActionListener(event -> {
            // Código para atualizar a AdministrativoView
            refreshGridFuncionario();
        });
    }


    @Autowired
    public ServicosExecutadosDiv( ) {
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

        com.vaadin.flow.component.Component gridFuncionario = createGridFuncionario();
        filtersordemsservico = new ServicosExecutadosDiv.FiltersFuncionario(() -> refreshGridFuncionario());
        HorizontalLayout mobileFiltersFuncionario = createMobileFiltersFuncionario();

        VerticalLayout layout = new VerticalLayout(mobileFiltersFuncionario, filtersordemsservico, gridFuncionario);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }

    public void refreshGridFuncionario() {
        setOrdemServicoGrid.getDataProvider().refreshAll();
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
            if (filtersordemsservico.getClassNames().contains("visible")) {
                filtersordemsservico.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filtersordemsservico.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    private com.vaadin.flow.component.Component createGridFuncionario() {

        //departamentoService.list(null,null);
        setOrdemServicoGrid = new Grid<>(SetOrdemServico.class, false);
        setOrdemServicoGrid.addColumn(SetOrdemServico::getId_ordemservico)
                .setHeader("ID")
                //.setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(SetOrdemServico::getNome_pontofocal)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(SetOrdemServico::getDatainicio_ordemservico)
                .setHeader("Data de Atendimento")
                .setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(SetOrdemServico::getHorarioinicio_ordemservico)
                .setHeader("Horario")
                .setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(SetOrdemServico::getConfirmado_ordemservico)
                .setHeader("Confirmado")
                .setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(SetOrdemServico::getDiasemanainicio_ordemservico)
                .setHeader("Dia")
                .setSortable(true)
                .setAutoWidth(true);

        setOrdemServicoGrid.addColumn(funcionario -> {
                    SetCliente departamento = clientesService.findById(funcionario.getId_cliente());
                    return departamento == null ? "N/A" : departamento.getNome_cliente();
                })
                .setHeader("Cliente")
                .setSortable(true)
                .setAutoWidth(true);





        // Adiciona o listener de clique nos itens da grade
        //setOrdemServicoGrid.addItemClickListener(event -> openDetalhesFuncionarioModal(event.getItem()));


        setOrdemServicoGrid.setItems(query -> ordemServicoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filtersordemsservico).stream());


        setOrdemServicoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setOrdemServicoGrid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return setOrdemServicoGrid;
    }
    private void openDetalhesFuncionarioModal(SetFuncionario item) {
        btnExcluir = new Button();
        btnExcluir.setVisible(true);
        btnExcluir.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }



    public class FiltersFuncionario extends Div implements Specification<SetOrdemServico> {


        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Nome");
        //private final MultiSelectComboBox<SetDepartamento> departamento = new MultiSelectComboBox<>("Departamento");


        public FiltersFuncionario(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            id.setPlaceholder("Código");

            //adcionar lista de funcionarios abaixo



            // Action buttons
            com.vaadin.flow.component.button.Button resetBtn = new com.vaadin.flow.component.button.Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                id.clear();
                nome.clear();
                onSearch.run();
            });
            com.vaadin.flow.component.button.Button createBtn = createFuncionarioCadastroButton();
            com.vaadin.flow.component.button.Button searchBtn = new com.vaadin.flow.component.button.Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());



            Div actions = new Div(resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(id, nome,  actions);
        }



        @Override
        public Predicate toPredicate(Root<SetOrdemServico> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_ordemservico"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "nome_pontofocal";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, nome.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

            }


//            if (!departamento.isEmpty()) {
//                String databaseColumn = "id_departamento";
//                List<Predicate> occupationPredicates = new ArrayList<>();
//                for (SetDepartamento occupation : departamento.getValue()) {
//                    occupationPredicates
//                            .add(criteriaBuilder.equal(criteriaBuilder.literal(occupation.getId_departamento()), root.get(databaseColumn)));
//                }
//                predicates.add(criteriaBuilder.or(occupationPredicates.toArray(Predicate[]::new)));
//            }

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
        SetCliente entidade = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");
        SetClienteTransiction.setCliente(entidade);
        ordemServicoCadastroModal.open();
    }
}
