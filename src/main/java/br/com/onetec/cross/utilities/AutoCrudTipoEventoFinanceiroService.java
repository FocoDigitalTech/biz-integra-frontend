package br.com.onetec.cross.utilities;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
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

public class AutoCrudTipoEventoFinanceiroService {

    public void openFormDialog(ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro,
                               TipoEventoFinanceiroService tipoEventoFinanceiroService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        TextField nomeField = new TextField("Nome");

        // Campos do formulário
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(nomeField, descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetTipoEventoFinanceiro dto = new SetTipoEventoFinanceiro();
                dto.setData_inclusao(LocalDateTime.now());
                dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                dto.setAtivo("S");
                dto.setNome_tipoeventofinanceiro(nomeField.getValue());
                dto.setDescricao_tipoeventofinanceiro(descricaoField.getValue());
                tipoEventoFinanceiroService.save(dto);
                servico.notificaSucesso("Tipo Evento Financeiro salvo: " + dto.getNome_tipoeventofinanceiro());
                List<SetTipoEventoFinanceiro> novaLista = tipoEventoFinanceiroService.findAll();
                id_tipoeventofinanceiro.setItems(novaLista);
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
        H2 title = new H2("Cadastro Tipo Evento Financeiro");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }
}
