package br.com.onetec.application.service.situacaopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetSituacaoPagamento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudSituacaoPagamentoService {

    public void openFormDialog(ComboBox<SetSituacaoPagamento> id_tipopagamento,
                               SituacaoPagamentoService situacaoPagamentoService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        // Campos do formulário
        TextField nomeField = new TextField("Nome");
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(nomeField, descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetSituacaoPagamento setSituacaoPagamento = new SetSituacaoPagamento();
                setSituacaoPagamento.setData_inclusao(LocalDateTime.now());
                setSituacaoPagamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                setSituacaoPagamento.setAtivo("S");
                setSituacaoPagamento.setNome_situacaopagamento(nomeField.getValue());
                setSituacaoPagamento.setDescricao_situacaopagamento(nomeField.getValue());
                situacaoPagamentoService.save(setSituacaoPagamento);
                servico.notificaSucesso("Tipo de Pagamento salvo: " + setSituacaoPagamento.getNome_situacaopagamento());
                List<SetSituacaoPagamento> novaLista = situacaoPagamentoService.listAll();
                id_tipopagamento.setItems(novaLista);
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
