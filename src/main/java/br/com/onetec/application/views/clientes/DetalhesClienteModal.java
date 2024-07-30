package br.com.onetec.application.views.clientes;

import br.com.onetec.application.data.Clientes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class DetalhesClienteModal extends Dialog {

    private TextField nomeField;
    private TextField telefoneField;
    private TextField enderecoField;
    private TextField internetField;
    private TextField aprovacaoField;

    public DetalhesClienteModal(Clientes cliente) {
        new Title("Detalhes do Cliente");

        nomeField = new TextField("Nome");
        telefoneField = new TextField("Telefone");
        enderecoField = new TextField("Endereço");
        internetField = new TextField("Internet");
        aprovacaoField = new TextField("Aprovação");

        // Preencher os campos com os dados do cliente
        nomeField.setValue(cliente.getNome());
        telefoneField.setValue(cliente.getFone());
        enderecoField.setValue(cliente.getEndereço());
        internetField.setValue("teste");
        aprovacaoField.setValue(cliente.getAprovação());

        // Desabilitar os campos para edição
        nomeField.setReadOnly(true);
        telefoneField.setReadOnly(true);
        enderecoField.setReadOnly(true);
        internetField.setReadOnly(true);
        aprovacaoField.setReadOnly(true);

        FormLayout formLayout = new FormLayout(nomeField, telefoneField, enderecoField, internetField, aprovacaoField);
        add(formLayout);

        Button closeButton = new Button("Fechar", event -> close());
        add(closeButton);
    }
}
