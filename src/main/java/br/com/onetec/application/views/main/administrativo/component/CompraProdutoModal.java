package br.com.onetec.application.views.main.administrativo.component;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.compraprodutoservice.CompraProdutoService;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCompraProduto;
import br.com.onetec.infra.db.model.SetOrcamentoContato;
import br.com.onetec.infra.db.model.SetProduto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CompraProdutoModal {


    public static void openModal(SetCompraProduto item, ProdutoService produtoService,
                                 CompraProdutoService compraProdutoService,
                                 UtilitySystemConfigService service, Grid<SetCompraProduto> grid) {

        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        ComboBox<SetProduto> id_produto = new ComboBox<>("Produto");
        IntegerField quantidade_compraproduto = new IntegerField("Quantidade");
        TextField valorunitario_compraproduto = new TextField("Valor Unitario");
        TextField valortotal_compraproduto = new TextField("Valor Total Compra");
        TextField numerolote_compraproduto = new TextField("Numero Lote");
        DatePicker datafabricacao_compraproduto = new DatePicker("Data Fabricação");
        DatePicker datavalidade_compraproduto = new DatePicker("Data Validade");
        DatePicker datarecebimento_compraproduto = new DatePicker("Data Recebimento");
        TextField responsavelrecebimento_compraproduto = new TextField("Responsável Recebimento");

        id_produto.setItems(produtoService.findAll());
        id_produto.setItemLabelGenerator(SetProduto::getNome_produto);

                quantidade_compraproduto.setValue(item.getQuantidade_compraproduto());
        valorunitario_compraproduto.setValue(String.valueOf(item.getValorunitario_compraproduto()));
                valortotal_compraproduto.setValue(String.valueOf(item.getValortotal_compraproduto()));
        numerolote_compraproduto.setValue(item.getNumerolote_compraproduto());
                datafabricacao_compraproduto.setValue(item.getDatafabricacao_compraproduto());
        datavalidade_compraproduto.setValue(item.getDatavalidade_compraproduto());
                datarecebimento_compraproduto.setValue(item.getDatarecebimento_compraproduto());
        responsavelrecebimento_compraproduto.setValue(item.getResponsavelrecebimento_compraproduto());

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





        Button saveBtn = new Button("Atualizar", eventbe -> {

            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setId_produto(id_produto.getValue().getId_produto());
                item.setQuantidade_compraproduto(quantidade_compraproduto.getValue());
                item.setValorunitario_compraproduto(service.getValorBigDecimal(valorunitario_compraproduto.getValue()));
                item.setValortotal_compraproduto(service.getValorBigDecimal(valortotal_compraproduto.getValue()));
                item.setNumerolote_compraproduto(numerolote_compraproduto.getValue());
                item.setDatafabricacao_compraproduto(datafabricacao_compraproduto.getValue());
                item.setDatavalidade_compraproduto(datavalidade_compraproduto.getValue());
                item.setDatarecebimento_compraproduto(datarecebimento_compraproduto.getValue());
                item.setResponsavelrecebimento_compraproduto(responsavelrecebimento_compraproduto.getValue());
                compraProdutoService.update(item);
                List<SetCompraProduto> listaProdutos = compraProdutoService.findByIdCompra(item.getId_compra());
                grid.setItems(listaProdutos);
                service.notificaSucesso("Atualizado com sucesso !");
                dialog.close();
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Adiciona os componentes ao layout
        formLayout.add( id_produto,
                quantidade_compraproduto,
                valorunitario_compraproduto,
                valortotal_compraproduto,
                numerolote_compraproduto,
                datafabricacao_compraproduto,
                datavalidade_compraproduto,
                datarecebimento_compraproduto,
                responsavelrecebimento_compraproduto);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();





    }
}
