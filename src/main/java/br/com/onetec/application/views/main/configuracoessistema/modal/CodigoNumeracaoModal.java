package br.com.onetec.application.views.main.configuracoessistema.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.codigonumeracaoservice.CodigoNumeracaoService;
import br.com.onetec.application.views.main.configuracoessistema.div.CodigoNumeracaoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCodigoNumeracao;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Component
@UIScope
public class CodigoNumeracaoModal extends Dialog {


    @Autowired
    CodigoNumeracaoService regiaoService;

    @Autowired
    @Lazy
    CodigoNumeracaoDiv regiaoDiv;
    private com.vaadin.flow.component.button.Button saveButton;
    private com.vaadin.flow.component.button.Button cancelButton;

    private SetCodigoNumeracao codigoNumeracao;
    private IntegerField orcamento_codigonumeracao;
    private IntegerField ordemservico_codigonumeracao;
    private IntegerField contrato_codigonumeracao;
    private IntegerField cliente_codigonumeracao;



    public CodigoNumeracaoModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Atualizar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
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


    private Div createFormCadastroEmpresa() {

        orcamento_codigonumeracao = new IntegerField("Código Orçamento");
        ordemservico_codigonumeracao = new IntegerField("Código OS");
        contrato_codigonumeracao = new IntegerField("Código Contrato");
        cliente_codigonumeracao = new IntegerField("Código Cliente");

        orcamento_codigonumeracao.setStepButtonsVisible(true);
        ordemservico_codigonumeracao.setStepButtonsVisible(true);
        contrato_codigonumeracao.setStepButtonsVisible(true);
        cliente_codigonumeracao.setStepButtonsVisible(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(orcamento_codigonumeracao,
                ordemservico_codigonumeracao,
                contrato_codigonumeracao,
                cliente_codigonumeracao);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        // Lógica para salvar o cadastro
        codigoNumeracao.setOrcamento_codigonumeracao(BigInteger.valueOf(orcamento_codigonumeracao.getValue()));
        codigoNumeracao.setContrato_codigonumeracao(BigInteger.valueOf(contrato_codigonumeracao.getValue()));
        codigoNumeracao.setOrdemservico_codigonumeracao(BigInteger.valueOf(ordemservico_codigonumeracao.getValue()));
        codigoNumeracao.setCliente_codigonumeracao(BigInteger.valueOf(cliente_codigonumeracao.getValue()));
        codigoNumeracao.setAtivo("S");
        codigoNumeracao.setData_alteracao(LocalDateTime.now());
        codigoNumeracao.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            regiaoService.update(codigoNumeracao);
            regiaoDiv.refreshGrid();
            orcamento_codigonumeracao.clear();
            ordemservico_codigonumeracao.clear();
            contrato_codigonumeracao.clear();
            cliente_codigonumeracao.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }



    public void setCodigo(SetCodigoNumeracao item) {
        this.codigoNumeracao = item;
        open();
        UI.getCurrent().access(() -> {
            orcamento_codigonumeracao.setValue(item.getOrcamento_codigonumeracao().intValue());
            ordemservico_codigonumeracao.setValue(item.getOrdemservico_codigonumeracao().intValue());
            contrato_codigonumeracao.setValue(item.getContrato_codigonumeracao().intValue());
            cliente_codigonumeracao.setValue(item.getCliente_codigonumeracao().intValue());

            orcamento_codigonumeracao.setMin(codigoNumeracao.getOrcamento_codigonumeracao().intValue());
            ordemservico_codigonumeracao.setMin(codigoNumeracao.getOrdemservico_codigonumeracao().intValue());
            contrato_codigonumeracao.setMin(codigoNumeracao.getContrato_codigonumeracao().intValue());
            cliente_codigonumeracao.setMin(codigoNumeracao.getCliente_codigonumeracao().intValue());
        });
    }
}