package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.model.Departamento;
import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.layouts.notificationAlert.NotificationForm;
import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@UIScope
public class DepartamentoDetalhesModal extends Dialog {

    //campos
    private TextField codigoField;
    private TextField decricaoField;
    private ComboBox<SetFuncionario> responsavelield;

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

    @Autowired
    @Lazy
    AdministrativoView administrativoView;


    private SetDepartamento departamento;


    private UtilitySystemConfigService service;

    public DepartamentoDetalhesModal() {
        UI.getCurrent().access(() -> {

            service = new UtilitySystemConfigService();
            saveButton = new Button("Atualizar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));


            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });

    }

    private void save() {
        SetFuncionario selectedFuncionario = responsavelield.getValue();
        // Lógica para salvar o cadastro
        Departamento dto = new Departamento();
        dto.setDescricao(decricaoField.getValue());
        if (selectedFuncionario != null) {
            dto.setResponsavel(selectedFuncionario.getId_funcionario());
        }

        try {
            departamentoService.atualizar(dto, departamento.getId_departamento());
            administrativoView.refreshGrid();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            service.notificaSucesso(ModalMessageConst.ERROR_CREATE);
        }
        close();
    }

    private Div createFormCadastroEmpresa() {
        //codigoField = new TextField("Código Departamento");
        decricaoField = new TextField("Nome ou Descrição");
        responsavelield = new ComboBox<>("Responsável");

        responsavelield.setItems(funcionarioService.listAll());
        responsavelield.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

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
        try {
            departamentoService.deletar(departamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
        new NotificationForm().showSuccessNotification("Deletado com sucesso");
    }


    public void setDepartamento(SetDepartamento item) {
        UI.getCurrent().access(() -> {
            this.departamento = item;
            if(Objects.nonNull(item.getId_departamento()))
          //  codigoField.setValue(item.getId_departamento().toString());
            if(Objects.nonNull(item.getDescricao_departamento()))
            decricaoField.setValue(item.getDescricao_departamento());
            List<SetFuncionario> funcionarioLista = funcionarioService.listAll();
            if(Objects.nonNull(item.getId_funcionario()))
            responsavelield.setValue(funcionarioLista.stream()
                    .filter(objeto -> objeto.getId_funcionario().equals(item.getId_funcionario()))
                    .findFirst().orElse(null));
        });
    }
}
