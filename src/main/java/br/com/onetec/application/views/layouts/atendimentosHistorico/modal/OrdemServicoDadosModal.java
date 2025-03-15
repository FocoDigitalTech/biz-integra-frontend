package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.clientesservice.ClientesService;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.clientesservice.ResponsavelCobrancaService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.dadosempresaservice.DadosEmpresaService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.execucaoservico.ExecucaoServicoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentocontatoservice.OrcamentoContatoService;
import br.com.onetec.application.service.orcamentoposvendaservice.OrcamentoPosVendasService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.ordemservicoexecucaoservicoservice.OrdemServicoExecucaoServicoService;
import br.com.onetec.application.service.ordemservicoservice.*;
import br.com.onetec.application.service.pragaservice.PragaService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.service.regiaoservice.RegiaoService;
import br.com.onetec.application.service.tipoatendimentoservice.TipoAtendimentoService;
import br.com.onetec.application.service.tipoimovelservice.TipoImovelService;
import br.com.onetec.application.service.tipomidiaservice.TipoMidiaService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.layouts.atendimentosHistorico.component.ContatoModal;
import br.com.onetec.application.views.layouts.atendimentosHistorico.component.ServicoModal;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
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
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
public class OrdemServicoDadosModal extends Dialog {

    SetOrdemServico ordemServico;

    private Div cadastroOrcamantosDadosFinanceiros;
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;

    // Formulario
    private TextField id_orcamento;
    private ComboBox<SetTipoAtendimento> id_tipoatendimento;
    private DatePicker datainicio_ordemservico;
    private TextField diasemanainicio_ordemservico;
    private TimePicker horarioinicio_ordemservico;
    private TextField nome_pontofocal;
    private ComboBox<SetFuncionario> id_funcionarioassistente;
    private ComboBox<SetFuncionario> id_funcionariotecnico;
    private TextArea ocorrencias_ordemservico;
    private ComboBox<SetEnderecos> localTratamentoOrcamento;
    final RadioButtonGroup<String> confirmado_ordemservico = new RadioButtonGroup<>("Confirmado ?");

    private UtilitySystemConfigService service;


    private SetCliente cliente;

    // Formulario OrdemServicoPraga
    private Div cadastroOrdemServicoFuncionarioAlocado;
    private ComboBox<SetFuncionario> id_funcionario;
    private TextArea descricao_ordemservicofuncionarioalocado;
    private Grid<SetOrdemServicoFuncionarioAlocado> funcionarioAlocadoGrid;
    List<SetOrdemServicoFuncionarioAlocado> listaOrdemServicoFuncionarioAlocados;
    List<SetOrdemServicoFuncionarioAlocado> listaOrdemServicoFuncionarioAlocadosUpdate = new ArrayList<>();

    // Formulario ordemServicoExecucaoServico
    private Div ordemServicoExecucaoServico;
    private ComboBox<SetExecucaoServico> id_execucaoservico;
    private TextField valor_ordemservicoexecucaoservico;
    private TextField garantia_ordemservicoexecucaoservico;
    private TextArea descricao_ordemservicoexecucaoservico;
    private Grid<SetOrdemServicoExecucaoServico> ordemServicoExecucaoGrid;
    List<SetOrdemServicoExecucaoServico> listaOrdemServicoExecucaoServico;
    List<SetOrdemServicoExecucaoServico> listaOrdemServicoExecucaoServicoUpdate = new ArrayList<>();


    // Formulario cadastroOrdemServicoMateriais
    private Div cadastroOrdemServicoMateriais;
    private ComboBox<SetProduto> id_produto;
    private TextField numerolote_ordemservicomateriais;
    private IntegerField quantidadeprevista_ordemservicomateriais;
    private IntegerField quantidadeconsumida_ordemservicomateriais;
    private TextArea descricao_ordemservicomateriais;
    List<SetOrdemServicoMateriais> listaOrdemServicoMateriais;
    List<SetOrdemServicoMateriais> listaOrdemServicoMateriaisUpdate = new ArrayList<>();
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
    List<SetOrdemServicoMisturas> listaOrdemServicoMisturasUpdate = new ArrayList<>();
    private Grid<SetOrdemServicoMisturas> ordemServicoMisturasGrid;


