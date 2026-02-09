package br.com.onetec.cross.utilities;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.contacorrenteservice.ContaCorrenteService;
import br.com.onetec.infra.db.model.SetContaCorrente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudContaCorrenteService {

    private TextField nome_contacorrente;
    private TextField banco_contacorrente;
    private TextField agencia_contacorrente;
    private TextField numero_contacorrente;
    private TextField limete_contacorrente;
    private DatePicker ultimolancamento_contacorrente;

    public void openFormDialog(ComboBox<SetContaCorrente> id_contacorrente,
                               ContaCorrenteService contaCorrenteService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();


        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        nome_contacorrente = new TextField("Nome Conta");
        banco_contacorrente = new TextField("Banco");
        agencia_contacorrente = new TextField("Agencia");
        numero_contacorrente = new TextField("Numero");
        limete_contacorrente = new TextField("Limite");
        ultimolancamento_contacorrente = new DatePicker("Data Ultimo Lançamento");
        servico.configuraCalendario(ultimolancamento_contacorrente);

        // Adiciona os campos ao layout do formulário
        formLayout.add(nome_contacorrente,
                banco_contacorrente,
                agencia_contacorrente,
                numero_contacorrente,
                limete_contacorrente,
                ultimolancamento_contacorrente);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                SetContaCorrente dto = new SetContaCorrente();
                dto.setData_inclusao(LocalDateTime.now());
                dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                dto.setAtivo("S");
                dto.setNome_contacorrente(nome_contacorrente.getValue());
                dto.setBanco_contacorrente(banco_contacorrente.getValue());
                dto.setAgencia_contacorrente(agencia_contacorrente.getValue());
                dto.setNumero_contacorrente(numero_contacorrente.getValue());
                dto.setLimete_contacorrente(servico.getValorBigDecimal(limete_contacorrente.getValue()));
                dto.setUltimolancamento_contacorrente(ultimolancamento_contacorrente.getValue().atStartOfDay());
                contaCorrenteService.save(dto);
                servico.notificaSucesso("Conta Corrente Salva salvo: " + dto.getNome_contacorrente());
                List<SetContaCorrente> novaLista = contaCorrenteService.findAll();
                id_contacorrente.setItems(novaLista);
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
        H2 title = new H2("Cadastro Conta Corrente");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }
}
