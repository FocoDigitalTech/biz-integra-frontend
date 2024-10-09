package br.com.onetec.application.views.main.clientes;

import br.com.onetec.application.service.clientesservice.*;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.main.clientes.modal.CadastroClientesModal;
import br.com.onetec.application.views.main.clientes.modal.DadosClienteModal;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.model.SetOrcamento;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@Route(value = "clientes",layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
@PageTitle(ViewsTitleConst.CLIENTES_NAV_TITLE)
@org.springframework.stereotype.Component
@UIScope
public class ClientesView extends Div {

    private Grid<SetCliente> grid;

    private Filters filters;

    private ClientesService clientesService;

    private EstadoService estadoService;

    private UsuarioService usuarioService;

    private OrcamentoService orcamentoService;

    private EnderecoService enderecoService;

    private ResponsavelCobrancaService responsavelCobrancaService;

    private ResponsavelAgendamentoService responsavelAgendamentoService;

    private ResponsavelAprovacaoService responsavelAprovacaoService;

    private CadastroClientesModal cadastroModal;

    private DadosClienteModal detalhesClienteModal;

    private UtilitySystemConfigService service;


    @Autowired
    public void initServices(ClientesService clientesService1,
                             EstadoService estadoService1,
                             UsuarioService usuarioService1,
                             EnderecoService enderecoService1,
                             ResponsavelCobrancaService responsavelCobrancaService1,
                             ResponsavelAgendamentoService responsavelAgendamentoService1,
                             ResponsavelAprovacaoService responsavelAprovacaoService1,
                             CadastroClientesModal cadastroModal1,
                             DadosClienteModal detalhesClienteModal1,
                             OrcamentoService orcamentoService) {
        this.estadoService = estadoService1;
        this.clientesService = clientesService1;
        this.usuarioService = usuarioService1;
        this.enderecoService = enderecoService1;
        this.responsavelAprovacaoService = responsavelAprovacaoService1;
        this.responsavelCobrancaService = responsavelCobrancaService1;
        this.responsavelAgendamentoService = responsavelAgendamentoService1;
        this.cadastroModal = cadastroModal1;
        this.detalhesClienteModal = detalhesClienteModal1;
        this.service = new UtilitySystemConfigService();
        this.orcamentoService = orcamentoService;
    }

    @Autowired
    public ClientesView() {
        UI.getCurrent().access(() -> {
            this.service = new UtilitySystemConfigService();
            setSizeFull();
            addClassNames("telarelatorios-view");
            filters = new Filters(() -> refreshGrid());
            VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            add(layout);
        });

    }



    private HorizontalLayout createMobileFilters() {
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
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    private Button createCadastroButton() {
        Button cadastroButton = new Button("Cadastrar", event -> openCadastroModal());
        return cadastroButton;
    }



    public class Filters extends Div implements Specification<SetCliente> {


        private final IntegerField orcamento = new IntegerField("N° do Orçamento");
        private final TextField email = new TextField("E-mail");
        private final TextField telefone = new TextField("Telefone Fixo");
        private final TextField razaosocial = new TextField("Razão Social");
        private final TextField cpfcnpj = new TextField("CPF/CNPJ");

        private final IntegerField numerocontrato = new IntegerField("N° Contrato");
        private final TextField numeroos = new TextField("N° Ordem de Serviço");
        private final TextField endereco = new TextField("Endereço");
        private final CheckboxGroup<String> stringCheckboxGroup = new CheckboxGroup<>("Situação");
        private final ComboBox<String> FJFieldCombo = new ComboBox<>("Natureza Juridica");


        public Filters(Runnable onSearch) {

            service = new UtilitySystemConfigService();
            service.configureTelefoneResidencialField(telefone);


            FJFieldCombo.setItems(List.of("Pessoa Fisica","Pessoa Juridica"));
            FJFieldCombo.addValueChangeListener(event -> {
                if ("Pessoa Fisica".equals(event.getValue())) {
                    cpfcnpj.clear();
                    cpfcnpj.setLabel("Número CPF");
                    service.configureCPFField(cpfcnpj);

                } else if ("Pessoa Juridica".equals(event.getValue())) {
                    cpfcnpj.clear();
                    cpfcnpj.setLabel("Número CNPJ");
                    service.configureCNPJTextField(cpfcnpj);
                }
            });

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            numerocontrato.setPlaceholder("Numero do Contrato");


            stringCheckboxGroup.setItems("Monitoramento", "Executado", "Cancelado");
            stringCheckboxGroup.addClassName("double-width");

            // Action buttons
            Button resetBtn = new Button("Limpar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                orcamento.clear();
                email.clear();
                telefone.clear();
                razaosocial.clear();
                cpfcnpj.clear();
                numerocontrato.clear();
                numeroos.clear();
                endereco.clear();
                stringCheckboxGroup.clear();
                FJFieldCombo.clear();
                onSearch.run();
            });
            Button createBtn = createCadastroButton();
            Button searchBtn = new Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn,createBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(orcamento,
                    email,
                    telefone,
                    razaosocial,
                    FJFieldCombo,
                    cpfcnpj,
                    numerocontrato,
                    numeroos,
                    endereco,
                    stringCheckboxGroup,
                    actions);
        }




        @Override
        public Predicate toPredicate(Root<SetCliente> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!cpfcnpj.isEmpty()) {
                String lowerCaseFilter = service.removeMascara(cpfcnpj.getValue());
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("cpf_cgc_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            if (!razaosocial.isEmpty()) {
                String lowerCaseFilter = razaosocial.getValue();
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("nome_fantasia_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            if (!telefone.isEmpty()) {
                String lowerCaseFilter = service.removeMascara(telefone.getValue());
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("telefone_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            if (!email.isEmpty()) {
                String lowerCaseFilter = email.getValue().toString();
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("email_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }

            if (!numerocontrato.isEmpty()) {
                String lowerCaseFilter = numerocontrato.getValue().toString();
                String ignore = "- ()";
                Predicate firstNameMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get("nome_cliente"))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            if (!orcamento.isEmpty()) {
                SetOrcamento listaOrc = orcamentoService.findAllById(orcamento.getValue());
                if (Objects.nonNull(listaOrc)) {
                    String databaseColumn = "id_cliente";
                    String ignore = "- ()";

                    Integer lowerCaseFilter = listaOrc.getId_cliente();
                    Predicate phoneMatch = criteriaBuilder.equal(root.get(databaseColumn), lowerCaseFilter);

                    predicates.add(phoneMatch);
                }

            }


            if (!stringCheckboxGroup.isEmpty()) {
                String databaseColumn = "ativo";
                List<Predicate> rolePredicates = new ArrayList<>();
                for (String role : stringCheckboxGroup.getValue()) {
                    rolePredicates.add(criteriaBuilder.equal(criteriaBuilder.literal(role), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(rolePredicates.toArray(Predicate[]::new)));
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



    private Component createGrid() {
        grid = new Grid<>(SetCliente.class, false);
        grid.addColumn(cliente -> {
            List<SetOrcamento> orcamento = orcamentoService.findAllClienteId(cliente.getId_cliente());
            Optional<SetOrcamento> orcamentoMaisRecente = orcamento.stream()
                    .max(Comparator.comparing(SetOrcamento::getData_orcamento));
            return orcamentoMaisRecente.isPresent() ? orcamentoMaisRecente.get().getData_orcamento() : "N/A";
        })
                .setHeader("Ult. Orçamento")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getNome_cliente)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getNome_contato_cliente)
                .setHeader("Nome Contato")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(cliente -> {
            List<SetEnderecos> listaEnderecos = enderecoService.findAllClienteId(cliente.getId_cliente());
            Optional<SetEnderecos> setEnderecos = listaEnderecos.stream()
                    .max(Comparator.comparing(SetEnderecos::getData_inclusao));
            return setEnderecos.isPresent() ? setEnderecos.get().getEndereco_imovel() : "N/A";
        })
                .setHeader("Endereço")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getTelefone_cliente)
                .setHeader("Telefone Fixo")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getCelular_cliente)
                .setHeader("Telefone Celular")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getEmail_cliente)
                .setHeader("E-mail")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getNome_fantasia_cliente)
                .setHeader("Nome Fantasia")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getData_inclusao)
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCliente::getData_alteracao)
                .setHeader("Ultima Alteração")
                .setSortable(true)
                .setAutoWidth(true);

        // Adiciona o listener de clique nos itens da grade
       // grid.addItemClickListener(event -> openDetalhesClienteModal(event.getItem()));
        grid.addItemClickListener(event ->
                UI.getCurrent().access(() -> openDetalhesClienteModal(event.getItem()))
        );
        // Atualiza a UI (precisa ser feito na thread do Vaadin)
        grid.setItems(query -> clientesService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }



    public void refreshGrid() {
        if (UI.getCurrent().isAttached()) {
            UI.getCurrent().access(() -> grid.getDataProvider().refreshAll());
        }
    }



    private void openDetalhesClienteModal(SetCliente cliente) {
        UI.getCurrent().access(() -> {
            UI.getCurrent().getSession().setAttribute("cliente",cliente);
            SetClienteTransiction.setCliente(cliente);
//            DadosClienteModal detalhesClienteModal = new DadosClienteModal(cliente,
//                    clientesService,
//                    estadoService,
//                    usuarioService,
//                    enderecoService,
//                    responsavelCobrancaService,
//                    responsavelAgendamentoService,
//                    responsavelAprovacaoService);
            detalhesClienteModal.setCliente(cliente);
            detalhesClienteModal.open();
        });
    }



    private void openCadastroModal() {
        UI.getCurrent().access(() -> {
            cadastroModal.open();
        });

    }
}

