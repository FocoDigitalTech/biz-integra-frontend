package br.com.onetec.application.service.condicaopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudCondicaoPagamentoService {

    public void openFormDialog(ComboBox<SetCondicaoPagamento> condicaoPagamentoComboBox,
                               CondicaoPagamentoService condicaoPagamentoService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        // Campos do formulário
        IntegerField intervalo = new IntegerField("Intervalo/Prazo");
        IntegerField quantidadeParcelas = new IntegerField("Quantidade Parcelas");
        TextField descricaoField = new TextField("Descrição");

        intervalo.setStepButtonsVisible(true);
        quantidadeParcelas.setStepButtonsVisible(true);

        // Adiciona os campos ao layout do formulário
        formLayout.add(descricaoField, quantidadeParcelas, intervalo);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetCondicaoPagamento condicaoPagamento = new SetCondicaoPagamento();
                condicaoPagamento.setData_inclusao(LocalDateTime.now());
                condicaoPagamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                condicaoPagamento.setAtivo("S");
                condicaoPagamento.setDescricao_condicaopagamento(descricaoField.getValue());
                condicaoPagamento.setPrazo_intervalo(intervalo.getValue().toString());
                condicaoPagamento.setQuantidade_parcelas(quantidadeParcelas.getValue().toString());
                condicaoPagamentoService.save(condicaoPagamento);
                servico.notificaSucesso("Tipo de Pagamento salvo: " + condicaoPagamento.getDescricao_condicaopagamento());
                List<SetCondicaoPagamento> novaLista = condicaoPagamentoService.listAll();
                condicaoPagamentoComboBox.setItems(novaLista);
                dialog.close();
            } catch (Exception e) {
                servico.notificaErro("Por favor, preencha todos os campos obrigatórios.");
                e.printStackTrace();
            }
        });

        // Botão para fechar o diálogo
        Button closeButton = new Button("Fechar", event -> dialog.close());

        // Adiciona o formulário e os botões ao diálogo
        dialog.add(formLayout);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton, closeButton);

        // Abre o diálogo
        dialog.open();
    }
}
