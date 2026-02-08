package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.compraprodutoservice.CompraProdutoService;
import br.com.onetec.application.service.compraservice.CompraService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.views.main.administrativo.component.CompraProdutoModal;
import br.com.onetec.application.views.main.administrativo.div.ComprasDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.AbstractField;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
@UIScope
public class CompraDetalhesModal extends Dialog {


    List<SetCondicaoPagamento> condicaopagamentoLista;
    List<SetFornecedor> fornecedorLista;
    List<SetContaCorrente> contacorrenteLista;
    Tabs tabs = new Tabs();
    @Autowired
    @Lazy
    ComprasDiv comprasDiv;
    SetCompra compramodel;
    private Button saveButton;
    private Button cancelButton;
    private Button btnExcluir;
    //Cadastro Fornecedor
    private Div cadastroCompra;
    private ComboBox<SetFornecedor> id_fornecedor;
    private TextField notafiscal_compra;
    private ComboBox<SetCondicaoPagamento> id_condicaopagamento;
    private ComboBox<SetContaCorrente> id_contacorrente;
    private TextField numeronotafiscal_compra;
    private DatePicker datanotafiscal_compra;
    private DatePicker datapagamento_compra;
    private TextField valoritemstotal_compra;
    private TextField valorfrete_compra;
    private TextField valordesconto_compra;
    private TextField valortotal_compra;
    private TextArea observacoes_compra;
    private TextField responsavelaprovacao_compra;
    private DatePicker dataaprovacao_compra;
    private TimePicker horario_compra;
    private DatePicker datavalidate_compra;
    private DatePicker data_compra;
    //Cadastro Contatos Fornecedor
    private Div cadastroComprasPedidos;
    private ComboBox<SetProduto> id_produto;
    private IntegerField quantidade_compraproduto;
    private TextField valorunitario_compraproduto;
    private TextField valortotal_compraproduto;
    private TextField numerolote_compraproduto;
    private DatePicker datafabricacao_compraproduto;
    private DatePicker datavalidade_compraproduto;
    private DatePicker datarecebimento_compraproduto;
    private TextField responsavelrecebimento_compraproduto;
    private UtilitySystemConfigService service;
    private FornecedorService fornecedorService;
    private CondicaoPagamentoService condicaoPagamentoService;
    private ContaCorrenteService contaCorrenteService;
    private List<SetCompraProduto> produtoList;
    private List<SetCompraProduto> produtoListAdcionar;
    private Grid<SetCompraProduto> grid;
    private ProdutoService produtoService;
    private CompraProdutoService compraProdutoService;
    private CompraService compraService;
    private TextField totalEstoque;
    private BigDecimal valorTotalItems = BigDecimal.ZERO;

