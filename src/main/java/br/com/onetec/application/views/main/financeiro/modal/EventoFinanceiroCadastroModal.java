package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.grupofinanceiroservice.GrupoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.EventoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.*;
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

import java.time.LocalDateTime;

@Component
@UIScope
public class EventoFinanceiroCadastroModal  extends Dialog {

    private ComboBox<SetGrupoFinanceiro> id_grupoeventofinanceiro;
    private TextField nome_eventofinanceiro;
    private TextField observacoes_eventofinanceiro;

    @Autowired
    EventoFinanceiroService eventoFinanceiroService;

    @Autowired
    GrupoFinanceiroService grupoFinanceiroService;

    @Autowired
    @Lazy
    EventoFinanceiroDiv eventoFinanceiroDiv;
    private Button saveButton;
    private Button cancelButton;


    public EventoFinanceiroCadastroModal() {
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

        id_grupoeventofinanceiro = new ComboBox<>("Grupo Financeiro (Planos)");
        nome_eventofinanceiro = new TextField("Nome");
        observacoes_eventofinanceiro = new TextField("Descrição");

        id_grupoeventofinanceiro.setItems(grupoFinanceiroService.findAll());
        id_grupoeventofinanceiro.setItemLabelGenerator(SetGrupoFinanceiro::getNome_grupoeventofinanceiro);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_grupoeventofinanceiro,
                nome_eventofinanceiro,
                observacoes_eventofinanceiro);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        SetEventoFinanceiro dto = new SetEventoFinanceiro();
        SetGrupoFinanceiro setGrupoFinanceiro = id_grupoeventofinanceiro.getValue();
        if (setGrupoFinanceiro == null) {
            service.notificaErro("Grupo financeiro não pode ser vazio !");
            throw new Exception();
        }
        dto.setId_grupoeventofinanceiro(setGrupoFinanceiro.getId_grupoeventofinanceiro());
        // Lógica para salvar o cadastro

        dto.setNome_eventofinanceiro(nome_eventofinanceiro.getValue());
        dto.setObservacoes_eventofinanceiro(observacoes_eventofinanceiro.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            eventoFinanceiroService.save(dto);
            eventoFinanceiroDiv.refreshGrid();
            id_grupoeventofinanceiro.clear();
            nome_eventofinanceiro.clear();
            observacoes_eventofinanceiro.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}
