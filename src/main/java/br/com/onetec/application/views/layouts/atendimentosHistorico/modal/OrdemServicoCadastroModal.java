package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.execucaoservico.ExecucaoServicoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoexecucaoservicoservice.OrdemServicoExecucaoServicoService;
import br.com.onetec.application.service.ordemservicoservice.*;
import br.com.onetec.application.service.pragaservice.PragaService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Component
@UIScope
public class OrdemServicoCadastroModal extends Dialog {

    private SetCliente cliente;



    private Button saveButton;
    private Button cancelButton;

    // Formulario Ordem servico
    private Div cadastroOrcamantosDadosFinanceiros;
    private ComboBox<SetSituacaoCadastro> id_situacaoservico;
    private DatePicker datainicio_ordemservico;
    private TextField diasemanainicio_ordemservico;
    private TimePicker horarioinicio_ordemservico;
    private IntegerField quantidade_ordemservico;
    private IntegerField intervalo_ordemservico;
    private TextField nome_pontofocal;
    private ComboBox<SetFuncionario> id_funcionarioassistente;
    private ComboBox<SetFuncionario> id_funcionariotecnico;
    private TextArea ocorrencias_ordemservico;
    private ComboBox<SetEnderecos> localTratamentoOrcamento;
    final RadioButtonGroup<String> confirmado_ordemservico = new RadioButtonGroup<>("Confirmado ?");

    // Formulario OrdemServicoPraga
    private Div cadastroOrdemServicoFuncionarioAlocado;
    private ComboBox<SetFuncionario> id_funcionario;
    private TextArea descricao_ordemservicofuncionarioalocado;
    private Grid<SetOrdemServicoFuncionarioAlocado> funcionarioAlocadoGrid;
    List<SetOrdemServicoFuncionarioAlocado> listaOrdemServicoFuncionarioAlocados;

    // Formulario ordemServicoExecucaoServico
    private Div ordemServicoExecucaoServico;
    private ComboBox<SetExecucaoServico> id_execucaoservico;
    private TextField valor_ordemservicoexecucaoservico;
    private TextField garantia_ordemservicoexecucaoservico;
    private TextArea descricao_ordemservicoexecucaoservico;
    private Grid<SetOrdemServicoExecucaoServico> ordemServicoExecucaoGrid;
    List<SetOrdemServicoExecucaoServico> listaOrdemServicoExecucaoServico;


    // Formulario cadastroOrdemServicoMateriais
    private Div cadastroOrdemServicoMateriais;
    private ComboBox<SetProduto> id_produto;
    private TextField numerolote_ordemservicomateriais;
    private IntegerField quantidadeprevista_ordemservicomateriais;
    private IntegerField quantidadeconsumida_ordemservicomateriais;
    private TextArea descricao_ordemservicomateriais;
    List<SetOrdemServicoMateriais> listaOrdemServicoMateriais;
    private Grid<SetOrdemServicoMateriais> ordemServicoMateriaisGrid;


    // Formulario cadastroOrdemServicoMistura
    private Div cadastroOrdemServicoMistura;
    private ComboBox<SetProduto> id_produtoMistura;
    private ComboBox<SetProduto> id_produtosolvente;
    private TextField numerolote_ordemservicomisturas;
    private IntegerField quantidadeprevistaproduto_ordemservicomisturas;
    private TextField unidademedidaproduto_ordemservicomisturas;
    private IntegerField quantidadeconsumidaproduto_ordemservicomisturas;
    private IntegerField quantidadeprevistasolvente_ordemservicomisturas;
    private TextField unidademedidasolvente_ordemservicomisturas;
    private IntegerField quantidadeconsumidasolvente_ordemservicomisturas;
    private TextArea descricao_ordemservicomisturas;
    List<SetOrdemServicoMisturas> listaOrdemServicoMisturas;
    private Grid<SetOrdemServicoMisturas> ordemServicoMisturasGrid;


