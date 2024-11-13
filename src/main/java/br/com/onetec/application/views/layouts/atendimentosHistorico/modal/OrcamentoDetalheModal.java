package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.arquivoorcamentoservice.ArquivoOrcamentoService;
import br.com.onetec.application.service.comissoesservice.ComissoesService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.faturamentoservice.FaturamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.application.service.orcamentocontatoservice.OrcamentoContatoService;
import br.com.onetec.application.service.orcamentoposvendaservice.OrcamentoPosVendasService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.pagamentoservice.PagamentoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.service.situacaopagamentoservice.SituacaoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.AutoCrudTipoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.SetClienteTransiction;
import br.com.onetec.application.views.layouts.atendimentosHistorico.component.*;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.application.views.main.financeiro.modal.TipoPagamentoCadastroModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.provider.ListDataView;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
public class OrcamentoDetalheModal extends Dialog {

    private SetCliente cliente;

    private Button saveButton;
    private Button cancelButton;
    private Button botaoContrato;
    private Button botaoPagamento;

    // Formulario Orçamento.
    private TextField clienteNomeOrcamento;
    private ComboBox<SetEnderecos> localTratamentoOrcamento;
    private TextArea problemaOrcamento;
    private DatePicker dataOrcamento;
    private ComboBox<SetFuncionario> atendenteOrcamento;
    private ComboBox<SetSituacaoCadastro> situacaoOrcamento;
    private DatePicker dataInspecaoOrcamento;
    private TimePicker horarioOrcamento;
    private ComboBox<SetFuncionario> consultorOrcamento;
    private ComboBox<SetCondicaoPagamento> condicaoOrcamento;
    private TextField garantiaOrcamento;
    private TextField valorOrcamento;
    private final CheckboxGroup<SetServico> servicoOrcamentoChekBox = new CheckboxGroup<>("Serviço");

    //formulario contrato
    private RadioButtonGroup<String> aplicacoes_periodicas;
    private TextField valor_total;
    private TextField valor_nagasaki;
    private DatePicker data_venda;
    private RadioButtonGroup<String> tipo_cobranca;
    private ComboBox<SetCondicaoPagamento> id_condicaopagamento;
    private DatePicker datainicio_execucao;
    private DatePicker datainicio_vencimento;
    private IntegerField meses_garantia;
    private DatePicker datafim_garantia;
    private IntegerField quantidade_aplicacoes;
    private TextArea observacoes_contrato;



    //formulario notafiscal
    private TextField numero_notafiscal;
    private TextField serie_notafiscal;
    private DatePicker dataemissao_notafiscal;
    private TextField natureza_notafiscal;
    private TextField unidade_notafiscal;
    private IntegerField quantidade_notafiscal;
    private TextField valorunitario_notafiscal;
    private TextField valortotal_notafiscal;
    private TextField descricao_notafiscal;
    private List<SetNotaFiscal> listaNotas ;
    List<SetNotaFiscal> listaNotasRemover = new ArrayList<>();
    List<SetNotaFiscal> listaNotasNova = new ArrayList<>();
    private Grid<SetNotaFiscal> gridNotaFiscal;

    //formulario faturamento
    private TextField nome_faturamento;
    private TextField endereco_faturamento;
    private TextField bairro_faturamento;
    private TextField cep_faturamento;
    private TextField cidade_faturamento;
    private TextField estado_faturamento;
    private ComboBox<String> pfpj_faturamento;
    private TextField cpfcnpf_faturamento;
    private TextField incricaoestadual_faturamento;
    private TextArea observacao_faturamento;

    //formulario pagamento
    private IntegerField numeroparcela_pagamento;
    private IntegerField totalparcela_pagamento;
    private DatePicker vencimento_pagamento;
    private TextField valor_pagamento;
    private DatePicker data_pagamento;
    private TextField valorpago_pagamento;
    private TextField numerodocumento_pagamento;
    private ComboBox<SetTipoPagamento> id_tipopagamento;
    private ComboBox<SetSituacaoPagamento> id_situacaopagamento;
    private TextArea descricao_pagamento;
    private Grid<SetPagamento> gridPagamento;
    private List<SetPagamento> listaPagamentos;
    List<SetPagamento> listaPagamentosRemover = new ArrayList<>();
    List<SetPagamento> listaPagamentosNova = new ArrayList<>();
    private IntegerField parcelamentoPagar;

    //formulario comissoes
    private ComboBox<SetFuncionario> id_funcionarioComissao;
    private ComboBox<String> parcelas_comissoes;
    private TextField porcentagem_comissoes;
    private DatePicker data_comissao;
    private TextField valor_comissao;
    private IntegerField parcela_comisao;
    private IntegerField totalparcelas_comissao;
    private DatePicker datapagamento_comissao;
    private TextArea descricao_comissao;
    private Grid<SetComissoes> gridComissoes;
    private List<SetComissoes> listaComissoes;
    List<SetComissoes> listaComissoesRemover = new ArrayList<>();
    List<SetComissoes> listaComissoesNova = new ArrayList<>();

    //cadastro contatos
    private DatePicker data_orcamentocontato;
    private TimePicker horario_orcamentocontato;
    private ComboBox<SetFuncionario> id_funcionarioContato;
    private TextField nome_orcamentocontato;
    private TextField telefone_orcamentocontato;
    private DatePicker dataretorno_orcamentocontato;
    private TextField unidade_orcamentocontato;
    private TextArea descricao_orcamentocontato;
    private Grid<SetOrcamentoContato> gridOrcamentoContato;
    private Button adcionarContato;

    private UtilitySystemConfigService service;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private SituacaoCadastroService situacaoCadastroService;

    @Autowired
    private CondicaoPagamentoService condicaoPagamentoService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private OrcamentoService orcamentoService;

    @Autowired
    private ServicosOrcamentoService setServicosOrcamentoservice;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ComissoesService comissoesService;

    @Autowired
    private FaturamentoService faturamentoService;

    @Autowired
    private NotaFiscalService notaFiscalService;

    @Autowired
    private SituacaoPagamentoService situacaoPagamentoService;

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private TipoPagamentoCadastroModal tipoPagamentoCadastroModal;

    @Autowired
    private ArquivoOrcamentoService arquivoOrcamentoService;

    @Autowired
    private ServicosOrcamentoService servicosOrcamentoService;

    private List<SetOrcamentoContato> listOrcamentoContato = new ArrayList<>();
    List<SetOrcamentoContato> listOrcamentoContatoRemover = new ArrayList<>();
    List<SetOrcamentoContato> listOrcamentoContatoNova = new ArrayList<>();

    @Autowired
    private OrcamentoContatoService orcamentoContatoService;

    @Autowired
    private OrcamentoPosVendasService orcamentoPosVendasService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;


    private Div cadastroOrcamantosDadosFinanceiros;
    private Div cadastroFechamentodeContrado;
    private Div cadastroComissoes;
    private Div cadastroPagamentos;
    private Div cadastroFaturamento;
    private Div cadastroNotaFiscal;
    private Div cadastroArquivosOrcamento;
    private Div cadastroContatoOrcamento;
    private Div cadastroOrcamentoPosVenda;
    private Anchor downloadLink;


    private Tab tab2;
    private Tab tab3;
    private Tab tab4;
    private Tab tab5;
    private Tab tab6;
    private Tab tab7;
    private Tab tab8;
    private Tab tab9;
    private SetOrcamento orcamento;

    private void loadClienteData(SetCliente cliente) {
        // Lógica para carregar os dados do cliente usando o objeto cliente
    }


