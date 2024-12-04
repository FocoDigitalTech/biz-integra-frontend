package br.com.onetec.application.views.main.financeiro.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.grupofinanceiroservice.GrupoFinanceiroService;
import br.com.onetec.application.views.main.financeiro.div.EventoFinanceiroDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
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
import java.util.List;
import java.util.Objects;

@Component
@UIScope
public class EventoFinanceiroDetalhesModal extends Dialog {

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
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
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
        SetEventoFinanceiro dto = eventoFinanceiro;
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
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        service = new UtilitySystemConfigService();
        try {
            eventoFinanceiroService.update(dto);
            eventoFinanceiroDiv.refreshGrid();
            id_grupoeventofinanceiro.clear();
            nome_eventofinanceiro.clear();
            observacoes_eventofinanceiro.clear();
            service.notificaSucesso(ModalMessageConst.UPDATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }

    public void setEventoFinanceiro(SetEventoFinanceiro item) {
        UI.getCurrent().access(() -> {
            this.eventoFinanceiro = item;
            List<SetGrupoFinanceiro> grupofinanceirolista = grupoFinanceiroService.findAll();
            if (Objects.nonNull(item.getId_grupoeventofinanceiro())) {
                id_grupoeventofinanceiro.setValue(grupofinanceirolista.stream()
                        .filter(objeto -> objeto.getId_grupoeventofinanceiro().equals(item.getId_grupoeventofinanceiro()))
                        .findFirst().orElse(null));
            }
            if (Objects.nonNull(item.getNome_eventofinanceiro()))
                nome_eventofinanceiro.setValue(item.getNome_eventofinanceiro());
            if (Objects.nonNull(item.getObservacoes_eventofinanceiro()))
                observacoes_eventofinanceiro.setValue(item.getObservacoes_eventofinanceiro());
        });
    }
}