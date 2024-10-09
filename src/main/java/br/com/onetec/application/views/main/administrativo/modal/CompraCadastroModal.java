package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.compraprodutoservice.CompraProdutoService;
import br.com.onetec.application.service.compraservice.CompraService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.views.main.administrativo.div.ComprasDiv;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class CompraCadastroModal extends Dialog {



    private Button saveButton;
    private  Button cancelButton;

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

    private Grid<SetCompraProduto> grid;

    private ProdutoService produtoService;

    private CompraProdutoService compraProdutoService;

    private CompraService compraService;

    @Autowired
    @Lazy
    ComprasDiv comprasDiv;

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



    @Autowired
    public CompraCadastroModal(UtilitySystemConfigService service,
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
            addClassName("cadastro-modal");
            saveButton = new Button("Salvar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            Tabs tabs = new Tabs();
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
            getFooter().add(footerLayout,saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    private void save() {

        try {
            SetCompra compra = new SetCompra();

            if (id_fornecedor.isEmpty()) {
                id_fornecedor.setRequiredIndicatorVisible(true);
                id_fornecedor.setErrorMessage("Campo obrigatório");
                id_fornecedor.setInvalid(true);
            }else if (id_condicaopagamento.isEmpty()) {
                id_condicaopagamento.setRequiredIndicatorVisible(true);
                id_condicaopagamento.setErrorMessage("Campo obrigatório");
                id_condicaopagamento.setInvalid(true);
            }else if (valoritemstotal_compra.isEmpty()) {
                valoritemstotal_compra.setRequiredIndicatorVisible(true);
                valoritemstotal_compra.setErrorMessage("Campo obrigatório");
                valoritemstotal_compra.setInvalid(true);
            }else if  (valortotal_compra.isEmpty()) {
                valortotal_compra.setRequiredIndicatorVisible(true);
                valortotal_compra.setErrorMessage("Campo obrigatório");
                valortotal_compra.setInvalid(true);
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
                compra.setData_inclusao(LocalDateTime.now());
                compra.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                compra.setAtivo("S");
                compraService.save(compra);
                if (produtoList.size() > 0) {
                    produtoList.forEach(pedido -> {
                        try {
                            pedido.setId_compra(compra.getId_compra());
                            compraProdutoService.save(pedido);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                service.notificaSucesso("Cadastrado com sucesso");
                comprasDiv.refreshGrid();
                close();
            }
        } catch (Exception e){
            service.notificaErro("Erro ao Salvar");
        }

    }

    private Div createFormCadastroFuncionario() {

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

        id_fornecedor.setItems(fornecedorService.findAll());
        id_fornecedor.setItemLabelGenerator(SetFornecedor::getNomefantasia_fornecedor);
        id_condicaopagamento.setItems(condicaoPagamentoService.listAll());
        id_condicaopagamento.setItemLabelGenerator(SetCondicaoPagamento::getDescricao_condicaopagamento);
        id_contacorrente.setItems(contaCorrenteService.findAll());
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
        id_produto.setItems(produtoService.findAll());
        id_produto.setItemLabelGenerator(SetProduto::getNome_produto);

        valorunitario_compraproduto.addValueChangeListener(event -> {
            BigDecimal valorQuantidade  = service.getValorBigDecimal(valorunitario_compraproduto.getValue())
                    .multiply(BigDecimal.valueOf(quantidade_compraproduto.getValue()));
            valortotal_compraproduto.setValue(valorQuantidade.toString());
        });

        quantidade_compraproduto.addValueChangeListener(event -> {
            BigDecimal valorQuantidade  = service.getValorBigDecimal(valorunitario_compraproduto.getValue())
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

        Button saveButton = new Button("Adicionar Compra Produto", event -> {
            if (id_produto.isEmpty()) {
                id_produto.setRequiredIndicatorVisible(true);
                id_produto.setErrorMessage("Campo obrigatório");
                id_produto.setInvalid(true);
            }else if (quantidade_compraproduto.isEmpty()) {
                quantidade_compraproduto.setRequiredIndicatorVisible(true);
                quantidade_compraproduto.setErrorMessage("Campo obrigatório");
                quantidade_compraproduto.setInvalid(true);
            } else if (valorunitario_compraproduto.isEmpty()) {
                valorunitario_compraproduto.setRequiredIndicatorVisible(true);
                valorunitario_compraproduto.setErrorMessage("Campo obrigatório");
                valorunitario_compraproduto.setInvalid(true);
            } else {

                SetCompraProduto produto = new SetCompraProduto();
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
                responsavelrecebimento_compraproduto,saveButton);

        VerticalLayout layout = new VerticalLayout(formLayout, grid);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        Div div = new Div();
        div.add(layout);

        return div;
    }

    private BigDecimal valorTotalItems = BigDecimal.ZERO;

    private void calculoTotalCompra(SetCompraProduto produto) {
        // Adiciona o valor ao total e armazena o resultado em valorTotalItems
        valorTotalItems = valorTotalItems.add(produto.getValortotal_compraproduto());

        // Atualiza o valor do campo valoritemstotal_compra
        valoritemstotal_compra.setValue(valorTotalItems.toString());
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
}