    public OrcamentoDetalheModal() {
        UI.getCurrent().access(() -> {
            downloadLink = new Anchor();
            botaoContrato = new Button("Gerar Contrato", e -> {

                if (valor_total.isEmpty()) {
                    valor_total.setRequiredIndicatorVisible(true);
                    valor_total.setErrorMessage("Campo obrigatório");
                    valor_total.setInvalid(true);
                }else if (valor_nagasaki.isEmpty()) {
                    valor_nagasaki.setRequiredIndicatorVisible(true);
                    valor_nagasaki.setErrorMessage("Campo obrigatório");
                    valor_nagasaki.setInvalid(true);
                }else if (data_venda.isEmpty()) {
                    data_venda.setRequiredIndicatorVisible(true);
                    data_venda.setErrorMessage("Campo obrigatório");
                    data_venda.setInvalid(true);
                }else if  (id_condicaopagamento.isEmpty()) {
                    id_condicaopagamento.setRequiredIndicatorVisible(true);
                    id_condicaopagamento.setErrorMessage("Campo obrigatório");
                    id_condicaopagamento.setInvalid(true);
                }else if (datainicio_execucao.isEmpty()) {
                    datainicio_execucao.setRequiredIndicatorVisible(true);
                    datainicio_execucao.setErrorMessage("Campo obrigatório");
                    datainicio_execucao.setInvalid(true);
                }else if (datainicio_vencimento.isEmpty()) {
                    datainicio_vencimento.setRequiredIndicatorVisible(true);
                    datainicio_vencimento.setErrorMessage("Campo obrigatório");
                    datainicio_vencimento.setInvalid(true);
                }else if  (meses_garantia.isEmpty()) {
                    meses_garantia.setRequiredIndicatorVisible(true);
                    meses_garantia.setErrorMessage("Campo obrigatório");
                    meses_garantia.setInvalid(true);
                } else if (datafim_garantia.isEmpty()) {
                    datafim_garantia.setRequiredIndicatorVisible(true);
                    datafim_garantia.setErrorMessage("Campo obrigatório");
                    datafim_garantia.setInvalid(true);
                } else {
                    try {
                        String hora = String.valueOf(LocalDateTime.now().getSecond());
                        String idorc = String.valueOf(orcamento.getId_orcamento()).concat(String.valueOf(orcamento.getId_cliente()));
                        String nameClien = cliente.getNome_cliente();
                        String compositeId = hora+idorc+nameClien;
                        // Caminho do arquivo Word de entrada e dos arquivos de saída
                        String wordPath = "C:\\SYSTEM_files_NAGASAKI\\DOC_FILES\\matriz_contrato_sentricon.docx";
                        String updatedWordPath = "C:\\SYSTEM_files_NAGASAKI\\GENERATED_FILES\\matriz_contrato_"+compositeId+"sentriconatualizado.docx";
                        String pdfPath = "C:\\SYSTEM_files_NAGASAKI\\DOC_FILES\\documento_atualizado.pdf";
                        String clientId = orcamento.getId_orcamento().toString(); // Exemplo de ID do cliente a ser substituído

                        // Edita o documento Word
                        SetClienteTransiction.editWordDocument(wordPath, updatedWordPath, "81038", clientId,orcamento,cliente);

                        // Converte o documento editado para PDF
                        SetClienteTransiction.convertDocxToPdf(updatedWordPath, pdfPath);

                        // Baixa o PDF
                        SetClienteTransiction.downloadPdf(pdfPath);
                    } catch (IOException exa) {
                        Notification.show("Erro ao gerar o PDF: " + exa.getMessage());
                        exa.printStackTrace();
                    }
                }
            });

            botaoPagamento = new Button("Gerar Pagamentos", eventbe -> {

                openModalPagamentosParcela();
            });

            botaoPagamento.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SUCCESS);


            botaoContrato.setVisible(false);
            botaoPagamento.setVisible(false);

            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão
            cliente = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");
            setHeaderTitle("Cadastro Orçamento e Dados Financeiros");
            if (cliente != null) {
                loadClienteData(cliente);
            } else {
                // Tratar caso o objeto cliente não esteja presente na sessão
            }

            saveButton = new Button("Atualizar", eventbe -> save());
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            //cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();


            Tabs tabs = new Tabs();
            Tab tab1 = new Tab("Orçamento");
            tab2 = new Tab("Contrato");
            tab3 = new Tab("Comissões");
            tab4 = new Tab("Pagamentos");
            tab5 = new Tab("Faturamento");
            tab6 = new Tab("Nota Fiscal");
            tab7 = new Tab("Arquivos Orçamento");
            tab8 = new Tab("Contatos Realizados");
            tab9 = new Tab("Pós Venda");

            tabs.add(tab1, tab2, tab3, tab4, tab5,tab6,tab7,tab8,tab9);
            tab2.setVisible(false);
            tab3.setVisible(false);
            tab4.setVisible(false);
            tab5.setVisible(false);
            tab6.setVisible(false);

            checkbox = new Checkbox();
            checkbox.setLabel("Incluir contrato ?");
            checkbox.addValueChangeListener(event -> {
                if (event.getValue()) {
                    contratoincluido= true;
                    tab2.setVisible(true);
                    tab3.setVisible(true);
                    tab4.setVisible(true);
                    tab5.setVisible(true);
                    tab6.setVisible(true);
                    tab7.setVisible(true);
                    tab8.setVisible(true);
                    tab9.setVisible(true);
                    System.out.println("Contrato será incluído.");
                } else {
                    contratoincluido = false;
                    tab2.setVisible(false);
                    tab3.setVisible(false);
                    tab4.setVisible(false);
                    tab5.setVisible(false);
                    tab6.setVisible(false);
                    tab7.setVisible(true);
                    tab8.setVisible(true);
                    tab9.setVisible(true);
                    System.out.println("Contrato não será incluído.");
                    tabs.setSelectedTab(tab1);
                }
            });


            cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();
            cadastroFechamentodeContrado = createFormCadastroFechamentodeContrado();
            cadastroComissoes = createFormCadastroComissoes();
            cadastroPagamentos = createFormCadastroPagamentos();
            cadastroFaturamento = createFormCadastroFaturamento();
            cadastroNotaFiscal = createFormcadastroNotaFiscal();
            cadastroArquivosOrcamento = createArquivosOrcamento();
            cadastroContatoOrcamento = createOrcamentoContato();
            cadastroOrcamentoPosVenda = createorcamentoPosVenda();

            Div content = new Div(cadastroOrcamantosDadosFinanceiros,cadastroFechamentodeContrado,cadastroComissoes,
                    cadastroPagamentos,cadastroFaturamento,cadastroNotaFiscal,cadastroArquivosOrcamento,cadastroContatoOrcamento,cadastroOrcamentoPosVenda);
            content.setSizeFull();
            cadastroOrcamantosDadosFinanceiros.setVisible(true);
            cadastroFechamentodeContrado.setVisible(false);
            cadastroComissoes.setVisible(false);
            cadastroPagamentos.setVisible(false);
            cadastroFaturamento.setVisible(false);
            cadastroNotaFiscal.setVisible(false);
            cadastroArquivosOrcamento.setVisible(false);
            cadastroContatoOrcamento.setVisible(false);
            cadastroOrcamentoPosVenda.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroOrcamantosDadosFinanceiros.setVisible(false);
                cadastroFechamentodeContrado.setVisible(false);
                cadastroComissoes.setVisible(false);
                cadastroPagamentos.setVisible(false);
                cadastroFaturamento.setVisible(false);
                cadastroNotaFiscal.setVisible(false);
                cadastroArquivosOrcamento.setVisible(false);
                cadastroContatoOrcamento.setVisible(false);
                cadastroOrcamentoPosVenda.setVisible(false);

                Tab selectedTab = tabs.getSelectedTab();
                if (selectedTab.equals(tab1)) {
                    cadastroOrcamantosDadosFinanceiros.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab2)) {
                    cadastroFechamentodeContrado.setVisible(true);
                    botaoContrato.setVisible(true);
                    botaoPagamento.setVisible(true);
                } else if (selectedTab.equals(tab3)) {
                    cadastroComissoes.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab4)) {
                    cadastroPagamentos.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(true);
                } else if (selectedTab.equals(tab5)) {
                    cadastroFaturamento.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab6)) {
                    cadastroNotaFiscal.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab7)) {
                    cadastroArquivosOrcamento.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab8)) {
                    cadastroContatoOrcamento.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                } else if (selectedTab.equals(tab9)) {
                    cadastroOrcamentoPosVenda.setVisible(true);
                    botaoContrato.setVisible(false);
                    botaoPagamento.setVisible(false);
                }
            });

            Div contentTabs = new Div(cadastroOrcamantosDadosFinanceiros,cadastroFechamentodeContrado,cadastroComissoes,
                    cadastroPagamentos,cadastroFaturamento,cadastroNotaFiscal,cadastroArquivosOrcamento,cadastroContatoOrcamento,cadastroOrcamentoPosVenda);
            contentTabs.setSizeFull();

            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add();

            // Criando o layout do rodapé e ajustando o alinhamento dos botões
            HorizontalLayout footerLayout = new HorizontalLayout();
            footerLayout.setWidthFull();
            footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);// Alinha o conteúdo

            // Adiciona o botão "Excluir" ao lado esquerdo e os outros ao lado direito
            // Alinha à esquerda
            HorizontalLayout rightButtons = new HorizontalLayout(checkbox,saveButton, cancelButton);
            footerLayout.add(rightButtons); // Alinha à direita
            getFooter().add(botaoContrato,botaoPagamento,downloadLink,footerLayout);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    private RadioButtonGroup<String> bomatendimento_orcamentoposvenda;
    private RadioButtonGroup<String> funcionariosuniformizados_orcamentoposvenda;
    private RadioButtonGroup<String> limpeza_orcamentoposvenda;
    private RadioButtonGroup<String> duvidas_orcamentoposvenda;
    private RadioButtonGroup<String> notegeral_orcamentoposvenda;
    private RadioButtonGroup<String> utilizarianovamente_orcamentoposvenda;
    private RadioButtonGroup<String> sugestao_orcamentoposvenda;
    private TextArea descricaosugestao_orcamentoposvenda;
    private DatePicker data_orcamentoposvenda;
    private Grid<SetOrcamentoPosVenda> orcamentoposvendaGrid;
    List<SetOrcamentoPosVenda> listaSetOrcamentoPosVendas = new ArrayList<>();
    List<SetOrcamentoPosVenda>  listaSetOrcamentoPosVendasNova = new ArrayList<>();
    List<SetOrcamentoPosVenda> listaSetOrcamentoPosVendasRemover = new ArrayList<>();


    private Div createorcamentoPosVenda() {
        bomatendimento_orcamentoposvenda = new RadioButtonGroup("FOI BEM ATENDIDO ?");
        bomatendimento_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        funcionariosuniformizados_orcamentoposvenda = new RadioButtonGroup("ESTAVAM UNIFORMIZADOS E USAVAM CRACHÁ ?");
        funcionariosuniformizados_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        limpeza_orcamentoposvenda = new RadioButtonGroup("TRABALHARAM COM LIMPEZA E ORGANIZAÇÃO ?");
        limpeza_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        duvidas_orcamentoposvenda = new RadioButtonGroup("ESCLARECERAM TODAS AS DUVIDAS ?");
        duvidas_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        notegeral_orcamentoposvenda = new RadioButtonGroup("AVALIAÇÃO GERAL NOTA 0 ATÉ 5");
        notegeral_orcamentoposvenda.setItems(List.of("1", "2","3","4","5"));

        utilizarianovamente_orcamentoposvenda = new RadioButtonGroup("UTILIZARIA NOVAMENTE OS SERVIÇOS ?");
        utilizarianovamente_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        sugestao_orcamentoposvenda = new RadioButtonGroup("TEM ALGUMA SUGESTÃO ?");
        sugestao_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        descricaosugestao_orcamentoposvenda = new TextArea("Sugestões/ Observações");

        data_orcamentoposvenda = new DatePicker("Data Ligação");

        // RadioButtonGroup tipo_cobranca
        bomatendimento_orcamentoposvenda.setRequiredIndicatorVisible(true);
        bomatendimento_orcamentoposvenda.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                bomatendimento_orcamentoposvenda.setErrorMessage("Campo obrigatório");
                bomatendimento_orcamentoposvenda.setInvalid(true);
            } else {
                bomatendimento_orcamentoposvenda.setInvalid(false);
            }
        });

        notegeral_orcamentoposvenda.setRequiredIndicatorVisible(true);
        notegeral_orcamentoposvenda.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                notegeral_orcamentoposvenda.setErrorMessage("Campo obrigatório");
                notegeral_orcamentoposvenda.setInvalid(true);
            } else {
                notegeral_orcamentoposvenda.setInvalid(false);
            }
        });

        orcamentoposvendaGrid = new Grid<>(SetOrcamentoPosVenda.class, false);
        orcamentoposvendaGrid.addColumn(new ComponentRenderer<>(orc -> {
            Span span = new Span(orc.getBomatendimento_orcamentoposvenda());
            if ("SIM".equals(orc.getBomatendimento_orcamentoposvenda())) {
                span.getStyle().set("color", "green");
            } else {
                span.getStyle().set("color", "red");
            }
            return span;
        }))
                .setHeader("Bem atendido ?")
                .setSortable(true)
                .setAutoWidth(true);
        orcamentoposvendaGrid.addColumn(new ComponentRenderer<>(orc -> {
            Span span = new Span(orc.getFuncionariosuniformizados_orcamentoposvenda());
            if ("SIM".equals(orc.getFuncionariosuniformizados_orcamentoposvenda())) {
                span.getStyle().set("color", "green");
            } else {
                span.getStyle().set("color", "red");
            }
            return span;
        }))
                .setHeader("Funcionarios Uniformizados ?")
                .setSortable(true)
                .setAutoWidth(true);
        orcamentoposvendaGrid.addColumn(new ComponentRenderer<>(orc -> {
            Span span = new Span(orc.getDuvidas_orcamentoposvenda());
            if ("SIM".equals(orc.getDuvidas_orcamentoposvenda())) {
                span.getStyle().set("color", "green");
            } else {
                span.getStyle().set("color", "red");
            }
            return span;
            }))
                .setHeader("Duvidas ?")
                .setSortable(true)
                .setAutoWidth(true);
        orcamentoposvendaGrid.addColumn(new ComponentRenderer<>(orc -> {
            Span span = new Span(String.valueOf(orc.getNotegeral_orcamentoposvenda()));
            Integer nota = orc.getNotegeral_orcamentoposvenda();
            if (nota >= 4 ) {
                span.getStyle().set("color", "green");
            }if (nota == 3) {
                span.getStyle().set("color", "yellow");
            } else {
                span.getStyle().set("color", "red");
            }
            return span;
        }))
                .setHeader("Nota Geral")
                .setSortable(true)
                .setAutoWidth(true);
        orcamentoposvendaGrid.addColumn(data -> UtilitySystemConfigService.
                getDataFormatada(data.getData_inclusao()))
                .setHeader("Data Inclusão")
                .setSortable(true)
                .setAutoWidth(true);
        orcamentoposvendaGrid.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_orcamentoposvenda())){
                    listaSetOrcamentoPosVendasRemover.add(e);
                } else {
                    listaSetOrcamentoPosVendasNova.add(e);
                }
                listaSetOrcamentoPosVendas.remove(e);
                // Atualiza os itens da grid
                orcamentoposvendaGrid.setItems(listaSetOrcamentoPosVendas);
                // Feedback ao usuário
                Notification.show("Questionário removido: " , 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);
        orcamentoposvendaGrid.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_orcamentoposvenda())) {
                    PosVendaModal.openModalPosVenda(event.getItem(),orcamentoPosVendasService, service,orcamentoposvendaGrid);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar novo Questionário !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });

        Button saveAdicionarButton = new Button("Adicionar", event -> {
            SetOrcamentoPosVenda dto = new SetOrcamentoPosVenda();
            dto.setBomatendimento_orcamentoposvenda(bomatendimento_orcamentoposvenda.getValue());
            dto.setFuncionariosuniformizados_orcamentoposvenda(funcionariosuniformizados_orcamentoposvenda.getValue());
            dto.setLimpeza_orcamentoposvenda(limpeza_orcamentoposvenda.getValue());
            dto.setDuvidas_orcamentoposvenda(duvidas_orcamentoposvenda.getValue());
            dto.setNotegeral_orcamentoposvenda(Integer.valueOf(notegeral_orcamentoposvenda.getValue()));
            dto.setUtilizarianovamente_orcamentoposvenda(utilizarianovamente_orcamentoposvenda.getValue());
            dto.setSugestao_orcamentoposvenda(sugestao_orcamentoposvenda.getValue());
            dto.setDescricaosugestao_orcamentoposvenda(descricaosugestao_orcamentoposvenda.getValue());
            dto.setData_orcamentoposvenda(data_orcamentoposvenda.getValue());
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaSetOrcamentoPosVendas.add(dto);
            listaSetOrcamentoPosVendasNova.add(dto);
            orcamentoposvendaGrid.setItems(listaSetOrcamentoPosVendas);
        });

        orcamentoposvendaGrid.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                listaSetOrcamentoPosVendas.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                orcamentoposvendaGrid.setItems(listaSetOrcamentoPosVendas);
                // Feedback ao usuário
                Notification.show("Removido", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(bomatendimento_orcamentoposvenda,
                funcionariosuniformizados_orcamentoposvenda,
                limpeza_orcamentoposvenda,
                duvidas_orcamentoposvenda,
                notegeral_orcamentoposvenda,
                utilizarianovamente_orcamentoposvenda,
                sugestao_orcamentoposvenda,
                descricaosugestao_orcamentoposvenda,
                data_orcamentoposvenda,saveAdicionarButton);
        Accordion accordion = new Accordion();
        AccordionPanel customDetailsPanel = accordion.add("Questionario",
                formLayout);
        customDetailsPanel.addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                customDetailsPanel.setSummaryText("Questionario");
            }
        });

        //formLayout.set(FlexComponent.Alignment.AUTO);

        VerticalLayout layout = new VerticalLayout(customDetailsPanel, orcamentoposvendaGrid);

        Div div = new Div(layout);
        div.setSizeFull();

        return div;


    }

    private List<SetArquivoOrcamento> listArquivo = new ArrayList<>();

    private List<SetArquivoOrcamento> listArquivoAtualizada = new ArrayList<>();

    private List<SetArquivoOrcamento> listArquivoNova = new ArrayList<>();

    private Grid<SetArquivoOrcamento> gridArquivos = new Grid<>(SetArquivoOrcamento.class, false);

    private Div createArquivosOrcamento() {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        // Define o diretório de salvamento dos arquivos
        String uploadDir = "C:/SYSTEM_files_NAGASAKI/filesidcliente" + cliente.getId_cliente() + "orcamento";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório se não existir
        }
        // Configura a grid de arquivos
        gridArquivos.removeAllColumns(); // Remove as colunas automáticas
        gridArquivos.addColumn(SetArquivoOrcamento::getNome_arquivoorcamento)
                .setHeader("Nome Arquivo")
                .setSortable(true)
                .setAutoWidth(true);
        gridArquivos.addComponentColumn(arquivoOrcamento -> {
            // Cria um link para download associado ao arquivo
            Anchor downloadLink = new Anchor(new StreamResource(arquivoOrcamento.getNome_arquivoorcamento(), () -> {
                try {
                    return new FileInputStream(new File(arquivoOrcamento.getCaminho_arquivoorcamento()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }), "Download");
            downloadLink.getElement().setAttribute("download", true);
            return downloadLink;
        }).setHeader("Download").setSortable(true).setAutoWidth(true);
        gridArquivos.addComponentColumn(arquivoOrcamento -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                arquivoOrcamento.setAtivo("N");
                arquivoOrcamento.setData_exclusao(LocalDateTime.now());
                arquivoOrcamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                listArquivoAtualizada.add(arquivoOrcamento);
                listArquivo.remove(arquivoOrcamento);
                // Atualiza os itens da grid
                gridArquivos.setItems(listArquivo);
                // Feedback ao usuário
                Notification.show("Arquivo removido: " + arquivoOrcamento.getNome_arquivoorcamento(), 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            // Salva o arquivo no diretório especificado
            File targetFile = new File(uploadDir + "/" + fileName);
            if (targetFile.exists()) {
                // Arquivo já existe, pode optar por ignorar, sobrescrever ou renomear
                Notification.show("O arquivo já existe: " + fileName, 3000, Notification.Position.MIDDLE);
                return;
            }
            try (OutputStream outputStream = new FileOutputStream(targetFile)) {
                //listArquivo = arquivoOrcamentoService.findAllByOrcamentoId(orcamento.getId_orcamento());
                inputStream.transferTo(outputStream);
                SetArquivoOrcamento arquivoOrcamento = new SetArquivoOrcamento();
                arquivoOrcamento.setAtivo("S");
                arquivoOrcamento.setCaminho_arquivoorcamento(uploadDir + "/" + fileName);
                arquivoOrcamento.setNome_arquivoorcamento(fileName);
                arquivoOrcamento.setData_inclusao(LocalDateTime.now());
                arquivoOrcamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                arquivoOrcamento.setId_cliente(cliente.getId_cliente());
                listArquivoNova.add(arquivoOrcamento);
                listArquivo.add(arquivoOrcamento);
                // Atualiza os itens da grid
                gridArquivos.setItems(listArquivo);
            } catch (IOException e) {
                e.printStackTrace();
                Notification.show("Erro ao salvar o arquivo: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        // Adiciona os componentes ao layout
        formLayout.add(upload, gridArquivos);
        // Retorna o container principal
        Div div = new Div();
        div.setSizeFull();
        div.add(formLayout);
        return div;
    }

    private List<SetOrcamentoContato> listOrcamentoContatoAtualizatos;

    private Div createOrcamentoContato() {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        data_orcamentocontato = new DatePicker("Data Contato");
        horario_orcamentocontato = new TimePicker("Hora");
        nome_orcamentocontato = new TextField("Nome Contato");
        telefone_orcamentocontato = new TextField("Telefone");
        id_funcionarioContato = new ComboBox<>("Funcionario");
        dataretorno_orcamentocontato = new DatePicker("Data Retorno");
        unidade_orcamentocontato = new TextField("Unidade");
        descricao_orcamentocontato = new TextArea("O que foi contatado ?");

        id_funcionarioContato.setItems(funcionarioService.listAll());
        id_funcionarioContato.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        listOrcamentoContato = new ArrayList<>();
        // Configura a grid de SetOrcamentoContato
        gridOrcamentoContato = new Grid<>(SetOrcamentoContato.class, false);
        gridOrcamentoContato.setItems(listOrcamentoContato);
        gridOrcamentoContato.addColumn(SetOrcamentoContato::getNome_orcamentocontato)
                .setHeader("Nome Contato")
                .setSortable(true)
                .setAutoWidth(true);
        gridOrcamentoContato.addColumn(SetOrcamentoContato::getData_orcamentocontato)
                .setHeader("Data")
                .setSortable(true)
                .setAutoWidth(true);
        gridOrcamentoContato.addColumn(SetOrcamentoContato::getDescricao_orcamentocontato)
                .setHeader("O que foi contatado ?")
                .setSortable(true)
                .setAutoWidth(true);
        gridOrcamentoContato.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_orcamentocontato())){
                    listOrcamentoContatoRemover.add(e);
                } else {
                    listOrcamentoContatoNova.add(e);
                }
                listOrcamentoContato.remove(e);
                // Atualiza os itens da grid
                gridOrcamentoContato.setItems(listOrcamentoContato);
                // Feedback ao usuário
                Notification.show("Contato removido: " + e.getNome_orcamentocontato(), 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);
        gridOrcamentoContato.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_orcamentocontato())) {
                    ContatoModal.openModalContato(event.getItem(),orcamentoContatoService, funcionarioService,service);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar novo Contato !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
            });

        formLayout.setWidthFull();
        Accordion accordion = new Accordion();
        AccordionPanel customDetailsPanel = accordion.add("Cadastrar Contato",
                formLayout);
        customDetailsPanel.addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                customDetailsPanel.setSummaryText("Cadastrar Contato");
            }
        });

        VerticalLayout layout = new VerticalLayout(customDetailsPanel, gridOrcamentoContato);

        Button saveButton = new Button("Adicionar Contato", event -> {
            SetOrcamentoContato obj = new SetOrcamentoContato();


            obj.setData_orcamentocontato(data_orcamentocontato.getValue());
            obj.setHorario_orcamentocontato(horario_orcamentocontato.getValue());
            obj.setNome_orcamentocontato(nome_orcamentocontato.getValue());
            obj.setTelefone_orcamentocontato(telefone_orcamentocontato.getValue());
            if(Objects.nonNull(id_funcionarioContato.getValue())) {
                obj.setId_funcionario(id_funcionarioContato.getValue().getId_funcionario());
            }
            obj.setDataretorno_orcamentocontato(dataretorno_orcamentocontato.getValue());
            obj.setUnidade_orcamentocontato(unidade_orcamentocontato.getValue());
            obj.setDescricao_orcamentocontato(descricao_orcamentocontato.getValue());
            listOrcamentoContato.add(obj);
            listOrcamentoContatoNova.add(obj);
            service.notificaSucesso("Contato Adcionado");
            gridOrcamentoContato.setItems(listOrcamentoContato);
        });

        // Adiciona os componentes ao layout
        formLayout.add( data_orcamentocontato,
                horario_orcamentocontato,
                nome_orcamentocontato,
                telefone_orcamentocontato,
                id_funcionarioContato,
                dataretorno_orcamentocontato,
                unidade_orcamentocontato,
                descricao_orcamentocontato,saveButton);
        // Retorna o container principal
        Div div = new Div();
        div.setSizeFull();
        //layout.add(formLayout, gridOrcamentoContato);
        div.add(layout);
        return div;
    }


    private void openModalPagamentosParcela() {
        Dialog modalPagamento = new Dialog();
        if (datainicio_vencimento.isEmpty()) {
            datainicio_vencimento.setRequiredIndicatorVisible(true);
            datainicio_vencimento.setErrorMessage("Campo obrigatório");
            datainicio_vencimento.setInvalid(true);
            service.notificaErro("Campos obrigatórios devem estar preenchidos");
        } else if (id_condicaopagamento.isEmpty()) {
            id_condicaopagamento.setRequiredIndicatorVisible(true);
            id_condicaopagamento.setErrorMessage("Campo obrigatório");
            id_condicaopagamento.setInvalid(true);
            service.notificaErro("Campos obrigatórios devem estar preenchidos");
        } else if (valor_total.isEmpty()) {
            valor_total.setRequiredIndicatorVisible(true);
            valor_total.setErrorMessage("Campo obrigatório");
            valor_total.setInvalid(true);
            service.notificaErro("Campos obrigatórios devem estar preenchidos");
        } else {
            // Criação dos campos
            TextField valorTotalAPagar = new TextField("Valor Total do Pagamento");
            parcelamentoPagar = new IntegerField("Parcelar em quantas vezes?");
            DatePicker dataVencimentoParcela = new DatePicker("1° Vencimento Parcela");
            TextField valorFinalParcela = new TextField("Valor de Cada Parcela");

            // Configurando os valores iniciais
            parcelamentoPagar.setValue(Integer.valueOf(id_condicaopagamento.getValue().getQuantidade_parcelas()));
            valorTotalAPagar.setValue((valor_total.getValue()));  // Valor inicial sem máscara

            service.formataMoedaBrasileira(valorTotalAPagar);  // Formatar como moeda brasileira
            BigDecimal valorTots = service.getValorBigDecimal(valorTotalAPagar.getValue());  // Converter o valor total para BigDecimal
            int divisors = parcelamentoPagar.getValue();

            if (divisors > 0) {
                BigDecimal valorParcela = valorTots.divide(BigDecimal.valueOf(divisors), 2, RoundingMode.HALF_UP);
                valorFinalParcela.setValue(valorParcela.toString());
                service.formataMoedaBrasileira(valorFinalParcela);
            }

            parcelamentoPagar.setStepButtonsVisible(true);
            parcelamentoPagar.setValueChangeMode(ValueChangeMode.EAGER);
            parcelamentoPagar.addValueChangeListener(event -> {
                service.formataMoedaBrasileira(valorTotalAPagar);  // Formatar como moeda brasileira
                BigDecimal valorTot = service.getValorBigDecimal(valorTotalAPagar.getValue());  // Converter o valor total para BigDecimal
                int divisor = parcelamentoPagar.getValue();

                if (divisor > 0) {
                    BigDecimal valorParcela = valorTot.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
                    valorFinalParcela.setValue(valorParcela.toString());
                    service.formataMoedaBrasileira(valorFinalParcela);
                } else {
                    valorFinalParcela.setValue("R$ 0,00");  // Caso divisor seja inválido
                }
            });


            dataVencimentoParcela.setValue(datainicio_vencimento.getValue());
            // Modo de atualização imediata ao alterar o campo
            valorTotalAPagar.setValueChangeMode(ValueChangeMode.EAGER);

            // Listener para alterar o valor de cada parcela ao alterar o valor total ou o número de parcelas
            valorTotalAPagar.addValueChangeListener(event -> {
                service.formataMoedaBrasileira(valorTotalAPagar);  // Formatar como moeda brasileira
                BigDecimal valorTot = service.getValorBigDecimal(valorTotalAPagar.getValue());  // Converter o valor total para BigDecimal
                int divisor = parcelamentoPagar.getValue();

                if (divisor > 0) {
                    BigDecimal valorParcela = valorTot.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
                    valorFinalParcela.setValue(valorParcela.toString());
                    service.formataMoedaBrasileira(valorFinalParcela);
                } else {
                    valorFinalParcela.setValue("R$ 0,00");  // Caso divisor seja inválido
                }
            });

            parcelamentoPagar.addValueChangeListener(event -> {
                BigDecimal valorTot = service.getValorBigDecimal(valorTotalAPagar.getValue());
                int divisor = parcelamentoPagar.getValue();

                if (divisor > 0) {
                    BigDecimal valorParcela = valorTot.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
                    valorFinalParcela.setValue(valorParcela.toString());
                    service.formataMoedaBrasileira(valorFinalParcela);
                } else {
                    valorFinalParcela.setValue("R$ 0,00");
                }
            });

            valorTotalAPagar.setPlaceholder("R$ 0,00");
            valorFinalParcela.setValueChangeMode(ValueChangeMode.EAGER);
            valorFinalParcela.setPlaceholder("R$ 0,00");

            // Botões de ação
            Button pagamentoButon = new Button("Gerar Pagamentos", event -> {
                for (int i = 0; i < parcelamentoPagar.getValue(); i++) {
                    SetPagamento pay = new SetPagamento();
                    pay.setNumeroparcela_pagamento(i + 1);
                    pay.setTotalparcela_pagamento(parcelamentoPagar.getValue());
                    pay.setVencimento_pagamento(dataVencimentoParcela.getValue().plusMonths(i + 1));
                    pay.setValor_pagamento(service.getValorBigDecimal(valorFinalParcela.getValue()));
                    pay.setBaixado("N");
                    pay.setData_inclusao(LocalDateTime.now());
                    pay.setAtivo("S");
                    pay.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    listaPagamentos.add(pay);
                }
                gridPagamento.setItems(listaPagamentos);
                service.notificaSucesso("Pagamentos Gerados com sucesso");
                modalPagamento.close();

            });
            pagamentoButon.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Button cancelar = new Button("Cancelar", event -> service.askForConfirmation(modalPagamento));
            cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);


            // Layout
            VerticalLayout layout = new VerticalLayout(valorTotalAPagar, dataVencimentoParcela, parcelamentoPagar, valorFinalParcela);

            // Modal para gerar os pagamentos

            modalPagamento.setHeaderTitle("Gerar Parcelas de Pagamento do Contrato?");
            modalPagamento.add(layout);
            modalPagamento.getFooter().add(pagamentoButon, cancelar);
            modalPagamento.open();
        }
    }




    private Div createFormcadastroNotaFiscal() {
        numero_notafiscal = new TextField("Numero");
        serie_notafiscal = new TextField("Série");
        dataemissao_notafiscal = new DatePicker("Data Emissão");
        natureza_notafiscal = new TextField("Natureza");
        unidade_notafiscal = new TextField("Unidade");
        quantidade_notafiscal = new IntegerField("Quantidade");
        valorunitario_notafiscal = new TextField("Valor Unitario");
        valortotal_notafiscal = new TextField("Valor Total");
        descricao_notafiscal = new TextField("Descrição");


        valorunitario_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valorunitario_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valorunitario_notafiscal));
        valorunitario_notafiscal.setPlaceholder("R$ 0,00");

        valortotal_notafiscal.setValueChangeMode(ValueChangeMode.EAGER);
        valortotal_notafiscal.addValueChangeListener(event -> service.formataMoedaBrasileira(valortotal_notafiscal));
        valortotal_notafiscal.setPlaceholder("R$ 0,00");

        quantidade_notafiscal.setStepButtonsVisible(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        listaNotas = new ArrayList<>();

        // Configurar o Grid
        gridNotaFiscal = new Grid<>(SetNotaFiscal.class, false);
        gridNotaFiscal.setItems(listaNotas);
        gridNotaFiscal.addColumn(SetNotaFiscal::getNumero_notafiscal)
                .setHeader("N° Nota")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addColumn(SetNotaFiscal::getSerie_notafiscal)
                .setHeader("Série")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addColumn(SetNotaFiscal::getDataemissao_notafiscal)
                .setHeader("Emissão")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addColumn(SetNotaFiscal::getNatureza_notafiscal)
                .setHeader("Natureza")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addColumn(SetNotaFiscal::getUnidade_notafiscal)
                .setHeader("Unidade")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addColumn(SetNotaFiscal::getValortotal_notafiscal)
                .setHeader("Valor Total")
                .setSortable(true)
                .setAutoWidth(true);
        gridNotaFiscal.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_notafiscal())){
                    listaNotasRemover.add(e);
                } else {
                    listaNotasNova.add(e);
                }
                listaNotas.remove(e);
                // Atualiza os itens da grid
                gridNotaFiscal.setItems(listaNotas);
                // Feedback ao usuário
                Notification.show("Comissao removida: ", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);
        gridNotaFiscal.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_notafiscal())) {
                    NotaFiscalModal.openModalNota(event.getItem(),notaFiscalService, service);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar nova Nota Fiscal !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });
        Button saveAdicionarButton = new Button("Adicionar Nota Fiscal", event -> {
            SetNotaFiscal nota = new SetNotaFiscal();
            nota.setNumero_notafiscal(numero_notafiscal.getValue());
            nota.setSerie_notafiscal(serie_notafiscal.getValue());
            nota.setDataemissao_notafiscal(dataemissao_notafiscal.getValue());
            nota.setNatureza_notafiscal(natureza_notafiscal.getValue());
            nota.setUnidade_notafiscal(unidade_notafiscal.getValue());
            nota.setQuantidade_notafiscal(quantidade_notafiscal.getValue().toString());
            nota.setValorunitario_notafiscal(service.getValorBigDecimal(valorunitario_notafiscal.getValue()));
            nota.setValortotal_notafiscal(service.getValorBigDecimal(valortotal_notafiscal.getValue()));
            nota.setDescricao_notafiscal(descricao_notafiscal.getValue());
            listaNotasNova.add(nota);
            listaNotas.add(nota);
            numero_notafiscal.clear();
            serie_notafiscal.clear();
            dataemissao_notafiscal.clear();
            natureza_notafiscal.clear();
            unidade_notafiscal.clear();
            quantidade_notafiscal.clear();
            valorunitario_notafiscal.clear();
            valortotal_notafiscal.clear();
            descricao_notafiscal.clear();
            gridNotaFiscal.setItems(listaNotas);
        });

        formLayout.add(numero_notafiscal,
                serie_notafiscal,
                dataemissao_notafiscal,
                natureza_notafiscal,
                unidade_notafiscal,
                quantidade_notafiscal,
                valorunitario_notafiscal,
                valortotal_notafiscal,
                descricao_notafiscal,
                saveAdicionarButton);

        Div div = new Div();
        div.setSizeFull();
        VerticalLayout layout = new VerticalLayout(formLayout, gridNotaFiscal);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        div.add(layout);

        return div;
    }



    private Div createFormCadastroFaturamento() {
        service = new UtilitySystemConfigService();
        nome_faturamento = new TextField("Nome");
        endereco_faturamento = new TextField("Endereço");
        bairro_faturamento = new TextField("Bairro");
        cep_faturamento = new TextField("CEP");
        cidade_faturamento = new TextField("Cidade");
        estado_faturamento = new TextField("Estado");
        pfpj_faturamento = new ComboBox<>("Natureza Juridica");
        cpfcnpf_faturamento = new TextField("CPF");
        incricaoestadual_faturamento = new TextField("Incrição Estadual");
        observacao_faturamento = new TextArea("Observações");

        pfpj_faturamento = new ComboBox<>("Natureza Juridica");
        pfpj_faturamento.setItems(List.of("Pessoa Fisica","Pessoa Juridica"));
        pfpj_faturamento.addValueChangeListener(event -> {
            if ("Pessoa Fisica".equals(event.getValue())) {
                cpfcnpf_faturamento.clear();
                cpfcnpf_faturamento.setLabel("Número CPF");
                service.configureCPFField(cpfcnpf_faturamento);

            } else if ("Pessoa Juridica".equals(event.getValue())) {
                cpfcnpf_faturamento.clear();
                cpfcnpf_faturamento.setLabel("Número CNPJ");
                service.configureCNPJTextField(cpfcnpf_faturamento);
            }
        });


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_faturamento,
                endereco_faturamento,
                bairro_faturamento,
                cep_faturamento,
                cidade_faturamento,
                estado_faturamento,
                pfpj_faturamento,
                cpfcnpf_faturamento,
                incricaoestadual_faturamento,
                observacao_faturamento);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }


    private Div createFormCadastroPagamentos() {

        numeroparcela_pagamento = new IntegerField("Parcela Pagamento");
        totalparcela_pagamento = new IntegerField("Total Parcelas");
        vencimento_pagamento = new DatePicker("Vencimento");
        valor_pagamento = new TextField("Valor Pagamento");
        data_pagamento = new DatePicker("Data Pagamento");
        valorpago_pagamento = new TextField("Valor Pago");
        numerodocumento_pagamento = new TextField("N° Doc/Comprovante");
        id_tipopagamento = new ComboBox<>("Tipo de Pagamento");
        id_situacaopagamento = new ComboBox<>("Status Pagamento");
        descricao_pagamento = new TextArea("Observações");

        id_situacaopagamento.setItems(situacaoPagamentoService.listAll());
        id_situacaopagamento.setItemLabelGenerator(SetSituacaoPagamento::getNome_situacaopagamento);

        HorizontalLayout id_situacaopagamentoLayout =
                new CustomizedComboBox()
                        .customizeSituacaoPagamento(id_situacaopagamento,situacaoPagamentoService);

        id_tipopagamento.setItems(tipoPagamentoService.listAll());
        id_tipopagamento.setItemLabelGenerator(SetTipoPagamento::getNome_tipopagamento);

        HorizontalLayout id_tipopagamentolayout =
                new CustomizedComboBox().customizeTipoPagamento(id_tipopagamento,tipoPagamentoService);

        valor_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valor_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_pagamento));
        valor_pagamento.setPlaceholder("R$ 0,00");

        valorpago_pagamento.setValueChangeMode(ValueChangeMode.EAGER);
        valorpago_pagamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valorpago_pagamento));
        valorpago_pagamento.setPlaceholder("R$ 0,00");


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        listaPagamentos = new ArrayList<>();

        // Configurar o Grid
        gridPagamento = new Grid<>(SetPagamento.class, false);
        gridPagamento.setItems(listaPagamentos);
        gridPagamento.addColumn(SetPagamento::getNumeroparcela_pagamento)
                .setHeader("N° Parcela")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(SetPagamento::getTotalparcela_pagamento)
                .setHeader("Total de Parcelas")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(SetPagamento::getVencimento_pagamento)
                .setHeader("Vencimento")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(SetPagamento::getValor_pagamento)
                .setHeader("Valor Total")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(SetPagamento::getValorpago_pagamento)
                .setHeader("Valor Pago")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(SetPagamento::getData_pagamento)
                .setHeader("Data Pagamento")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addColumn(pagamento -> {
            return pagamento.getBaixado().equals("S") ? "SIM" : "NÃO"; })
                .setHeader("Baixado")
                .setSortable(true)
                .setAutoWidth(true);
        gridPagamento.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_pagamento())){
                    listaPagamentosRemover.add(e);
                } else {
                    listaPagamentosNova.add(e);
                }
                listaPagamentos.remove(e);
                // Atualiza os itens da grid
                gridPagamento.setItems(listaPagamentos);
                // Feedback ao usuário
                Notification.show("Comissao removida: ", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);
        gridPagamento.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_pagamento())) {
                    PagamentoModal.openModalPagamento(event.getItem(),pagamentoService, tipoPagamentoService
                            ,situacaoPagamentoService);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar nova Comissão !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });
        Button saveAdicionarButton = new Button("Adicionar Pagamento", event -> {
            SetPagamento pay = new SetPagamento();
            pay.setNumeroparcela_pagamento(numeroparcela_pagamento.getValue());
            pay.setTotalparcela_pagamento(totalparcela_pagamento.getValue());
            pay.setVencimento_pagamento(vencimento_pagamento.getValue());
            pay.setValor_pagamento(service.getValorBigDecimal(valor_pagamento.getValue()));
            pay.setData_pagamento(data_pagamento.getValue());
            pay.setValorpago_pagamento(service.getValorBigDecimal(valorpago_pagamento.getValue()));
            pay.setNumerodocumento_pagamento(numerodocumento_pagamento.getValue());
            pay.setId_tipopagamento(id_tipopagamento.getValue().getId_tipopagamento());
            pay.setId_situacaopagamento(id_situacaopagamento.getValue().getId_situacaopagamento());
            pay.setBaixado("N");
            pay.setDescricao_pagamento(descricao_pagamento.getValue());
            pay.setData_inclusao(LocalDateTime.now());
            pay.setAtivo("S");
            pay.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaPagamentos.add(pay);
            listaPagamentosNova.add(pay);
            gridPagamento.setItems(listaPagamentos);
        });

        formLayout.add(numeroparcela_pagamento,
                totalparcela_pagamento,
                vencimento_pagamento,
                valor_pagamento,
                data_pagamento,
                valorpago_pagamento,
                numerodocumento_pagamento,
                id_tipopagamentolayout,
                id_situacaopagamentoLayout,
                descricao_pagamento,
                saveAdicionarButton,
                gridPagamento);

        Div div = new Div();
        div.setSizeFull();
        VerticalLayout layout = new VerticalLayout(formLayout, gridPagamento);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        div.add(layout);

        return div;
    }




    private Div createFormCadastroComissoes() {

        id_funcionarioComissao = new ComboBox<>("Funcionário");
        parcelas_comissoes = new ComboBox<>("Pagamento Comissão ?");
        porcentagem_comissoes = new TextField("Porcentagem");
        data_comissao = new DatePicker("Data");
        valor_comissao = new TextField("Valor Comissão");
        parcela_comisao = new IntegerField("Parcela Vigente");
        totalparcelas_comissao = new IntegerField("Total de Parcelas");
        datapagamento_comissao = new DatePicker("Data de Pagamento");
        descricao_comissao = new TextArea("Observações");

        parcelas_comissoes.setItems
                (List.of("A Vista", "Parcelado"));
        id_funcionarioComissao.setItems
                (funcionarioService.listAll());
        id_funcionarioComissao.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        service.configuraCalendario(data_comissao);
        service.configuraCalendario(datapagamento_comissao);

        valor_comissao.setValueChangeMode(ValueChangeMode.EAGER);
        valor_comissao.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_comissao));
        valor_comissao.setPlaceholder("R$ 0,00");

        porcentagem_comissoes.setValueChangeMode(ValueChangeMode.EAGER);
        porcentagem_comissoes.addValueChangeListener(event -> service.formataPorcentagem(porcentagem_comissoes));
        porcentagem_comissoes.setPlaceholder("% 0,00");

        totalparcelas_comissao.setValue(1);
        totalparcelas_comissao.setStepButtonsVisible(true);
        totalparcelas_comissao.setMin(1);
        totalparcelas_comissao.addValueChangeListener(event -> {
            parcela_comisao.setMax(totalparcelas_comissao.getValue());
        });

        parcela_comisao.setValue(1);
        parcela_comisao.setStepButtonsVisible(true);
        parcela_comisao.setMin(1);
        parcela_comisao.setMax(totalparcelas_comissao.getValue());

        porcentagem_comissoes.addValueChangeListener(event -> {
            BigDecimal valorsomado = service.getValorBigDecimal(valor_nagasaki.getValue());
            BigDecimal valorporcentagem = service.extrairPorcentagem(valorsomado,
                    service.getValorBigDecimal(porcentagem_comissoes.getValue()));
            valor_comissao.setValue(valorporcentagem.toString());
        });

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        listaComissoes = new ArrayList<>();

        // Configurar o Grid
        gridComissoes = new Grid<>(SetComissoes.class, false);
        gridComissoes.setItems(listaComissoes);
        gridComissoes.addColumn(comissoes -> {
            SetFuncionario funcionario = funcionarioService.findById(comissoes.getId_funcionario());
            return funcionario != null ? funcionario.getNome_funcionario() : "N/A"; })
                .setHeader("Funcionário")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addColumn(SetComissoes::getPorcentagem_comissoes)
                .setHeader("Porcentagem")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addColumn(SetComissoes::getData_comissao)
                .setHeader("Data")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addColumn(SetComissoes::getValor_comissao)
                .setHeader("Valor")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addColumn(SetComissoes::getParcela_comisao)
                .setHeader("Numero Parcela")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addColumn(SetComissoes::getTotalparcelas_comissao)
                .setHeader("Total Parcelas")
                .setSortable(true)
                .setAutoWidth(true);
        gridComissoes.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_comissoes())){
                    listaComissoesRemover.add(e);
                } else {
                    listaComissoesNova.add(e);
                }
                listaComissoes.remove(e);
                // Atualiza os itens da grid
                gridComissoes.setItems(listaComissoes);
                // Feedback ao usuário
                Notification.show("Comissao removida: ", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);
        gridComissoes.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_comissoes())) {
                    ComissoesModal.openModalComissoes(event.getItem(),comissoesService, funcionarioService,
                            valor_nagasaki);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar nova Comissão !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });

        Button saveAdicionarButton = new Button("Adicionar Endereço", event -> {
            SetComissoes comissoesNovo = new SetComissoes();
            SetFuncionario fu = id_funcionarioComissao.getValue();
            comissoesNovo.setId_funcionario(fu.getId_funcionario());
            comissoesNovo.setParcelas_comissoes(parcelas_comissoes.getValue());
            comissoesNovo.setPorcentagem_comissoes(service.getValorBigDecimal(porcentagem_comissoes.getValue()));
            comissoesNovo.setData_comissao(data_comissao.getValue());
            comissoesNovo.setValor_comissao(service.getValorBigDecimal(valor_comissao.getValue()));
            comissoesNovo.setParcela_comisao(parcela_comisao.getValue());
            comissoesNovo.setTotalparcelas_comissao(totalparcelas_comissao.getValue());
            comissoesNovo.setDatapagamento_comissao(datapagamento_comissao.getValue());
            comissoesNovo.setDescricao_comissao(descricao_comissao.getValue());
            comissoesNovo.setData_inclusao(LocalDateTime.now());
            comissoesNovo.setAtivo("S");
            comissoesNovo.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            listaComissoes.add(comissoesNovo);
            listaComissoesNova.add(comissoesNovo);
            gridComissoes.setItems(listaComissoes);
        });

        formLayout.add(id_funcionarioComissao,
                parcelas_comissoes,
                porcentagem_comissoes,
                data_comissao,
                valor_comissao,
                parcela_comisao,
                totalparcelas_comissao,
                datapagamento_comissao,
                descricao_comissao,
                saveAdicionarButton
        );

        Div div = new Div();
        div.setSizeFull();
        VerticalLayout layout = new VerticalLayout(formLayout, gridComissoes);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        div.add(layout);

        return div;
    }

    private Div createFormCadastroFechamentodeContrado() {
        service = new UtilitySystemConfigService();
        aplicacoes_periodicas = new RadioButtonGroup<>("Aplicações Periódicas ?");
        valor_total = new TextField("Valor Total");
        valor_nagasaki = new TextField("Valor Nagasaki");
        data_venda = new DatePicker("Data da Venda");
        tipo_cobranca = new RadioButtonGroup<>("Tipo de Cobrança");
        id_condicaopagamento = new ComboBox<>("Condição de Pagamento");
        datainicio_execucao = new DatePicker("Data Inicio Execução");
        datainicio_vencimento = new DatePicker("Data Inicio Vencimento");
        meses_garantia = new IntegerField("Meses de Garantia");
        datafim_garantia = new DatePicker("Data fim garantia");
        quantidade_aplicacoes = new IntegerField("Quantidade de Aplicações");
        observacoes_contrato = new TextArea("Observações");

        // RadioButtonGroup aplicacoes_periodicas
        aplicacoes_periodicas.setRequiredIndicatorVisible(true);
        aplicacoes_periodicas.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                aplicacoes_periodicas.setErrorMessage("Campo obrigatório");
                aplicacoes_periodicas.setInvalid(true);
            } else {
                aplicacoes_periodicas.setInvalid(false);
            }
        });