    // Formulario cadastroOrdemServicoPraga
    private Div cadastroOrdemServicoPraga;
    private ComboBox<SetPraga> id_praga;
    private TextField nivelinfestacao_ordemservicopraga;
    private TextArea descricao_ordemservicopraga;
    List<SetOrdemServicoPraga> listaOrdemServicoPraga;
    private Grid<SetOrdemServicoPraga> ordemServicoPragaGrid;


    private UtilitySystemConfigService service;

    @Autowired
    private ExecucaoServicoService execucaoServicoService;

    @Autowired
    private OrdemServicoExecucaoServicoService ordemServicoExecucaoServicoService;

    @Autowired
    private PragaService pragaService;

    @Autowired
    private OrdemServicoPragaService ordemServicoPragaService;

    @Autowired
    private OrdemServicoMisturaService ordemServicoMisturaService;

    @Autowired
    private OrdemServicoMateriaisService ordemServicoMateriaisService;

    @Autowired
    private OrdemServicoFuncionarioAlocadoService funcionarioAlocadoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private SituacaoCadastroService situacaoCadastroService;

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private OrcamentoService orcamentoService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;


    @Autowired
    public void initServices (){
        UI.getCurrent().access(() -> {
            cliente = SetClienteTransiction.getCliente();
        });
    }

