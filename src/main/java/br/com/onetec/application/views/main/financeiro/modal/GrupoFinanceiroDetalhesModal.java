package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.grupofinanceiroservice.GrupoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.GrupoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;


@Component
@UIScope
public class GrupoFinanceiroDetalhesModal extends Dialog {

    private TextField nome_grupoeventofinanceiro;
    private TextField descricao_grupoeventofinanceiro;

    @Autowired
    GrupoFinanceiroService grupoFinanceiroService;

    @Autowired
    @Lazy
    GrupoFinanceiroDiv grupoFinanceiroDiv;

    private Button saveButton;
    private Button cancelButton;
    private Button excluirButton;

    private SetGrupoFinanceiro grupoFinanceiro;



    public GrupoFinanceiroDetalhesModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Atualizar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            excluirButton = new Button("Excluir", event -> {
                deleta(grupoFinanceiro);
            });
            excluirButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(excluirButton, saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }


    private Div createFormCadastroEmpresa() {

        nome_grupoeventofinanceiro = new TextField("Nome");
        descricao_grupoeventofinanceiro = new TextField("Descrição");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_grupoeventofinanceiro,
                descricao_grupoeventofinanceiro);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetGrupoFinanceiro dto = grupoFinanceiro;
        dto.setNome_grupoeventofinanceiro(nome_grupoeventofinanceiro.getValue());
        dto.setDescricao_grupoeventofinanceiro(descricao_grupoeventofinanceiro.getValue());
        dto.setAtivo("S");
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            grupoFinanceiroService.update(dto);
            grupoFinanceiroDiv.refreshGrid();
            nome_grupoeventofinanceiro.clear();
            descricao_grupoeventofinanceiro.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    private void deleta(SetGrupoFinanceiro item) {
        try {
            grupoFinanceiroService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            grupoFinanceiroDiv.refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }


    public void setGrupoFinanceiro(SetGrupoFinanceiro item) {
        UI.getCurrent().access(() -> {
            this.grupoFinanceiro = item;
            if(Objects.nonNull(item.getNome_grupoeventofinanceiro()))
            nome_grupoeventofinanceiro.setValue(item.getNome_grupoeventofinanceiro());
            if(Objects.nonNull(item.getDescricao_grupoeventofinanceiro()))
            descricao_grupoeventofinanceiro.setValue(item.getDescricao_grupoeventofinanceiro());
        });
    }
}