// TextField valor_total
        valor_total.setRequiredIndicatorVisible(true);
        valor_total.addValueChangeListener(event -> {
            if (valor_total.isEmpty()) {
                valor_total.setErrorMessage("Campo obrigatório");
                valor_total.setInvalid(true);
            } else {
                valor_total.setInvalid(false);
            }
        });

// TextField valor_nagasaki
        valor_nagasaki.setRequiredIndicatorVisible(true);
        valor_nagasaki.addValueChangeListener(event -> {
            if (valor_nagasaki.isEmpty()) {
                valor_nagasaki.setErrorMessage("Campo obrigatório");
                valor_nagasaki.setInvalid(true);
            } else {
                valor_nagasaki.setInvalid(false);
            }
        });

// DatePicker data_venda
        data_venda.setRequiredIndicatorVisible(true);
        data_venda.addValueChangeListener(event -> {
            if (data_venda.isEmpty()) {
                data_venda.setErrorMessage("Campo obrigatório");
                data_venda.setInvalid(true);
            } else {
                data_venda.setInvalid(false);
            }
        });

// RadioButtonGroup tipo_cobranca
        tipo_cobranca.setRequiredIndicatorVisible(true);
        tipo_cobranca.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                tipo_cobranca.setErrorMessage("Campo obrigatório");
                tipo_cobranca.setInvalid(true);
            } else {
                tipo_cobranca.setInvalid(false);
            }
        });

