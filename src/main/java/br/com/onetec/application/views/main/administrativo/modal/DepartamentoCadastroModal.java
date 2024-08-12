package br.com.onetec.application.views.main.administrativo.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoCadastroModal extends Dialog {

    //cadastro empresa
    private TextField codigoField;
    private TextField decricaoField;
    private ComboBox responsavelield;

    private Button saveButton;
    private Button cancelButton;


    public DepartamentoCadastroModal() {
        addClassName("cadastro-modal");
        saveButton = new Button("Salvar", eventbe -> save());
        cancelButton = new Button("Cancelar", event -> close());




        Div contentTabs = new Div(createFormCadastroEmpresa());
        contentTabs.setSizeFull();

        VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
        add(layout);

    }
    private List<String> getItemsAdministradora() {
        List<String> lista = new ArrayList<>();
        lista.add("Danilo");
        lista.add("Carlos");
        lista.add("Fulano");
        return lista;
    }

    private Div createFormCadastroEmpresa() {
        codigoField = new TextField("Código Departamento");
        decricaoField = new TextField("Nome ou Descrição");
        responsavelield = new ComboBox("Responsável");
        responsavelield.setItems(getItemsAdministradora());

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(codigoField,
                       decricaoField,
                       responsavelield);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }



    private void save() {
        // Lógica para salvar o cadastro
        close();
    }
}
