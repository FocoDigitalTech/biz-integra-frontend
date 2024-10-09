package br.com.onetec.application.views.main.administrativo.div;

import br.com.onetec.application.service.compraservice.CompraService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.administrativo.modal.CompraCadastroModal;
import br.com.onetec.application.views.main.configuracoessistema.modal.PragaCadastroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCompra;
import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetPraga;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ComprasDiv extends Div {

    List<SetPraga> list = new ArrayList<>();
    private Grid<SetCompra> grid;

    private ComprasDiv.Filter filter;

    private UtilitySystemConfigService service;

    private CompraService compraService;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    private CompraCadastroModal compraCadastroModal;

    private FornecedorService fornecedorService;

    @Autowired
    public void initServices(CompraService compraService1,
                             UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             CompraCadastroModal compraCadastroModal1,
                             FornecedorService fornecedorService1) {
        this.compraService = compraService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.compraCadastroModal = compraCadastroModal1;
        this.fornecedorService = fornecedorService1;
    }


    @Autowired
    public ComprasDiv(CompraService compraService1,
                      UtilitySystemConfigService service1,
                      UsuarioService usuarioService1,
                      CompraCadastroModal compraCadastroModal1,
                      FornecedorService fornecedorService1) {
        this.compraService = compraService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.compraCadastroModal = compraCadastroModal1;
        this.fornecedorService = fornecedorService1;
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
        filter = new ComprasDiv.Filter(() -> refreshGrid());
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

    private void deleta(SetCompra item) {
        try {
            compraService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }



    private com.vaadin.flow.component.Component createGrid() {

        //departamentoService.list(null,null);
        grid = new Grid<>(SetCompra.class, false);
        grid.addColumn(SetCompra::getId_compra)
                .setHeader("ID")
                //.setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(midia -> {
            SetFornecedor fornecedor =
                    fornecedorService.findById(midia.getId_fornecedor());
            return fornecedor == null ? "N/A" : fornecedor.getRazaosocial_fornecedor();
        })
                .setHeader("Fornecedor")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getNotafiscal_compra)
                .setHeader("Nota")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getNumeronotafiscal_compra)
                .setHeader("Numero Nota Fiscal")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getDatanotafiscal_compra)
                .setHeader("Data Nota Fiscal")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getDatapagamento_compra)
                .setHeader("Data Compra")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getValoritemstotal_compra)
                .setHeader("Valor Compra")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getValorfrete_compra)
                .setHeader("Valor Frete")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getValordesconto_compra)
                .setHeader("Valor Desconto")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getValortotal_compra)
                .setHeader("Valor Total")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getObservacoes_compra)
                .setHeader("Observações")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getResponsavelaprovacao_compra)
                .setHeader("Aprovado Por")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getDataaprovacao_compra)
                .setHeader("Data Aprovação")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getHorario_compra)
                .setHeader("Horário Compra")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getDatavalidate_compra)
                .setHeader("Validade")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompra::getData_inclusao)
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



        grid.setItems(query -> compraService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

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


    public class Filter extends Div implements Specification<SetCompra> {


        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Numero Nota Fiscal");


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
        public Predicate toPredicate(Root<SetCompra> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_compra"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "numeronotafiscal_compra";
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
            compraCadastroModal.open();
        });
    }
}
