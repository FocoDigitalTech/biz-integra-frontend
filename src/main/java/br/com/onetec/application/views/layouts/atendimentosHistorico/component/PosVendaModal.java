package br.com.onetec.application.views.layouts.atendimentosHistorico.component;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.orcamentoposvendaservice.OrcamentoPosVendasService;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetOrcamentoPosVenda;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;

import java.time.LocalDateTime;
import java.util.List;

public class PosVendaModal {
    public static void openModalPosVenda(SetOrcamentoPosVenda item, OrcamentoPosVendasService orcamentoPosVendasService,
                                         UtilitySystemConfigService service, Grid<SetOrcamentoPosVenda> orcamentoposvendaGrid) {
        Dialog dialog = new Dialog();
        RadioButtonGroup<String> bomatendimento_orcamentoposvenda = new RadioButtonGroup("FOI BEM ATENDIDO ?");
        bomatendimento_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        RadioButtonGroup<String> funcionariosuniformizados_orcamentoposvenda = new RadioButtonGroup("ESTAVAM UNIFORMIZADOS E USAVAM CRACHÁ ?");
        funcionariosuniformizados_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        RadioButtonGroup<String> limpeza_orcamentoposvenda = new RadioButtonGroup("TRABALHARAM COM LIMPEZA E ORGANIZAÇÃO ?");
        limpeza_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        RadioButtonGroup<String> duvidas_orcamentoposvenda = new RadioButtonGroup("ESCLARECERAM TODAS AS DUVIDAS ?");
        duvidas_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        RadioButtonGroup<String> notegeral_orcamentoposvenda = new RadioButtonGroup("AVALIAÇÃO GERAL NOTA 0 ATÉ 5");
        notegeral_orcamentoposvenda.setItems(List.of("1", "2", "3", "4", "5"));

        RadioButtonGroup<String> utilizarianovamente_orcamentoposvenda = new RadioButtonGroup("UTILIZARIA NOVAMENTE OS SERVIÇOS ?");
        utilizarianovamente_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        RadioButtonGroup<String> sugestao_orcamentoposvenda = new RadioButtonGroup("TEM ALGUMA SUGESTÃO ?");
        sugestao_orcamentoposvenda.setItems(List.of("SIM", "NÃO"));

        TextArea descricaosugestao_orcamentoposvenda = new TextArea("Sugestões/ Observações");

        DatePicker data_orcamentoposvenda = new DatePicker("Data Ligação");

        bomatendimento_orcamentoposvenda.setValue(item.getBomatendimento_orcamentoposvenda());
        funcionariosuniformizados_orcamentoposvenda.setValue(item.getFuncionariosuniformizados_orcamentoposvenda());
        limpeza_orcamentoposvenda.setValue(item.getLimpeza_orcamentoposvenda());
        duvidas_orcamentoposvenda.setValue(item.getDuvidas_orcamentoposvenda());
        notegeral_orcamentoposvenda.setValue(String.valueOf(item.getNotegeral_orcamentoposvenda()));
        utilizarianovamente_orcamentoposvenda.setValue(item.getUtilizarianovamente_orcamentoposvenda());
        sugestao_orcamentoposvenda.setValue(item.getSugestao_orcamentoposvenda());
        descricaosugestao_orcamentoposvenda.setValue(item.getDescricaosugestao_orcamentoposvenda());
        data_orcamentoposvenda.setValue(item.getData_orcamentoposvenda());

        // RadioButtonGroup tipo_cobranca
        bomatendimento_orcamentoposvenda.setRequiredIndicatorVisible(true);
        bomatendimento_orcamentoposvenda.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                bomatendimento_orcamentoposvenda.setErrorMessage("Campo obrigatório");
                bomatendimento_orcamentoposvenda.setInvalid(true);
            } else {
                bomatendimento_orcamentoposvenda.setInvalid(false);
            }
        });

        notegeral_orcamentoposvenda.setRequiredIndicatorVisible(true);
        notegeral_orcamentoposvenda.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                notegeral_orcamentoposvenda.setErrorMessage("Campo obrigatório");
                notegeral_orcamentoposvenda.setInvalid(true);
            } else {
                notegeral_orcamentoposvenda.setInvalid(false);
            }
        });

        Button saveBtn = new Button("Atualizar", eventbe -> {


            item.setData_alteracao(LocalDateTime.now());
            item.setAtivo("S");
            item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            try {
                item.setBomatendimento_orcamentoposvenda(bomatendimento_orcamentoposvenda.getValue());
                item.setFuncionariosuniformizados_orcamentoposvenda(funcionariosuniformizados_orcamentoposvenda.getValue());
                item.setLimpeza_orcamentoposvenda(limpeza_orcamentoposvenda.getValue());
                item.setDuvidas_orcamentoposvenda(duvidas_orcamentoposvenda.getValue());
                item.setNotegeral_orcamentoposvenda(Integer.valueOf(notegeral_orcamentoposvenda.getValue()));
                item.setUtilizarianovamente_orcamentoposvenda(utilizarianovamente_orcamentoposvenda.getValue());
                item.setSugestao_orcamentoposvenda(sugestao_orcamentoposvenda.getValue());
                item.setDescricaosugestao_orcamentoposvenda(descricaosugestao_orcamentoposvenda.getValue());
                item.setData_orcamentoposvenda(data_orcamentoposvenda.getValue());
                item.setData_alteracao(LocalDateTime.now());
                item.setAtivo("S");
                item.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                orcamentoPosVendasService.update(item);
                dialog.close();
                List<SetOrcamentoPosVenda> listfrid = orcamentoPosVendasService.findAllByOrcamentoId(item.getId_orcamento());
                orcamentoposvendaGrid.setItems(listfrid);
                service.notificaSucesso("Atualizado com sucesso !");
            } catch (Exception e) {
                service.notificaErro("ERRO: Contate o Administrador !");
                e.printStackTrace();
            }
        });
        Button cancelBtn = new Button("Cancelar", event -> service.askForConfirmation(dialog));

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(bomatendimento_orcamentoposvenda,
                funcionariosuniformizados_orcamentoposvenda,
                limpeza_orcamentoposvenda,
                duvidas_orcamentoposvenda,
                notegeral_orcamentoposvenda,
                utilizarianovamente_orcamentoposvenda,
                sugestao_orcamentoposvenda,
                descricaosugestao_orcamentoposvenda,
                data_orcamentoposvenda);

        dialog.add(formLayout);
        dialog.getFooter().add(saveBtn, cancelBtn);
        dialog.open();
    }
}