// ComboBox id_condicaopagamento
        id_condicaopagamento.setRequiredIndicatorVisible(true);
        id_condicaopagamento.addValueChangeListener(event -> {
            if (id_condicaopagamento.isEmpty()) {
                id_condicaopagamento.setErrorMessage("Campo obrigatório");
                id_condicaopagamento.setInvalid(true);
            } else {
                id_condicaopagamento.setInvalid(false);
            }
        });

// DatePicker datainicio_execucao
        datainicio_execucao.setRequiredIndicatorVisible(true);
        datainicio_execucao.addValueChangeListener(event -> {
            if (datainicio_execucao.isEmpty()) {
                datainicio_execucao.setErrorMessage("Campo obrigatório");
                datainicio_execucao.setInvalid(true);
            } else {
                datainicio_execucao.setInvalid(false);
            }
        });

// DatePicker datainicio_vencimento
        datainicio_vencimento.setRequiredIndicatorVisible(true);
        datainicio_vencimento.addValueChangeListener(event -> {
            if (datainicio_vencimento.isEmpty()) {
                datainicio_vencimento.setErrorMessage("Campo obrigatório");
                datainicio_vencimento.setInvalid(true);
            } else {
                datainicio_vencimento.setInvalid(false);
            }
        });

// IntegerField meses_garantia
        meses_garantia.setRequiredIndicatorVisible(true);
        meses_garantia.addValueChangeListener(event -> {
            if (meses_garantia.isEmpty()) {
                meses_garantia.setErrorMessage("Campo obrigatório");
                meses_garantia.setInvalid(true);
            } else {
                meses_garantia.setInvalid(false);
            }
        });

        // DatePicker datafim_garantia
        datafim_garantia.setRequiredIndicatorVisible(true);
        datafim_garantia.addValueChangeListener(event -> {
            if (datafim_garantia.isEmpty()) {
                datafim_garantia.setErrorMessage("Campo obrigatório");
                datafim_garantia.setInvalid(true);
            } else {
                datafim_garantia.setInvalid(false);
            }
        });

        service.configuraCalendario(data_venda);
        service.configuraCalendario(datainicio_execucao);
        service.configuraCalendario(datainicio_vencimento);
        service.configuraCalendario(datafim_garantia);

        valor_nagasaki.setValueChangeMode(ValueChangeMode.EAGER);
        valor_nagasaki.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_nagasaki));
        valor_nagasaki.setPlaceholder("R$ 0,00");

        valor_total.setValueChangeMode(ValueChangeMode.EAGER);
        valor_total.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_total));
        valor_total.setPlaceholder("R$ 0,00");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        meses_garantia.setValue(0);
        meses_garantia.setStepButtonsVisible(true);
        meses_garantia.setMin(0);

        quantidade_aplicacoes.setValue(1);
        quantidade_aplicacoes.setStepButtonsVisible(true);
        quantidade_aplicacoes.setMin(1);
        aplicacoes_periodicas.setItems(List.of("SIM","NÃO"));
        tipo_cobranca.setItems(List.of("Por Serviço","Mensal"));

        id_condicaopagamento.setItems(condicaoPagamentoService.listAll());
        id_condicaopagamento.setItemLabelGenerator(SetCondicaoPagamento::getDescricao_condicaopagamento);

        HorizontalLayout id_condicaopagamentolayout =
                new CustomizedComboBox().customizeCondicaoPagamento(id_condicaopagamento,condicaoPagamentoService);


        datainicio_vencimento.setValue(LocalDate.now());

        meses_garantia.addValueChangeListener(event -> {
            datafim_garantia.setValue(datainicio_execucao.getValue().plusMonths(event.getValue()));
        });


        datafim_garantia.isReadOnly();
        datafim_garantia.setReadOnly(true);

        formLayout.add(aplicacoes_periodicas,tipo_cobranca,valor_total,valor_nagasaki,data_venda,
                id_condicaopagamentolayout,datainicio_execucao,datainicio_vencimento,meses_garantia,
                datafim_garantia,quantidade_aplicacoes,observacoes_contrato);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    private Checkbox checkbox;
    private boolean contratoincluido = false;

    public InputStream createPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Criar PDF Writer
            return new SetClienteTransiction().EditDocAndGeneratePdf();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Div createFormCadastroOrcamantosDadosFinanceiros() {


        service = new UtilitySystemConfigService();

        clienteNomeOrcamento = new TextField("Nome Cliente");
        clienteNomeOrcamento.isReadOnly();
        clienteNomeOrcamento.setReadOnly(true);
        clienteNomeOrcamento.setValue(cliente.getNome_cliente());



        localTratamentoOrcamento = new ComboBox<>("Local Tratamento");
        localTratamentoOrcamento.setItems
                (enderecoService.findAllClienteId(cliente.getId_cliente()));
        localTratamentoOrcamento.setItemLabelGenerator(SetEnderecos::getEnderecoImovel);

        problemaOrcamento = new TextArea("Problema");
        dataOrcamento = new DatePicker("Data");
        service.configuraCalendario(dataOrcamento);

        atendenteOrcamento = new ComboBox<>("Atendente");
        atendenteOrcamento.setItems(funcionarioService.listAll());
        atendenteOrcamento.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        situacaoOrcamento = new ComboBox<>("Situação");
        situacaoOrcamento.setItems(situacaoCadastroService.listAll());
        situacaoOrcamento.setItemLabelGenerator(SetSituacaoCadastro::getDescricao_situacaocadastro);




        dataInspecaoOrcamento = new DatePicker("Data Inspeção");
        service.configuraCalendario(dataInspecaoOrcamento);

        horarioOrcamento = new TimePicker("Horário");

        consultorOrcamento = new ComboBox<>("Consultor");
        consultorOrcamento.setItems(funcionarioService.listAll());
        consultorOrcamento.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        condicaoOrcamento = new ComboBox<>("Condição");
        condicaoOrcamento.setItems(condicaoPagamentoService.listAll());
        condicaoOrcamento.setItemLabelGenerator(SetCondicaoPagamento::getDescricao_condicaopagamento);


        garantiaOrcamento = new TextField("Garantia");

        valorOrcamento = new TextField("Valor Orçamento");
        valorOrcamento.setValueChangeMode(ValueChangeMode.EAGER);
        valorOrcamento.addValueChangeListener(event -> service.formataMoedaBrasileira(valorOrcamento));
        valorOrcamento.setPlaceholder("R$ 0,00");

        localTratamentoOrcamento.setWidth("auto");
        atendenteOrcamento.setWidth("auto");
        situacaoOrcamento.setWidth("auto");
        consultorOrcamento.setWidth("auto");
        condicaoOrcamento.setWidth("auto");

        valorOrcamento.setWidth("auto");

        HorizontalLayout situacaoOrcamentolayout =
                new CustomizedComboBox().customizeSituacaoCadastro(situacaoOrcamento,situacaoCadastroService);

        List<String> items = new ArrayList<>();
        servicoService.listAll().forEach(obj -> {
            items.add(obj.getDescricao_servico());
        });
        List<SetServico> teste = servicoService.listAll();

        //servicoOrcamentoChekBox.setItems("Cupins", "Insetos Rasteiros", "Roedores", "Vazamentos", "Desobstrução", "Limp.Cx D'água", "Outros");
        servicoOrcamentoChekBox.setItems(servicoService.listAll());
        servicoOrcamentoChekBox.setItemLabelGenerator(SetServico::getDescricao_servico);
        servicoOrcamentoChekBox.addClassName("double-width");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();


        formLayout.add(clienteNomeOrcamento,localTratamentoOrcamento,problemaOrcamento,
                dataOrcamento,atendenteOrcamento,situacaoOrcamentolayout,dataInspecaoOrcamento,
                horarioOrcamento,consultorOrcamento,condicaoOrcamento,garantiaOrcamento,
                valorOrcamento,servicoOrcamentoChekBox);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }


    private AutoCrudTipoPagamentoService autoCrudServiceImp;

    private void save() {
        // Lógica para salvar o cadastro
        SetOrcamento dto = orcamento;

        try {
            if (atendenteOrcamento.getValue() != null) {
                dto.setId_funcionarioatendimento(atendenteOrcamento.getValue().getId_funcionario());
            }
            if (consultorOrcamento.getValue() != null) {
                dto.setId_funcionarioconsultor(consultorOrcamento.getValue().getId_funcionario());
            }
            if (situacaoOrcamento.getValue() != null) {
                dto.setId_situacao(situacaoOrcamento.getValue().getId_situacaocadastro());
            }
            if (condicaoOrcamento.getValue() != null) {
                dto.setId_condicaopagamento(condicaoOrcamento.getValue().getId_condicaopagamento());
            }
            if (localTratamentoOrcamento.getValue() != null) {
                dto.setId_endereco(localTratamentoOrcamento.getValue().getId_endereco());
            }




            dto.setId_cliente(cliente.getId_cliente());
            dto.setDescricao_problema(problemaOrcamento.getValue());
            dto.setData_orcamento(dataOrcamento.getValue());
            dto.setData_inspecao(dataInspecaoOrcamento.getValue());
            dto.setHorario_inspecao(horarioOrcamento.getValue());
            dto.setGarantia_orcamento(garantiaOrcamento.getValue());
            dto.setValor_orcamento(service.getValorBigDecimal(valorOrcamento.getValue()));
            dto.setData_inclusao(LocalDateTime.now());
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            dto.setAtivo("S");
            service = new UtilitySystemConfigService();
            SetOrcamento orc = orcamentoService.update(dto);
            if (listArquivoNova.size() > 0) {
                listArquivoNova.forEach(setArquivoOrcamento -> {
                    setArquivoOrcamento.setId_orcamento(orc.getId_orcamento());
                    setArquivoOrcamento.setId_cliente(orc.getId_cliente());
                    try {
                        arquivoOrcamentoService.save(setArquivoOrcamento);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (listArquivoAtualizada.size()>0){
                listArquivoAtualizada.forEach(p -> {
                    try {
                        arquivoOrcamentoService.delete(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            listArquivo.clear();
            gridArquivos.setItems(listArquivo);
            problemaOrcamento.clear();
            if (servicoOrcamentoChekBox.getValue().size() > 0) {
                servicoOrcamentoChekBox.getValue().forEach(p -> {
                    SetServicosOrcamento obj = new SetServicosOrcamento();
                    obj.setAtivo("S");
                    obj.setData_inclusao(LocalDateTime.now());
                    obj.setId_orcamento(orc.getId_orcamento());
                    obj.setId_servico(p.getId_servico());
                    obj.setId_cliente(orc.getId_cliente());
                    obj.setId_usuario(orc.getId_usuario());
                    try {
                       // setServicosOrcamentoservice.update(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (contratoincluido) {
                SetContrato getContrato = contratoService.findByIdOrcamento(dto.getId_orcamento());
                SetContrato contrato = new SetContrato();
                if (Objects.nonNull(getContrato)){
                    contrato = getContrato;
                    contrato.setId_orcamento(dto.getId_orcamento());
                    contrato.setId_cliente(dto.getId_cliente());
                    contrato.setAplicacoes_periodicas(aplicacoes_periodicas.getValue());
                    contrato.setTipo_cobranca(tipo_cobranca.getValue());
                    contrato.setValor_total(service.getValorBigDecimal(valor_total.getValue()));
                    contrato.setValor_nagasaki(service.getValorBigDecimal(valor_nagasaki.getValue()));
                    contrato.setData_venda(data_venda.getValue());
                    contrato.setId_condicaopagamento(id_condicaopagamento.getValue().getId_condicaopagamento());
                    contrato.setDatainicio_execucao(datainicio_execucao.getValue());
                    contrato.setDatainicio_vencimento(datainicio_vencimento.getValue());
                    contrato.setMeses_garantia(meses_garantia.getValue());
                    contrato.setDatafim_garantia(datafim_garantia.getValue());
                    contrato.setQuantidade_aplicacoes(quantidade_aplicacoes.getValue());
                    contrato.setObservacoes_contrato(observacoes_contrato.getValue());
                    contrato.setData_inclusao(LocalDateTime.now());
                    contrato.setAtivo("S");
                    contrato.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    contratoService.update(contrato);
                } else {
                    contrato.setId_orcamento(dto.getId_orcamento());
                    contrato.setId_cliente(dto.getId_cliente());
                    contrato.setAplicacoes_periodicas(aplicacoes_periodicas.getValue());
                    contrato.setTipo_cobranca(tipo_cobranca.getValue());
                    contrato.setValor_total(service.getValorBigDecimal(valor_total.getValue()));
                    contrato.setValor_nagasaki(service.getValorBigDecimal(valor_nagasaki.getValue()));
                    contrato.setData_venda(data_venda.getValue());
                    contrato.setId_condicaopagamento(id_condicaopagamento.getValue().getId_condicaopagamento());
                    contrato.setDatainicio_execucao(datainicio_execucao.getValue());
                    contrato.setDatainicio_vencimento(datainicio_vencimento.getValue());
                    contrato.setMeses_garantia(meses_garantia.getValue());
                    contrato.setDatafim_garantia(datafim_garantia.getValue());
                    contrato.setQuantidade_aplicacoes(quantidade_aplicacoes.getValue());
                    contrato.setObservacoes_contrato(observacoes_contrato.getValue());
                    contrato.setData_inclusao(LocalDateTime.now());
                    contrato.setAtivo("S");
                    contrato.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    contratoService.save(contrato);
                }
                if (listaComissoesNova.size() > 0) {
                    SetContrato finalContrato = contrato;
                    listaComissoesNova.forEach(comissoes -> {
                        comissoes.setId_orcamento(dto.getId_orcamento());
                        comissoes.setId_cliente(dto.getId_cliente());
                        comissoes.setId_contrato(finalContrato.getId_contrato());
                        try {
                            comissoesService.save(comissoes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                if (listaComissoesRemover.size() > 0) {
                    SetContrato finalContrato = contrato;
                    listaComissoesRemover.forEach(comissoes -> {
                        comissoes.setId_orcamento(dto.getId_orcamento());
                        comissoes.setId_cliente(dto.getId_cliente());
                        comissoes.setId_contrato(finalContrato.getId_contrato());
                        try {
                            comissoesService.delete(comissoes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                listaComissoes = comissoesService.listAll(dto.getId_orcamento());
                gridComissoes.setItems(listaComissoes);



                if (listaPagamentosNova.size() > 0) {
                    SetContrato finalContrato1 = contrato;
                    listaPagamentosNova.forEach(pag -> {
                        pag.setId_orcamento(dto.getId_orcamento());
                        pag.setId_contrato(finalContrato1.getId_contrato());
                        pag.setId_cliente(dto.getId_cliente());
                        try {
                            pagamentoService.save(pag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                if (listaPagamentosRemover.size() > 0) {
                    SetContrato finalContrato1 = contrato;
                    listaPagamentosRemover.forEach(pag -> {
                        pag.setId_orcamento(dto.getId_orcamento());
                        pag.setId_contrato(finalContrato1.getId_contrato());
                        pag.setId_cliente(dto.getId_cliente());
                        try {
                            pagamentoService.delete(pag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                listaPagamentos = pagamentoService.findAllByOrcamentoId(dto.getId_orcamento());
                gridPagamento.setItems(listaPagamentos);

                if (nome_faturamento.getValue() != null && cpfcnpf_faturamento.getValue() != null) {
                    SetFaturamento fatu = new SetFaturamento();
                    fatu.setId_orcamento(dto.getId_orcamento());
                    fatu.setId_cliente(dto.getId_cliente());
                    fatu.setId_contrato(contrato.getId_contrato());
                    fatu.setNome_faturamento(nome_faturamento.getValue());
                    fatu.setEndereco_faturamento(endereco_faturamento.getValue());
                    fatu.setBairro_faturamento(bairro_faturamento.getValue());
                    fatu.setCep_faturamento(cep_faturamento.getValue());
                    fatu.setCidade_faturamento(cidade_faturamento.getValue());
                    fatu.setEstado_faturamento(estado_faturamento.getValue());
                    fatu.setPfpj_faturamento(pfpj_faturamento.getValue());
                    fatu.setCpfcnpf_faturamento(cpfcnpf_faturamento.getValue());
                    fatu.setIncricaoestadual_faturamento(incricaoestadual_faturamento.getValue());
                    fatu.setObservacao_faturamento(observacao_faturamento.getValue());
                    fatu.setData_inclusao(LocalDateTime.now());
                    fatu.setAtivo("S");
                    fatu.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    //faturamentoService.update(fatu);
                }

                if(listaNotasNova.size() > 0) {
                    SetContrato finalContrato2 = contrato;
                    listaNotasNova.forEach(nota -> {
                        nota.setId_orcamento(dto.getId_orcamento());
                        nota.setId_cliente(dto.getId_cliente());
                        nota.setId_contrato(finalContrato2.getId_contrato());
                        nota.setData_inclusao(LocalDateTime.now());
                        nota.setAtivo("S");
                        nota.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                        try {
                            notaFiscalService.save(nota);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                if(listaNotasRemover.size() > 0) {
                    SetContrato finalContrato2 = contrato;
                    listaNotasRemover.forEach(nota -> {
                        nota.setId_orcamento(dto.getId_orcamento());
                        nota.setId_cliente(dto.getId_cliente());
                        nota.setId_contrato(finalContrato2.getId_contrato());
                        nota.setData_inclusao(LocalDateTime.now());
                        nota.setAtivo("S");
                        nota.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                        try {
                            notaFiscalService.delete(nota);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                listaNotas = notaFiscalService.findAllByOrcamentoId(dto.getId_orcamento());
                gridNotaFiscal.setItems(listaNotas);


                if(listOrcamentoContatoNova.size() > 0 ){
                    listOrcamentoContatoNova.forEach(p -> {
                        try {
                            p.setId_orcamento(dto.getId_orcamento());
                            p.setId_cliente(dto.getId_cliente());
                            orcamentoContatoService.save(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                if(listOrcamentoContatoRemover.size() > 0 ){
                    listOrcamentoContatoRemover.forEach(p -> {
                        try {
                            orcamentoContatoService.delete(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                listOrcamentoContato = orcamentoContatoService.findAllByOrcamentoId(dto.getId_orcamento());
                gridOrcamentoContato.setItems(listOrcamentoContato);

                if (listaSetOrcamentoPosVendasNova.size() > 0 ){
                    listaSetOrcamentoPosVendasNova.forEach(p -> {
                        try {
                            p.setId_orcamento(dto.getId_orcamento());
                            p.setId_cliente(dto.getId_cliente());
                            p.setData_inclusao(LocalDateTime.now());
                            p.setAtivo("S");
                            p.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                            orcamentoPosVendasService.save(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                if (listaSetOrcamentoPosVendasRemover.size() > 0 ){
                    listaSetOrcamentoPosVendasRemover.forEach(p -> {
                        try {
                            orcamentoPosVendasService.delete(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                listaSetOrcamentoPosVendas = orcamentoPosVendasService.findAllByOrcamentoId(dto.getId_orcamento());
                orcamentoposvendaGrid.setItems(listaSetOrcamentoPosVendas);


            }
            orcamentoDiv.refreshGrid();
            aplicacoes_periodicas.clear();
            tipo_cobranca.clear();
            gridComissoes.setItems(new ArrayList<>());
            gridPagamento.setItems(new ArrayList<>());
            gridNotaFiscal.setItems(new ArrayList<>());
            valor_total.clear();
            valor_nagasaki.clear();
            data_venda.clear();
            id_condicaopagamento.clear();
            datainicio_execucao.clear();
            datainicio_vencimento.clear();
            datafim_garantia.clear();
            observacoes_contrato.clear();
            problemaOrcamento.clear();
            dataOrcamento.clear();
            dataInspecaoOrcamento.clear();
            horarioOrcamento.clear();
            garantiaOrcamento.clear();
            valorOrcamento.clear();
            nome_faturamento.clear();
            endereco_faturamento.clear();
            bairro_faturamento.clear();
            cep_faturamento.clear();
            cidade_faturamento.clear();
            estado_faturamento.clear();
            cpfcnpf_faturamento.clear();
            incricaoestadual_faturamento.clear();
            observacao_faturamento.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }

    }


    public void setOrcamento(SetOrcamento item) {
        this.orcamento = item;
        open();
        UI.getCurrent().access(() -> {
            contratoincluido = false;
            checkbox.setValue(false);
            problemaOrcamento.clear();
            dataOrcamento.clear();
            dataInspecaoOrcamento.clear();
            horarioOrcamento.clear();
            garantiaOrcamento.clear();
            valorOrcamento.clear();
            valor_total.clear();
            valor_nagasaki.clear();
            data_venda.clear();
            datainicio_execucao.clear();
            datainicio_vencimento.clear();
            //meses_garantia.clear();
            datafim_garantia.clear();
            quantidade_aplicacoes.clear();
            observacoes_contrato.clear();
            nome_faturamento.clear();
            endereco_faturamento.clear();
            bairro_faturamento.clear();
            cep_faturamento.clear();
            cidade_faturamento.clear();
            estado_faturamento.clear();
            cpfcnpf_faturamento.clear();
            incricaoestadual_faturamento.clear();
            observacao_faturamento.clear();

            problemaOrcamento.setValue(item.getDescricao_problema());
            dataOrcamento.setValue(item.getData_orcamento());
            dataInspecaoOrcamento.setValue(item.getData_inspecao());
            horarioOrcamento.setValue(item.getHorario_inspecao());
            garantiaOrcamento.setValue(item.getGarantia_orcamento());
            valorOrcamento.setValue(item.getValor_orcamento().toString());


            List<SetServicosOrcamento> servicosListOrcamneto = servicosOrcamentoService.listByOrcamento(item.getId_orcamento());
            List<SetServico> servicos = servicoService.listAll();

            Set<Integer> idsServicosOrcamento = servicosListOrcamneto.stream()
                    .map(SetServicosOrcamento::getId_servico)  // Extrair os IDs de servicosListOrcamento
                    .collect(Collectors.toSet());

            List<SetServico> servicosFiltrados = servicos.stream()
                    .filter(servico -> idsServicosOrcamento.contains(servico.getId_servico()))
                    .collect(Collectors.toList());

            servicoOrcamentoChekBox.setItems(servicos);

            // Defina os itens que devem ser selecionados inicialmente
            Set<SetServico> servicosSelecionados = new HashSet<>(servicosFiltrados);

            // Define os itens selecionados no CheckboxGroup
            servicoOrcamentoChekBox.setValue(servicosSelecionados);

            List<SetFuncionario> listafuncionarios = funcionarioService.listAll();
            List<SetSituacaoCadastro> listaSituacao = situacaoCadastroService.listAll();
            List<SetEnderecos> listaenderecos = enderecoService.findAllClienteId(item.getId_cliente());
            List<SetCondicaoPagamento> listaCondicaoPagamento = condicaoPagamentoService.listAll();


            atendenteOrcamento.setValue(listafuncionarios.stream()
                    .filter(objeto -> objeto.getId_funcionario().equals(item.getId_funcionarioatendimento()))
                    .findFirst().orElse(null));

            situacaoOrcamento.setValue(listaSituacao.stream()
                    .filter(objeto -> objeto.getId_situacaocadastro().equals(item.getId_situacao()))
                    .findFirst().orElse(null));

            localTratamentoOrcamento.setValue(listaenderecos.stream()
                    .filter(objeto -> objeto.getId_endereco().equals(item.getId_endereco()))
                    .findFirst().orElse(null));

            consultorOrcamento.setValue(listafuncionarios.stream()
                    .filter(objeto -> objeto.getId_funcionario().equals(item.getId_funcionarioconsultor()))
                    .findFirst().orElse(null));

            cliente = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");

            condicaoOrcamento.setValue(listaCondicaoPagamento.stream()
                    .filter(objeto -> objeto.getId_condicaopagamento().equals(item.getId_condicaopagamento()))
                    .findFirst().orElse(null));

            clienteNomeOrcamento.setValue(cliente.getNome_cliente());

            SetContrato contrato = contratoService.findByIdOrcamento(item.getId_orcamento());
            if (Objects.nonNull(contrato)){
                contratoincluido = true;
                checkbox.setValue(true);
                aplicacoes_periodicas.setValue(contrato.getAplicacoes_periodicas());
                valor_total.setValue(contrato.getValor_total().toString());
                valor_nagasaki.setValue(contrato.getValor_nagasaki().toString());
                data_venda.setValue(contrato.getData_venda());
                tipo_cobranca.setValue(contrato.getTipo_cobranca());
                id_condicaopagamento.setValue(listaCondicaoPagamento.stream()
                        .filter(objeto -> objeto.getId_condicaopagamento().equals(item.getId_condicaopagamento()))
                        .findFirst().orElse(null));
                datainicio_execucao.setValue(contrato.getDatainicio_execucao());
                datainicio_vencimento.setValue(contrato.getDatainicio_vencimento());
                meses_garantia.setValue(contrato.getMeses_garantia());
                datafim_garantia.setValue(contrato.getDatafim_garantia());
                quantidade_aplicacoes.setValue(contrato.getQuantidade_aplicacoes());
                observacoes_contrato.setValue(contrato.getObservacoes_contrato());
            }

            List<SetComissoes> comissoes = comissoesService.listAll(item.getId_orcamento());
            if (comissoes.size() > 0) {
                gridComissoes.setItems(comissoes);
                listaComissoes = comissoes;
            }
            List<SetPagamento> setPagamentos = pagamentoService.findAllByOrcamentoId(item.getId_orcamento());
            if (setPagamentos.size() > 0){
                gridPagamento.setItems(setPagamentos);
                listaPagamentos = setPagamentos;
            }
            List<SetNotaFiscal> notaFiscal = notaFiscalService.findAllByOrcamentoId(item.getId_orcamento());
            if (notaFiscal.size() > 0){
                gridNotaFiscal.setItems(notaFiscal);
                listaNotas = notaFiscal;
            }
//            List<SetArquivoOrcamento> arquivoOrcamentos = arquivoOrcamentoService.
//                    findAllByOrcamentoId(item.getId_orcamento());
            listArquivo = arquivoOrcamentoService.findAllByOrcamentoId(item.getId_orcamento());
            if (listArquivo.size() > 0){
                gridArquivos.setItems(listArquivo);
            }

            SetFaturamento faturamento = faturamentoService.findByIdOrcamento(item.getId_orcamento());
            if (Objects.nonNull(faturamento)){
                nome_faturamento.setValue(faturamento.getNome_faturamento());
                endereco_faturamento.setValue(faturamento.getEndereco_faturamento());
                bairro_faturamento.setValue(faturamento.getBairro_faturamento());
                cep_faturamento.setValue(cep_faturamento.getValue());
                cidade_faturamento.setValue(faturamento.getCidade_faturamento());
                estado_faturamento.setValue(faturamento.getEstado_faturamento());
                pfpj_faturamento.setValue(faturamento.getPfpj_faturamento());
                cpfcnpf_faturamento.setValue(faturamento.getCpfcnpf_faturamento());
                incricaoestadual_faturamento.setValue(faturamento.getIncricaoestadual_faturamento());
                observacao_faturamento.setValue(faturamento.getObservacao_faturamento());
            }

            List<SetOrcamentoContato> orcamentoContato = orcamentoContatoService.findAllByOrcamentoId(item.getId_orcamento());
            if (orcamentoContato.size() > 0) {
                gridOrcamentoContato.setItems(orcamentoContato);
               // listaContato
            }
            List<SetOrcamentoPosVenda> listaPosVendas = orcamentoPosVendasService.findAllByOrcamentoId(item.getId_orcamento());
            if (listaPosVendas.size() > 0){
                orcamentoposvendaGrid.setItems(listaPosVendas);
            }


        });
    }


}
