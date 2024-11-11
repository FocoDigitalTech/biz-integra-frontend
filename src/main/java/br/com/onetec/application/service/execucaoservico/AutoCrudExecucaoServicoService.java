package br.com.onetec.application.service.execucaoservico;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetExecucaoServico;
import br.com.onetec.infra.db.model.SetTipoPagamento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudExecucaoServicoService {


    public void openFormDialog(ComboBox<SetExecucaoServico> id_execucaoservico,
                               ExecucaoServicoService execucaoServicoService) {
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
                SetExecucaoServico tipoPagamento = new SetExecucaoServico();
                tipoPagamento.setData_inclusao(LocalDateTime.now());
                tipoPagamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                tipoPagamento.setAtivo("S");
                tipoPagamento.setNome_execucaoservico(nomeField.getValue());
                tipoPagamento.setDescricao_execucaoservico(descricaoField.getValue());
                execucaoServicoService.save(tipoPagamento);
                servico.notificaSucesso("Tipo de Pagamento salvo: " + tipoPagamento.getNome_execucaoservico());
                List<SetExecucaoServico> novaLista = execucaoServicoService.findAll();
                id_execucaoservico.setItems(novaLista);
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
