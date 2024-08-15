package br.com.onetec.application.views.main.administrativo.modal;


import br.com.onetec.application.model.Departamento;
import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IFuncionarioRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartamentoCadastroModal extends Dialog {

    //cadastro empresa
    private TextField codigoField;
    private TextField decricaoField;
    private ComboBox<SetFuncionario> responsavelield;

    private Button saveButton;
    private Button cancelButton;


    List<SetFuncionario> funcionarioList;


    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    @Lazy
    AdministrativoView administrativoView;


    public DepartamentoCadastroModal() {


        addClassName("cadastro-modal");
        saveButton = new Button("Salvar", eventbe -> save());
        cancelButton = new Button("Cancelar", event -> close());





        Div contentTabs = new Div(createFormCadastroEmpresa());
        contentTabs.setSizeFull();

        VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
        add(layout);

    }


    private Div createFormCadastroEmpresa() {
        //codigoField = new TextField("Código Departamento");
        decricaoField = new TextField("Nome ou Descrição");
        responsavelield = new ComboBox<>("Responsável");

        responsavelield.setItems(new ArrayList<>());
        responsavelield.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(decricaoField,
                       responsavelield);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }



    private void save() {
        SetFuncionario selectedFuncionario = responsavelield.getValue();
        // Lógica para salvar o cadastro
        Departamento dto = new Departamento();
        dto.setDescricao(decricaoField.getValue());
        if (selectedFuncionario != null) {
            dto.setResponsavel(selectedFuncionario.getId_funcionario());
        }

        departamentoService.cadastrar(dto);
        administrativoView.refreshGrid();
        close();
    }
}
