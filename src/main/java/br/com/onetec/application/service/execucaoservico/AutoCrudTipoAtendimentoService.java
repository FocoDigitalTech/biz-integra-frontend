package br.com.onetec.application.service.execucaoservico;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.tipoatendimentoservice.TipoAtendimentoService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetTipoAtendimento;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudTipoAtendimentoService {

    public void openFormDialog(ComboBox<SetTipoAtendimento> id_tipoatendimento,
                               TipoAtendimentoService situacaoCadastroService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        TextField nomeField = new TextField("Nome");

        // Campos do formulário
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetTipoAtendimento dto = new SetTipoAtendimento();
                dto.setData_inclusao(LocalDateTime.now());
                dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                dto.setAtivo("S");
                dto.setDescricao_tipoatendimento(descricaoField.getValue());
                situacaoCadastroService.save(dto);
                servico.notificaSucesso("Tipo Atendimento salvo: " + dto.getDescricao_tipoatendimento());
                List<SetTipoAtendimento> novaLista = situacaoCadastroService.listAll();
                id_tipoatendimento.setItems(novaLista);
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
        H2 title = new H2("Cadastro Tipo Atendimento");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }
}
