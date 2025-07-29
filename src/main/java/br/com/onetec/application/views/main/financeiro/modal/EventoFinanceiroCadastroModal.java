package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.grupofinanceiroservice.GrupoFinanceiroService;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.EventoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
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

    private ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro;
    private TextField nome_eventofinanceiro;
    private TextField observacoes_eventofinanceiro;

    @Autowired
    EventoFinanceiroService eventoFinanceiroService;

    @Autowired
    TipoEventoFinanceiroService grupoFinanceiroService;

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

        id_tipoeventofinanceiro = new ComboBox<>("Tipo Evento Financeiro (Contas)");
        nome_eventofinanceiro = new TextField("Nome");
        observacoes_eventofinanceiro = new TextField("Descrição");

        id_tipoeventofinanceiro.setItems(grupoFinanceiroService.findAll());
        id_tipoeventofinanceiro.setItemLabelGenerator(SetTipoEventoFinanceiro::getNome_tipoeventofinanceiro);
        id_tipoeventofinanceiro.addFocusListener(event -> {
            id_tipoeventofinanceiro.setItems(grupoFinanceiroService.findAll());
        });

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_tipoeventofinanceiro,
                nome_eventofinanceiro,
                observacoes_eventofinanceiro);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }


    UtilitySystemConfigService service;

    private void save() throws Exception {
        SetEventoFinanceiro dto = new SetEventoFinanceiro();
        SetTipoEventoFinanceiro setGrupoFinanceiro = id_tipoeventofinanceiro.getValue();
        if (setGrupoFinanceiro == null) {
            service.notificaErro("Tipo de evento financeiro (Contas) não pode ser vazio !");
        } else {
            dto.setId_tipoeventofinanceiro(setGrupoFinanceiro.getId_tipoeventofinanceiro());
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
                id_tipoeventofinanceiro.clear();
                nome_eventofinanceiro.clear();
                observacoes_eventofinanceiro.clear();
                service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
                close();
            } catch (Exception e) {
                service.notificaErro(ModalMessageConst.ERROR_CREATE);
            }
        }
    }
}
