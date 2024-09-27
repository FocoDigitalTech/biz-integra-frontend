package br.com.onetec.application.views.layouts.atendimentosHistorico.div;

import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.financeiro.div.ContaCorrenteDiv;
import br.com.onetec.application.views.main.financeiro.modal.ContaCorrenteCadastroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetContaCorrente;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class OrcamentoDiv extends Div {
    private boolean sidebarCollapsed;
//    private final Button button;
//    private final Icon leftArrowIcon;
//    private final Icon rightArrowIcon;
//    private final SplitLayout splitLayout;

    private Grid<SetContaCorrente> grid;

    private OrcamentoDiv.Filter filter;

    private UtilitySystemConfigService service;

    private ContaCorrenteService contaCorrenteService;

    private ContaCorrenteCadastroModal contaCorrenteCadastroModal;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             ApplicationContext applicationContext1,
                             ContaCorrenteService contaCorrenteService1,
                             ContaCorrenteCadastroModal contaCorrenteCadastroModal1) {
        this.contaCorrenteService = contaCorrenteService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.contaCorrenteCadastroModal = contaCorrenteCadastroModal1;
    }

    @Autowired
    public OrcamentoDiv() {
//        button = new Button();
//        leftArrowIcon = VaadinIcon.ARROW_LEFT.create();
//        rightArrowIcon = VaadinIcon.ARROW_RIGHT.create();
//        Div masterContainer = new Div();
//        Div detailContent = new Div();
//        detailContent.add(new H3("Texto"));
//        masterContainer.setSizeFull();
//
//        sidebarCollapsed = true;
//
//        button.addClickListener(event -> {
//            sidebarCollapsed = !sidebarCollapsed;
//            updateSidebar();
//        });
//        button.setAriaLabel("Expand/collapse sidebar");
//        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        button.getStyle().set("float", "right");
//
//        masterContainer.add(button, telaDiv());
//        masterContainer.setWidthFull();
//
//       // masterContainer.getStyle().set("overflow", "auto");
//
//        splitLayout = new SplitLayout(masterContainer, detailContent);
//
//        updateSidebar();
//
//        splitLayout.setSizeFull();
//        splitLayout.setWidthFull();
        add(telaDiv());
    }
//
//    private void updateSidebar() {
//        button.setIcon(sidebarCollapsed ? rightArrowIcon : leftArrowIcon);
//        splitLayout.setSplitterPosition(sidebarCollapsed ? 30 : 94);
//    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        com.vaadin.flow.component.Component gridFuncionario = createGrid();
        filter = new OrcamentoDiv.Filter(() -> refreshGrid());
        HorizontalLayout mobileFiltersFuncionario = createMobileFiltersFuncionario();

        VerticalLayout layout = new VerticalLayout(mobileFiltersFuncionario, filter, gridFuncionario);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

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
        // Cria o layout principal
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        // Cria o grid
        grid = new Grid<>(SetContaCorrente.class, false);
        grid.addColumn(SetContaCorrente::getId_contacorrente)
                .setHeader("Id")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetContaCorrente::getNome_contacorrente)
                .setHeader("Apelido da Conta")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetContaCorrente::getBanco_contacorrente)
                .setHeader("Banco")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetContaCorrente::getData_inclusao)
                .setHeader("Data de Inclusão")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(midia -> {
            SetUsuarios usuarios = usuarioService.findById(midia.getId_usuario());
            return usuarios == null ? "N/A" : usuarios.getNome_usuario();
        })
                .setHeader("Usuario")
                .setSortable(true)
                .setAutoWidth(true);

        grid.setItems(query -> contaCorrenteService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        VerticalLayout sidebar = new VerticalLayout();
        sidebar.setWidth("300px");
        sidebar.setVisible(false); // Inicialmente escondido


        // Cria o botão de fechar o sidebar
        Button btnCloseSidebar = new Button("Fechar", event -> {
            sidebar.setVisible(false);
        });

        // Cria o sidebar que será mostrado quando o item for clicado
        sidebar.add(btnCloseSidebar);
        sidebar.addClassName("v-sidebar");

        // Configura o item click listener para abrir o sidebar com detalhes
        grid.addItemClickListener(event -> {
            SetContaCorrente selectedConta = event.getItem();

            // Atualiza o conteúdo do sidebar com as informações da conta corrente
            sidebar.removeAll();
            sidebar.add(
                    new H3("Detalhes da Conta"),
                    new Text("Nome: " + selectedConta.getNome_contacorrente()),
                    new Text("Banco: " + selectedConta.getBanco_contacorrente()),
                    new Text("Usuário: " + usuarioService.findById(selectedConta.getId_usuario()).getNome_usuario()),
                    btnCloseSidebar
            );

            sidebar.addClassName("visible");
            // Mostra o sidebar
            sidebar.setVisible(true);

        });

        // Adiciona o grid e o sidebar ao layout principal
        mainLayout.add(grid, sidebar);

        return mainLayout;
    }

    private void abrirDetalhesDoEstoque(SetEstoque estoque) {
        //  lógica para abrir modal
        // ProdutoDetalheModal dialog = applicationContext.getBean(ProdutoDetalheModal.class, produto);
        //dialog.open();
    }

    private void deleta(SetContaCorrente item) {
        try {
            contaCorrenteService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public class Filter extends Div implements Specification<SetContaCorrente> {


        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Descrição");


        public Filter(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            id.setPlaceholder("Código");
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


            btnExcluir = new Button("Excluir");
            btnExcluir.setVisible(false);
            btnExcluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);
            Div actions = new Div(resetBtn, searchBtn,createBtn,btnExcluir);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");



            add(id, nome, actions);
        }



        @Override
        public Predicate toPredicate(Root<SetContaCorrente> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_contacorrente"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "nome_contacorrente";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, nome.getValue().toLowerCase());
                Predicate phoneMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(phoneMatch);

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
        Button cadastroButton = new Button("Cadastrar", event -> openCadastroModal());
        return cadastroButton;
    }


    private void openCadastroModal() {
        UI.getCurrent().access(() -> {
            contaCorrenteCadastroModal.open();
        });
    }

}
