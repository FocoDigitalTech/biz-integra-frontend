package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.orcamentocontatoservice.OrcamentoContatoService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.model.SetOrcamentoContato;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ContatoModal {
    public static void openModalContato(SetOrcamentoContato item, OrcamentoContatoService orcamentoContatoService,
                                        FuncionarioService funcionarioService, UtilitySystemConfigService service,
                                        Grid<SetOrcamentoContato> gridOrcamentoContato) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        DatePicker data_orcamentocontato = new DatePicker("Data Contato");
        TimePicker horario_orcamentocontato = new TimePicker("Hora");
        TextField nome_orcamentocontato = new TextField("Nome Contato");
        ComboBox<SetFuncionario> id_funcionarioContato = new ComboBox<>("Funcionario");
        DatePicker dataretorno_orcamentocontato = new DatePicker("Data Retorno");
        TextArea descricao_orcamentocontato = new TextArea("O que foi contatado ?");

        id_funcionarioContato.setItems(funcionarioService.listAll());
        id_funcionarioContato.setItemLabelGenerator(SetFuncionario::getNome_funcionario);

        //config form
        data_orcamentocontato.setValue
                (item.getData_orcamentocontato());
        horario_orcamentocontato.setValue(
                item.getHorario_orcamentocontato());
        nome_orcamentocontato.setValue(
                item.getNome_orcamentocontato() != null? item.getNome_orcamentocontato() : ""
        );
        id_funcionarioContato.setValue(funcionarioService.listAll().stream()
                .filter(objeto -> objeto.getId_funcionario().equals(item.getId_funcionario()))
                .findFirst().orElse(null));
        dataretorno_orcamentocontato.setValue(item.getDataretorno_orcamentocontato());
        descricao_orcamentocontato.setValue(item.getDescricao_orcamentocontato() != null?
                item.getDescricao_orcamentocontato() : ""
        );

        Button saveBtn = new Button("Atualizar", eventbe -> {

            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setData_orcamentocontato(data_orcamentocontato.getValue());
                item.setHorario_orcamentocontato(horario_orcamentocontato.getValue());
                item.setNome_orcamentocontato(nome_orcamentocontato.getValue());
                if(Objects.nonNull(id_funcionarioContato.getValue())) {
                    item.setId_funcionario(id_funcionarioContato.getValue().getId_funcionario());
                }
                item.setDataretorno_orcamentocontato(dataretorno_orcamentocontato.getValue());
                item.setDescricao_orcamentocontato(descricao_orcamentocontato.getValue());
                orcamentoContatoService.update(item);
                dialog.close();
                List<SetOrcamentoContato> orcamentoContato = orcamentoContatoService.findAllByOrcamentoId(item.getId_orcamento());
                gridOrcamentoContato.setItems(orcamentoContato);
                service.notificaSucesso("Atualizado com sucesso !");
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        // Adiciona os componentes ao layout
        formLayout.add( data_orcamentocontato,
                horario_orcamentocontato,
                nome_orcamentocontato,
                id_funcionarioContato,
                dataretorno_orcamentocontato,
                descricao_orcamentocontato);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