    // Formulario cadastroOrdemServicoPraga
    private Div cadastroOrdemServicoPraga;
    private ComboBox<SetPraga> id_praga;
    private TextField nivelinfestacao_ordemservicopraga;
    private TextArea descricao_ordemservicopraga;
    List<SetOrdemServicoPraga> listaOrdemServicoPraga;
    List<SetOrdemServicoPraga> listaOrdemServicoPragaUpdate = new ArrayList<>();
    private Grid<SetOrdemServicoPraga> ordemServicoPragaGrid;


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
    private TipoAtendimentoService tipoAtendimentoService;

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private OrcamentoService orcamentoService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;



    public void initServices (){
        UI.getCurrent().access(() -> {
            //new OrdemServicoDadosModal();
            cliente = SetClienteTransiction.getCliente();
        });
    }


    public OrdemServicoDadosModal() {

        UI.getCurrent().access(() -> {
            downloadLink = new Anchor();

            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão

            saveButton = new Button("Salvar", eventbe -> save());
            deleteButton = new Button("Excluir", e -> deletaOrdem(ordemServico));
            Button botaoinspecao = new Button("Gerar Ficha Ordem de Serviço", event -> gerarFichaOs());
            botaoinspecao.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            botaoinspecao.setVisible(true);

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
                    botaoinspecao.setVisible(true);
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

            deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

            //getFooter().add(saveButton, cancelButton,deleteButton);

            getFooter().add(deleteButton); // Alinha à esquerda
            HorizontalLayout rightButtons = new HorizontalLayout(botaoinspecao,saveButton, cancelButton);
            getFooter().add(rightButtons,downloadLink); // Alinha à direita

            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            H2 title = new H2("Dados Ordem de Serviço");
            getHeader().add(title);
            add(layout);
        });
    }

    @Autowired
    private RegiaoService regiaoService;

    @Autowired
    private TipoMidiaService tipoMidiaService;

    @Autowired
    private ResponsavelCobrancaService responsavelCobrancaService;

    @Autowired
    private TipoImovelService tipoImovelService;

    @Autowired
    private OrcamentoContatoService orcamentoContatoService;

    @Autowired
    private OrcamentoPosVendasService orcamentoPosVendasService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ClientesService clientesService;

    private Anchor downloadLink;

    private List<SetServico> servicosFilter;

    private List<SetOrdemServicoExecucaoServico> listaservicosexecucaodetalhadas;

    @Autowired
    private DadosEmpresaService dadosEmpresaService;

    SetDadosEmpresa setDadosEmpresa;


