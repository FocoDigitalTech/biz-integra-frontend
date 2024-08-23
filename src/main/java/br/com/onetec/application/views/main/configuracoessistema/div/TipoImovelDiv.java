package br.com.onetec.application.views.main.configuracoessistema.div;

import br.com.onetec.application.service.tipoimovelservice.TipoImovelService;
import br.com.onetec.application.service.tipomidiaservice.TipoMidiaService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.configuracoessistema.modal.TipoImovelCadastroModal;
import br.com.onetec.application.views.main.configuracoessistema.modal.TipoMidiaCadastroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.Servicos;
import br.com.onetec.infra.db.model.SetTipoImovel;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.model.SetUsuarios;
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
public class TipoImovelDiv extends Div {

    List<SetTipoImovel> list = new ArrayList<>();
    private Grid<SetTipoImovel> grid;

    private TipoImovelDiv.Filter filter;

    private Servicos service;

    private TipoImovelService tipoMidiaService;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    private TipoImovelCadastroModal tipoMidiaCadastroModal;


    @Autowired
    public void initServices(TipoImovelService tipoImovelService1,
                             Servicos service1,
                             UsuarioService usuarioService1,
                             TipoImovelCadastroModal tipoImovelCadastroModal1) {
        this.tipoMidiaService = tipoImovelService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.tipoMidiaCadastroModal = tipoImovelCadastroModal1;
//        funcionarioCadastroModal.addDialogCloseActionListener(event -> {
//            // Código para atualizar a AdministrativoView
//            refreshGridFuncionario();
//        });
    }


    @Autowired
    public TipoImovelDiv( ) {
        add(telaDiv());

    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        com.vaadin.flow.component.Component gridFuncionario = createGrid();
        filter = new TipoImovelDiv.Filter(() -> refreshGrid());
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
        grid = new Grid<>(SetTipoImovel.class, false);
        grid.addColumn(SetTipoImovel::getId_tipoimovel)
                .setHeader("ID")
                //.setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetTipoImovel::getDescricao_tipoimovel)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetTipoImovel::getData_inclusao)
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



        grid.setItems(query -> tipoMidiaService.list(
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

    private void deleta(SetTipoImovel item) {
        try {
            tipoMidiaService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public class Filter extends Div implements Specification<SetTipoImovel> {


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
        public Predicate toPredicate(Root<SetTipoImovel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();


            if (!id.isEmpty()) {
                Integer lowerCaseFilter = Integer.valueOf(id.getValue().toLowerCase());
                Predicate idMatch = criteriaBuilder.equal(root.get("id_tipoimovel"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "descricao_tipoimovel";
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
        tipoMidiaCadastroModal.open();
    }


}

