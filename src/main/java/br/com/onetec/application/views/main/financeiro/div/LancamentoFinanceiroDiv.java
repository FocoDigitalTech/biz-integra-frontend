package br.com.onetec.application.views.main.financeiro.div;

import br.com.onetec.application.service.lancamentoservice.LancamentoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.financeiro.modal.LancamentoFinanceiroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetFluxoRecebimentoPagamento;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
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
public class LancamentoFinanceiroDiv extends Div{

    private LancamentoService lancamentoService;

    private UtilitySystemConfigService service;

    private Grid<SetFluxoRecebimentoPagamento> grid;

    private LancamentoFinanceiroDiv.Filter filter;

    private LancamentoFinanceiroModal lancamentoFinanceiroModal;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             LancamentoService lancamentoService1,
                             LancamentoFinanceiroModal lancamentoFinanceiroModal1) {
        this.lancamentoService = lancamentoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.lancamentoFinanceiroModal = lancamentoFinanceiroModal1;
    }


    @Autowired
    public LancamentoFinanceiroDiv() {
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
        filter = new LancamentoFinanceiroDiv.Filter(() -> refreshGrid());
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
        grid = new Grid<>(SetFluxoRecebimentoPagamento.class, false);
        grid.addColumn(SetFluxoRecebimentoPagamento::getId_fluxorecebimentopagamento)
                .setHeader("Id")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getNome_fluxorecebimentopagamento)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getValor_lancamento)
                .setHeader("Valor")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getData_inclusao)
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



        grid.setItems(query -> lancamentoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

        grid.addItemDoubleClickListener(event -> {
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

    private void abrirDetalhesDoEstoque(SetEstoque estoque) {
        //  lógica para abrir modal
        // ProdutoDetalheModal dialog = applicationContext.getBean(ProdutoDetalheModal.class, produto);
        //dialog.open();
    }

    private void deleta(SetFluxoRecebimentoPagamento item) {
        try {
            lancamentoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public class Filter extends Div implements Specification<SetFluxoRecebimentoPagamento> {

        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Descrição");


        public Filter(Runnable onSearch) {

            Accordion accordion = new Accordion();


            HorizontalLayout personalInformationLayout = new HorizontalLayout(id,
                    nome);
            personalInformationLayout.setSpacing(false);
            personalInformationLayout.setPadding(false);
            accordion.add("Filtros",personalInformationLayout);


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



            add(accordion, actions);
        }



        @Override
        public Predicate toPredicate(Root<SetFluxoRecebimentoPagamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_fluxorecebimentopagamento"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "nome_fluxorecebimentopagamento";
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
        Button cadastroButton = new Button("Novo Lançamento", event -> openCadastroModal());
        return cadastroButton;
    }



    private void openCadastroModal() {
        UI.getCurrent().access(() -> {
            lancamentoFinanceiroModal.open();
        });
    }
}
