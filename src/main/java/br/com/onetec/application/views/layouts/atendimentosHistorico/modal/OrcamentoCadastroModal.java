package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.comissoesservice.ComissoesService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.faturamentoservice.FaturamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.pagamentoservice.PagamentoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.service.situacaopagamentoservice.SituacaoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
@UIScope
public class OrcamentoCadastroModal extends Dialog {

    private SetCliente cliente;

    private Button saveButton;
    private Button cancelButton;
    private Button botaoContrato;
    private Button botaoPagamento;

    // Formulario Orçamento
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
    private IntegerField parcelamentoPagar;

    //formulario comissoes
    private ComboBox<SetFuncionario> id_funcionario;
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
    @Lazy
    OrcamentoDiv orcamentoDiv;


    private Div cadastroOrcamantosDadosFinanceiros;
    private Div cadastroFechamentodeContrado;
    private Div cadastroComissoes;
    private Div cadastroPagamentos;
    private Div cadastroFaturamento;
    private Div cadastroNotaFiscal;
    private Anchor downloadLink;
    private Button botaoNotaFiscal;


    private Tab tab2;
    private Tab tab3;
    private Tab tab4;
    private Tab tab5;
    private Tab tab6;

    private void loadClienteData(SetCliente cliente) {
        // Lógica para carregar os dados do cliente usando o objeto cliente
    }