    public OrdemServicoCadastroModal() {
        UI.getCurrent().access(() -> {
            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão

            saveButton = new Button("Salvar", eventbe -> save());
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            Tabs tabs = new Tabs();
            Tab tab1 = new Tab("Dados Principais");
            Tab tab2 = new Tab("Funcionarios Alocados");
            Tab tab3 = new Tab("Materiais");
            Tab tab4 = new Tab("Misturas");
            Tab tab5 = new Tab("Pragas");
            Tab tab6 = new Tab("Serviços");


            tabs.add(tab1,tab6,tab2,tab3,tab4,tab5);
            // Formulario ordemServicoExecucaoServico
            cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();
            cadastroOrdemServicoFuncionarioAlocado = createFormcadastroOrdemServicoFuncionarioAlocado();
            cadastroOrdemServicoMateriais = createFormCadastroOrdemServicoMateriais();
            cadastroOrdemServicoMistura =  createFormCadastroOrdemServicoMistura();
            cadastroOrdemServicoPraga =  createFormCadastroOrdemServicoPraga();
            ordemServicoExecucaoServico = createFormCadastroOrdemServicoExecucaoServico();

//            Div content = new Div(cadastroOrcamantosDadosFinanceiros, cadastroFornecedor);
//            content.setSizeFull();
            cadastroOrcamantosDadosFinanceiros.setVisible(true);
            cadastroOrdemServicoFuncionarioAlocado.setVisible(false);
            cadastroOrdemServicoMateriais.setVisible(false);
            cadastroOrdemServicoMistura.setVisible(false);
            cadastroOrdemServicoPraga.setVisible(false);
            ordemServicoExecucaoServico.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroOrcamantosDadosFinanceiros.setVisible(false);
                cadastroOrdemServicoFuncionarioAlocado.setVisible(false);
                cadastroOrdemServicoMateriais.setVisible(false);
                cadastroOrdemServicoMistura.setVisible(false);
                cadastroOrdemServicoPraga.setVisible(false);
                ordemServicoExecucaoServico.setVisible(false);

                Tab selectedTab = tabs.getSelectedTab();
                if (selectedTab.equals(tab1)) {
                    cadastroOrcamantosDadosFinanceiros.setVisible(true);
                } else if (selectedTab.equals(tab2)) {
                    cadastroOrdemServicoFuncionarioAlocado.setVisible(true);
                } else if (selectedTab.equals(tab3)) {
                    cadastroOrdemServicoMateriais.setVisible(true);
                } else if (selectedTab.equals(tab4)) {
                    cadastroOrdemServicoMistura.setVisible(true);
                } else if (selectedTab.equals(tab5)) {
                    cadastroOrdemServicoPraga.setVisible(true);
                } else if (selectedTab.equals(tab6)) {
                    ordemServicoExecucaoServico.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroOrcamantosDadosFinanceiros,
                                    cadastroOrdemServicoFuncionarioAlocado,
                                    cadastroOrdemServicoMateriais,
                                    cadastroOrdemServicoMistura,
                                    cadastroOrdemServicoPraga,
                                    ordemServicoExecucaoServico);
            contentTabs.setSizeFull();
            //cadastroOrcamantosDadosFinanceiros.setVisible(true);
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    private Div createFormCadastroOrdemServicoPraga() {
        service = new UtilitySystemConfigService();
        id_praga = new ComboBox<>("Praga");
        id_praga.setItems(pragaService.listAll());
        id_praga.setItemLabelGenerator(SetPraga::getDescricao_praga);

        nivelinfestacao_ordemservicopraga = new TextField("Nivel Infestação");
        descricao_ordemservicopraga = new TextArea("Descrição");
        listaOrdemServicoPraga = new ArrayList<>();

        ordemServicoPragaGrid = new Grid<>(SetOrdemServicoPraga.class, false);
        ordemServicoPragaGrid.addColumn(praga -> {
            SetPraga praga1 = pragaService.findById(praga.getId_praga());
            return praga1 != null ? praga1.getDescricao_praga() : "N/A"; })
                .setHeader("Praga")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoPragaGrid.addColumn(SetOrdemServicoPraga::getNivelinfestacao_ordemservicopraga)
                .setHeader("Nivel Infestação")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoPragaGrid.addColumn(SetOrdemServicoPraga::getDescricao_ordemservicopraga)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoPragaGrid.addColumn(data -> UtilitySystemConfigService.
                getDataFormatada(data.getData_inclusao()))
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);

        Button saveAdicionarButton = new Button("Adicionar", event -> {
            SetOrdemServicoPraga dto = new SetOrdemServicoPraga();
            dto.setId_praga(id_praga.getValue().getId_praga());
            dto.setNivelinfestacao_ordemservicopraga(nivelinfestacao_ordemservicopraga.getValue());
            dto.setDescricao_ordemservicopraga(descricao_ordemservicopraga.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaOrdemServicoPraga.add(dto);
            ordemServicoPragaGrid.setItems(listaOrdemServicoPraga);
        });

        ordemServicoPragaGrid.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                listaOrdemServicoPraga.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                ordemServicoPragaGrid.setItems(listaOrdemServicoPraga);
                // Feedback ao usuário
                Notification.show("Removido", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_praga,
                nivelinfestacao_ordemservicopraga,
                descricao_ordemservicopraga,saveAdicionarButton);
        Div div = new Div(formLayout,ordemServicoPragaGrid);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroOrdemServicoMistura() {

        service = new UtilitySystemConfigService();
        id_produtoMistura = new ComboBox<>("Produto");
        id_produtoMistura.setItems(produtoService.findAll());
        id_produtoMistura.setItemLabelGenerator(SetProduto::getNome_produto);

        id_produtosolvente = new ComboBox<>("Solvente");
        id_produtosolvente.setItems(produtoService.findAll());
        id_produtosolvente.setItemLabelGenerator(SetProduto::getNome_produto);

        numerolote_ordemservicomisturas= new TextField("N° Lote");
        quantidadeprevistaproduto_ordemservicomisturas = new IntegerField("Quantidade Prevista Produto");
        unidademedidaproduto_ordemservicomisturas = new TextField("Unidade Medida Produto");
        quantidadeconsumidaproduto_ordemservicomisturas = new IntegerField("Quantidade Consumida Produto");
        quantidadeprevistasolvente_ordemservicomisturas = new IntegerField("Quantidade Prevista Solvente");
        unidademedidasolvente_ordemservicomisturas = new TextField("Unidade Medida Solvente");
        quantidadeconsumidasolvente_ordemservicomisturas = new IntegerField("Quantidade Consumida Solvente");
        descricao_ordemservicomisturas = new TextArea("Descrição");

        quantidadeprevistaproduto_ordemservicomisturas.setStepButtonsVisible(true);
        quantidadeconsumidaproduto_ordemservicomisturas.setStepButtonsVisible(true);
        quantidadeprevistasolvente_ordemservicomisturas.setStepButtonsVisible(true);
        quantidadeconsumidasolvente_ordemservicomisturas.setStepButtonsVisible(true);

        listaOrdemServicoMisturas = new ArrayList<>();

        ordemServicoMisturasGrid = new Grid<>(SetOrdemServicoMisturas.class, false);
        ordemServicoMisturasGrid.addColumn(produto -> {
            SetProduto setProduto = produtoService.findById(produto.getId_produto());
            return setProduto != null ? setProduto.getNome_produto() : "N/A"; })
                .setHeader("Produto")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoMisturasGrid.addColumn(produto -> {
            SetProduto setProduto = produtoService.findById(produto.getId_produtosolvente());
            return setProduto != null ? setProduto.getNome_produto() : "N/A"; })
                .setHeader("Solvente")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoMisturasGrid.addColumn(SetOrdemServicoMisturas::getQuantidadeprevistaproduto_ordemservicomisturas)
                .setHeader("Valor")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoMisturasGrid.addColumn(SetOrdemServicoMisturas::getQuantidadeprevistasolvente_ordemservicomisturas)
                .setHeader("Garantia")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoMisturasGrid.addColumn(SetOrdemServicoMisturas::getDescricao_ordemservicomisturas)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoMisturasGrid.addColumn(data -> UtilitySystemConfigService.
                getDataFormatada(data.getData_inclusao()))
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);

        Button saveAdicionarButton = new Button("Adicionar", event -> {
            SetOrdemServicoMisturas dto = new SetOrdemServicoMisturas();
            dto.setId_produto(id_produtoMistura.getValue().getId_produto());
            dto.setId_produtosolvente(id_produtosolvente.getValue().getId_produto());
            dto.setQuantidadeconsumidaproduto_ordemservicomisturas(quantidadeconsumidaproduto_ordemservicomisturas.getValue());
            dto.setQuantidadeconsumidasolvente_ordemservicomisturas(quantidadeconsumidasolvente_ordemservicomisturas.getValue());
            dto.setQuantidadeprevistaproduto_ordemservicomisturas(quantidadeprevistaproduto_ordemservicomisturas.getValue());
            dto.setQuantidadeprevistasolvente_ordemservicomisturas(quantidadeprevistasolvente_ordemservicomisturas.getValue());
            dto.setDescricao_ordemservicomisturas(descricao_ordemservicomisturas.getValue());
            dto.setUnidademedidaproduto_ordemservicomisturas(unidademedidaproduto_ordemservicomisturas.getValue());
            dto.setUnidademedidasolvente_ordemservicomisturas(unidademedidasolvente_ordemservicomisturas.getValue());
            dto.setNumerolote_ordemservicomisturas(numerolote_ordemservicomisturas.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaOrdemServicoMisturas.add(dto);
            ordemServicoMisturasGrid.setItems(listaOrdemServicoMisturas);
        });

        ordemServicoMisturasGrid.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                listaOrdemServicoMisturas.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                ordemServicoMisturasGrid.setItems(listaOrdemServicoMisturas);
                // Feedback ao usuário
                Notification.show("Removido", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_produtoMistura,
                id_produtosolvente,
                numerolote_ordemservicomisturas,
                quantidadeprevistaproduto_ordemservicomisturas,
                unidademedidaproduto_ordemservicomisturas,
                quantidadeconsumidaproduto_ordemservicomisturas,
                quantidadeprevistasolvente_ordemservicomisturas,
                unidademedidasolvente_ordemservicomisturas,
                quantidadeconsumidasolvente_ordemservicomisturas,
                descricao_ordemservicomisturas,saveAdicionarButton);
        Div div = new Div(formLayout,ordemServicoMisturasGrid);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroOrdemServicoExecucaoServico() {
        service = new UtilitySystemConfigService();
        listaOrdemServicoExecucaoServico = new ArrayList<>();
        id_execucaoservico = new ComboBox<>("Serviço");
        id_execucaoservico.setItems(execucaoServicoService.findAll());
        id_execucaoservico.setItemLabelGenerator(SetExecucaoServico::getNome_execucaoservico);
        valor_ordemservicoexecucaoservico = new TextField("Valor");
        valor_ordemservicoexecucaoservico.setValueChangeMode(ValueChangeMode.EAGER);
        valor_ordemservicoexecucaoservico.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_ordemservicoexecucaoservico));
        valor_ordemservicoexecucaoservico.setPlaceholder("R$ 0,00");
        garantia_ordemservicoexecucaoservico = new TextField("Garantia");
        descricao_ordemservicoexecucaoservico = new TextArea("Descrição");

        ordemServicoExecucaoGrid = new Grid<>(SetOrdemServicoExecucaoServico.class, false);
        ordemServicoExecucaoGrid.addColumn(produto -> {
            SetExecucaoServico setProduto = execucaoServicoService.findById(produto.getId_execucaoservico());
            return setProduto != null ? setProduto.getNome_execucaoservico() : "N/A"; })
                .setHeader("Serviço")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoExecucaoGrid.addColumn(SetOrdemServicoExecucaoServico::getValor_ordemservicoexecucaoservico)
                .setHeader("Valor")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoExecucaoGrid.addColumn(SetOrdemServicoExecucaoServico::getGarantia_ordemservicoexecucaoservico)
                .setHeader("Garantia")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoExecucaoGrid.addColumn(SetOrdemServicoExecucaoServico::getDescricao_ordemservicoexecucaoservico)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);
        ordemServicoExecucaoGrid.addColumn(data -> UtilitySystemConfigService.
                getDataFormatada(data.getData_inclusao()))
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);

        Button saveAdicionarButton = new Button("Adicionar", event -> {
            SetOrdemServicoExecucaoServico dto = new SetOrdemServicoExecucaoServico();
            dto.setId_execucaoservico(id_execucaoservico.getValue().getId_execucaoservico());
            dto.setDescricao_ordemservicoexecucaoservico(descricao_ordemservicoexecucaoservico.getValue());
            dto.setGarantia_ordemservicoexecucaoservico(garantia_ordemservicoexecucaoservico.getValue());
            dto.setValor_ordemservicoexecucaoservico(service.getValorBigDecimal
                    (valor_ordemservicoexecucaoservico.getValue()));
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaOrdemServicoExecucaoServico.add(dto);
            ordemServicoExecucaoGrid.setItems(listaOrdemServicoExecucaoServico);
        });

        ordemServicoExecucaoGrid.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                listaOrdemServicoExecucaoServico.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                ordemServicoExecucaoGrid.setItems(listaOrdemServicoExecucaoServico);
                // Feedback ao usuário
                Notification.show("Removido", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);

        HorizontalLayout id_execucaoservicolayout =
                new CustomizedComboBox().customizeExecucaoServico(id_execucaoservico,execucaoServicoService);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_execucaoservicolayout,
                valor_ordemservicoexecucaoservico,
                garantia_ordemservicoexecucaoservico,
                descricao_ordemservicoexecucaoservico,saveAdicionarButton);
        Div div = new Div(formLayout,ordemServicoExecucaoGrid);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroOrdemServicoMateriais() {
        listaOrdemServicoMateriais = new ArrayList<>();
        id_produto = new ComboBox<>("Produto");
        id_produto.setItems(produtoService.findAll());
        id_produto.setItemLabelGenerator(SetProduto::getNome_produto);
        numerolote_ordemservicomateriais = new TextField("N° Lote");
        quantidadeprevista_ordemservicomateriais = new IntegerField("Quantidade Prevista");
        quantidadeconsumida_ordemservicomateriais = new IntegerField("Quantidade Consumida");
        descricao_ordemservicomateriais = new TextArea("Observações");

        quantidadeprevista_ordemservicomateriais.setStepButtonsVisible(true);
        quantidadeconsumida_ordemservicomateriais.setStepButtonsVisible(true);
            //quantidadeprevista_ordemservicomateriais.setValue(0);
            //quantidadeprevista_ordemservicomateriais.setMin(0);

            ordemServicoMateriaisGrid = new Grid<>(SetOrdemServicoMateriais.class, false);
            ordemServicoMateriaisGrid.addColumn(produto -> {
                SetProduto setProduto = produtoService.findById(produto.getId_produto());
                return setProduto != null ? setProduto.getNome_produto() : "N/A"; })
                    .setHeader("Produto")
                    .setSortable(true)
                    .setAutoWidth(true);
            ordemServicoMateriaisGrid.addColumn(SetOrdemServicoMateriais::getNumerolote_ordemservicomateriais)
                    .setHeader("N° Lote")
                    .setSortable(true)
                    .setAutoWidth(true);
            ordemServicoMateriaisGrid.addColumn(SetOrdemServicoMateriais::getQuantidadeprevista_ordemservicomateriais)
                    .setHeader("Quantidade Prevista")
                    .setSortable(true)
                    .setAutoWidth(true);
            ordemServicoMateriaisGrid.addColumn(SetOrdemServicoMateriais::getQuantidadeconsumida_ordemservicomateriais)
                    .setHeader("Quantidade Consumida")
                    .setSortable(true)
                    .setAutoWidth(true);
            ordemServicoMateriaisGrid.addColumn(SetOrdemServicoMateriais::getDescricao_ordemservicomateriais)
                    .setHeader("Observações")
                    .setSortable(true)
                    .setAutoWidth(true);
            ordemServicoMateriaisGrid.addColumn(data -> UtilitySystemConfigService.
                    getDataFormatada(data.getData_inclusao()))
                    .setHeader("Data Inclusão")
                    .setSortable(true)
                    .setAutoWidth(true);

            Button saveAdicionarButton = new Button("Adicionar", event -> {
                SetOrdemServicoMateriais dto = new SetOrdemServicoMateriais();
                dto.setId_produto(id_produto.getValue().getId_produto());
                dto.setDescricao_ordemservicomateriais(descricao_ordemservicomateriais.getValue());
                dto.setQuantidadeconsumida_ordemservicomateriais(quantidadeconsumida_ordemservicomateriais.getValue());
                dto.setQuantidadeprevista_ordemservicomateriais(quantidadeprevista_ordemservicomateriais.getValue());
                dto.setNumerolote_ordemservicomateriais(numerolote_ordemservicomateriais.getValue());
                dto.setData_inclusao(LocalDateTime.now());
                dto.setAtivo("S");
                dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                listaOrdemServicoMateriais.add(dto);
                ordemServicoMateriaisGrid.setItems(listaOrdemServicoMateriais);
            });

            ordemServicoMateriaisGrid.addComponentColumn(arquivoOrcamento -> {
                // Cria o botão de deletar com um ícone de lixeira
                Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                    // Remove o item da lista
                    listaOrdemServicoMateriais.remove(arquivoOrcamento);
                    // Atualiza os itens da grid
                    ordemServicoMateriaisGrid.setItems(listaOrdemServicoMateriais);
                    // Feedback ao usuário
                    Notification.show("Removido", 3000, Notification.Position.MIDDLE);
                });
                del.getElement().setAttribute("aria-label", "Delete");
                del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
                return del;
            }).setSortable(false).setAutoWidth(true);


