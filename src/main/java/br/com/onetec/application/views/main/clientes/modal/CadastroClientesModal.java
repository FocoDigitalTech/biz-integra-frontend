package br.com.onetec.application.views.main.clientes.modal;

import br.com.onetec.application.model.Cliente;
import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.*;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CadastroClientesModal extends Dialog {

    //cadastro empresa
    private TextField nomeField;
    private TextField telefoneField;
    private TextField celularField;
    private DatePicker dataField;
    private TimePicker horaField;
    private TextField contatoField;
    private ComboBox administradora;
    private ComboBox tipoMidia;
    private TextField nomeIndicacaoField;
    private TextField faxField;
    private TextField internetEmailField;
    private TextField FJField;
    private TextField CGCCPFField;
    private TextField inscEstatualField;
    private TextField observacaoField;
    //Agendamento
    private TextField nomeAgendamentoField;
    private TextField contatoAgendamentoField;
    private TextField telefoneAgendamentoField;
    private TextField faxAgendamentoField;
    private TextField celularAgendamentoField;
    private TextField internetEmailAgendamentoField;
    private TextField CGCCPFAgendamentoField;
    private TextField inscEstatualAgendamentoField;
    private TextField observacaoAgendamentoField;
    //Aprovacao
    private TextField nomeAprovacaoField;
    private TextField contatoAprovacaoField;
    private TextField telefoneAprovacaoField;
    private TextField faxAprovacaoField;
    private TextField celularAprovacaoField;
    private TextField internetEmailAprovacaoField;
    private TextField CGCCPFAprovacaoField;
    private TextField inscEstatualAprovacaoField;
    private TextField observacaoAprovacaoField;
    //Cobranca
    private TextField nomeCobrancaField;
    private TextField contatoCobrancaField;
    private TextField telefoneCobrancaField;
    private TextField faxCobrancaField;
    private TextField celularCobrancaField;
    private TextField internetEmailCobrancaField;
    private TextField CGCCPFCobrancaField;
    private TextField inscEstatualCobrancaField;
    private TextField observacaoCobrancaField;
    //Enderecos
    private TextField fieldEnderecosCEP;
    private ComboBox comboEnderecosTipoImovel;
    private TextField fieldEnderecosArea;
    private TextField fieldEnderecosEndereço;
    private TextField fieldEnderecosNumero;
    private TextField fieldEnderecosComplemento;
    private TextField fieldEnderecosBairro;
    private TextField fieldEnderecosCidade;
    private ComboBox<SetEstado> comboEnderecosUF;
    private TextField fieldEnderecosTelefone;
    private TextField fieldEnderecosPagGuia;
    private TextField fieldEnderecosReponsavel;
    private ComboBox comboEnderecosRegiao;
    private TextField fieldEnderecosPontodeReferencia;

    private Tabs tabs;
    private Div cadastroEmpresa;
    private Div cadastroAgendamento;
    private Div cadastroAprovacao;
    private Div cadastroCobranca;
    private Div cadastroEnderecos;
    private Button saveButton;
    private Button cancelButton;

    private final ClientesService clientesService;
    private final EstadoService estadoService;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;

    private final ResponsavelCobrancaService responsavelCobrancaService;
    private final ResponsavelAgendamentoService responsavelAgendamentoService;
    private final ResponsavelAprovacaoService responsavelAprovacaoService;

    public CadastroClientesModal(ClientesService clientesService,
                                 EstadoService estadoService,
                                 UsuarioService usuarioService,
                                 EnderecoService enderecoService,
                                 ResponsavelCobrancaService responsavelCobrancaService,
                                 ResponsavelAgendamentoService responsavelAgendamentoService,
                                 ResponsavelAprovacaoService responsavelAprovacaoService) {
        this.clientesService = clientesService;
        this.estadoService = estadoService;
        this.usuarioService = usuarioService;
        this.enderecoService =  enderecoService;
        this.responsavelCobrancaService = responsavelCobrancaService;
        this.responsavelAgendamentoService = responsavelAgendamentoService;
        this.responsavelAprovacaoService = responsavelAprovacaoService;

        addClassName("cadastro-modal");
        saveButton = new Button("Salvar", eventbe -> save());
        cancelButton = new Button("Cancelar", event -> close());
        tabs = new Tabs();
        Tab tab1 = new Tab("Cadastro Empresa");
        Tab tab2 = new Tab("Agendamento");
        Tab tab3 = new Tab("Aprovação");
        Tab tab4 = new Tab("Cobrança");
        Tab tab5 = new Tab("Endereços");

        tabs.add(tab1, tab2, tab3, tab4, tab5);
        cadastroEmpresa = createFormCadastroEmpresa();
        cadastroAgendamento = createFormCadastroAgendamento();
        cadastroAprovacao = createFormCadastroAprovacao();
        cadastroEnderecos = createFormEnderecos();
        cadastroCobranca = createFormCadastroCobranca();

        Div content = new Div(cadastroEmpresa, cadastroAgendamento, cadastroAprovacao,cadastroEnderecos);
        content.setSizeFull();
        cadastroEmpresa.setVisible(true);
        cadastroAgendamento.setVisible(false);
        cadastroAprovacao.setVisible(false);
        cadastroEnderecos.setVisible(false);
        cadastroCobranca.setVisible(false);

        tabs.addSelectedChangeListener(event -> {
            cadastroEmpresa.setVisible(false);
            cadastroAgendamento.setVisible(false);
            cadastroAprovacao.setVisible(false);
            cadastroEnderecos.setVisible(false);
            cadastroCobranca.setVisible(false);

            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab.equals(tab1)) {
                cadastroEmpresa.setVisible(true);
            } else if (selectedTab.equals(tab2)) {
                cadastroAgendamento.setVisible(true);
            } else if (selectedTab.equals(tab3)) {
                cadastroAprovacao.setVisible(true);
            }   else if (selectedTab.equals(tab4)) {
                cadastroCobranca.setVisible(true);
            }   else if (selectedTab.equals(tab5)) {
                cadastroEnderecos.setVisible(true);
            }
        });VerticalLayout section2 = createSectionCobranca("Cadastro Cobrança");

        Div contentTabs = new Div(cadastroEmpresa, cadastroAgendamento, cadastroAprovacao,cadastroEnderecos,cadastroCobranca);
        contentTabs.setSizeFull();

        VerticalLayout layout = new VerticalLayout(tabs,contentTabs, saveButton, cancelButton);
        add(layout);

    }

    private List<Endereco> enderecos = new ArrayList<>();
    private Grid<Endereco> grid = new Grid<>(Endereco.class, false);



    private Div createFormEnderecos() {
        Div div = new Div();

        fieldEnderecosCEP = new TextField("CEP");
        comboEnderecosTipoImovel  = new ComboBox("Tipo de Imóvel");;
        comboEnderecosTipoImovel.setItems(getItemsAdministradora());
        fieldEnderecosArea = new TextField("Area");
        fieldEnderecosEndereço = new TextField("Endereço");
        fieldEnderecosNumero = new TextField("Número");
        fieldEnderecosComplemento = new TextField("Complemento");
        fieldEnderecosBairro = new TextField("Bairro");
        fieldEnderecosCidade = new TextField("Cidade");
        comboEnderecosUF  = new ComboBox("UF");;
        comboEnderecosUF.setItems(getUFList());
        comboEnderecosUF.setItemLabelGenerator(SetEstado::getNome_estado);
        fieldEnderecosTelefone = new TextField("Telefone do Local");
        fieldEnderecosPagGuia = new TextField("Pag. Guia");
        fieldEnderecosReponsavel = new TextField("Responsável");
        comboEnderecosRegiao  = new ComboBox("Região");;
        comboEnderecosRegiao.setItems(getItemsAdministradora());
        fieldEnderecosPontodeReferencia = new TextField("Ponto de Referencia");

        // Configurar o Grid
        //grid.setColumns("Tipo de Imóvel","Endereço","N°","Bairro","CEP","Cidade","Estado","Fone");
        grid.setItems(enderecos);

        grid.addColumn("comboEnderecosTipoImovel").setHeader("TipoImovel").setAutoWidth(true);
        grid.addColumn("fieldEnderecosEndereço").setHeader("Endereço").setAutoWidth(true);
        grid.addColumn("fieldEnderecosNumero").setHeader("Numero").setAutoWidth(true);
        grid.addColumn("fieldEnderecosBairro").setHeader("Bairro").setAutoWidth(true);
        grid.addColumn("fieldEnderecosCEP").setHeader("CEP").setAutoWidth(true);
        grid.addColumn("fieldEnderecosCidade").setHeader("Cidade").setAutoWidth(true);
        grid.addColumn("comboEnderecosUF").setHeader("UF").setAutoWidth(true);
        grid.addColumn("fieldEnderecosTelefone").setHeader("Telefone").setAutoWidth(true);

        // Campos de texto para entrada de dados
        fieldEnderecosCEP.setPlaceholder("Digite");
        comboEnderecosTipoImovel.setPlaceholder("Digite");
        fieldEnderecosArea.setPlaceholder("Digite");
        fieldEnderecosEndereço.setPlaceholder("Digite");
        fieldEnderecosNumero.setPlaceholder("Digite");
        fieldEnderecosComplemento.setPlaceholder("Digite");
        fieldEnderecosBairro.setPlaceholder("Digite");
        fieldEnderecosCidade.setPlaceholder("Digite");
        comboEnderecosUF.setPlaceholder("Digite");
        fieldEnderecosTelefone.setPlaceholder("Digite");
        fieldEnderecosPagGuia.setPlaceholder("Digite");
        fieldEnderecosReponsavel.setPlaceholder("Digite");
        comboEnderecosRegiao.setPlaceholder("Digite");
        fieldEnderecosPontodeReferencia.setPlaceholder("Digite");

//        Div estadoError = new Div();
//        estadoError.addClassName("error-message");
//        estadoError.setText("Campo obrigatório");
//        estadoError.setVisible(false);

        // Botão para salvar o endereço
        Button saveButton = new Button("Adicionar Endereço", event -> {
            String CEP = fieldEnderecosCEP.getValue();
            String TipoImovel = comboEnderecosTipoImovel.toString();
            String Area = fieldEnderecosArea.getValue();
            String Endereço = fieldEnderecosEndereço.getValue();
            String Numero = fieldEnderecosNumero.getValue();
            String Complemento = fieldEnderecosComplemento.getValue();
            String Bairro = fieldEnderecosBairro.getValue();
            String Cidade = fieldEnderecosCidade.getValue();
            SetEstado UF = comboEnderecosUF.getValue();
            String Telefone = fieldEnderecosTelefone.getValue();
            String PagGuia = fieldEnderecosPagGuia.getValue();
            String Reponsavel = fieldEnderecosReponsavel.getValue();
            String Regiao = comboEnderecosRegiao.toString();
            String PontodeReferencia = fieldEnderecosPontodeReferencia.getValue();



            if (!fieldEnderecosCEP.isEmpty()) {
                Endereco endereco =
                        new Endereco(CEP,TipoImovel,Area,Endereço,Numero,Complemento,Bairro,Cidade,UF,Telefone,PagGuia,Reponsavel,Regiao,PontodeReferencia);
                enderecos.add(endereco);
                grid.setItems(enderecos);
                Notification.show("Endereço Adicionado!");
                fieldEnderecosCEP.clear();
                comboEnderecosTipoImovel.clear();
                fieldEnderecosArea.clear();
                fieldEnderecosEndereço.clear();
                fieldEnderecosNumero.clear();
                fieldEnderecosComplemento.clear();
                fieldEnderecosBairro.clear();
                fieldEnderecosCidade.clear();
                comboEnderecosUF.clear();
                fieldEnderecosTelefone.clear();
                fieldEnderecosPagGuia.clear();
                fieldEnderecosReponsavel.clear();
                comboEnderecosRegiao.clear();
                fieldEnderecosPontodeReferencia.clear();

            } else {
                fieldEnderecosPagGuia.addClassName("error-border");
                //estadoError.setVisible(true);
                Notification.show("Por favor, preencha todos os campos.");
            }
        });
        FormLayout formLayout =  new FormLayout(fieldEnderecosCEP,
                comboEnderecosTipoImovel,fieldEnderecosArea,fieldEnderecosEndereço,fieldEnderecosNumero,fieldEnderecosComplemento,fieldEnderecosBairro,fieldEnderecosCidade,comboEnderecosUF,fieldEnderecosTelefone,fieldEnderecosPagGuia,fieldEnderecosReponsavel,comboEnderecosRegiao,fieldEnderecosPontodeReferencia
                , saveButton);
        formLayout.setWidthFull();
        //formLayout.set(FlexComponent.Alignment.AUTO);

        VerticalLayout layout = new VerticalLayout(formLayout, grid);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        div.add(layout);

        return div;
    }

    private List<SetEstado> getUFList() {
       return estadoService.listAll();
    }

    private Div createFormCadastroEmpresa() {
        dataField = new DatePicker("Data Cadastro");
        dataField.setValue(LocalDate.now());
        nomeField = new TextField("Nome");
        administradora = new ComboBox("Administradora");
        administradora.setItems(getItemsAdministradora());
        contatoField = new TextField("Contato");
        tipoMidia = new ComboBox("Tipo de Midia");
        horaField = new TimePicker("Hora Ligação");
        nomeIndicacaoField = new TextField("Nome Indicação");
        telefoneField = new TextField("Telefone de Contato");
        faxField = new TextField("Fax");
        celularField = new TextField("Celular");
        internetEmailField = new TextField("Internet/E-mail");
        FJField = new TextField("F/J");
        CGCCPFField = new TextField("CGC/CPF");
        inscEstatualField = new TextField("Insc. Estatual");
        observacaoField = new TextField("Observação");

        configureCelularField();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(dataField,nomeField,administradora,contatoField,tipoMidia,horaField,nomeIndicacaoField,telefoneField,faxField,celularField,internetEmailField,FJField,CGCCPFField,inscEstatualField,observacaoField);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroAgendamento() {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        Div div = new Div(formLayout);
        div.setSizeFull();
        nomeAgendamentoField = new TextField("Nome");
        contatoAgendamentoField = new TextField("Contato");
        telefoneAgendamentoField = new TextField("Telefone de Contato");
        faxAgendamentoField = new TextField("Fax");
        celularAgendamentoField = new TextField("Celular");
        internetEmailAgendamentoField = new TextField("Internet/E-mail");
        CGCCPFAgendamentoField = new TextField("CGC/CPF");
        inscEstatualAgendamentoField = new TextField("Insc. Estatual");
        observacaoAgendamentoField = new TextField("Observação");

        configureCelularField();
        formLayout.setWidthFull();
        formLayout.add(nomeAgendamentoField,contatoAgendamentoField,telefoneAgendamentoField,faxAgendamentoField,celularAgendamentoField,internetEmailAgendamentoField,CGCCPFAgendamentoField,inscEstatualAgendamentoField,observacaoAgendamentoField);


        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroAprovacao() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        // Create two separate sections with a border and title
        VerticalLayout section1 = createSectionAprovacao("Cadastro Aprovador");

        verticalLayout.add(section1);
        Div div = new Div(verticalLayout);
        div.setSizeFull();
        return div;
    }

    private Div createFormCadastroCobranca() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        // Create two separate sections with a border and title
        VerticalLayout section1 = createSectionCobranca("Cadastro Cobrança");

        verticalLayout.add(section1);
        Div div = new Div(verticalLayout);
        div.setSizeFull();
        return div;
    }

    private VerticalLayout createSectionAprovacao(String title) {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.addClassName("section-border");

        // Create title for the section
        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidthFull();
        titleLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        titleLayout.add(title);
        titleLayout.addClassName("section-title");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        // Create fields
        nomeAprovacaoField = new TextField("Nome Aprovador");
        contatoAprovacaoField = new TextField("Contato Aprovador");
        telefoneAprovacaoField = new TextField("Telefone de Contato Aprovador");
        faxAprovacaoField = new TextField("Fax Aprovador");
        celularAprovacaoField = new TextField("Celular Aprovador");
        internetEmailAprovacaoField = new TextField("Internet/E-mail Aprovador");
        CGCCPFAprovacaoField = new TextField("CGC/CPF Aprovador");
        inscEstatualAprovacaoField = new TextField("Insc. Estatual Aprovador");
        observacaoAprovacaoField = new TextField("Observação Aprovador");
        formLayout.setWidthFull();
        formLayout.add(nomeAprovacaoField,contatoAprovacaoField,telefoneAprovacaoField,faxAprovacaoField,celularAprovacaoField,internetEmailAprovacaoField,CGCCPFAprovacaoField,inscEstatualAprovacaoField,observacaoAprovacaoField);

        section.add(titleLayout, formLayout);

        return section;
    }

    private VerticalLayout createSectionCobranca(String title) {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.addClassName("section-border");

        // Create title for the section
        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidthFull();
        titleLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        titleLayout.add(title);
        titleLayout.addClassName("section-title");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        // Create fields
        nomeCobrancaField = new TextField("Nome Cobrança");
        contatoCobrancaField = new TextField("Contato Cobrança");
        telefoneCobrancaField = new TextField("Telefone de Contato Cobrança");
        faxCobrancaField = new TextField("Fax Cobrança");
        celularCobrancaField = new TextField("Celular Cobrança");
        internetEmailCobrancaField = new TextField("Internet/E-mail Cobrança");
        CGCCPFCobrancaField = new TextField("CGC/CPF Cobrança");
        inscEstatualCobrancaField = new TextField("Insc. Estatual Cobrança");
        observacaoCobrancaField = new TextField("Observação Cobrança");
        formLayout.setWidthFull();
        formLayout.add(nomeCobrancaField,contatoCobrancaField,telefoneCobrancaField,faxCobrancaField,celularCobrancaField,internetEmailCobrancaField,CGCCPFCobrancaField,inscEstatualCobrancaField,observacaoCobrancaField);

        section.add(titleLayout, formLayout);

        return section;
    }
    private List<String> getItemsAdministradora() {
        List<String> lista = new ArrayList<>();
        lista.add("1");
        lista.add("2");
        lista.add("3");
        return lista;
    }

    private void configureCelularField() {
        celularField.setPlaceholder("(XX) XXXXX-XXXX");
        celularField.setValueChangeMode(ValueChangeMode.EAGER);
        celularField.addValueChangeListener(event -> {
            String value = event.getValue();
            value = value.replaceAll("[^0-9]", "");
            if (value.length() > 2) {
                value = "(" + value.substring(0, 2) + ") " + value.substring(2);
            }
            if (value.length() > 9) {
                value = value.substring(0, 10) + "-" + value.substring(10);
            }
            celularField.setValue(value);
        });
    }

    private void save() {
        Cliente dto = newCliente();
        SetCliente cliente = clientesService.save(dto);
        SetResponsavelAgendamento agendamento = newPessoaAgendamento(cliente.getId_cliente());
        SetResponsavelAprovacao aprovacao = newPessoaAprovacao(cliente.getId_cliente());
        SetResponsavelCobranca cobranca = newPessoaCobranca(cliente.getId_cliente());
        responsavelCobrancaService.save(cobranca);
        responsavelAgendamentoService.save(agendamento);
        responsavelAprovacaoService.save(aprovacao);
        if (enderecos.size() > 0) {
            enderecoService.save(enderecos, cliente.getId_cliente(), 1);
        }
        close();
    }

    private SetResponsavelCobranca newPessoaCobranca(Integer id_cliente) {
        SetResponsavelCobranca cobranca = new SetResponsavelCobranca();
        cobranca.setNome_cobranca(nomeCobrancaField.getValue());
        cobranca.setTelefone_fixo(telefoneCobrancaField.getValue());
        cobranca.setTelefone_celular(celularCobrancaField.getValue());
        cobranca.setFax(faxCobrancaField.getValue());
        cobranca.setEmail(internetEmailCobrancaField.getValue());
        cobranca.setCgc_cpf(CGCCPFCobrancaField.getValue());
        cobranca.setInscricao_estatual(inscEstatualCobrancaField.getValue());
        cobranca.setObservacao(observacaoCobrancaField.getValue());
        cobranca.setValor_cobranca(BigDecimal.TEN);
        cobranca.setId_cliente(id_cliente);
        return cobranca;
    }

    private SetResponsavelAprovacao newPessoaAprovacao(Integer id_cliente) {
        SetResponsavelAprovacao aprovacao = new SetResponsavelAprovacao();
        aprovacao.setNome_aprovacao(nomeAprovacaoField.getValue());
        aprovacao.setTelefone_fixo(telefoneAprovacaoField.getValue());
        aprovacao.setFax(faxAprovacaoField.getValue());
        aprovacao.setTelefone_celular(celularAprovacaoField.getValue());
        aprovacao.setEmail(internetEmailAprovacaoField.getValue());
        aprovacao.setCgc_cpf(CGCCPFAprovacaoField.getValue());
        aprovacao.setInscricao_estatual(inscEstatualAprovacaoField.getValue());
        aprovacao.setObservacao(observacaoAprovacaoField.getValue());
        aprovacao.setId_cliente(id_cliente);
        return aprovacao;
    }

    private SetResponsavelAgendamento newPessoaAgendamento(Integer id_cliente) {
        SetResponsavelAgendamento agendamento = new SetResponsavelAgendamento();
        agendamento.setNome_agendamento(nomeAgendamentoField.getValue());
        agendamento.setTelefone_fixo(contatoAgendamentoField.getValue());
        agendamento.setTelefone_celular(telefoneAgendamentoField.getValue());
        agendamento.setFax(faxAgendamentoField.getValue());
        agendamento.setEmail(internetEmailAgendamentoField.getValue());
        agendamento.setCgc_cpf(CGCCPFAgendamentoField.getValue());
        agendamento.setInscricao_estatual(inscEstatualAgendamentoField.getValue());
        agendamento.setObservacao(observacaoAgendamentoField.getValue());
        agendamento.setData_agendamento(LocalDate.now());
        agendamento.setId_cliente(id_cliente);
        return agendamento;
    }

    private Cliente newCliente() {
        Cliente cliente = new Cliente();
        cliente.setNomeField(nomeField.getValue());
        cliente.setTelefoneField(telefoneField.getValue());
        cliente.setCelularField(celularField.getValue());
        cliente.setDataField(dataField.getValue());
        cliente.setHoraField(horaField.getValue());
        cliente.setContatoField(contatoField.getValue());
        cliente.setAdministradora("Teste");
        cliente.setTipoMidia("Teste");
        cliente.setNomeIndicacaoField(nomeIndicacaoField.getValue());
        cliente.setFaxField(faxField.getValue());
        cliente.setInternetEmailField(internetEmailField.getValue());
        cliente.setFJField(FJField.getValue());
        cliente.setCGCCPFField(CGCCPFField.getValue());
        cliente.setInscEstatualField(inscEstatualField.getValue());
        cliente.setObservacaoField(observacaoField.getValue());
        return cliente;
    }




}


