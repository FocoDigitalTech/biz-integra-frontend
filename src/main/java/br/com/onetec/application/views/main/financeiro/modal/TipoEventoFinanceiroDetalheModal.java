package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.TipoEventoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
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
public class TipoEventoFinanceiroDetalheModal extends Dialog {

    @Autowired
    TipoEventoFinanceiroService tipoEventoFinanceiroService;
    @Autowired
    @Lazy
    TipoEventoFinanceiroDiv tipoPagamentoDiv;
    UtilitySystemConfigService service;
    private TextField nome_tipoeventofinanceiro;
    private TextField descricao_tipoeventofinanceiro;
    private Button saveButton;
    private Button cancelButton;
    private Button excluirButton;
    private SetTipoEventoFinanceiro tipoEventoFinanceiro;


    public TipoEventoFinanceiroDetalheModal() {
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
                deleta(tipoEventoFinanceiro);
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
        SetTipoEventoFinanceiro dto = tipoEventoFinanceiro;
        dto.setNome_tipoeventofinanceiro(nome_tipoeventofinanceiro.getValue());
        dto.setDescricao_tipoeventofinanceiro(descricao_tipoeventofinanceiro.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            tipoEventoFinanceiroService.update(dto);
            tipoPagamentoDiv.refreshGrid();
            nome_tipoeventofinanceiro.clear();
            descricao_tipoeventofinanceiro.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void setTipoEventoFinanceiro(SetTipoEventoFinanceiro item) {
        UI.getCurrent().access(() -> {
            this.tipoEventoFinanceiro = item;
            if (Objects.nonNull(item.getNome_tipoeventofinanceiro()))
                nome_tipoeventofinanceiro.setValue(item.getNome_tipoeventofinanceiro());
            if (Objects.nonNull(item.getDescricao_tipoeventofinanceiro()))
                descricao_tipoeventofinanceiro.setValue(item.getDescricao_tipoeventofinanceiro());
        });
    }

    private void deleta(SetTipoEventoFinanceiro item) {
        try {
            tipoEventoFinanceiroService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            tipoPagamentoDiv.refreshGrid();
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }
}