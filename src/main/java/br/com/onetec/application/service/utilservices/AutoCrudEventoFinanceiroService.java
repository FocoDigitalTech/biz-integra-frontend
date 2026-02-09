package br.com.onetec.application.service.utilservices;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.eventofinanceiro.EventoFinanceiroService;
import br.com.onetec.application.service.tipoeventofinanceiroservice.TipoEventoFinanceiroService;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AutoCrudEventoFinanceiroService {
    public void openFormDialog(ComboBox<SetEventoFinanceiro> id_eventofinanceiro,
                               EventoFinanceiroService eventoFinanceiroService,
                               TipoEventoFinanceiroService grupoFinanceiroService) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        // Cria o diálogo
        Dialog dialog = new Dialog();

        // Layout do formulário
        FormLayout formLayout = new FormLayout();

        TextField nomeField = new TextField("Nome");
        ComboBox<SetTipoEventoFinanceiro> id_tipoeventofinanceiro = new ComboBox<>("Tipo Evento Financeiro (Contas)");
        id_tipoeventofinanceiro.setItems(grupoFinanceiroService.findAll());
        id_tipoeventofinanceiro.setItemLabelGenerator(SetTipoEventoFinanceiro::getNome_tipoeventofinanceiro);
        servico.setRequiredField(id_eventofinanceiro);

        // Campos do formulário
        TextField descricaoField = new TextField("Descrição");

        // Adiciona os campos ao layout do formulário
        formLayout.add(id_tipoeventofinanceiro, nomeField, descricaoField);

        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            try {
                if (Objects.isNull(id_tipoeventofinanceiro.getValue())) {
                    servico.notificaErro(ModalMessageConst.FIELD_ERROR);
                } else {
                    SetEventoFinanceiro dto = new SetEventoFinanceiro();
                    dto.setData_inclusao(LocalDateTime.now());
                    dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    dto.setAtivo("S");
                    dto.setNome_eventofinanceiro(nomeField.getValue());
                    dto.setObservacoes_eventofinanceiro(descricaoField.getValue());
                    if (Objects.nonNull(id_tipoeventofinanceiro.getValue())) {
                        dto.setId_tipoeventofinanceiro(id_tipoeventofinanceiro.getValue().getId_tipoeventofinanceiro());
                    }
                    eventoFinanceiroService.save(dto);
                    servico.notificaSucesso("Evento Financeiro salvo: " + dto.getNome_eventofinanceiro());
                    List<SetEventoFinanceiro> novaLista = eventoFinanceiroService.findAll();
                    id_eventofinanceiro.setItems(novaLista);
                    dialog.close();
                }
            } catch (Exception e) {
                servico.notificaErro("Por favor, preencha todos os campos obrigatórios.");
                e.printStackTrace();
            }
        });

        // Botão para fechar o diálogo
        Button closeButton = new Button("Fechar", event -> dialog.close());

        // Adiciona o formulário e os botões ao diálogo
        dialog.add(formLayout);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton, closeButton);
        H2 title = new H2("Cadastro Evento Financeiro");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }
}
