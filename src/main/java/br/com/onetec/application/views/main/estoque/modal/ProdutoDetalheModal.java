package br.com.onetec.application.views.main.estoque.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.views.main.estoque.div.ProdutosDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetProduto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class ProdutoDetalheModal extends Dialog {

    private SetProduto entity;
    @Autowired
    ProdutoService produtoService;

    @Autowired
    @Lazy
    ProdutosDiv produtosDiv;

    private Button saveButton;
    private Button cancelButton;

    //private TextField id_classificacaoproduto;
    private TextField nome_produto;
    private TextField unidade_entrada;
    private TextField unidade_aplicacao;
    private TextField fator_conversao;
    private TextField quantidade_estoque;
    private TextField quantidade_minima;
    private TextField valor_item;
    private TextField utimo_lote;
    private TextField grupo_quimico;
    private TextField principio_ativo;
    private TextField classificacao_nome;
    private TextField antidoto_nome;
    private TextField concentrado_nome;
    private TextField numero_registro;



    @Autowired
    public ProdutoDetalheModal(SetProduto produto) {
        UI.getCurrent().access(() -> {
            entity = produto;
            Div contentTabs = new Div(createFormCadastro(entity));
            saveButton = new com.vaadin.flow.component.button.Button("Atualizar", event -> {
                try {
                    update();
                } catch (Exception e) {
                }
            });
            cancelButton = new Button("Cancelar", event -> close());
            contentTabs.setSizeFull();
            VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
            add(layout);
        });
    }


    private Div createFormCadastro(SetProduto produto) {
        service = new UtilitySystemConfigService();
        //id_classificacaoproduto = new ComboBox<String>("Nome ou Descrição");
        nome_produto = new TextField("Nome ou Descrição");
        unidade_entrada = new TextField("Unidade Entrada");
        unidade_aplicacao = new TextField("Unidade Aplicação");
        fator_conversao = new TextField("Fator de Conversão");
        quantidade_estoque = new TextField("Quantidade em Estoque");
        quantidade_minima = new TextField("Quantidade Minima");
        valor_item = new TextField("Valor do Item");
        utimo_lote = new TextField("Ultimo Lote");
        grupo_quimico = new TextField("Grupo Quimico");
        principio_ativo = new TextField("Principio Ativo");
        classificacao_nome = new TextField("Classificação");
        antidoto_nome = new TextField("Antidoto");
        concentrado_nome = new TextField("Concentrado");
        numero_registro = new TextField("N° Registro");

        valor_item.setValueChangeMode(ValueChangeMode.EAGER);
        valor_item.setPlaceholder("R$ 0,00");
        valor_item.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_item));

        nome_produto.setValue(produto.getNome_produto());
        unidade_entrada.setValue(produto.getUnidade_entrada());
        unidade_aplicacao.setValue(produto.getUnidade_aplicacao());
        fator_conversao.setValue(produto.getFator_conversao());
        quantidade_estoque.setValue(produto.getQuantidade_estoque());
        quantidade_minima.setValue(produto.getQuantidade_minima());
        valor_item.setValue(produto.getValor_item().toEngineeringString());
        utimo_lote.setValue(produto.getUtimo_lote());
        grupo_quimico.setValue(produto.getGrupo_quimico());
        principio_ativo.setValue(produto.getPrincipio_ativo());
        classificacao_nome.setValue(produto.getClassificacao_nome());
        antidoto_nome.setValue(produto.getAntidoto_nome());
        concentrado_nome.setValue(produto.getConcentrado_nome());
        numero_registro.setValue(produto.getNumero_registro());

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_produto,unidade_entrada,
                unidade_aplicacao,fator_conversao,quantidade_estoque,quantidade_minima,
                valor_item,utimo_lote,grupo_quimico,principio_ativo,classificacao_nome,antidoto_nome,concentrado_nome,numero_registro);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void update() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetProduto dto = new SetProduto();
        dto.setId_produto(entity.getId_produto());
        dto.setNome_produto(nome_produto.getValue());
        dto.setUnidade_entrada(unidade_entrada.getValue());
        dto.setUnidade_aplicacao(unidade_aplicacao.getValue());
        dto.setFator_conversao(fator_conversao.getValue());
        dto.setQuantidade_estoque(quantidade_estoque.getValue());
        dto.setQuantidade_minima(quantidade_minima.getValue());
        dto.setValor_item(service.getValorBigDecimal(valor_item.getValue()));
        dto.setUtimo_lote(utimo_lote.getValue());
        dto.setGrupo_quimico(grupo_quimico.getValue());
        dto.setPrincipio_ativo(principio_ativo.getValue());
        dto.setClassificacao_nome(classificacao_nome.getValue());
        dto.setAntidoto_nome(antidoto_nome.getValue());
        dto.setConcentrado_nome(concentrado_nome.getValue());
        dto.setNumero_registro(numero_registro.getValue());
        dto.setAtivo("S");
        dto.setData_alteracao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());

        try {
            produtoService.update(dto);
            produtosDiv.refreshGrid();
            nome_produto.clear();
            unidade_entrada.clear();
            unidade_aplicacao.clear();
            fator_conversao.clear();
            quantidade_estoque.clear();
            quantidade_minima.clear();
            valor_item.clear();
            utimo_lote.clear();
            grupo_quimico.clear();
            principio_ativo.clear();
            classificacao_nome.clear();
            antidoto_nome.clear();
            concentrado_nome.clear();
            numero_registro.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}