    private void gerarFichaOs() {

       if (localTratamentoOrcamento.isEmpty()) {
            localTratamentoOrcamento.setRequiredIndicatorVisible(true);
            localTratamentoOrcamento.setErrorMessage("Campo obrigatório");
            localTratamentoOrcamento.setInvalid(true);
        }
        if (id_tipoatendimento.isEmpty()) {
            id_tipoatendimento.setRequiredIndicatorVisible(true);
            id_tipoatendimento.setErrorMessage("Campo obrigatório");
            id_tipoatendimento.setInvalid(true);
        } else {
            try {
                cliente = clientesService.findById(ordemServico.getId_cliente());
                String hora = String.valueOf(LocalDateTime.now().getSecond());
                String idorc = String.valueOf(ordemServico.getId_orcamento()).concat(String.valueOf(ordemServico.getId_cliente()));
                String nameClien = cliente.getNome_cliente();
                String compositeId = hora+idorc+nameClien;
                // Caminho do arquivo Word de entrada e dos arquivos de saída
                String wordPath = "C:\\SYSTEM_files_NAGASAKI\\DOC_FILES\\matriz_ordem_serviço.docx";
                String updatedWordPath = "C:\\SYSTEM_files_NAGASAKI\\GENERATED_FILES\\matriz_ordem_serviço"+compositeId+"ordem_serviço.docx";
                String pdfPath = "C:\\SYSTEM_files_NAGASAKI\\DOC_FILES\\matriz_ordem_serviço"+compositeId+"ordem_serviço.pdf";
                String clientId = ordemServico.getId_orcamento().toString(); // Exemplo de ID do cliente a ser substituído

                SetEnderecos enderecos = enderecoService.findAllById(ordemServico.getId_endereco());

                SetContrato contrato = contratoService.findByIdOrcamento(ordemServico.getId_orcamento());
                // Edita o documento Word
                SetRegiao regiao = regiaoService.findByIdRegiao(enderecos.getId_regiao());
                SetTipoMidia midia = tipoMidiaService.findByIdMidia(cliente.getId_anuncio()
                );
                SetResponsavelCobranca cobranca = responsavelCobrancaService.find(cliente.getId_cliente());
                SetEstado uf = estadoService.findById(enderecos.getId_estado());
                SetTipoImovel tipoImovel = tipoImovelService.findByIdImovel(enderecos.getId_tipoimovel());

                SetFuncionario funcionario = funcionarioService.findById(ordemServico.getId_funcionariotecnico());

                listaservicosexecucaodetalhadas = listaOrdemServicoExecucaoServico;

                List<SetExecucaoServico> servicoList = execucaoServicoService.findAll();


                SetTipoAtendimento tipoordem = id_tipoatendimento.getValue();

                setDadosEmpresa = dadosEmpresaService.getDados(1);



                SetClienteTransiction.editWordFichaOrdemDocument(wordPath, updatedWordPath,
                        "81038",ordemServico, cliente,
                        enderecos,contrato,uf,cobranca,midia,funcionario,regiao,tipoImovel
                ,diasemanainicio_ordemservico.getValue(),listaservicosexecucaodetalhadas,tipoordem,servicoList,
                        setDadosEmpresa.getNomequimico_dadosempresa());

                // Converte o documento editado para PDF
                SetClienteTransiction.convertDocxToPdf(updatedWordPath, pdfPath);

                // Baixa o PDF
                File wordFile = new File(updatedWordPath);

                if (wordFile.exists()) {
                    // Cria um recurso de fluxo para o arquivo Word
                    StreamResource resource = new StreamResource(wordFile.getName(), () -> {
                        try {
                            return new FileInputStream(wordFile);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                            return null;
                        }
                    });

                    // Adiciona um link para download na interface
                    downloadLink.setText("Baixar Ficha Ordem de Serviço");
                    //downloadLink.getElement().setAttribute("download", true);
                    downloadLink.getStyle().set("margin-top", "20px");
                    downloadLink.getStyle().set("font-size", "18px");
                    downloadLink.setHref(resource);  // Seta o recurso de download
                    downloadLink.setVisible(true);

                    // Exibe uma notificação de sucesso
                    Notification.show("Documento Word disponível para download. : "+ updatedWordPath);
                    // Definir o alvo para abrir em nova aba
                    downloadLink.setTarget("_blank");
                } else {
                    Notification.show("Erro: Documento Word não encontrado.");
                }
            } catch (IOException exa) {
                Notification.show("Erro ao gerar o Word: " + exa.getMessage());
                exa.printStackTrace();
            }
        }
    }

