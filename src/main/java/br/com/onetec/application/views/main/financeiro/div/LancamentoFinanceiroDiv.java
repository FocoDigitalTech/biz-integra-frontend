package br.com.onetec.application.views.main.financeiro.div;

import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.lancamentoservice.LancamentoService;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.financeiro.modal.LancamentoFinanceiroDetalhesModal;
import br.com.onetec.application.views.main.financeiro.modal.LancamentoFinanceiroModal;
import br.com.onetec.cross.constants.FinanceiroDataConst;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetFluxoRecebimentoPagamento;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;
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
import java.util.Objects;

@Component
@UIScope
public class LancamentoFinanceiroDiv extends Div {

    private LancamentoService lancamentoService;

    private UtilitySystemConfigService service;

    private Grid<SetFluxoRecebimentoPagamento> grid;

    private LancamentoFinanceiroDiv.Filter filter;

    private LancamentoFinanceiroModal lancamentoFinanceiroModal;

    private LancamentoFinanceiroDetalhesModal lancamentoFinanceiroDetalhesModal;

    private UsuarioService usuarioService;

    private TipoEventoFinanceiroService tipoEventoFinanceiroService;

    private EventoFinanceiroService eventoFinanceiroService;

    private Button btnExcluir;

    private Checkbox abreviarCheckbox;
    private String situation;
    private boolean abreviaverificacao = false;

