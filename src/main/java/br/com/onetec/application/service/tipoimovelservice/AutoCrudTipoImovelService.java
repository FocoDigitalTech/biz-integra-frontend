package br.com.onetec.application.service.tipoimovelservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetTipoImovel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudTipoImovelService {

    public void openFormDialog(ComboBox<SetTipoImovel> comboEnderecosTipoImovel, TipoImovelService tipoimovelService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        // Campos do formulário
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetTipoImovel situacaoCadastro = new SetTipoImovel();
                situacaoCadastro.setData_inclusao(LocalDateTime.now());
                situacaoCadastro.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                situacaoCadastro.setAtivo("S");
                situacaoCadastro.setDescricao_tipoimovel(descricaoField.getValue());
                tipoimovelService.save(situacaoCadastro);
                servico.notificaSucesso("Tipo de Imovel salvo: " + situacaoCadastro.getDescricao_tipoimovel());
                List<SetTipoImovel> novaLista = tipoimovelService.findAllImovel();
                comboEnderecosTipoImovel.setItems(novaLista);
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