    public OrcamentoCadastroModal() {
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
                    // Criar o StreamResource para gerar e abrir o PDF
                    StreamResource resource = new StreamResource("contrato.pdf", this::createPdf);

                    // Criar o Anchor (link) para o StreamResource e definir o texto

                    downloadLink.setHref(resource);  // Seta o recurso de download
                    downloadLink.setText("Abrir Contrato PDF");  // Texto do link

                    // Definir o alvo para abrir em nova aba
                    downloadLink.setTarget("_blank");
                    // Adiciona o link de download ao layout
                }
            });

            botaoNotaFiscal = new Button("Gerar Nota Fiscal", e -> {
                    // Criar o StreamResource para gerar e abrir o PDF
                    StreamResource resource = new StreamResource("notafiscal.pdf", this::gerarNotaFiscalPdf);
                    // Criar o Anchor (link) para o StreamResource e definir o texto
                    downloadLink.setHref(resource);  // Seta o recurso de download
                    downloadLink.setText("Abrir Nota PDF");  // Texto do link
                    // Definir o alvo para abrir em nova aba
                    downloadLink.setTarget("_blank");
                    // Adiciona o link de download ao layout
                });
            botaoPagamento = new Button("Gerar Pagamentos", eventbe -> openModalPagamentosParcela());

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

            saveButton = new Button("Salvar", eventbe -> save());
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

            tabs.add(tab1, tab2, tab3, tab4, tab5,tab6);
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
                    System.out.println("Contrato será incluído.");
                } else {
                    contratoincluido = false;
                    tab2.setVisible(false);
                    tab3.setVisible(false);
                    tab4.setVisible(false);
                    tab5.setVisible(false);
                    tab6.setVisible(false);
                    System.out.println("Contrato não será incluído.");

                }
            });


            cadastroOrcamantosDadosFinanceiros = createFormCadastroOrcamantosDadosFinanceiros();
            cadastroFechamentodeContrado = createFormCadastroFechamentodeContrado();
            cadastroComissoes = createFormCadastroComissoes();
            cadastroPagamentos = createFormCadastroPagamentos();
            cadastroFaturamento = createFormCadastroFaturamento();
            cadastroNotaFiscal = createFormcadastroNotaFiscal();

            Div content = new Div(cadastroOrcamantosDadosFinanceiros,cadastroFechamentodeContrado,cadastroComissoes,
                    cadastroPagamentos,cadastroFaturamento,cadastroNotaFiscal);
            content.setSizeFull();
            cadastroOrcamantosDadosFinanceiros.setVisible(true);
            cadastroFechamentodeContrado.setVisible(false);
            cadastroComissoes.setVisible(false);
            cadastroPagamentos.setVisible(false);
            cadastroFaturamento.setVisible(false);
            cadastroNotaFiscal.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroOrcamantosDadosFinanceiros.setVisible(false);
                cadastroFechamentodeContrado.setVisible(false);
                cadastroComissoes.setVisible(false);
                cadastroPagamentos.setVisible(false);
                cadastroFaturamento.setVisible(false);
                cadastroNotaFiscal.setVisible(false);

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
                }
            });

            Div contentTabs = new Div(cadastroOrcamantosDadosFinanceiros,cadastroFechamentodeContrado,cadastroComissoes,
                    cadastroPagamentos,cadastroFaturamento,cadastroNotaFiscal);
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
            getFooter().add(botaoNotaFiscal,botaoContrato,botaoPagamento,downloadLink,footerLayout);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
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
            Button cancelar = new Button("Cancelar", event -> close());
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
        id_tipopagamento.setItems(tipoPagamentoService.listAll());

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
            gridPagamento.setItems(listaPagamentos);
        });

        formLayout.add(numeroparcela_pagamento,
                totalparcela_pagamento,
                vencimento_pagamento,
                valor_pagamento,
                data_pagamento,
                valorpago_pagamento,
                numerodocumento_pagamento,
                id_tipopagamento,
                id_situacaopagamento,
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

        id_funcionario = new ComboBox<>("Funcionário");
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
        id_funcionario.setItems
                (funcionarioService.listAll());
        id_funcionario.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

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
            BigDecimal valorsomado = service.getValorBigDecimal(valor_total.getValue());
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
        Button saveAdicionarButton = new Button("Adicionar Endereço", event -> {
            SetComissoes comissoesNovo = new SetComissoes();
            comissoesNovo.setId_funcionario(id_funcionario.getValue().getId_funcionario());
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
            gridComissoes.setItems(listaComissoes);
        });

        formLayout.add(id_funcionario,
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

        datainicio_vencimento.setValue(LocalDate.now());

        meses_garantia.addValueChangeListener(event -> {
            datafim_garantia.setValue(datainicio_execucao.getValue().plusMonths(event.getValue()));
        });


        datafim_garantia.isReadOnly();
        datafim_garantia.setReadOnly(true);

        formLayout.add(aplicacoes_periodicas,tipo_cobranca,valor_total,valor_nagasaki,data_venda,
                id_condicaopagamento,datainicio_execucao,datainicio_vencimento,meses_garantia,
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
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDoc);

            // Adicionar cabeçalho com logotipo
            Image logo = new Image(ImageDataFactory.create("https://nagasakidedetizacao.com.br/wp-content/uploads/2020/08/Logo-Nagasaki.png"));
            logo.setWidth(100);
            logo.setHorizontalAlignment(HorizontalAlignment.LEFT);
            document.add(logo);

            // Título do contrato
            Paragraph title = new Paragraph("Contrato de Prestação de Serviços")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Informações gerais do contrato
            document.add(new Paragraph("Cliente: "+ cliente.getNome_cliente() + "   CNPJ/CPF: "+ cliente.getCpf_cgc_cliente())
                    .setFontSize(12)
                    .setBold()
                    .setMarginBottom(5));
            document.add(new Paragraph("Data: " + LocalDate.now())
                    .setFontSize(12)
                    .setMarginBottom(15));

            // Texto introdutório
            Paragraph intro = new Paragraph("Este contrato de prestação de serviços é firmado entre a empresa Nagasaki e o cliente mencionado acima, respeitando as cláusulas e condições descritas a seguir.")
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(20);
            document.add(intro);

            // Adicionar seções com títulos destacados
            document.add(new Paragraph("1. Objetivo do Contrato")
                    .setBold()
                    .setFontSize(14)
                    .setMarginBottom(10));
            List<String> listaServicos = new ArrayList<>();
            AtomicReference<String> services = new AtomicReference<>("");
            servicoOrcamentoChekBox.getValue().forEach(p->{
                    services.set("," + p.getDescricao_servico());
            });
            document.add(new Paragraph("O presente contrato tem como objetivo a prestação de serviços de "+services.get()+", conforme detalhado nas cláusulas seguintes.")
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(15));

            // Seção com tabela
            document.add(new Paragraph("2. Valores e Pagamentos")
                    .setBold()
                    .setFontSize(14)
                    .setMarginBottom(10));
            document.add(new Paragraph("O valor total acordado entre as partes é de R$ "+ valor_total.getValue() +", pago conforme o seguinte cronograma:")
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(10));

            Integer parcelas = 1;
            if(Objects.isNull(parcelamentoPagar)) {
                parcelas = Integer.valueOf(id_condicaopagamento.getValue().getQuantidade_parcelas());
            } else {
                if (parcelamentoPagar.getValue() != null) {
                    if (parcelamentoPagar.getValue() > 1) {
                        parcelas = parcelamentoPagar.getValue();
                    }
                }
            }

            BigDecimal value = service.getValorBigDecimal(valor_total.getValue())
                    .divide(BigDecimal.valueOf(parcelas), RoundingMode.HALF_UP);

            List<BigDecimal> listaValues = new ArrayList<>();
            for (int i = 0; i < parcelas; i++){
                listaValues.add(value);
            }

            // Tabela de pagamentos
            Table paymentTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}));
            paymentTable.setWidth(UnitValue.createPercentValue(100));
            paymentTable.addHeaderCell(new Cell().add(new Paragraph("Parcela")).setBold());
            paymentTable.addHeaderCell(new Cell().add(new Paragraph("Valor")).setBold());
            paymentTable.addHeaderCell(new Cell().add(new Paragraph("Data")).setBold());
            paymentTable.addHeaderCell(new Cell().add(new Paragraph("Status")).setBold());

            // Adicionar conteúdo à tabela
            AtomicInteger finalParcelas = new AtomicInteger();
            listaValues.forEach(v -> {

                finalParcelas.getAndIncrement();
                String p = String.valueOf(finalParcelas.get());
                String data = "";
                if(datainicio_vencimento.getValue()!=null){
                    data = datainicio_vencimento.getValue().plusMonths(finalParcelas.get()).toString();
                }else {
                    data = LocalDate.now().toString();
                }

                paymentTable.addCell(new Cell().add(new Paragraph(p)));
                paymentTable.addCell(new Cell().add(new Paragraph("R$ "+v)));
                paymentTable.addCell(new Cell().add(new Paragraph(data)));
                paymentTable.addCell(new Cell().add(new Paragraph("Pendente")));
            });
            // Adicionar mais linhas conforme necessário...
            document.add(paymentTable.setMarginBottom(20));

            // Parágrafos adicionais de seções
            document.add(new Paragraph("3. Prazo de Execução")
                    .setBold()
                    .setFontSize(14)
                    .setMarginBottom(10));
            document.add(new Paragraph("A execução dos serviços terá início na data de "+datainicio_execucao.getValue()+", conforme detalhado no cronograma abaixo.")
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(15));

            // Adicionar rodapé
            Paragraph footer = new Paragraph("Assinatura: ________________________________")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12)
                    .setMarginTop(30);
            document.add(footer);

            document.close();

            return new ByteArrayInputStream(baos.toByteArray());
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
        localTratamentoOrcamento.setItemLabelGenerator(SetEnderecos::getEndereco_imovel);

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
                dataOrcamento,atendenteOrcamento,situacaoOrcamento,dataInspecaoOrcamento,
                horarioOrcamento,consultorOrcamento,condicaoOrcamento,garantiaOrcamento,
                valorOrcamento,servicoOrcamentoChekBox);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }


    private void save() {
        // Lógica para salvar o cadastro
        SetOrcamento dto = new SetOrcamento();

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
            SetOrcamento orc = orcamentoService.save(dto);
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
                        setServicosOrcamentoservice.save(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (contratoincluido) {
                SetContrato contrato = new SetContrato();
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

                if (listaComissoes.size() > 0) {
                    listaComissoes.forEach(comissoes -> {
                        comissoes.setId_orcamento(dto.getId_orcamento());
                        comissoes.setId_cliente(dto.getId_cliente());
                        comissoes.setId_contrato(contrato.getId_contrato());
                        try {
                            comissoesService.save(comissoes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                if (listaPagamentos.size() > 0) {
                    listaPagamentos.forEach(pag -> {
                        pag.setId_orcamento(dto.getId_orcamento());
                        pag.setId_contrato(contrato.getId_contrato());
                        pag.setId_cliente(dto.getId_cliente());
                        try {
                            pagamentoService.save(pag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
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
                    faturamentoService.save(fatu);
                }

                if(listaNotas.size() > 0) {
                    listaNotas.forEach(nota -> {
                        nota.setId_orcamento(dto.getId_orcamento());
                        nota.setId_cliente(dto.getId_cliente());
                        nota.setId_contrato(contrato.getId_contrato());
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


    public InputStream gerarNotaFiscalPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Criar PDF Writer
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            document.setMargins(20, 20, 20, 20);

            // Adicionar Logotipo
            String logoPath = "https://nagasakidedetizacao.com.br/wp-content/uploads/2020/08/Logo-Nagasaki.png"; // Caminho para o logotipo
            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).scaleToFit(100, 50);
            document.add(logo.setFixedPosition(20, 700));

            // Informações da empresa e cliente
            Paragraph empresaInfo = new Paragraph("Nagasaki\nCNPJ: 04.004.186/0001-08\nEndereço: RUA JOÃO BERTACCHI, 49\nSão Paulo, SP, CEP 04777-110")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(10);
            document.add(empresaInfo);

            Paragraph clienteInfo = new Paragraph("Cliente: "+cliente.getNome_cliente()+"\nCPF/CNPJ: "+cliente.getCpf_cgc_cliente())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(10);
            document.add(clienteInfo);

            // Título
            Paragraph title = new Paragraph("Nota Fiscal de Serviços")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Seção de dados da nota fiscal
            Table table = new Table(UnitValue.createPercentArray(new float[] {1, 2, 1, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Cabeçalho da tabela
            table.addHeaderCell(new Paragraph("Campo").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Informação").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Campo").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Informação").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

            // Dados da nota
            addTableCell(table, "Número", numero_notafiscal.getValue());
            addTableCell(table, "Série", serie_notafiscal.getValue());
            addTableCell(table, "Data Emissão", dataemissao_notafiscal.getValue().toString());
            addTableCell(table, "Natureza", natureza_notafiscal.getValue());

            addTableCell(table, "Código Fiscal", numero_notafiscal.getValue());  // Exemplo de campo extra
            addTableCell(table, "Unidade", unidade_notafiscal.getValue());
            addTableCell(table, "Quantidade", quantidade_notafiscal.getValue().toString());
            addTableCell(table, "Valor Unitário", valorunitario_notafiscal.getValue());
            document.add(table);

            // Serviços detalhados
            Paragraph serviceTitle = new Paragraph("Serviços Prestados").setBold().setFontSize(14).setMarginTop(10);
            document.add(serviceTitle);

            Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{1, 3, 1, 1, 1}));
            serviceTable.setWidth(UnitValue.createPercentValue(100));

            // Cabeçalhos dos serviços
            serviceTable.addHeaderCell(new Paragraph("Código").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            serviceTable.addHeaderCell(new Paragraph("Descrição").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            serviceTable.addHeaderCell(new Paragraph("Quantidade").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            serviceTable.addHeaderCell(new Paragraph("Valor Unitário").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            serviceTable.addHeaderCell(new Paragraph("Valor Total").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

            // Exemplo de serviço prestado
            serviceTable.addCell(new Paragraph("001"));
            serviceTable.addCell(new Paragraph(descricao_notafiscal.getValue()));
            serviceTable.addCell(new Paragraph(quantidade_notafiscal.getValue().toString()));
            serviceTable.addCell(new Paragraph(valorunitario_notafiscal.getValue()));
            serviceTable.addCell(new Paragraph(valortotal_notafiscal.getValue()));

            document.add(serviceTable);

            // Totalização e impostos
            Table totalTable = new Table(UnitValue.createPercentArray(new float[]{3, 1}));
            totalTable.setWidth(UnitValue.createPercentValue(100));
            totalTable.addCell(new Paragraph("Subtotal").setBold());
            totalTable.addCell(new Paragraph(valorunitario_notafiscal.getValue()));


            totalTable.addCell(new Paragraph("Valor Total").setBold());
            totalTable.addCell(new Paragraph("R$ " + valortotal_notafiscal.getValue()));

            document.add(totalTable);

            // Adicionar uma linha para assinatura
            Paragraph assinatura = new Paragraph("\n\n________________________________________\nAssinatura do Emitente")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20);
            document.add(assinatura);

            // Rodapé
            Paragraph footer = new Paragraph("Este documento não é válido como nota fiscal eletrônica. Emitido por: Nagasaki Controle de Pragas")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginTop(30);
            document.add(footer);

            // Fechar o documento
            document.close();

            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método auxiliar para adicionar células estilizadas na tabela
    private void addTableCell(Table table, String campo, String valor) {
        table.addCell(new Paragraph(campo).setBold());
        table.addCell(new Paragraph(valor != null ? valor : "N/A"));
    }
}
