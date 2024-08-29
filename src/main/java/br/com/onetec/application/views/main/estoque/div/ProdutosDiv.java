package br.com.onetec.application.views.main.estoque.div;

import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.configuracoessistema.modal.PragaCadastroModal;
import br.com.onetec.application.views.main.estoque.modal.ProdutoCadastroModal;
import br.com.onetec.application.views.main.estoque.modal.ProdutoDetalheModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ProdutosDiv extends Div {

    List<SetProduto> list = new ArrayList<>();
    private Grid<SetProduto> grid;

    private ProdutosDiv.Filter filter;

    private UtilitySystemConfigService service;

    private ProdutoService produtoService;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    private ProdutoCadastroModal produtoCadastroModal;

    private ApplicationContext applicationContext;

    @Autowired
    public void initServices(ProdutoService produtoService1,
                             UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             ProdutoCadastroModal produtoCadastroModal1,
                             ApplicationContext applicationContext1) {
        this.produtoService = produtoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.produtoCadastroModal = produtoCadastroModal1;
        this.applicationContext = applicationContext1;
    }


    @Autowired
    public ProdutosDiv() {
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

        com.vaadin.flow.component.Component gridFuncionario = createGrid();
        filter = new ProdutosDiv.Filter(() -> refreshGrid());
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

        //departamentoService.list(null,null);
        grid = new Grid<>(SetProduto.class, false);
        grid.addColumn(SetProduto::getId_produto)
                .setHeader("ID")
                //.setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getNome_produto)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getUnidade_entrada)
                .setHeader("Unidade de Entrada")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getUnidade_aplicacao)
                .setHeader("Unidade de Aplicação")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getFator_conversao)
                .setHeader("Fator de Conversão")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getQuantidade_estoque)
                .setHeader("Quantidade de Estoque")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getValor_item)
                .setHeader("Valor Item")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetProduto::getData_inclusao)
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



        grid.setItems(query -> produtoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

        grid.addItemDoubleClickListener(event -> {
            SetProduto produto = event.getItem();
            // Aqui você pode definir a ação que será realizada ao dar double-click
            // Por exemplo, abrir um modal com os detalhes do produto
            abrirDetalhesDoProduto(produto);
        });

        grid.addItemClickListener(event -> {
            // Configura o botão "Deletar" para deletar o item clicado
            btnExcluir.addClickListener(event1 -> deleta(event.getItem()));

            // Torna o botão "Deletar" visível
            btnExcluir.setVisible(true);
        });

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void abrirDetalhesDoProduto(SetProduto produto) {
        //  lógica para abrir modal
        ProdutoDetalheModal dialog = applicationContext.getBean(ProdutoDetalheModal.class, produto);
        dialog.open();
    }

    private void deleta(SetProduto item) {
        try {
            produtoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public class Filter extends Div implements Specification<SetProduto> {


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
        public Predicate toPredicate(Root<SetProduto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_produto"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "nome_produto";
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
            produtoCadastroModal.open();
        });
    }
}

