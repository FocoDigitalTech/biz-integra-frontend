package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.application.views.main.financeiro.div.TipopagamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetTipoPagamento;
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
public class TipoPagamentoDetalheModal extends Dialog {

    @Autowired
    TipoPagamentoService tipoPagamentoService;
    @Autowired
    @Lazy
    TipopagamentoDiv tipoPagamentoDiv;
    UtilitySystemConfigService service;
    private TextField nome_tipoeventofinanceiro;
    private TextField descricao_tipoeventofinanceiro;
    private Button saveButton;
    private Button cancelButton;
    private Button excluirButton;
    private SetTipoPagamento tipoPagamento;

    public TipoPagamentoDetalheModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            excluirButton = new Button("Excluir", event -> {
                deleta(tipoPagamento);
            });
            excluirButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);

            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton, excluirButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }

    private void deleta(SetTipoPagamento item) {
        try {
            tipoPagamentoService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            tipoPagamentoDiv.refreshGrid();
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private Div createFormCadastroEmpresa() {

        nome_tipoeventofinanceiro = new TextField("Nome");
        descricao_tipoeventofinanceiro = new TextField("Descrição");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_tipoeventofinanceiro,
                descricao_tipoeventofinanceiro);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetTipoPagamento dto = tipoPagamento;
        dto.setNome_tipopagamento(nome_tipoeventofinanceiro.getValue());
        dto.setDescricao_tipopagamento(descricao_tipoeventofinanceiro.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            tipoPagamentoService.update(dto);
            tipoPagamentoDiv.refreshGrid();
            nome_tipoeventofinanceiro.clear();
            descricao_tipoeventofinanceiro.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void setTipoPagamento(SetTipoPagamento item) {
        UI.getCurrent().access(() -> {
            this.tipoPagamento = item;
            if (Objects.nonNull(item.getNome_tipopagamento()))
                nome_tipoeventofinanceiro.setValue(item.getNome_tipopagamento());
            if (Objects.nonNull(item.getDescricao_tipopagamento()))
                descricao_tipoeventofinanceiro.setValue(item.getDescricao_tipopagamento());
        });
    }
}