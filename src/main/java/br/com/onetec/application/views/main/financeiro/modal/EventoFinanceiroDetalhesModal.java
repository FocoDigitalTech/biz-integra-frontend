package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.EventoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
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

import java.util.List;
import java.util.Objects;

@Component
@UIScope
public class EventoFinanceiroDetalhesModal extends Dialog {

    @Autowired
    EventoFinanceiroService eventoFinanceiroService;
    @Autowired
    TipoEventoFinanceiroService tipoEventoFinanceiroService;
    @Autowired
    @Lazy
    EventoFinanceiroDiv eventoFinanceiroDiv;
    UtilitySystemConfigService service;
    private ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro;
    private TextField nome_eventofinanceiro;
    private TextField observacoes_eventofinanceiro;
    private Button saveButton;
    private Button cancelButton;
    private Button excluirButton;
    private SetEventoFinanceiro eventoFinanceiro;

    public EventoFinanceiroDetalhesModal() {
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
                deleta(eventoFinanceiro);
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

    private void deleta(SetEventoFinanceiro item) {
        try {
            eventoFinanceiroService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            eventoFinanceiroDiv.refreshGrid();
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private Div createFormCadastroEmpresa() {

        id_tipoeventofinanceiro = new ComboBox<>("Tipo Evento Financeiro (Contas)");
        nome_eventofinanceiro = new TextField("Nome");
        observacoes_eventofinanceiro = new TextField("Descrição");

        id_tipoeventofinanceiro.setItems(tipoEventoFinanceiroService.findAll());
        id_tipoeventofinanceiro.setItemLabelGenerator(SetTipoEventoFinanceiro::getNome_tipoeventofinanceiro);
        id_tipoeventofinanceiro.addFocusListener(event -> {
            id_tipoeventofinanceiro.setItems(tipoEventoFinanceiroService.findAll());
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

    private void save() throws Exception {
        SetEventoFinanceiro dto = eventoFinanceiro;
        SetTipoEventoFinanceiro setGrupoFinanceiro = id_tipoeventofinanceiro.getValue();
        if (setGrupoFinanceiro == null) {
            service.notificaErro("Tipo de evento financeiro (Contas) não pode ser vazio !");
        } else {
            dto.setId_tipoeventofinanceiro(setGrupoFinanceiro.getId_tipoeventofinanceiro());
            // Lógica para salvar o cadastro

            dto.setNome_eventofinanceiro(nome_eventofinanceiro.getValue());
            dto.setObservacoes_eventofinanceiro(observacoes_eventofinanceiro.getValue());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            service = new UtilitySystemConfigService();
            try {
                eventoFinanceiroService.update(dto);
                eventoFinanceiroDiv.refreshGrid();
                id_tipoeventofinanceiro.clear();
                nome_eventofinanceiro.clear();
                observacoes_eventofinanceiro.clear();
                service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
                close();
            } catch (Exception e) {
                service.notificaErro(ModalMessageConst.ERROR_CREATE);
            }
        }
    }

    public void setEventoFinanceiro(SetEventoFinanceiro item) {
        UI.getCurrent().access(() -> {
            this.eventoFinanceiro = item;
            List<SetTipoEventoFinanceiro> grupofinanceirolista = tipoEventoFinanceiroService.findAll();
            if (Objects.nonNull(item.getId_tipoeventofinanceiro())) {
                id_tipoeventofinanceiro.setValue(grupofinanceirolista.stream()
                        .filter(objeto -> objeto.getId_tipoeventofinanceiro().equals(item.getId_tipoeventofinanceiro()))
                        .findFirst().orElse(getUndefinedClassEntity()));
            }
            if (Objects.nonNull(item.getNome_eventofinanceiro()))
                nome_eventofinanceiro.setValue(item.getNome_eventofinanceiro());
            if (Objects.nonNull(item.getObservacoes_eventofinanceiro()))
                observacoes_eventofinanceiro.setValue(item.getObservacoes_eventofinanceiro());
        });
    }

    private SetTipoEventoFinanceiro getUndefinedClassEntity() {
        SetTipoEventoFinanceiro result = new SetTipoEventoFinanceiro();
        result.setId_tipoeventofinanceiro(0);
        result.setNome_tipoeventofinanceiro("Excluido");
        return result;
    }
}