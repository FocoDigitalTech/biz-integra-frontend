package br.com.onetec.application.views.layouts.atendimentosHistorico.modal;

import br.com.onetec.application.configuration.OrcamentoTransiction;
import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.comissoesservice.ComissoesService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.faturamentoservice.FaturamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.application.service.orcamentoservice.OrcamentoService;
import br.com.onetec.application.service.servicoorcamentos.ServicosOrcamentoService;
import br.com.onetec.application.service.servicoservices.ServicoService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.service.situacaopagamentoservice.SituacaoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
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
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Div;
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

import java.math.BigDecimal;
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
    private ServicosOrcamentoService servicosOrcamentoService;


    @Autowired
    @Lazy
    OrcamentoDiv orcamentoDiv;


    private Div cadastroOrcamantosDadosFinanceiros;
    private Div cadastroFechamentodeContrado;
    private Div cadastroComissoes;
    private Div cadastroPagamentos;
    private Div cadastroFaturamento;
    private Div cadastroNotaFiscal;

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


    private Tab tab2;
    private Tab tab3;
    private Tab tab4;
    private Tab tab5;
    private Tab tab6;
    private SetOrcamento orcamento;


    private void setDadosComplementates(SetCliente cliente, SetOrcamento orcamento) {
    }

    private void loadClienteData(SetCliente cliente) {
        // Lógica para carregar os dados do cliente usando o objeto cliente
    }


    public OrcamentoDetalheModal() {
        UI.getCurrent().access(() -> {
            addClassName(LumoUtility.Gap.SMALL);
            // Recupera o objeto Cliente da sessão
            cliente = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");

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
                } else if (selectedTab.equals(tab2)) {
                    cadastroFechamentodeContrado.setVisible(true);
                } else if (selectedTab.equals(tab3)) {
                    cadastroComissoes.setVisible(true);
                } else if (selectedTab.equals(tab4)) {
                    cadastroPagamentos.setVisible(true);
                } else if (selectedTab.equals(tab5)) {
                    cadastroFaturamento.setVisible(true);
                } else if (selectedTab.equals(tab6)) {
                    cadastroNotaFiscal.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroOrcamantosDadosFinanceiros,cadastroFechamentodeContrado,cadastroComissoes,
                    cadastroPagamentos,cadastroFaturamento,cadastroNotaFiscal);
            contentTabs.setSizeFull();

            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(checkbox,saveButton, cancelButton);

            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

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

    private Div createFormCadastroOrcamantosDadosFinanceiros() {


        service = new UtilitySystemConfigService();
        clienteNomeOrcamento = new TextField("Nome Cliente");
        clienteNomeOrcamento.isReadOnly();
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
            if (contratoincluido) {
                SetContrato contrato = new SetContrato();

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
            orcamentoDiv.refreshGrid();
            aplicacoes_periodicas.clear();
            tipo_cobranca.clear();
            valor_total.clear();
            valor_nagasaki.clear();
            data_venda.clear();
            id_condicaopagamento.clear();
            datainicio_execucao.clear();
            datainicio_vencimento.clear();
            meses_garantia.clear();
            datafim_garantia.clear();
            quantidade_aplicacoes.clear();
            observacoes_contrato.clear();
            localTratamentoOrcamento.clear();
            problemaOrcamento.clear();
            dataOrcamento.clear();
            atendenteOrcamento.clear();
            situacaoOrcamento.clear();
            dataInspecaoOrcamento.clear();
            horarioOrcamento.clear();
            consultorOrcamento.clear();
            condicaoOrcamento.clear();
            garantiaOrcamento.clear();
            valorOrcamento.clear();
            servicoOrcamentoChekBox.clear();
            nome_faturamento.clear();
            endereco_faturamento.clear();
            bairro_faturamento.clear();
            cep_faturamento.clear();
            cidade_faturamento.clear();
            estado_faturamento.clear();
            pfpj_faturamento.clear();
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

            setDadosComplementates(cliente, orcamento);
        });
    }


}