    @Autowired
    public CompraDetalhesModal(UtilitySystemConfigService service,
                               FornecedorService fornecedorService1,
                               CondicaoPagamentoService condicaoPagamentoService1,
                               ContaCorrenteService contaCorrenteService1,
                               ProdutoService produtoService1,
                               CompraProdutoService compraProdutoService1,
                               CompraService compraService1) {
        this.contaCorrenteService = contaCorrenteService1;
        this.condicaoPagamentoService = condicaoPagamentoService1;
        this.service = service;
        this.fornecedorService = fornecedorService1;
        this.produtoService = produtoService1;
        this.compraProdutoService = compraProdutoService1;
        this.compraService = compraService1;

        UI.getCurrent().access(() -> {
            setHeaderTitle("Cadastro Formulario Compra");


            valortotal_compra = new TextField("Total Calculado Compra");
            saveButton = new Button("Atualizar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            btnExcluir = new Button("Excluir", eventbe -> excluirPedido());
            btnExcluir.setVisible(true);
            btnExcluir.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);


            Tab tab1 = new Tab("Pedido Compra");
            Tab tab2 = new Tab("Items Pedido");

            tabs.add(tab1, tab2);

            cadastroCompra = createFormCadastroFuncionario();
            cadastroComprasPedidos = createFormCadastroFornecedorContatos();

            Div content = new Div(cadastroCompra, cadastroComprasPedidos);
            content.setSizeFull();
            cadastroCompra.setVisible(true);
            cadastroComprasPedidos.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroCompra.setVisible(false);
                cadastroComprasPedidos.setVisible(false);

                Tab selectedTab = tabs.getSelectedTab();
                if (selectedTab.equals(tab1)) {
                    cadastroCompra.setVisible(true);
                } else if (selectedTab.equals(tab2)) {
                    cadastroComprasPedidos.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroCompra, cadastroComprasPedidos);
            contentTabs.setSizeFull();

            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            // Criando o layout do rodapé e ajustando o alinhamento dos botões
            HorizontalLayout footerLayout = new HorizontalLayout();
            footerLayout.setWidthFull();
            footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);// Alinha o conteúdo

            HorizontalLayout rightButtons = new HorizontalLayout(valortotal_compra);
            footerLayout.add(rightButtons); // Alinha à direita
            getFooter().add(footerLayout, saveButton, cancelButton, btnExcluir);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    @Autowired
    public void initServices(UtilitySystemConfigService service,
                             FornecedorService fornecedorService1,
                             CondicaoPagamentoService condicaoPagamentoService1,
                             ContaCorrenteService contaCorrenteService1,
                             ProdutoService produtoService1,
                             CompraProdutoService compraProdutoService1,
                             CompraService compraService1) {
        this.contaCorrenteService = contaCorrenteService1;
        this.condicaoPagamentoService = condicaoPagamentoService1;
        this.service = service;
        this.fornecedorService = fornecedorService1;
        this.produtoService = produtoService1;
        this.compraProdutoService = compraProdutoService1;
        this.compraService = compraService1;
        UI.getCurrent().access(() -> {

        });
    }

    private void excluirPedido() {
        if (produtoList.size() > 0) {
            for (SetCompraProduto setCompraProduto : produtoList) {
                if (Objects.nonNull(setCompraProduto.getId_compraproduto())) {
                    produtoService.updateEstoqueQuantidadeAoDeletar(setCompraProduto.getId_produto(),
                            setCompraProduto.getQuantidadefator_compraproduto());
                    deleta(setCompraProduto);
                }
            }
        }
        try {
            compraService.delete(compramodel);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            comprasDiv.refreshGrid();
            close();
        } catch (Exception e) {
            e.printStackTrace();
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private void save() {

        try {
            SetCompra compra = compramodel;

            if (id_fornecedor.isEmpty()) {
                id_fornecedor.setRequiredIndicatorVisible(true);
                id_fornecedor.setErrorMessage("Campo obrigatório");
                id_fornecedor.setInvalid(true);
            } else if (id_condicaopagamento.isEmpty()) {
                id_condicaopagamento.setRequiredIndicatorVisible(true);
                id_condicaopagamento.setErrorMessage("Campo obrigatório");
                id_condicaopagamento.setInvalid(true);
            } else {
                // Define os valores dos campos no objeto SetFuncionario
                compra.setId_fornecedor(id_fornecedor.getValue().getId_fornecedor());
                compra.setId_condicaopagamento(id_condicaopagamento.getValue().getId_condicaopagamento());
                compra.setId_contacorrente(id_contacorrente.getValue().getId_contacorrente());
                compra.setNumeronotafiscal_compra(numeronotafiscal_compra.getValue());
                compra.setDatanotafiscal_compra(datanotafiscal_compra.getValue());
                compra.setDatapagamento_compra(datapagamento_compra.getValue());
                compra.setValoritemstotal_compra(service.getValorBigDecimal(valoritemstotal_compra.getValue()));
                compra.setValorfrete_compra(service.getValorBigDecimal(valorfrete_compra.getValue()));
                compra.setValordesconto_compra(service.getValorBigDecimal(valordesconto_compra.getValue()));
                compra.setValortotal_compra(service.getValorBigDecimal(valortotal_compra.getValue()));
                compra.setObservacoes_compra(observacoes_compra.getValue());
                compra.setResponsavelaprovacao_compra(responsavelaprovacao_compra.getValue());
                compra.setDataaprovacao_compra(dataaprovacao_compra.getValue());
                compra.setHorario_compra(horario_compra.getValue());
                compra.setDatavalidate_compra(datavalidate_compra.getValue());
                compra.setData_compra(data_compra.getValue());
                compra.setData_alteracao(LocalDateTime.now());
                compra.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                compra.setAtivo("S");
                compraService.update(compra);
                if (produtoListAdcionar.size() > 0) {
                    produtoListAdcionar.forEach(pedido -> {
                        try {
                            pedido.setId_compra(compra.getId_compra());
                            compraProdutoService.save(pedido);
                            produtoService.updateQuantidadeEstoque(pedido);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                service.notificaSucesso("Atualizado com sucesso");
                comprasDiv.refreshGrid();
                close();
            }
        } catch (Exception e) {
            service.notificaErro("Erro ao Salvar");
        }

    }

    private Div createFormCadastroFuncionario() {
        condicaopagamentoLista = new ArrayList<>();
        fornecedorLista = new ArrayList<>();
        contacorrenteLista = new ArrayList<>();

        id_fornecedor = new ComboBox<>("Fornecedor");
        notafiscal_compra = new TextField("Nota Fiscal");
        id_condicaopagamento = new ComboBox<>("Condição Pagamento");
        id_contacorrente = new ComboBox<>("Conta");
        numeronotafiscal_compra = new TextField("N° Nota Fiscal");
        datanotafiscal_compra = new DatePicker("Data Nota Fiscal");
        datapagamento_compra = new DatePicker("Data Pagamento");
        valoritemstotal_compra = new TextField("Valor Total Items");
        valorfrete_compra = new TextField("Valor Frete");
        valordesconto_compra = new TextField("Valor Desconto");

        valoritemstotal_compra.addValueChangeListener(this::valueChanged);
        valordesconto_compra.addValueChangeListener(this::valueChanged);
        valorfrete_compra.addValueChangeListener(this::valueChanged);

        observacoes_compra = new TextArea("Observações");
        responsavelaprovacao_compra = new TextField("Aprovado Por");
        dataaprovacao_compra = new DatePicker("Data Aprovação");
        horario_compra = new TimePicker("Horário Aprovação");
        datavalidate_compra = new DatePicker("Validade");
        data_compra = new DatePicker("Data Compra");

        service.configuraCalendario(datanotafiscal_compra);
        service.configuraCalendario(datapagamento_compra);
        service.configuraCalendario(dataaprovacao_compra);
        service.configuraCalendario(datavalidate_compra);
        service.configuraCalendario(data_compra);

        condicaopagamentoLista = condicaoPagamentoService.listAll();
        fornecedorLista = fornecedorService.findAll();
        contacorrenteLista = contaCorrenteService.findAll();


        id_fornecedor.setItems(fornecedorLista);
        id_fornecedor.setItemLabelGenerator(SetFornecedor::getNomefantasia_fornecedor);

        id_condicaopagamento.setItems(condicaopagamentoLista);
        id_condicaopagamento.setItemLabelGenerator(SetCondicaoPagamento::getDescricao_condicaopagamento);

        id_contacorrente.setItems(contacorrenteLista);
        id_contacorrente.setItemLabelGenerator(SetContaCorrente::getNome_contacorrente);

        valortotal_compra.setValueChangeMode(ValueChangeMode.EAGER);
        valortotal_compra.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valortotal_compra));
        valortotal_compra.setPlaceholder("R$ 0,00");

        valordesconto_compra.setValueChangeMode(ValueChangeMode.EAGER);
        valordesconto_compra.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valordesconto_compra));
        valordesconto_compra.setPlaceholder("R$ 0,00");

        valorfrete_compra.setValueChangeMode(ValueChangeMode.EAGER);
        valorfrete_compra.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valorfrete_compra));
        valorfrete_compra.setPlaceholder("R$ 0,00");

        valoritemstotal_compra.setValueChangeMode(ValueChangeMode.EAGER);
        valoritemstotal_compra.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valoritemstotal_compra));
        valoritemstotal_compra.setPlaceholder("R$ 0,00");


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_fornecedor,
                notafiscal_compra,
                id_condicaopagamento,
                id_contacorrente,
                numeronotafiscal_compra,
                datanotafiscal_compra,
                datapagamento_compra,
                valoritemstotal_compra,
                valorfrete_compra,
                valordesconto_compra,
                observacoes_compra,
                responsavelaprovacao_compra,
                dataaprovacao_compra,
                horario_compra,
                datavalidate_compra,
                data_compra);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroFornecedorContatos() {
        produtoList = new ArrayList<>();
        id_produto = new ComboBox<>("Produto");
        quantidade_compraproduto = new IntegerField("Quantidade");
        valorunitario_compraproduto = new TextField("Valor Unitario");
        valortotal_compraproduto = new TextField("Valor Total Compra");
        numerolote_compraproduto = new TextField("Numero Lote");
        datafabricacao_compraproduto = new DatePicker("Data Fabricação");
        datavalidade_compraproduto = new DatePicker("Data Validade");
        datarecebimento_compraproduto = new DatePicker("Data Recebimento");
        responsavelrecebimento_compraproduto = new TextField("Responsável Recebimento");
        totalEstoque = new TextField("Quantidade que sera adicionada em estoque :");
        totalEstoque.setReadOnly(true);
        totalEstoque.setVisible(false);
        AtomicInteger fatorConversao = new AtomicInteger();
        AtomicReference<String> unidadeEntrada = new AtomicReference<>();

        service = new UtilitySystemConfigService();
        service.configuraCalendario(datafabricacao_compraproduto);
        service.configuraCalendario(datavalidade_compraproduto);
        service.configuraCalendario(datarecebimento_compraproduto);

        id_produto.setItems(produtoService.findAll());
        id_produto.setItemLabelGenerator(SetProduto::getNome_produto);
        id_produto.addValueChangeListener(event -> {
            var produto = event.getValue();
            valorunitario_compraproduto.setValue(produto.getValor_item().toString());
            fatorConversao.set(produto.getFator_conversao());
            unidadeEntrada.set(produto.getUnidade_aplicacao());
        });

        valorunitario_compraproduto.addValueChangeListener(event -> {
            BigDecimal valorQuantidade = service.getValorBigDecimal(valorunitario_compraproduto.getValue())
                    .multiply(BigDecimal.valueOf(quantidade_compraproduto.getValue()));
            valortotal_compraproduto.setValue(valorQuantidade.toString());
        });

        quantidade_compraproduto.addValueChangeListener(event -> {
            BigDecimal valorQuantidade = service.getValorBigDecimal(valorunitario_compraproduto.getValue())
                    .multiply(BigDecimal.valueOf(quantidade_compraproduto.getValue()));
            valortotal_compraproduto.setValue(valorQuantidade.toString());
        });
        valorunitario_compraproduto.setValueChangeMode(ValueChangeMode.EAGER);
        valorunitario_compraproduto.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valorunitario_compraproduto));
        valorunitario_compraproduto.setPlaceholder("R$ 0,00");

        valortotal_compraproduto.setValueChangeMode(ValueChangeMode.EAGER);
        valortotal_compraproduto.addValueChangeListener(event -> service.
                formataMoedaBrasileira(valortotal_compraproduto));
        valortotal_compraproduto.setPlaceholder("R$ 0,00");

        quantidade_compraproduto.setValue(1);
        quantidade_compraproduto.setStepButtonsVisible(true);
        quantidade_compraproduto.setMin(1);

        quantidade_compraproduto.addValueChangeListener(event -> {
            BigDecimal valorQuantidade = service.getValorBigDecimal(valorunitario_compraproduto.getValue())
                    .multiply(BigDecimal.valueOf(quantidade_compraproduto.getValue()));
            valortotal_compraproduto.setValue(valorQuantidade.toString());
            int totalSoma = quantidade_compraproduto.getValue() * fatorConversao.get();
            String frase = totalSoma + " " + unidadeEntrada.get();
            totalEstoque.setValue(frase);
            totalEstoque.setVisible(true);
        });

        grid = new Grid<>();
        grid.addColumn(midia -> {
            SetProduto usuarios = produtoService.findById(midia.getId_produto());
            return usuarios == null ? "N/A" : usuarios.getNome_produto();
        })
                .setHeader("Nome Produto")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetCompraProduto::getQuantidade_compraproduto)
                .setHeader("Quantidade")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompraProduto::getValorunitario_compraproduto)
                .setHeader("Valor Unitario")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCompraProduto::getValortotal_compraproduto)
                .setHeader("Valor Total")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addComponentColumn(e -> {
            // Cria o botão de deletar com um ícone de lixeira
            Button del = new Button(new Icon(VaadinIcon.TRASH), event -> {
                // Remove o item da lista
                if (Objects.nonNull(e.getId_compraproduto())) {
                    deleta(e);
                    produtoService.updateEstoqueQuantidadeAoDeletar(e.getId_produto(), e.getQuantidadefator_compraproduto());
                } else {
                    //produtoList.add(e);
                }
                calculoSubtrairTotalCompra(e);
                produtoList.remove(e);
                // Atualiza os itens da grid
                grid.setItems(produtoList);
                // Feedback ao usuário
                Notification.show("Item removido ! ", 3000, Notification.Position.MIDDLE);
            });
            del.getElement().setAttribute("aria-label", "Delete");
            del.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR); // Estiliza o botão com variantes de ícone e erro
            return del;
        }).setSortable(false).setAutoWidth(true);

        grid.addItemClickListener(event -> {
            if (Objects.nonNull(event.getItem())) {
                if (Objects.nonNull(event.getItem().getId_compraproduto())) {
                    CompraProdutoModal.openModal
                            (event.getItem(), produtoService,
                                    compraProdutoService, service, grid);
                } else {
                    service.notificaErro("ERRO: Necessário clicar em atualizar antes de editar novo Contato !");
                }
            } else {
                service.notificaErro("ERRO INTERNO/ CONTATAR SUPORTE");
            }
        });

        Button saveButton = new Button("Adicionar Compra Produto", event -> {
            if (id_produto.isEmpty()) {
                id_produto.setRequiredIndicatorVisible(true);
                id_produto.setErrorMessage("Campo obrigatório");
                id_produto.setInvalid(true);
            } else if (quantidade_compraproduto.isEmpty()) {
                quantidade_compraproduto.setRequiredIndicatorVisible(true);
                quantidade_compraproduto.setErrorMessage("Campo obrigatório");
                quantidade_compraproduto.setInvalid(true);
            } else if (valorunitario_compraproduto.isEmpty()) {
                valorunitario_compraproduto.setRequiredIndicatorVisible(true);
                valorunitario_compraproduto.setErrorMessage("Campo obrigatório");
                valorunitario_compraproduto.setInvalid(true);
            } else {

                int totalSoma = quantidade_compraproduto.getValue() * fatorConversao.get();
                SetCompraProduto produto = new SetCompraProduto();
                produto.setQuantidadefator_compraproduto(totalSoma);
                produto.setId_produto(id_produto.getValue().getId_produto());
                produto.setQuantidade_compraproduto(quantidade_compraproduto.getValue());
                produto.setValorunitario_compraproduto(service.getValorBigDecimal(valorunitario_compraproduto.getValue()));
                produto.setValortotal_compraproduto(service.getValorBigDecimal(valortotal_compraproduto.getValue()));
                produto.setNumerolote_compraproduto(numerolote_compraproduto.getValue());
                produto.setDatafabricacao_compraproduto(datafabricacao_compraproduto.getValue());
                produto.setDatavalidade_compraproduto(datavalidade_compraproduto.getValue());
                produto.setDatarecebimento_compraproduto(datarecebimento_compraproduto.getValue());
                produto.setResponsavelrecebimento_compraproduto(responsavelrecebimento_compraproduto.getValue());
                produto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                produto.setAtivo("S");
                produto.setData_inclusao(LocalDateTime.now());
                produtoList.add(produto);
                produtoListAdcionar.add(produto);
                grid.setItems(produtoList);
                calculoTotalCompra(produto);
                service.notificaSucesso("Produto Adcionado");
                id_produto.clear();
                //quantidade_compraproduto.clear();
                valorunitario_compraproduto.clear();
                valortotal_compraproduto.clear();
                numerolote_compraproduto.clear();
                datafabricacao_compraproduto.clear();
                datavalidade_compraproduto.clear();
                datarecebimento_compraproduto.clear();
                responsavelrecebimento_compraproduto.clear();
                grid.setItems(produtoList);
            }


        });

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_produto,
                quantidade_compraproduto,
                valorunitario_compraproduto,
                valortotal_compraproduto,
                numerolote_compraproduto,
                datafabricacao_compraproduto,
                datavalidade_compraproduto,
                datarecebimento_compraproduto,
                responsavelrecebimento_compraproduto, totalEstoque, saveButton);

        VerticalLayout layout = new VerticalLayout(formLayout, grid);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        Div div = new Div();
        div.add(layout);

        return div;
    }

    private void calculoTotalCompra(SetCompraProduto produto) {
        String valorCampo = valoritemstotal_compra.getValue();

        // Trata nulo, vazio ou apenas espaços
        if (valorCampo == null || valorCampo.trim().isEmpty()) {
            valorCampo = "0.0";
        }

        // Remove "R$", espaços e pontos de milhar, troca vírgula por ponto
        valorCampo = valorCampo
                .replace("R$", "")
                .replace(" ", "")
                .replace(".", "")
                .replace(",", ".");

        BigDecimal valorAtual;
        try {
            valorAtual = new BigDecimal(valorCampo);
        } catch (NumberFormatException e) {
            valorAtual = BigDecimal.ZERO; // fallback de segurança
        }

        // Soma o valor total
        valorTotalItems = valorAtual.add(produto.getValortotal_compraproduto());

        // Atualiza o campo com formatação monetária brasileira
        NumberFormat formatoBR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        valoritemstotal_compra.setValue(formatoBR.format(valorTotalItems));
    }

    private void calculoSubtrairTotalCompra(SetCompraProduto produto) {
        // Adiciona o valor ao total e armazena o resultado em valorTotalItems
        valorTotalItems = service.removeFormatoMoeda(valoritemstotal_compra.getValue());
        valorTotalItems = valorTotalItems.subtract(produto.getValortotal_compraproduto());

        // Se o resultado for negativo, zera o valor
        if (valorTotalItems.compareTo(BigDecimal.ZERO) < 0) {
            valorTotalItems = BigDecimal.ZERO;
        }
        // Atualiza o valor do campo valoritemstotal_compra
        valoritemstotal_compra.setValue(valorTotalItems.toString());
        save();
    }

    private void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        BigDecimal frete = BigDecimal.ZERO;
        BigDecimal desconto = BigDecimal.ZERO;
        BigDecimal totalItems = BigDecimal.ZERO;
        if (valorfrete_compra.getValue() != null) {
            frete = service.getValorBigDecimal(valorfrete_compra.getValue());
        }
        if (valordesconto_compra.getValue() != null) {
            desconto = service.getValorBigDecimal(valordesconto_compra.getValue());
        }
        if (valoritemstotal_compra.getValue() != null) {
            totalItems = service.getValorBigDecimal(valoritemstotal_compra.getValue()).subtract(desconto);
        }
        BigDecimal resultado = totalItems.add(frete);
        valortotal_compra.setValue(resultado.toString());
    }

    private void subTotalCompra(SetCompraProduto produto) {
        // Adiciona o valor ao total e armazena o resultado em valorTotalItems
        valorTotalItems = valorTotalItems.add(produto.getValortotal_compraproduto());

        // Atualiza o valor do campo valoritemstotal_compra
        valoritemstotal_compra.setValue(valorTotalItems.toString());
    }

    public void setComprarModel(SetCompra item) {
        UI.getCurrent().access(() -> {
            grid.setItems(new ArrayList<>());

            if (tabs != null && !tabs.getChildren().findAny().isEmpty()) {
                tabs.setSelectedIndex(0);
            }

            this.compramodel = item;

            condicaopagamentoLista = condicaoPagamentoService.listAll();
            fornecedorLista = fornecedorService.findAll();
            contacorrenteLista = contaCorrenteService.findAll();

            if (Objects.nonNull(item.getNotafiscal_compra())) {
                notafiscal_compra.setValue(item.getNotafiscal_compra());
            }
            numeronotafiscal_compra.setValue(item.getNumeronotafiscal_compra());
            datanotafiscal_compra.setValue(item.getData_compra());
            datapagamento_compra.setValue(item.getDatapagamento_compra());
            //valoritemstotal_compra.setValue(item.getNotafiscal_compra());
            valorfrete_compra.setValue(item.getValorfrete_compra().toString());
            valordesconto_compra.setValue(item.getValordesconto_compra().toString());
            valortotal_compra.setValue(item.getValortotal_compra().toString());
            observacoes_compra.setValue(item.getObservacoes_compra());
            responsavelaprovacao_compra.setValue(item.getResponsavelaprovacao_compra());
            dataaprovacao_compra.setValue(item.getData_compra());
            horario_compra.setValue(item.getHorario_compra());
            datavalidate_compra.setValue(item.getDatavalidate_compra());
            data_compra.setValue(item.getData_compra());

            id_condicaopagamento.setValue(condicaopagamentoLista.stream()
                    .filter(objeto -> objeto.getId_condicaopagamento().equals(item.getId_condicaopagamento()))
                    .findFirst().orElse(null));

            id_contacorrente.setValue(contacorrenteLista.stream()
                    .filter(objeto -> objeto.getId_contacorrente().equals(item.getId_contacorrente()))
                    .findFirst().orElse(null));

            id_fornecedor.setValue(fornecedorLista.stream()
                    .filter(objeto -> objeto.getId_fornecedor().equals(item.getId_fornecedor()))
                    .findFirst().orElse(null));

            List<SetCompraProduto> listaProdutosCompra = compraProdutoService.findByIdCompra(item.getId_compra());
            grid.setItems(listaProdutosCompra);
            produtoList = listaProdutosCompra;
            produtoListAdcionar = new ArrayList<>();

        });
    }

    private void deleta(SetCompraProduto item) {
        try {
            compraProdutoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }
}