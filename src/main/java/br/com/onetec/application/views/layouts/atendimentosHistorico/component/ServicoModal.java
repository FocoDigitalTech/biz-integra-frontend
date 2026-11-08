package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.execucaoservico.ExecucaoServicoService;
import br.com.onetec.application.service.ordemservicoexecucaoservicoservice.OrdemServicoExecucaoServicoService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetExecucaoServico;
import br.com.onetec.infra.db.model.SetOrdemServicoExecucaoServico;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ServicoModal {


    public static void openServicoContato(SetOrdemServicoExecucaoServico item,
                                          ExecucaoServicoService execucaoServicoService,
                                          UtilitySystemConfigService service,
                                          Grid<SetOrdemServicoExecucaoServico> ordemServicoExecucaoGrid,
                                          OrdemServicoExecucaoServicoService ordemServicoExecucaoServicoService) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        ComboBox<SetExecucaoServico> id_execucaoservico = new ComboBox<>("Serviço");
        id_execucaoservico.setItems(execucaoServicoService.findAll());
        id_execucaoservico.setItemLabelGenerator(SetExecucaoServico::getNome_execucaoservico);
        TextField valor_ordemservicoexecucaoservico = new TextField("Valor");
        valor_ordemservicoexecucaoservico.setValueChangeMode(ValueChangeMode.EAGER);
        valor_ordemservicoexecucaoservico.addValueChangeListener(event -> service.formataMoedaBrasileira(valor_ordemservicoexecucaoservico));
        valor_ordemservicoexecucaoservico.setPlaceholder("R$ 0,00");
        TextField garantia_ordemservicoexecucaoservico = new TextField("Garantia");
        TextArea descricao_ordemservicoexecucaoservico = new TextArea("Descrição");


        //config form
        valor_ordemservicoexecucaoservico.setValue(
                item.getValor_ordemservicoexecucaoservico() != null ? String.valueOf(item.getValor_ordemservicoexecucaoservico()) : BigDecimal.ZERO.toString());
        descricao_ordemservicoexecucaoservico.setValue(
                item.getDescricao_ordemservicoexecucaoservico() != null ? item.getDescricao_ordemservicoexecucaoservico() : ""
        );
        garantia_ordemservicoexecucaoservico.setValue(
                item.getGarantia_ordemservicoexecucaoservico() != null ? item.getGarantia_ordemservicoexecucaoservico() : ""
        );
        id_execucaoservico.setValue(execucaoServicoService.findAll().stream()
                .filter(objeto -> objeto.getId_execucaoservico().equals(item.getId_execucaoservico()))
                .findFirst().orElse(null));

        Button saveBtn = new Button("Atualizar", eventbe -> {

            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setId_execucaoservico(id_execucaoservico.getValue().getId_execucaoservico());
                item.setDescricao_ordemservicoexecucaoservico(descricao_ordemservicoexecucaoservico.getValue());
                item.setGarantia_ordemservicoexecucaoservico(garantia_ordemservicoexecucaoservico.getValue());
                item.setValor_ordemservicoexecucaoservico(service.getValorBigDecimal
                        (valor_ordemservicoexecucaoservico.getValue()));
                item.setData_inclusao(LocalDateTime.now());
                item.setAtivo("S");
                item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());

                ordemServicoExecucaoServicoService.update(item);
                dialog.close();
                List<SetOrdemServicoExecucaoServico> listaservico = ordemServicoExecucaoServicoService.listAllByOrdemServicoId(item.getId_ordemservico());
                ordemServicoExecucaoGrid.setItems(listaservico);
                service.notificaSucesso("Atualizado com sucesso !");
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        // Adiciona os componentes ao layout
        formLayout.add(id_execucaoservico,
                valor_ordemservicoexecucaoservico,
                valor_ordemservicoexecucaoservico,
                garantia_ordemservicoexecucaoservico,
                descricao_ordemservicoexecucaoservico);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
