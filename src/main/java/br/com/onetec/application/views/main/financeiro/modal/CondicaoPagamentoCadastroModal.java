package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.views.main.financeiro.div.CondicaoPagamentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetRegiao;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class CondicaoPagamentoCadastroModal extends Dialog {

    private TextField descricao_condicaopagamento;
    private NumberField quantidade_parcelas;
    private NumberField prazo_dd;

    @Autowired
    CondicaoPagamentoService condicaoPagamentoService;

    @Autowired
    @Lazy
    CondicaoPagamentoDiv condicaoPagamentoDiv;
    private Button saveButton;
    private Button cancelButton;



    public CondicaoPagamentoCadastroModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            cancelButton = new Button("Cancelar", event -> close());
            Div contentTabs = new Div(createFormCadastroEmpresa());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }


    private Div createFormCadastroEmpresa() {

        descricao_condicaopagamento = new TextField("Nome");
        quantidade_parcelas = new NumberField("Quantidade de Parcelas");
        prazo_dd = new NumberField("Prazo DD");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(descricao_condicaopagamento,
                quantidade_parcelas,
                prazo_dd);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        SetCondicaoPagamento dto = new SetCondicaoPagamento();
        dto.setDescricao_condicaopagamento(descricao_condicaopagamento.getValue());
        dto.setQuantidade_parcelas(String.valueOf(quantidade_parcelas.getValue().intValue()));
        dto.setPrazo_dd(String.valueOf(prazo_dd.getValue().intValue()));
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            condicaoPagamentoService.save(dto);
            condicaoPagamentoDiv.refreshGrid();
            descricao_condicaopagamento.clear();
            quantidade_parcelas.clear();
            prazo_dd.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}
