package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.model.Departamento;
import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.layouts.notificationAlert.NotificationForm;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UIScope
public class DepartamentoDetalhesModal extends Dialog {

    //campos
    private TextField codigoField;
    private TextField decricaoField;
    private ComboBox responsavelield;

    //botoes
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;

    String selectedValue;


    //Injecao do servico
    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    FuncionarioService funcionarioService;


    private SetDepartamento departamento;



    public DepartamentoDetalhesModal() {
        UI.getCurrent().access(() -> {
            SetDepartamento departamento = new SetDepartamento();
            addClassName("cadastro-modal");
            saveButton = new Button("Atualizar", eventbe -> update());
            cancelButton = new Button("Cancelar", event -> close());
            deleteButton = new Button("Excluir", event -> delete(departamento));


            Div contentTabs = new Div(createForm(departamento));
            contentTabs.setSizeFull();

            VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton, deleteButton);
            add(layout);
        });

    }


    private Div createForm(SetDepartamento departamento) {
        codigoField = new TextField("Código Departamento");
        decricaoField = new TextField("Nome ou Descrição");
        responsavelield = new ComboBox("Responsável");
        responsavelield.setItems(getFuncionarioNome(departamento.getId_funcionario()));


        codigoField.setValue(String.valueOf(departamento.getId_departamento()));
        decricaoField.setValue(departamento.getDescricao_departamento());

        responsavelield.addValueChangeListener(event -> {
            selectedValue = (String) event.getValue();
        });


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(decricaoField,
                responsavelield);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    List<SetFuncionario> funcionarios = new ArrayList<>();



    private List<String> getFuncionarioNome(Integer id_funcionario) {
        List<String> nomes = new ArrayList<>();
        funcionarios = funcionarioService.listAll();
        if (funcionarios.size() > 0) {
            funcionarios.forEach(f -> {
                nomes.add(f.getNome_funcionario());
            });
            if (id_funcionario != null && id_funcionario > 0) {
                Optional<SetFuncionario> foundFuncionario = funcionarios.stream()
                        .filter(funcionario -> funcionario.getId_funcionario()
                                == id_funcionario)
                        .findFirst();
                nomes.remove(foundFuncionario.get().getNome_funcionario());
                nomes.add(0, foundFuncionario.get().getNome_funcionario());
            } else {
                nomes.add(0, "");
            }
        }
        return nomes;
    }


    private void update() {
        // Lógica para atualizar o cadastro
        Departamento dto = new Departamento();
        dto.setDescricao(decricaoField.getValue());
        dto.setCodigo(Integer.valueOf(codigoField.getValue()));
        departamentoService.atualizar(dto);
        close();
    }

    private Integer getFuncionarioId() {

        Optional<SetFuncionario> foundFuncionario = funcionarios.stream()
                .filter(funcionario -> funcionario.getNome_funcionario().equals
                        (selectedValue))
                .findFirst();
        if (foundFuncionario.isPresent()){
            return foundFuncionario.get().getId_funcionario();
        } else {
            return 0;
        }
    }

    private void delete(SetDepartamento departamento) {
        // Lógica para deletar o cadastro
        departamentoService.deletar(departamento);
        close();
        new NotificationForm().showSuccessNotification("Deletado com sucesso");
    }




}
