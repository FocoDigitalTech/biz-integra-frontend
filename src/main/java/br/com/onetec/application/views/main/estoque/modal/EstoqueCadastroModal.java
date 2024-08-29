package br.com.onetec.application.views.main.estoque.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.estoqueservice.EstoqueService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.service.tecnicoassistenteservice.TecnicoAssistenteService;
import br.com.onetec.application.views.main.estoque.div.MovimentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.model.SetTecnicoAssistente;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@UIScope
public class EstoqueCadastroModal extends Dialog {

    @Autowired
    EstoqueService estoqueService;

    @Autowired
    TecnicoAssistenteService tecnicoAssistenteService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    @Lazy
    MovimentoDiv movimentoDiv;

    private Button saveButton;
    private Button cancelButton;

    private ComboBox <SetProduto> id_produto;
    private ComboBox <SetTecnicoAssistente> id_tecnicosassistentes;
    private Checkbox saida_controleproduto;
    private DatePicker data_controle;
    private IntegerField quantidade_enviada;
    private IntegerField quantidade_devolvida;
    private IntegerField quantidade_consumida;
    private TextField unidade_entrada;
    private TextField numero_lote;




    @Autowired
    public EstoqueCadastroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try { save();}
                catch (Exception e) {}
            });
            cancelButton = new Button("Cancelar", event -> close());
            Div contentTabs = new Div(createFormCadastro());
            contentTabs.setSizeFull();
            VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
            add(layout);
        });
    }


    private Div createFormCadastro() {
        service = new UtilitySystemConfigService();
        //id_classificacaoproduto = new ComboBox<String>("Nome ou Descrição");

        unidade_entrada = new TextField("Unidade Entrada");

        quantidade_enviada = new IntegerField();
        quantidade_enviada.setLabel("Quantidade Enviada");
        quantidade_enviada.setMin(0);
        quantidade_enviada.setMax(1000);
        quantidade_enviada.setValue(0);
        quantidade_enviada.setStepButtonsVisible(true);

        quantidade_devolvida = new IntegerField();
        quantidade_devolvida.setLabel("Quantidade Devolvida");
        quantidade_devolvida.setMin(0);
        quantidade_devolvida.setMax(1000);
        quantidade_devolvida.setValue(0);
        quantidade_devolvida.setStepButtonsVisible(true);

        quantidade_consumida = new IntegerField();
        quantidade_consumida.setLabel("Quantidade Enviada");
        quantidade_consumida.setMin(0);
        quantidade_consumida.setMax(1000);
        quantidade_consumida.setValue(0);
        quantidade_consumida.setStepButtonsVisible(true);


        id_produto = new ComboBox<>("Nome Produto");
        id_tecnicosassistentes = new ComboBox<>("Tecnico/Assistente");
        saida_controleproduto = new Checkbox("Saida Estoque ?");
        data_controle = new DatePicker("Data");

        numero_lote = new TextField("Numero do Lote");

        id_produto.setItems(produtoService.findAll());
        id_produto.setItemLabelGenerator(SetProduto::getNome_produto);
        id_tecnicosassistentes.setItems(tecnicoAssistenteService.findAll());
        id_tecnicosassistentes.setItemLabelGenerator(SetTecnicoAssistente::getNome_tecnicoassistente);



        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_produto, id_tecnicosassistentes, saida_controleproduto, data_controle, quantidade_enviada,
                quantidade_devolvida, quantidade_consumida, unidade_entrada, numero_lote);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetEstoque dto = new SetEstoque();
        //dto.setSaida_controleproduto(saida_controleproduto.getValue());
        dto.setData_controle(data_controle.getValue());
        dto.setQuantidade_enviada(quantidade_enviada.getValue().toString());
        dto.setQuantidade_devolvida(quantidade_devolvida.getValue().toString());
        dto.setQuantidade_consumida(quantidade_consumida.getValue().toString());
        dto.setUnidade_entrada(unidade_entrada.getValue());
        dto.setNumero_lote(numero_lote.getValue());

        SetProduto produto = id_produto.getValue();
        if (produto != null) {
            dto.setId_produto(produto.getId_produto());
        }
        SetTecnicoAssistente tecnicoAssistente = id_tecnicosassistentes.getValue();
        if (tecnicoAssistente != null){
            dto.setId_tecnicosassistentes(tecnicoAssistente.getId_tecnicoassistente());
        }
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());

        try {
            estoqueService.save(dto);
            movimentoDiv.refreshGrid();
            id_produto.clear();
            id_tecnicosassistentes.clear();
            saida_controleproduto.clear();
            data_controle.clear();
            quantidade_enviada.clear();
            quantidade_devolvida.clear();
            quantidade_consumida.clear();
            unidade_entrada.clear();
            numero_lote.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}