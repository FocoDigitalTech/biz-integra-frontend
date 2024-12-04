package br.com.onetec.application.service.utilservices;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetSituacaoCadastro;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudEventoFinanceiroService {
    public void openFormDialog(ComboBox<SetEventoFinanceiro> id_eventofinanceiro,
                               EventoFinanceiroService eventoFinanceiroService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        TextField nomeField = new TextField("Nome");

        // Campos do formulário
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(nomeField,descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetEventoFinanceiro dto = new SetEventoFinanceiro();
                dto.setData_inclusao(LocalDateTime.now());
                dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                dto.setAtivo("S");
                dto.setNome_eventofinanceiro(nomeField.getValue());
                dto.setObservacoes_eventofinanceiro(descricaoField.getValue());
                eventoFinanceiroService.save(dto);
                servico.notificaSucesso("Evento Financeiro salvo: " + dto.getNome_eventofinanceiro());
                List<SetEventoFinanceiro> novaLista = eventoFinanceiroService.findAll();
                id_eventofinanceiro.setItems(novaLista);
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
        H2 title = new H2("Cadastro Evento Financeiro");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }
}
