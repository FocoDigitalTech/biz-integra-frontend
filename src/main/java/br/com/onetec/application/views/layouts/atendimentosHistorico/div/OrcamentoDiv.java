package br.com.onetec.application.views.layouts.atendimentosHistorico.div;

import br.com.onetec.application.service.clientesservice.AutoExclusaoProceduralService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
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
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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


    private Grid<SetOrcamento> grid;

    Grid<SetOrdemServico> gridOrdemServico;

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

    private SituacaoCadastroService situacaoCadastroService;

    private AutoExclusaoProceduralService autoExclusaoProceduralService;

    private CondicaoPagamentoService condicaoPagamentoService;

    private ContratoService contratoService;

    private EnderecoService enderecoService;

    private ApplicationContext applicationContext;

    private String possuiContrato;

    private Boolean contractRequi = false;


    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             ApplicationContext applicationContext1,
                             OrcamentoService orcamentoService1,
                             OrcamentoCadastroModal contaCorrenteCadastroModal1,
                             OrcamentoDetalheModal orcamentoDetalheModal1,
                             OrdemServicoService ordemServicoService1,
                             OrdemServicoDadosModal ordemServicoDadosModal1,
                             OrdemServicoCadastroModal ordemServicoCadastroModal1,
                             SituacaoCadastroService situacaoCadastroService1,
                             CondicaoPagamentoService condicaoPagamentoService1,
                             EnderecoService enderecoService1,
                             ContratoService contratoService1,
                             AutoExclusaoProceduralService autoExclusaoProceduralService1) {
        this.orcamentoService = orcamentoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.orcamentoCadastroModal = contaCorrenteCadastroModal1;
        this.orcamentoDetalheModal = orcamentoDetalheModal1;
        this.ordemServicoService = ordemServicoService1;
        this.ordemServicoDadosModal = ordemServicoDadosModal1;
        this.ordemServicoCadastroModal = ordemServicoCadastroModal1;
        this.situacaoCadastroService = situacaoCadastroService1;
        this.condicaoPagamentoService = condicaoPagamentoService1;
        this.enderecoService = enderecoService1;
        this.contratoService = contratoService1;
        this.applicationContext = applicationContext1;
        this.autoExclusaoProceduralService = autoExclusaoProceduralService1;
    }

    @Autowired
    public OrcamentoDiv(UtilitySystemConfigService service1,
                        UsuarioService usuarioService1,
                        ApplicationContext applicationContext1,
                        OrcamentoService orcamentoService1,
                        OrcamentoCadastroModal contaCorrenteCadastroModal1,
                        OrcamentoDetalheModal orcamentoDetalheModal1,
                        OrdemServicoService ordemServicoService1,
                        OrdemServicoDadosModal ordemServicoDadosModal1,
                        OrdemServicoCadastroModal ordemServicoCadastroModal1,
                        SituacaoCadastroService situacaoCadastroService1,
                        CondicaoPagamentoService condicaoPagamentoService1,
                        EnderecoService enderecoService1,
                        ContratoService contratoService1,
                        AutoExclusaoProceduralService autoExclusaoProceduralService1) {
        this.orcamentoService = orcamentoService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.orcamentoCadastroModal = contaCorrenteCadastroModal1;
        this.orcamentoDetalheModal = orcamentoDetalheModal1;
        this.ordemServicoService = ordemServicoService1;
        this.ordemServicoDadosModal = ordemServicoDadosModal1;
        this.ordemServicoCadastroModal = ordemServicoCadastroModal1;
        this.situacaoCadastroService = situacaoCadastroService1;
        this.condicaoPagamentoService = condicaoPagamentoService1;
        this.enderecoService = enderecoService1;
        this.contratoService = contratoService1;
        this.applicationContext = applicationContext1;
        this.autoExclusaoProceduralService = autoExclusaoProceduralService1;
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
        //gridOrdemServico.getDataProvider().refreshAll();
        sidebar.setVisible(false);
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
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(s -> {
            SetSituacaoCadastro situacaoCadastro = situacaoCadastroService.fidById(s.getId_situacao());
            return situacaoCadastro == null ? "N/A" : situacaoCadastro.getDescricao_situacaocadastro();
              }).setHeader("Situação Orçamento")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(s -> {
            if (Objects.nonNull(s.getId_condicaopagamento())) {
                SetCondicaoPagamento condicaoPagamento = condicaoPagamentoService.
                        fidById(s.getId_condicaopagamento());
                return condicaoPagamento == null ? "N/A" : condicaoPagamento.getDescricao_condicaopagamento();
            } else {
                return  "N/A";
            }
        }).setHeader("Condição Pagamento")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(cliente -> {
            SetEnderecos listaEnderecos = enderecoService.findById(cliente.getId_endereco());
            return listaEnderecos == null ? "N/A" : listaEnderecos.getEnderecoImovel();
        })
                .setHeader("Endereço")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(valor -> {
            String valorOrcamento = service.stringMoedaBrasileira
                    (valor.getValor_orcamento().toString());
            return valorOrcamento;
        })
                .setHeader("Valor do Orçamento")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(orc -> {
            SetContrato contrato = contratoService.findByIdOrcamento(orc.getId_orcamento());
            possuiContrato = contrato == null ? "NÃO" : "SIM";
            Span span = new Span(possuiContrato);
            if ("SIM".equals(possuiContrato)) {
                span.getStyle().set("color", "green");
            } else {
                span.getStyle().set("color", "red");
            }
            return span;
        }))
                .setHeader("Possui Contrato ?")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);
        grid.addColumn(data -> {
            if (Objects.nonNull(data.getData_inclusao())){
                return UtilitySystemConfigService.
                        getDataFormatada(data.getData_inclusao());
            } else {
                return "";
            }
        })
                .setHeader("Data de Inclusão")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(midia -> {
            SetUsuarios usuarios = usuarioService.findById(midia.getId_usuario());
            return usuarios == null ? "N/A" : usuarios.getNome_usuario();
        })
                .setHeader("Usuario")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true);

        grid.setItems(query -> orcamentoService.listByCustomer(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filter,entidade).stream());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemDoubleClickListener(this::openDetalheCadastroModal);

        VerticalLayout sidebar = buildSideBar();
        sidebar.setWidth("300px");
        sidebar.setVisible(false);// Inicialmente escondido


        // Adiciona o grid e o sidebar ao layout principal
        mainLayout.add(grid, sidebar);

        return mainLayout;
    }

    private VerticalLayout sidebar;

    private VerticalLayout buildSideBar() {

        // Cria o botão de fechar o sidebar
        sidebar = new VerticalLayout();
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


        final Registration[] btnExcluirClickListenerRegistration = {null};

        // Configura o item click listener para abrir o sidebar com detalhes
        grid.addItemClickListener(event -> {
            SetOrcamento selectedConta = event.getItem();
            // Cria o sidebar que será mostrado quando o item for clicado
            //sidebar.add(btnCloseSidebar,createBtnSidebar);
            sidebar.addClassName("v-sidebar");
            if (btnExcluirClickListenerRegistration[0] != null) {
                btnExcluirClickListenerRegistration[0].remove();
                btnExcluirClickListenerRegistration[0] = null;
            }
            // Adiciona um novo ClickListener e armazena o Registration para remoção futura
            btnExcluirClickListenerRegistration[0] = createBtnSidebar.addClickListener(event1 -> {
                SetContrato contrato = contratoService.findByIdOrcamento(selectedConta.getId_orcamento());
                possuiContrato = contrato == null ? "NÃO" : "SIM";
                if (possuiContrato.equals("SIM")) {
                    openCadastroOrdemServicoModal(event.getItem(),contrato);
                } else {
                    service.notificaErro("OBRIGATÓRIO POSSUIR CONTRATO");
                }
            });

            gridOrdemServico = new Grid<>(SetOrdemServico.class, false);
            gridOrdemServico.addColumn(SetOrdemServico::getId_ordemservico)
                    .setHeader("Id")
                    .setSortable(true)
                    .setResizable(true)
                    .setAutoWidth(true);
            gridOrdemServico.addColumn(SetOrdemServico::getNome_pontofocal)
                    .setHeader("Ponto Focal")
                    .setSortable(true)
                    .setResizable(true)
                    .setAutoWidth(true);
            gridOrdemServico.addColumn(data -> {
                if (Objects.nonNull(data.getDatainicio_ordemservico())){
                    return UtilitySystemConfigService.
                            getDataFormatada(data.getDatainicio_ordemservico().atStartOfDay());
                } else {
                    return "";
                }
            })
                    .setHeader("Data Atendimento")
                    .setSortable(true)
                    .setResizable(true)
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

    private void openCadastroOrdemServicoModal(SetOrcamento item, SetContrato contrato) {
         ordemServicoCadastroModal.setOrdemServico(item,contrato);
         ordemServicoCadastroModal.open();
    }


    private void openDetalhesOrdemServico(SetOrdemServico item) {
        UI.getCurrent().access(() -> {
            ordemServicoDadosModal.setOrdemServico(item);
            ordemServicoDadosModal.open();
        });
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
        private final ComboBox<SetSituacaoCadastro> nome = new ComboBox<>("Situação");



        public Filter(Runnable onSearch) {
            nome.setItems(situacaoCadastroService.listAll());
            nome.setItemLabelGenerator(SetSituacaoCadastro::getDescricao_situacaocadastro);

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
                Predicate idMatch = criteriaBuilder.equal(root.get("id_orcamento"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(idMatch));
            }

            if (!nome.isEmpty()) {
                String databaseColumn = "id_situacao";
                String ignore = "- ()";

                Integer lowerCaseFilter = nome.getValue().getId_situacaocadastro();
                Predicate phoneMatch = criteriaBuilder.equal(root.get("id_situacao"), lowerCaseFilter);
                predicates.add(criteriaBuilder.or(phoneMatch));

            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }


    }

    private Button createFuncionarioCadastroButton() {
        Button cadastroButton = new Button("Cadastrar", event -> openCadastroModal());
        return cadastroButton;
    }


    private void openCadastroModal() {
        UI.getCurrent().access(() -> {
            orcamentoCadastroModal = applicationContext.getBean(OrcamentoCadastroModal.class);
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
