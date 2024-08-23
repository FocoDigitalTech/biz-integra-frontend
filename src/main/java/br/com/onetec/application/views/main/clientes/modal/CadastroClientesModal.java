package br.com.onetec.application.views.main.clientes.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Cliente;
import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.*;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.regiaoservice.RegiaoService;
import br.com.onetec.application.service.tipoimovelservice.TipoImovelService;
import br.com.onetec.application.service.tipomidiaservice.TipoMidiaService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.service.utilservices.ApiEnderecoService;
import br.com.onetec.cross.utilities.Servicos;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class CadastroClientesModal extends Dialog {

    //cadastro empresa
    private TextField nomeField;
    private TextField telefoneField;
    private TextField celularField;
    private DatePicker dataField;
    private TimePicker horaField;
    private TextField contatoField;
    private ComboBox administradora;
    private ComboBox <SetTipoMidia>tipoMidia;
    private TextField midiaAntigaField;
    private TextField internetEmailField;
    private ComboBox<String> FJField;
    private TextField CGCCPFField;
    private TextField inscEstatualField;
    private TextArea observacaoField;
    //Agendamento
    private TextField nomeAgendamentoField;
    private TextField nomesocialAgendamentoField;
    private TextField telefoneAgendamentoField;
    private TextField celularAgendamentoField;
    private TextField internetEmailAgendamentoField;
    private TextArea observacaoAgendamentoField;
    //Aprovacao
    private TextField nomeAprovacaoField;
    private TextField nomesocialAprovacaoField;
    private TextField telefoneAprovacaoField;
    private TextField celularAprovacaoField;
    private TextField internetEmailAprovacaoField;
    private TextArea observacaoAprovacaoField;
    //Cobranca
    private TextField nomeCobrancaField;
    private TextField nomesocialCobrancaField;
    private TextField telefoneCobrancaField;
    private TextField celularCobrancaField;
    private TextField internetEmailCobrancaField;
    private TextArea observacaoCobrancaField;
    //Enderecos
    private TextField fieldEnderecosCEP;
    private ComboBox <SetTipoImovel> comboEnderecosTipoImovel;
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
    private ComboBox <SetRegiao> comboEnderecosRegiao;
    private TextField fieldEnderecosPontodeReferencia;

    private Tabs tabs;
    private Div cadastroEmpresa;
    private Div cadastroAgendamento;
    private Div cadastroAprovacao;
    private Div cadastroCobranca;
    private Div cadastroEnderecos;
    private Button saveButton;
    private Button cancelButton;

    private ClientesService clientesService;
    private EstadoService estadoService;
    private UsuarioService usuarioService;
    private EnderecoService enderecoService;
    private Servicos service;

    private ResponsavelCobrancaService responsavelCobrancaService;
    private ResponsavelAgendamentoService responsavelAgendamentoService;
    private ResponsavelAprovacaoService responsavelAprovacaoService;

    private TipoMidiaService tipomidiaService;

    private TipoImovelService tipoimovelService;
    private RegiaoService regiaoService;
    private ApiEnderecoService apiEnderecoService;
    private List<SetEstado> estadoList = new ArrayList<>();

    @Autowired
    public void initServices (ClientesService clientesService,
                              EstadoService estadoService,
                              UsuarioService usuarioService,
                              EnderecoService enderecoService,
                              ResponsavelCobrancaService responsavelCobrancaService,
                              ResponsavelAgendamentoService responsavelAgendamentoService,
                              ResponsavelAprovacaoService responsavelAprovacaoService,
                              Servicos service1, TipoMidiaService tipomidiaService1,
                              TipoImovelService tipoimovelService1,
                              RegiaoService regiaoService1,
                              ApiEnderecoService enderecoService1) {
        this.clientesService = clientesService;
        this.estadoService = estadoService;
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.responsavelCobrancaService = responsavelCobrancaService;
        this.responsavelAgendamentoService = responsavelAgendamentoService;
        this.responsavelAprovacaoService = responsavelAprovacaoService;
        this.service = service1;
        this.tipomidiaService = tipomidiaService1;
        this.tipoimovelService = tipoimovelService1;
        this.regiaoService = regiaoService1;
        this.apiEnderecoService = enderecoService1;

        service.configureCEPField(fieldEnderecosCEP);
        service.configureCelularField(celularField);
        service.configureCPFField(CGCCPFField);
        service.configuraCalendario(dataField);

        fieldEnderecosCEP.addBlurListener(event -> buscarCep());
        fieldEnderecosCEP.addValueChangeListener(event -> {
            String value = event.getValue().replaceAll("[^0-9]", "");
            if (value.length() >= 8) {
                String cep = value;
                buscarCep();
            } if (value.length() <= 7){
                fieldEnderecosEndereço.clear();
                //complemento_funcionario.setValue(response.get);
                fieldEnderecosBairro.clear();
                fieldEnderecosCidade.clear();
                comboEnderecosUF.clear();
            }
        });


        estadoList = estadoService.listAll();
        tipoMidia.setItems(tipomidiaService.findAllMidia());
        tipoMidia.setItemLabelGenerator(SetTipoMidia::getDescricao_tipomidia);
        comboEnderecosTipoImovel.setItems(tipoimovelService.findAllImovel());
        comboEnderecosTipoImovel.setItemLabelGenerator(SetTipoImovel::getDescricao_tipoimovel);
        comboEnderecosRegiao.setItems(regiaoService.findAllRegiao());
        comboEnderecosRegiao.setItemLabelGenerator(SetRegiao::getDescricao_regiao);
        comboEnderecosUF.setItemLabelGenerator(SetEstado::getUf_estado);
    }

    private void buscarCep() {
        EApiEnderecoResponse response = service.buscarCep(fieldEnderecosCEP);
        fieldEnderecosEndereço.setValue(response.getLogradouro());
        //complemento_funcionario.setValue(response.get);
        fieldEnderecosBairro.setValue(response.getBairro());
        fieldEnderecosCidade.setValue(response.getLocalidade());
        comboEnderecosUF.setValue(service.configuraUF(estadoList, response.getUf()));
    }

    @Autowired
    public CadastroClientesModal() {

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
        comboEnderecosTipoImovel  = new ComboBox("Tipo de Imóvel");
        fieldEnderecosArea = new TextField("Area");
        fieldEnderecosEndereço = new TextField("Endereço");
        fieldEnderecosNumero = new TextField("Número");
        fieldEnderecosComplemento = new TextField("Complemento");
        fieldEnderecosBairro = new TextField("Bairro");
        fieldEnderecosCidade = new TextField("Cidade");
        comboEnderecosUF  = new ComboBox("UF");
        //comboEnderecosUF.setItems(getUFList());
        fieldEnderecosTelefone = new TextField("Telefone do Local");
        fieldEnderecosPagGuia = new TextField("Pag. Guia");
        fieldEnderecosReponsavel = new TextField("Responsável");
        comboEnderecosRegiao  = new ComboBox("Região");
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



    private Div createFormCadastroEmpresa() {
        dataField = new DatePicker("Data Cadastro");
        dataField.setValue(LocalDate.now());
        nomeField = new TextField("Nome");
        administradora = new ComboBox("Administradora");
        administradora.setItems(getItemsAdministradora());
        contatoField = new TextField("Contato");
        tipoMidia = new ComboBox<SetTipoMidia>("Tipo de Midia");
        horaField = new TimePicker("Hora Ligação");
        midiaAntigaField = new TextField("Midia Antiga");
        telefoneField = new TextField("Telefone de Contato");
        celularField = new TextField("Celular");
        internetEmailField = new TextField("E-mail");
        FJField = new ComboBox<>("Natureza Juridica");
        FJField.setItems(List.of("Pessoa Fisica","Pessoa Juridica"));
        FJField.addValueChangeListener(event -> {
            if ("Pessoa Fisica".equals(event.getValue())) {
                CGCCPFField.clear();
                CGCCPFField.setLabel("Número CPF");
                service.configureCPFField(CGCCPFField);

            } else if ("Pessoa Juridica".equals(event.getValue())) {
                CGCCPFField.clear();
                CGCCPFField.setLabel("Número CNPJ");
                service.configureCNPJTextField(CGCCPFField);
            }
        });
        CGCCPFField = new TextField("Número CPF");

        inscEstatualField = new TextField("Insc. Estatual");
        observacaoField = new TextArea("Observação");


        configureCelularField();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(dataField,nomeField,administradora,contatoField,tipoMidia,horaField, midiaAntigaField,telefoneField,celularField,internetEmailField,FJField,CGCCPFField,inscEstatualField,observacaoField);

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
        nomesocialAgendamentoField = new TextField("Nome Social");
        telefoneAgendamentoField = new TextField("Telefone de Contato");
        celularAgendamentoField = new TextField("Celular");
        internetEmailAgendamentoField = new TextField("E-mail");
        observacaoAgendamentoField = new TextArea("Observação");

        configureCelularField();
        formLayout.setWidthFull();
        formLayout.add(nomeAgendamentoField, nomesocialAgendamentoField,telefoneAgendamentoField,celularAgendamentoField,internetEmailAgendamentoField,observacaoAgendamentoField);


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
        nomesocialAprovacaoField = new TextField("Nome Social");
        telefoneAprovacaoField = new TextField("Telefone de Contato Aprovador");
        celularAprovacaoField = new TextField("Celular Aprovador");
        internetEmailAprovacaoField = new TextField("E-mail Aprovador");
        observacaoAprovacaoField = new TextArea("Observação Aprovador");
        formLayout.setWidthFull();
        formLayout.add(nomeAprovacaoField, nomesocialAprovacaoField,telefoneAprovacaoField,celularAprovacaoField,internetEmailAprovacaoField,observacaoAprovacaoField);

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
        nomesocialCobrancaField = new TextField("Nome Social");
        telefoneCobrancaField = new TextField("Telefone de Contato Cobrança");
        celularCobrancaField = new TextField("Celular Cobrança");
        internetEmailCobrancaField = new TextField("E-mail Cobrança");
        observacaoCobrancaField = new TextArea("Observação Cobrança");
        formLayout.setWidthFull();
        formLayout.add(nomeCobrancaField, nomesocialCobrancaField,telefoneCobrancaField,celularCobrancaField,internetEmailCobrancaField,observacaoCobrancaField);

        section.add(titleLayout, formLayout);

        return section;
    }
    private List<String> getItemsAdministradora() {
        List<String> lista = new ArrayList<>();
        lista.add("N/D");
        lista.add("Empresa");
        lista.add("Condominio");
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
        cobranca.setNome_social(nomesocialCobrancaField.getValue());
        cobranca.setEmail(internetEmailCobrancaField.getValue());
        cobranca.setObservacao(observacaoCobrancaField.getValue());
        cobranca.setValor_cobranca(BigDecimal.TEN);
        cobranca.setId_cliente(id_cliente);
        cobranca.setData_inclusao(LocalDateTime.now());
        cobranca.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        cobranca.setAtivo("S");
        return cobranca;
    }

    private SetResponsavelAprovacao newPessoaAprovacao(Integer id_cliente) {
        SetResponsavelAprovacao aprovacao = new SetResponsavelAprovacao();
        aprovacao.setNome_aprovacao(nomeAprovacaoField.getValue());
        aprovacao.setTelefone_fixo(telefoneAprovacaoField.getValue());
        aprovacao.setNome_social(nomesocialAprovacaoField.getValue());
        aprovacao.setTelefone_celular(celularAprovacaoField.getValue());
        aprovacao.setEmail(internetEmailAprovacaoField.getValue());
        aprovacao.setObservacao(observacaoAprovacaoField.getValue());
        aprovacao.setId_cliente(id_cliente);
        aprovacao.setData_inclusao(LocalDateTime.now());
        aprovacao.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        aprovacao.setAtivo("S");
        return aprovacao;
    }

    private SetResponsavelAgendamento newPessoaAgendamento(Integer id_cliente) {
        SetResponsavelAgendamento agendamento = new SetResponsavelAgendamento();
        agendamento.setNome_agendamento(nomeAgendamentoField.getValue());
        agendamento.setNome_social(nomesocialAgendamentoField.getValue());
        agendamento.setTelefone_fixo(telefoneAgendamentoField.getValue());
        agendamento.setTelefone_celular(celularAgendamentoField.getValue());
        agendamento.setEmail(internetEmailAgendamentoField.getValue());
        agendamento.setObservacao(observacaoAgendamentoField.getValue());
        agendamento.setData_agendamento(LocalDate.now());
        agendamento.setId_cliente(id_cliente);
        agendamento.setData_inclusao(LocalDateTime.now());
        agendamento.setAtivo("S");
        agendamento.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
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
        cliente.setNomeIndicacaoField(midiaAntigaField.getValue());
        cliente.setInternetEmailField(internetEmailField.getValue());
        cliente.setFJField(FJField.getValue());
        cliente.setCGCCPFField(CGCCPFField.getValue());
        cliente.setInscEstatualField(inscEstatualField.getValue());
        cliente.setObservacaoField(observacaoField.getValue());
        return cliente;
    }




}


