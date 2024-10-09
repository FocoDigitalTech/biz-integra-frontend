package br.com.onetec.application.views.layouts.atendimentosHistorico.div;

import br.com.onetec.application.configuration.OrcamentoTransiction;
import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.layouts.atendimentosHistorico.modal.OrcamentoCadastroModal;
import br.com.onetec.application.views.layouts.atendimentosHistorico.modal.OrcamentoDetalheModal;
import br.com.onetec.application.views.layouts.atendimentosHistorico.modal.OrdemServicoCadastroModal;
import br.com.onetec.application.views.layouts.atendimentosHistorico.modal.OrdemServicoDadosModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
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
import java.util.Objects;

@Component
@UIScope
public class OrcamentoDiv extends Div {
    private boolean sidebarCollapsed;
//    private final Button button;
//    private final Icon leftArrowIcon;
//    private final Icon rightArrowIcon;
//    private final SplitLayout splitLayout;

    private Grid<SetOrcamento> grid;

    private OrcamentoDiv.Filter filter;

    private UtilitySystemConfigService service;

    private OrcamentoService orcamentoService;

    private OrcamentoCadastroModal orcamentoCadastroModal;

    private OrcamentoDetalheModal orcamentoDetalheModal;

    private UsuarioService usuarioService;

    private OrdemServicoService ordemServicoService;

    private Button btnExcluir;

    private OrdemServicoDadosModal ordemServicoDadosModal;

    private OrdemServicoCadastroModal ordemServicoCadastroModal;

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             ApplicationContext applicationContext1,
                             OrcamentoService orcamentoService1,
                             OrcamentoCadastroModal contaCorrenteCadastroModal1,
                             OrcamentoDetalheModal orcamentoDetalheModal1,
                             OrdemServicoService ordemServicoService1,
                             OrdemServicoDadosModal ordemServicoDadosModal1,
                             OrdemServicoCadastroModal ordemServicoCadastroModal1) {
        this.orcamentoService = orcamentoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.orcamentoCadastroModal = contaCorrenteCadastroModal1;
        this.orcamentoDetalheModal = orcamentoDetalheModal1;
        this.ordemServicoService = ordemServicoService1;
        this.ordemServicoDadosModal = ordemServicoDadosModal1;
        this.ordemServicoCadastroModal = ordemServicoCadastroModal1;
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
        UI.getCurrent().access(() -> {
            add(telaDiv());
        });
    }
//
//    private void updateSidebar() {
//        button.setIcon(sidebarCollapsed ? rightArrowIcon : leftArrowIcon);
//        splitLayout.setSplitterPosition(sidebarCollapsed ? 30 : 94);
//    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        SetCliente entidade = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");
        entidade = SetClienteTransiction.getCliente();
        if (Objects.nonNull(entidade)) {
            setSizeFull();
            addClassNames("telarelatorios-view");

            com.vaadin.flow.component.Component gridFuncionario = createGrid(entidade);
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
        } else {
            ProgressBar progressBar = new ProgressBar();
            progressBar.setIndeterminate(true);
            add(progressBar);

            return new Div(progressBar);
        }

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

    private com.vaadin.flow.component.Component createGrid(SetCliente entidade) {
        // Cria o layout principal
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        // Cria o grid
        grid = new Grid<>(SetOrcamento.class, false);
        grid.addColumn(SetOrcamento::getId_orcamento)
                .setHeader("Id")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetOrcamento::getId_cliente)
                .setHeader("Apelido da Conta")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetOrcamento::getGarantia_orcamento)
                .setHeader("Banco")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetOrcamento::getData_inclusao)
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

        grid.setItems(query -> orcamentoService.listByCustomer(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter,entidade).stream());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemDoubleClickListener(this::openDetalheCadastroModal);

        VerticalLayout sidebar = buildSideBar();
        sidebar.setWidth("300px");
        sidebar.setVisible(false); // Inicialmente escondido


        // Adiciona o grid e o sidebar ao layout principal
        mainLayout.add(grid, sidebar);

        return mainLayout;
    }

    private VerticalLayout buildSideBar() {

        // Cria o botão de fechar o sidebar
        VerticalLayout sidebar = new VerticalLayout();
        Button btnCloseSidebar = new Button(new Icon(VaadinIcon.ARROW_RIGHT), event -> {
            sidebar.setVisible(false);
        });
        btnCloseSidebar.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        Button createBtnSidebar = new Button(new Icon(VaadinIcon.PLUS));
        createBtnSidebar.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
        createBtnSidebar.setAriaLabel("Add ordem de serviço");

        Button btnNovaOrdem = new Button("Novo", event -> {
        });

        // Cria o sidebar que será mostrado quando o item for clicado
        sidebar.add(btnCloseSidebar,btnNovaOrdem);
        sidebar.addClassName("v-sidebar");



        // Configura o item click listener para abrir o sidebar com detalhes
        grid.addItemClickListener(event -> {
            SetOrcamento selectedConta = event.getItem();
            // Cria o sidebar que será mostrado quando o item for clicado
            //sidebar.add(btnCloseSidebar,createBtnSidebar);
            sidebar.addClassName("v-sidebar");
            createBtnSidebar.addClickListener(event1 -> {
                openCadastroOrdemServicoModal(event.getItem());
            });

            Grid<SetOrdemServico> gridOrdemServico = new Grid<>(SetOrdemServico.class, false);
            gridOrdemServico.addColumn(SetOrdemServico::getId_ordemservico)
                    .setHeader("Id")
                    .setSortable(true)
                    .setAutoWidth(true);
            gridOrdemServico.addColumn(SetOrdemServico::getNome_pontofocal)
                    .setHeader("Ponto Focal")
                    .setSortable(true)
                    .setAutoWidth(true);
            gridOrdemServico.addColumn(SetOrdemServico::getDatainicio_ordemservico)
                    .setHeader("Data Atendimento")
                    .setSortable(true)
                    .setAutoWidth(true);
            gridOrdemServico.setItems(ordemServicoService.findAllByOrcamentoId(selectedConta.getId_orcamento()));

            gridOrdemServico.addItemClickListener(event1 -> {
                openDetalhesOrdemServico(event1.getItem());
            });

            // Atualiza o conteúdo do sidebar com as informações da conta corrente
            sidebar.removeAll();
            sidebar.add(
                    new Text("Ordems de Serviço Aberta: "),
                    gridOrdemServico,
                    new HorizontalLayout(btnCloseSidebar,createBtnSidebar)
            );

            sidebar.addClassName("visible");
            // Mostra o sidebar
            sidebar.setVisible(true);

        });

        return sidebar;
    }

    private void openCadastroOrdemServicoModal(SetOrcamento item) {
         ordemServicoCadastroModal.setOrdemServico(item);
         ordemServicoCadastroModal.open();
    }


    private void openDetalhesOrdemServico(SetOrdemServico item) {
        ordemServicoDadosModal.setOrdemServico(item);
        ordemServicoDadosModal.open();
    }


    private void deleta(SetOrcamento item) {
        try {
            orcamentoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public class Filter extends Div implements Specification<SetOrcamento> {


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
        public Predicate toPredicate(Root<SetOrcamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
            orcamentoCadastroModal.open();
        });
    }

    private void openDetalheCadastroModal(ItemDoubleClickEvent<SetOrcamento> event) {
        UI.getCurrent().access(() -> {
            UI.getCurrent().getSession().setAttribute("orcamento", event.getItem());
            orcamentoDetalheModal.setOrcamento(event.getItem());
            orcamentoDetalheModal.open();
        });
    }

}