            FormLayout formLayout = new FormLayout();
            formLayout.setWidthFull();
            formLayout.add(id_produto,
                    numerolote_ordemservicomateriais,
                    quantidadeprevista_ordemservicomateriais,
                    quantidadeconsumida_ordemservicomateriais,
                    descricao_ordemservicomateriais,saveAdicionarButton);
            Div div = new Div(formLayout,ordemServicoMateriaisGrid);
            div.setSizeFull();

            return div;
    }

    private Div createFormcadastroOrdemServicoFuncionarioAlocado() {
        listaOrdemServicoFuncionarioAlocados = new ArrayList<>();
        id_funcionario = new ComboBox<>("Funcionario");
        id_funcionario.setItems(funcionarioService.listAll());
        id_funcionario.setItemLabelGenerator(SetFuncionario::getNome_funcionario);
        descricao_ordemservicofuncionarioalocado = new TextArea("Descrição");

        funcionarioAlocadoGrid = new Grid<>(SetOrdemServicoFuncionarioAlocado.class, false);
        funcionarioAlocadoGrid.addColumn(funcionarioAlocado -> {
            SetFuncionario funcionario = funcionarioService.findById(funcionarioAlocado.getId_funcionario());
            return funcionario != null ? funcionario.getNome_funcionario() : "N/A"; })
                .setHeader("Funcionário")
                .setSortable(true)
                .setAutoWidth(true);
        funcionarioAlocadoGrid.addColumn(SetOrdemServicoFuncionarioAlocado::getDescricao_ordemservicofuncionarioalocado)
                .setHeader("Descrição")
                .setSortable(true)
                .setAutoWidth(true);
        funcionarioAlocadoGrid.addColumn(data -> UtilitySystemConfigService.
                                    getDataFormatada(data.getData_inclusao()))
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);



        Button saveAdicionarButton = new Button("Adicionar Funcionario", event -> {
            SetOrdemServicoFuncionarioAlocado dto = new SetOrdemServicoFuncionarioAlocado();
            dto.setId_funcionario(id_funcionario.getValue().getId_funcionario());
            dto.setDescricao_ordemservicofuncionarioalocado(descricao_ordemservicofuncionarioalocado.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaOrdemServicoFuncionarioAlocados.add(dto);
            funcionarioAlocadoGrid.setItems(listaOrdemServicoFuncionarioAlocados);
        });

        funcionarioAlocadoGrid.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                listaOrdemServicoFuncionarioAlocados.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                funcionarioAlocadoGrid.setItems(listaOrdemServicoFuncionarioAlocados);
                // Feedback ao usuário
                Notification.show("Funcionario removido", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_funcionario,
                descricao_ordemservicofuncionarioalocado,saveAdicionarButton);
        Div div = new Div(formLayout,funcionarioAlocadoGrid);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroOrcamantosDadosFinanceiros() {

        service = new UtilitySystemConfigService();


        localTratamentoOrcamento = new ComboBox<>("Local Tratamento");

        localTratamentoOrcamento.setItemLabelGenerator(SetEnderecos::getEnderecoImovel);

        id_situacaoservico = new ComboBox<>("Situação");
        id_situacaoservico.setItems
                (situacaoCadastroService.listAll());
        id_situacaoservico.setItemLabelGenerator(SetSituacaoCadastro::getDescricao_situacaocadastro);

        datainicio_ordemservico = new DatePicker("Data Inicio");
        service.configuraCalendario(datainicio_ordemservico);


        diasemanainicio_ordemservico = new TextField("Dia da Semana");
        diasemanainicio_ordemservico.isReadOnly();
        diasemanainicio_ordemservico.setReadOnly(true);

        datainicio_ordemservico.addValueChangeListener(event -> {
            DayOfWeek a = event.getValue().getDayOfWeek();
            diasemanainicio_ordemservico.setValue(a.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt")));
        });

        horarioinicio_ordemservico = new TimePicker("Horario");
        quantidade_ordemservico = new IntegerField("Quantidade");
        quantidade_ordemservico.setStepButtonsVisible(true);

        intervalo_ordemservico = new IntegerField("Intervalo");
        intervalo_ordemservico.setStepButtonsVisible(true);

        nome_pontofocal = new TextField("Responsável no Local");
        id_funcionarioassistente = new ComboBox<>("Assistente");
        id_funcionarioassistente.setItems
                (funcionarioService.listAll());
        id_funcionarioassistente.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        id_funcionariotecnico = new ComboBox<>("Técnico");
        id_funcionariotecnico.setItems
                (funcionarioService.listAll());
        id_funcionariotecnico.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        ocorrencias_ordemservico = new TextArea("Ocorrências");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        confirmado_ordemservico.setItems(List.of("SIM","NÃO"));

        HorizontalLayout situacaoOrcamentolayout =
                new CustomizedComboBox().customizeSituacaoCadastro(id_situacaoservico,situacaoCadastroService);

        formLayout.add(localTratamentoOrcamento,
                situacaoOrcamentolayout,
                datainicio_ordemservico,
                diasemanainicio_ordemservico,
                horarioinicio_ordemservico,
                quantidade_ordemservico,
                intervalo_ordemservico,
                nome_pontofocal,
                id_funcionarioassistente,id_funcionariotecnico,
                confirmado_ordemservico,
                ocorrencias_ordemservico);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }



    private void save() {
        // Lógica para salvar o cadastro
        SetOrdemServico dto = new SetOrdemServico();

        try {
            if (id_funcionarioassistente.getValue() != null) {
                dto.setId_funcionarioassistente(id_funcionarioassistente.getValue().getId_funcionario());
            }
            if (id_funcionariotecnico.getValue() != null) {
                dto.setId_funcionariotecnico(id_funcionariotecnico.getValue().getId_funcionario());
            }
            if (id_situacaoservico.getValue() != null) {
                dto.setId_situacaoservico(id_situacaoservico.getValue().getId_situacaocadastro());
            }
            if (localTratamentoOrcamento.getValue() != null) {
                dto.setId_endereco(localTratamentoOrcamento.getValue().getId_endereco());
            }

            dto.setId_cliente(cliente.getId_cliente());
            dto.setOcorrencias_ordemservico(ocorrencias_ordemservico.getValue());
            dto.setDatainicio_ordemservico(datainicio_ordemservico.getValue());
            dto.setHorarioinicio_ordemservico(horarioinicio_ordemservico.getValue());
            if(confirmado_ordemservico.getValue().equals("SIM")){
                dto.setConfirmado_ordemservico("S");
            } else if(confirmado_ordemservico.getValue().equals("NÃO")) {
                dto.setConfirmado_ordemservico("N");
            } else {
                service.notificaErro("Necessário selecionar confimação SIM/NÃO");
                return;
            }
           // dto.setConfirmado_ordemservico(confirmado_ordemservico.getValue());
            dto.setDiasemanainicio_ordemservico(diasemanainicio_ordemservico.getValue());
            dto.setNome_pontofocal(nome_pontofocal.getValue());
            dto.setQuantidade_ordemservico(quantidade_ordemservico.getValue());
            dto.setIntervalo_ordemservico(intervalo_ordemservico.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            dto.setAtivo("S");
            service = new UtilitySystemConfigService();
            ordemServicoService.save(dto);
            if(listaOrdemServicoFuncionarioAlocados.size() > 0){
                listaOrdemServicoFuncionarioAlocados.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        funcionarioAlocadoService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoExecucaoServico.size() > 0){
                listaOrdemServicoExecucaoServico.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoExecucaoServicoService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
            if(listaOrdemServicoMateriais.size() > 0){
                listaOrdemServicoMateriais.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoMateriaisService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoMisturas.size() > 0){
                listaOrdemServicoMisturas.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoMisturaService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoPraga.size() > 0){
                listaOrdemServicoPraga.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoPragaService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            id_situacaoservico.clear();
           // datainicio_ordemservico.clear();
            diasemanainicio_ordemservico.clear();
            horarioinicio_ordemservico.clear();
            quantidade_ordemservico.clear();
            intervalo_ordemservico.clear();
            nome_pontofocal.clear();
            id_funcionarioassistente.clear();
            id_funcionariotecnico.clear();
            ocorrencias_ordemservico.clear();
            localTratamentoOrcamento.clear();
            id_funcionario.clear();
            descricao_ordemservicofuncionarioalocado.clear();
            listaOrdemServicoFuncionarioAlocados = new ArrayList<>();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
            System.out.println(e.getMessage().toString());
        }

    }

    public void setOrdemServico(SetOrcamento item) {

        localTratamentoOrcamento.setItems
                (enderecoService.findAllClienteId(item.getId_cliente()));
        List<SetOrcamento> listaorcamentos = orcamentoService.findAllClienteId(item.getId_cliente());
//                (listaorcamentos.stream()
//                        .filter(objeto -> objeto.getId_orcamento().equals(item.getId_orcamento()))
//                        .findFirst().orElse(null));
    }
}