    @Autowired
    public LancamentoFinanceiroDiv() {
        UI.getCurrent().access(() -> {
            add(telaDiv());
        });
    }

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             LancamentoService lancamentoService1,
                             LancamentoFinanceiroModal lancamentoFinanceiroModal1,
                             TipoEventoFinanceiroService tipoEventoFinanceiroService1,
                             EventoFinanceiroService eventoFinanceiroService1,
                             LancamentoFinanceiroDetalhesModal lancamentoFinanceiroDetalhesModal1) {
        this.lancamentoService = lancamentoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.lancamentoFinanceiroModal = lancamentoFinanceiroModal1;
        this.lancamentoFinanceiroDetalhesModal = lancamentoFinanceiroDetalhesModal1;
        this.tipoEventoFinanceiroService = tipoEventoFinanceiroService1;
        this.eventoFinanceiroService = eventoFinanceiroService1;
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
        grid.addColumn(new ComponentRenderer<>(orc -> {
            String abreviacao = null;
            if (orc.getStatus_pagamento().equals(FinanceiroDataConst.STATUS_PREVISTO.getValor())) {
                abreviacao = FinanceiroDataConst.STATUS_PREVISTO.getAbreviacao();
            }
            if (orc.getStatus_pagamento().equals(FinanceiroDataConst.STATUS_REAL.getValor())) {
                abreviacao = FinanceiroDataConst.STATUS_REAL.getAbreviacao();
            }
            if (orc.getStatus_pagamento().equals(FinanceiroDataConst.STATUS_CONSOLIDADO.getValor())) {
                abreviacao = FinanceiroDataConst.STATUS_CONSOLIDADO.getAbreviacao();
            }
            if (abreviaverificacao) {
                situation = abreviacao;
            } else {
                situation = orc.getStatus_pagamento();
            }
            Span span = new Span(situation);
            if ("Previsão (P)".equals(orc.getStatus_pagamento())) {
                span.getStyle().set("color", "red");
            } else if ("Real (R)".equals(orc.getStatus_pagamento())) {
                span.getStyle().set("color", "blue");
            } else {
                span.getStyle().set("color", "green");
            }
            return span;

        }))
                .setHeader("Status (P/R/C)")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(data -> {
            if (Objects.nonNull(data.getData_vencimento())) {
                return UtilitySystemConfigService.
                        getDataFormatada(data.getData_vencimento().atStartOfDay());
            } else {
                return "";
            }
        })
                .setHeader("Data Vencimento")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getNome_fluxorecebimentopagamento)
                .setHeader("Nome Lançamento (Histórico)")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getValor_lancamento)
                .setHeader("Valor")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(event -> {
            SetTipoEventoFinanceiro dto = tipoEventoFinanceiroService.findById(event.getId_tipoeventofinanceiro());
            return dto == null ? "N/A" : dto.getNome_tipoeventofinanceiro();
        })
                .setHeader("Nome da Conta (Tipo Evento Financeiro)")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetFluxoRecebimentoPagamento::getNumero_documento)
                .setHeader("N° DOCTO")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(event -> Objects.isNull(event.getNumero_parcela()) || Objects.isNull(event.getQuantidade_parcelas())
                ? "N/A" : event.getNumero_parcela() + "/" + event.getQuantidade_parcelas())
                .setHeader("NumParc")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(data -> {
            if (Objects.nonNull(data.getData_lancamento())) {
                return UtilitySystemConfigService.
                        getDataFormatada(data.getData_lancamento());
            } else {
                return "";
            }
        })
                .setHeader("Data de Lançamento")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(midia -> {
            SetUsuarios usuarios = usuarioService.findById(midia.getId_funcionariolancamento());
            return usuarios == null ? "N/A" : usuarios.getNome_usuario();
        })
                .setHeader("Usuario Lançamento")
                .setSortable(true)
                .setAutoWidth(true);


        grid.setItems(query -> lancamentoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter).stream());

        grid.addItemDoubleClickListener(event -> {
        });

        final Registration[] btnExcluirClickListenerRegistration = {null};
        grid.addItemClickListener(event -> {
            // Torna o botão "Deletar" visível
            UI.getCurrent().access(() -> {
                lancamentoFinanceiroDetalhesModal.setFluxoRecebimentoPagamento(event.getItem());
                lancamentoFinanceiroDetalhesModal.open();
            });

//            btnExcluir.setVisible(true);
//            // Verifica se existe um ClickListener registrado anteriormente e o remove
//            if (btnExcluirClickListenerRegistration[0] != null) {
//                btnExcluirClickListenerRegistration[0].remove();
//                btnExcluirClickListenerRegistration[0] = null;
//            }
//            // Adiciona um novo ClickListener e armazena o Registration para remoção futura
//            btnExcluirClickListenerRegistration[0] = btnExcluir.addClickListener(event1 -> {
            //deleta(event.getItem());
//                // Torna o botão "Deletar" invisível após a ação ser concluída
//                btnExcluir.setVisible(false);
//            });
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
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
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

    public class Filter extends Div implements Specification<SetFluxoRecebimentoPagamento> {

        private final com.vaadin.flow.component.textfield.TextField id = new com.vaadin.flow.component.textfield.TextField("Id");
        private final com.vaadin.flow.component.textfield.TextField nome = new TextField("Nome Lançamento (Histórico)");
        private final DatePicker startDate = new DatePicker("Data (Vencimento)");
        private final DatePicker endDate = new DatePicker();

        public Filter(Runnable onSearch) {


            setWidthFull();
            addClassName("filter-layout");
            abreviarCheckbox = new Checkbox("Abreviar Status ?");
            abreviarCheckbox.addValueChangeListener(event -> {
                abreviaverificacao = event.getValue(); // Marca a opção de abreviar
                refreshGrid();  // Atualiza o Grid quando a opção mudar
            });

            HorizontalLayout personalInformationLayout = new HorizontalLayout(id,
                    nome, createDateRangeFilter(), abreviarCheckbox);


            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            id.setPlaceholder("Código");
            // Action buttons
            com.vaadin.flow.component.button.Button resetBtn = new com.vaadin.flow.component.button.Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                id.clear();
                nome.clear();
                startDate.clear();
                endDate.clear();
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
            Div actions = new Div(resetBtn, searchBtn, createBtn, btnExcluir);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");


            add(personalInformationLayout, actions);
        }

        private com.vaadin.flow.component.Component createDateRangeFilter() {
            startDate.setPlaceholder("De");

            endDate.setPlaceholder("Até");

            // For screen readers
            startDate.setAriaLabel("Data Inicio");
            endDate.setAriaLabel("Data Fim");
            service.configuraCalendario(startDate);
            service.configuraCalendario(endDate);


            HorizontalLayout dateRangeComponent = new HorizontalLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.MEDIUM);

            return dateRangeComponent;
        }


        @Override
        public Predicate toPredicate(Root<SetFluxoRecebimentoPagamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            query.orderBy(criteriaBuilder.desc(root.get("data_vencimento")));

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
            if (startDate.getValue() != null) {
                String databaseColumn = "data_vencimento";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "data_vencimento";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
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
}
