package br.com.onetec.cross.utilities;


import br.com.onetec.application.service.condicaopagamentoservice.AutoCrudCondicaoPagamentoService;
import br.com.onetec.application.service.condicaopagamentoservice.CondicaoPagamentoService;
import br.com.onetec.application.service.execucaoservico.AutoCrudExecucaoServicoService;
import br.com.onetec.application.service.execucaoservico.ExecucaoServicoService;
import br.com.onetec.application.service.situacaocadastroservice.AutoCrudSituacaoCadastroService;
import br.com.onetec.application.service.situacaocadastroservice.SituacaoCadastroService;
import br.com.onetec.application.service.situacaopagamentoservice.AutoCrudSituacaoPagamentoService;
import br.com.onetec.application.service.situacaopagamentoservice.SituacaoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.AutoCrudTipoPagamentoService;
import br.com.onetec.application.service.tipopagamentoservice.TipoPagamentoService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CustomizedComboBox {

    private AutoCrudTipoPagamentoService autoCrudServiceImp;

    private AutoCrudExecucaoServicoService autoCrudExecucaoServicoService;

    private AutoCrudSituacaoPagamentoService autoCrudSituacaoPagamentoService;

    private AutoCrudCondicaoPagamentoService autoCrudCondicaoPagamentoService;

    private AutoCrudSituacaoCadastroService autoCrudSituacaoCadastroService;


    public HorizontalLayout customizeSituacaoCadastro
            (ComboBox<SetSituacaoCadastro> combo, SituacaoCadastroService servicoBean) {
        // Criação do botão com ícone de "plus"
        Button addButtonTipoPagamento = new Button(new Icon(VaadinIcon.PLUS));
        addButtonTipoPagamento.addClickListener(event -> {
            autoCrudSituacaoCadastroService = new AutoCrudSituacaoCadastroService();
            autoCrudSituacaoCadastroService.openFormDialog(combo, servicoBean);
        });
        addButtonTipoPagamento.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(combo, addButtonTipoPagamento);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        return horizontalLayout;
    }

    public HorizontalLayout customizeCondicaoPagamento
            (ComboBox<SetCondicaoPagamento> combo, CondicaoPagamentoService servicoBean) {
        // Criação do botão com ícone de "plus"
        Button addButtonTipoPagamento = new Button(new Icon(VaadinIcon.PLUS));
        addButtonTipoPagamento.addClickListener(event -> {
            autoCrudCondicaoPagamentoService = new AutoCrudCondicaoPagamentoService();
            autoCrudCondicaoPagamentoService.openFormDialog(combo, servicoBean);
        });
        addButtonTipoPagamento.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(combo, addButtonTipoPagamento);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        return horizontalLayout;
    }

    public HorizontalLayout customizeSituacaoPagamento
            (ComboBox<SetSituacaoPagamento> combo, SituacaoPagamentoService servicoBean) {
        // Criação do botão com ícone de "plus"
        Button addButtonTipoPagamento = new Button(new Icon(VaadinIcon.PLUS));
        addButtonTipoPagamento.addClickListener(event -> {
            autoCrudSituacaoPagamentoService = new AutoCrudSituacaoPagamentoService();
            autoCrudSituacaoPagamentoService.openFormDialog(combo, servicoBean);
        });
        addButtonTipoPagamento.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(combo, addButtonTipoPagamento);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        return horizontalLayout;
    }

    public HorizontalLayout customizeTipoPagamento
            (ComboBox<SetTipoPagamento> id_tipopagamento, TipoPagamentoService tipoPagamentoService) {
        // Criação do botão com ícone de "plus"
        Button addButtonTipoPagamento = new Button(new Icon(VaadinIcon.PLUS));
        addButtonTipoPagamento.addClickListener(event -> {
            autoCrudServiceImp = new AutoCrudTipoPagamentoService();
            autoCrudServiceImp.openFormDialog(id_tipopagamento, tipoPagamentoService);
        });
        addButtonTipoPagamento.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout id_tipopagamentolayout = new HorizontalLayout(id_tipopagamento, addButtonTipoPagamento);
        id_tipopagamentolayout.setAlignItems(FlexComponent.Alignment.END);
        return id_tipopagamentolayout;
    }

    public HorizontalLayout customizeExecucaoServico
            (ComboBox<SetExecucaoServico> id_execucaoservico, ExecucaoServicoService execucaoServicoService) {
        // Criação do botão com ícone de "plus"
        Button addButtonTipoPagamento = new Button(new Icon(VaadinIcon.PLUS));
        addButtonTipoPagamento.addClickListener(event -> {
            autoCrudExecucaoServicoService = new AutoCrudExecucaoServicoService();
            autoCrudExecucaoServicoService.openFormDialog(id_execucaoservico, execucaoServicoService);
        });
        addButtonTipoPagamento.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout id_tipopagamentolayout = new HorizontalLayout(id_execucaoservico, addButtonTipoPagamento);
        id_tipopagamentolayout.setAlignItems(FlexComponent.Alignment.END);
        return id_tipopagamentolayout;
    }

    public HorizontalLayout customizeEnderecosCEP(TextField fieldEnderecosCEP, Button buscaEnderecosCEPButton) {
        // Criação do botão com ícone de "plus"
        buscaEnderecosCEPButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(fieldEnderecosCEP, buscaEnderecosCEPButton);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        return horizontalLayout;
    }
}