    private void deletaOrdem(SetOrdemServico ordemServico) {
        try {
            ordemServicoService.delete(ordemServico);
            service.notificaSucesso("Excluido com sucesso");
            orcamentoDiv.refreshGrid();
            close();
        } catch (Exception e){
            service.notificaErro("Erro ao excluir.");
        }
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
            listaOrdemServicoPragaUpdate.add(dto);
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
            listaOrdemServicoMisturasUpdate.add(dto);
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

        ordemServicoExecucaoGrid.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_ordemservicoexecucaoservico())) {
                    ServicoModal.openServicoContato(event.getItem(),execucaoServicoService, service,ordemServicoExecucaoGrid,ordemServicoExecucaoServicoService);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar novo Serviço !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });

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
            listaOrdemServicoExecucaoServicoUpdate.add(dto);
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
        //id_execucaoservicolayout.setSizeFull();

        descricao_ordemservicoexecucaoservico.setWidth("1000px"); // Ajuste conforme necessário
        descricao_ordemservicoexecucaoservico.getStyle().set("max-width", "1000px");

        valor_ordemservicoexecucaoservico.setWidth("480px"); // Ajuste conforme necessário
        valor_ordemservicoexecucaoservico.getStyle().set("max-width", "480px");

        garantia_ordemservicoexecucaoservico.setWidth("480px"); // Ajuste conforme necessário
        garantia_ordemservicoexecucaoservico.getStyle().set("max-width", "480px");
        //id_execucaoservico.getStyle().set("white-space", "normal");
        //id_execucaoservico.getStyle().set("word-wrap", "break-word");

        HorizontalLayout horizontalLayout = new HorizontalLayout(valor_ordemservicoexecucaoservico,
                garantia_ordemservicoexecucaoservico);

        VerticalLayout design = new VerticalLayout(id_execucaoservicolayout,
                horizontalLayout,
                descricao_ordemservicoexecucaoservico,saveAdicionarButton);
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(design);
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
            listaOrdemServicoMateriaisUpdate.add(dto);
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
            listaOrdemServicoFuncionarioAlocadosUpdate.add(dto);
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


        localTratamentoOrcamento.setItemLabelGenerator(event ->
                event.getEnderecoImovel() + "," + event.getNumero_imovel());

        id_orcamento = new TextField("Id Orçamento");

        //id_orcamento.setItemLabelGenerator(item -> item.getId_orcamento().toString());


        id_tipoatendimento = new ComboBox<>("Tipo Atendimento");
        id_tipoatendimento.setItems
                (tipoAtendimentoService.listAll());
        id_tipoatendimento.setItemLabelGenerator(SetTipoAtendimento::getDescricao_tipoatendimento);
        HorizontalLayout tipoeventofinanceirolayout =
                new CustomizedComboBox().customizeTipoAtendimento
                        (id_tipoatendimento, tipoAtendimentoService);


        tipoeventofinanceirolayout.setWidthFull();

        localTratamentoOrcamento.addClassName("combo-box-item");

        // Configuração do layout ou overlay se necessário
        localTratamentoOrcamento.getElement().getStyle().set("--vaadin-combo-box-overlay-width", "auto");


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

        nome_pontofocal = new TextField("Responsável no Local");
        id_orcamento.setReadOnly(true);
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

        localTratamentoOrcamento.setRequiredIndicatorVisible(true);
        localTratamentoOrcamento.addValueChangeListener(event -> {
            if (localTratamentoOrcamento.isEmpty()) {
                localTratamentoOrcamento.setErrorMessage("Campo obrigatório");
                localTratamentoOrcamento.setInvalid(true);
            } else {
                localTratamentoOrcamento.setInvalid(false);
            }
        });


        formLayout.add(localTratamentoOrcamento,
                tipoeventofinanceirolayout,
                datainicio_ordemservico,
                diasemanainicio_ordemservico,
                horarioinicio_ordemservico,
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
        SetOrdemServico dto = ordemServico;

        try {
            if (localTratamentoOrcamento.isEmpty()) {
                localTratamentoOrcamento.setRequiredIndicatorVisible(true);
                localTratamentoOrcamento.setErrorMessage("Campo obrigatório");
                localTratamentoOrcamento.setInvalid(true);
                service.notificaErro(ModalMessageConst.FIELD_ERROR);
            } else {
            if (id_orcamento.getValue() != null) {
                dto.setId_orcamento(Integer.valueOf(id_orcamento.getValue()));
            }
            if (id_funcionarioassistente.getValue() != null) {
                dto.setId_funcionarioassistente(id_funcionarioassistente.getValue().getId_funcionario());
            }
            if (id_funcionariotecnico.getValue() != null) {
                dto.setId_funcionariotecnico(id_funcionariotecnico.getValue().getId_funcionario());
            }
            if (id_tipoatendimento.getValue() != null) {
                dto.setId_tipoatendimento(id_tipoatendimento.getValue().getId_tipoatendimento());
            }
            if (localTratamentoOrcamento.getValue() != null) {
                dto.setId_endereco(localTratamentoOrcamento.getValue().getId_endereco());
            }

            dto.setId_cliente(dto.getId_cliente());
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
            dto.setDiasemanainicio_ordemservico(diasemanainicio_ordemservico.getValue());
            dto.setNome_pontofocal(nome_pontofocal.getValue());
            dto.setData_alteracao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            dto.setAtivo("S");
            service = new UtilitySystemConfigService();
            ordemServicoService.update(dto);
            if(listaOrdemServicoFuncionarioAlocadosUpdate.size() > 0){
                listaOrdemServicoFuncionarioAlocadosUpdate.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        funcionarioAlocadoService.update(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoExecucaoServicoUpdate.size() > 0){
                listaOrdemServicoExecucaoServicoUpdate.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    p.setData_inclusao(LocalDateTime.now());
                    p.setAtivo("S");
                    p.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    try {
                        ordemServicoExecucaoServicoService.save(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
            if(listaOrdemServicoMateriaisUpdate.size() > 0){
                listaOrdemServicoMateriaisUpdate.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoMateriaisService.update(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoMisturasUpdate.size() > 0){
                listaOrdemServicoMisturasUpdate.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoMisturaService.update(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if(listaOrdemServicoPragaUpdate.size() > 0){
                listaOrdemServicoPragaUpdate.forEach(p ->{
                    p.setId_cliente(dto.getId_cliente());
                    p.setId_orcamento(dto.getId_orcamento());
                    p.setId_contrato(dto.getId_contrato());
                    p.setId_ordemservico(dto.getId_ordemservico());
                    try {
                        ordemServicoPragaService.update(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            orcamentoDiv.refreshGrid();
            id_orcamento.clear();
            //id_situacaoservico.clear();
            // datainicio_ordemservico.clear();
            diasemanainicio_ordemservico.clear();
            ordemServicoExecucaoGrid.getDataProvider().refreshAll();
            listaOrdemServicoExecucaoServicoUpdate = new ArrayList<>();
            listaOrdemServicoExecucaoServico = new ArrayList<>();
            horarioinicio_ordemservico.clear();
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
            }
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
            System.out.println(e.getMessage().toString());
        }

    }

    public void setOrdemServico(SetOrdemServico item) {
    this.ordemServico = item;
    //this.id_orcamento = item.getId_orcamento();
        open();
        UI.getCurrent().access(() -> {
        List<SetTipoAtendimento> listasituacao = tipoAtendimentoService.listAll();
        List<SetFuncionario> listafuncionario = funcionarioService.listAll();
        List<SetEnderecos> enderecosList = enderecoService.findAllClienteId(item.getId_cliente());
            localTratamentoOrcamento.setItems
                    (enderecoService.findAllClienteId(item.getId_cliente()));
          id_orcamento.setValue(String.valueOf(item.getId_orcamento()));

        id_tipoatendimento.setValue(listasituacao.stream()
                .filter(midia -> midia.getId_tipoatendimento().equals(ordemServico.getId_tipoatendimento()))
                .findFirst().orElse(null));
        id_funcionarioassistente.setValue(listafuncionario.stream()
                .filter(midia -> midia.getId_funcionario().equals(ordemServico.getId_funcionarioassistente()))
                .findFirst().orElse(null));
        id_funcionariotecnico.setValue(listafuncionario.stream()
                .filter(midia -> midia.getId_funcionario().equals(ordemServico.getId_funcionariotecnico()))
                .findFirst().orElse(null));

        localTratamentoOrcamento.setItems(enderecosList);
        localTratamentoOrcamento.setValue(enderecosList.stream()
                .filter(end -> end.getId_endereco().equals(item.getId_endereco()))
                .findFirst().orElse(null));

        confirmado_ordemservico.setItems(List.of("SIM","NÃO"));

        if(ordemServico.getConfirmado_ordemservico().equals("S")){
            confirmado_ordemservico.setValue(("SIM"));
        } else if(ordemServico.getConfirmado_ordemservico().equals("N")) {
            confirmado_ordemservico.setValue(("NÃO"));
        }

        datainicio_ordemservico.setValue(ordemServico.getDatainicio_ordemservico());
        diasemanainicio_ordemservico.setValue(ordemServico.getDiasemanainicio_ordemservico());
        horarioinicio_ordemservico.setValue(ordemServico.getHorarioinicio_ordemservico());
        nome_pontofocal.setValue(ordemServico.getNome_pontofocal());
        ocorrencias_ordemservico.setValue(ordemServico.getOcorrencias_ordemservico());

        List<SetExecucaoServico> servicoList = execucaoServicoService.findAll();
        ordemServicoExecucaoGrid.setItems(new ArrayList<>());
        List<SetOrdemServicoExecucaoServico> ordemServicoExecucaoServicoList = ordemServicoExecucaoServicoService.
                listAllByOrdemServicoId(item.getId_ordemservico());
        if (ordemServicoExecucaoServicoList.size() > 0){
            listaOrdemServicoExecucaoServico = ordemServicoExecucaoServicoList;
            ordemServicoExecucaoGrid.setItems(listaOrdemServicoExecucaoServico);
            valor_ordemservicoexecucaoservico.clear();
            garantia_ordemservicoexecucaoservico.clear();
            descricao_ordemservicoexecucaoservico.clear();
        }
        List<SetOrdemServicoFuncionarioAlocado> funcionarioAlocadoList = funcionarioAlocadoService.
                listAllByOrdemServicoId(item.getId_ordemservico());
        if (funcionarioAlocadoList.size() > 0) {
            listaOrdemServicoFuncionarioAlocados = funcionarioAlocadoList;
            funcionarioAlocadoGrid.setItems(listaOrdemServicoFuncionarioAlocados);
            descricao_ordemservicofuncionarioalocado.clear();
        }

        List<SetOrdemServicoMateriais> ordemServicoMateriaisList = ordemServicoMateriaisService
                .listAllByOrdemServicoId(item.getId_ordemservico());
        if (ordemServicoMateriaisList.size() > 0){
            listaOrdemServicoMateriais = ordemServicoMateriaisList;
            ordemServicoMateriaisGrid.setItems(listaOrdemServicoMateriais);
            numerolote_ordemservicomateriais.clear();
            quantidadeprevista_ordemservicomateriais.clear();
            quantidadeconsumida_ordemservicomateriais.clear();
            descricao_ordemservicomateriais.clear();
        }

        List<SetOrdemServicoMisturas> servicoMisturasList = ordemServicoMisturaService
                .listAllByOrdemServicoId(item.getId_ordemservico());
        if (servicoMisturasList.size() > 0) {
            listaOrdemServicoMisturas = servicoMisturasList;
            ordemServicoMisturasGrid.setItems(listaOrdemServicoMisturas);
            numerolote_ordemservicomisturas.clear();
            quantidadeprevistaproduto_ordemservicomisturas.clear();
            unidademedidaproduto_ordemservicomisturas.clear();
            quantidadeconsumidaproduto_ordemservicomisturas.clear();
            quantidadeprevistasolvente_ordemservicomisturas.clear();
            unidademedidasolvente_ordemservicomisturas.clear();
            quantidadeconsumidasolvente_ordemservicomisturas.clear();
            descricao_ordemservicomisturas.clear();
        }

        List<SetOrdemServicoPraga> ordemServicoPragaList = ordemServicoPragaService.listAllByOrdemServicoId(item.getId_ordemservico());
        if (ordemServicoPragaList.size() > 0){
            listaOrdemServicoPraga = ordemServicoPragaList;
            ordemServicoPragaGrid.setItems(listaOrdemServicoPraga);
            nivelinfestacao_ordemservicopraga.clear();
            descricao_ordemservicopraga.clear();
        }
     });
    }

}