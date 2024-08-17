package br.com.onetec.application.views.main.clientes.modal;

import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.*;
import br.com.onetec.application.service.enderecoservice.EnderecoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DadosClienteModal extends Dialog {

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
    private ComboBox comboEnderecosUF;
    private TextField fieldEnderecosTelefone;
    private TextField fieldEnderecosPagGuia;
    private TextField fieldEnderecosReponsavel;
    private ComboBox comboEnderecosRegiao;
    private TextField fieldEnderecosPontodeReferencia;
    Button saveButton;
    Button atendimentoButton;
    Button cancelButton;
    //Button saveButton;
    private Tabs tabs;
    private Div dadosEmpresa;
    private Div dadosAgendamento;
    private Div dadosCobranca;
    private Div dadosAprovacao;
    private Div dadosEnderecos;
    private Div dadosHistoricoAtendimento;

    private final ClientesService clientesService;
    private final EstadoService estadoService;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;

    private final ResponsavelCobrancaService responsavelCobrancaService;
    private final ResponsavelAgendamentoService responsavelAgendamentoService;
    private final ResponsavelAprovacaoService responsavelAprovacaoService;

    public DadosClienteModal(SetCliente cliente, ClientesService clientesService, EstadoService estadoService, UsuarioService usuarioService, EnderecoService enderecoService, ResponsavelCobrancaService responsavelCobrancaService, ResponsavelAgendamentoService responsavelAgendamentoService, ResponsavelAprovacaoService responsavelAprovacaoService) {
        new Title("Detalhes do Cliente");
        this.clientesService = clientesService;
        this.estadoService = estadoService;
        this.usuarioService = usuarioService;
        this.enderecoService =  enderecoService;
        this.responsavelCobrancaService = responsavelCobrancaService;
        this.responsavelAgendamentoService = responsavelAgendamentoService;
        this.responsavelAprovacaoService = responsavelAprovacaoService;

        saveButton = new Button("Salvar", eventbe -> save());
        cancelButton = new Button("Cancelar", event -> close());
        atendimentoButton = new Button("Atendimento e Histórico", event -> atendimentoAbrir(cliente
        ));
        tabs = new Tabs();
        Tab tab1 = new Tab("Dados Empresa");
        Tab tab2 = new Tab("Dados Agendamento");
        Tab tab3 = new Tab("Aprovação");
        Tab tab4 = new Tab("Cobrança");
        Tab tab5 = new Tab("Dados Endereços");

        tabs.add(tab1, tab2, tab3, tab4,tab5);
        dadosEmpresa = createFormCadastroEmpresa(cliente);
        dadosAgendamento = createFormCadastroAgendamento(cliente);
        dadosCobranca = createFormCadastroCobranca(cliente);
        dadosAprovacao = createFormCadastroAprovacao(cliente);
        dadosEnderecos = createFormEnderecos(cliente);
        dadosHistoricoAtendimento = createFormHistoricoAtendimento();

        Div content = new Div(dadosEmpresa,
                dadosAgendamento,
                dadosCobranca,
                dadosAprovacao,
                dadosEnderecos,
                dadosHistoricoAtendimento);
        content.setSizeFull();
        dadosEmpresa.setVisible(true);
        dadosAgendamento.setVisible(false);
        dadosCobranca.setVisible(false);
        dadosAprovacao.setVisible(false);
        dadosEnderecos.setVisible(false);
        dadosHistoricoAtendimento.setVisible(false);

        tabs.addSelectedChangeListener(event -> {
            dadosEmpresa.setVisible(false);
            dadosAgendamento.setVisible(false);
            dadosCobranca.setVisible(false);
            dadosAprovacao.setVisible(false);
            dadosEnderecos.setVisible(false);
            dadosHistoricoAtendimento.setVisible(false);

            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab.equals(tab1)) {
                dadosEmpresa.setVisible(true);
            } else if (selectedTab.equals(tab2)) {
                dadosAgendamento.setVisible(true);
            } else if (selectedTab.equals(tab3)) {
                dadosAprovacao.setVisible(true);
            }   else if (selectedTab.equals(tab4)) {
                dadosCobranca.setVisible(true);
            }   else if (selectedTab.equals(tab5)) {
                dadosEnderecos.setVisible(true);
            }
        });

        Div contentTabs = new Div(dadosEmpresa,
                dadosAgendamento,
                dadosAprovacao,
                dadosCobranca,
                dadosEnderecos,
                dadosHistoricoAtendimento);
        contentTabs.setSizeFull();

        VerticalLayout layout = new VerticalLayout(tabs,contentTabs, saveButton, cancelButton, atendimentoButton);
        add(layout);
    }



    private void atendimentoAbrir(SetCliente cliente) {
        close();
        // Armazena o cliente na sessão do usuário
        UI.getCurrent().getSession().setAttribute("cliente", cliente);
        // Navega para a rota da view AtendimentoHistoricoView
        UI.getCurrent().navigate("atendimentos_historico");
    }

    private Div createFormHistoricoAtendimento() {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        //formLayout.add(dataField,nomeField,administradora,contatoField,tipoMidia,horaField,nomeIndicacaoField,telefoneField,faxField,celularField,internetEmailField,FJField,CGCCPFField,inscEstatualField,observacaoField);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    private List<SetEnderecos> enderecos = new ArrayList<>();
    private Grid<SetEnderecos> grid = new Grid<>(SetEnderecos.class, false);



    private Div createFormEnderecos(SetCliente cliente) {
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
        comboEnderecosUF.setItems(getItemsAdministradora());
        fieldEnderecosTelefone = new TextField("Telefone do Local");
        fieldEnderecosPagGuia = new TextField("Pag. Guia");
        fieldEnderecosReponsavel = new TextField("Responsável");
        comboEnderecosRegiao  = new ComboBox("Região");;
        comboEnderecosRegiao.setItems(getItemsAdministradora());
        fieldEnderecosPontodeReferencia = new TextField("Ponto de Referencia");

        enderecos = enderecoService.findAllClienteId(cliente.getId_cliente());
        // Configurar o Grid
        //grid.setColumns("Tipo de Imóvel","Endereço","N°","Bairro","CEP","Cidade","Estado","Fone");
        grid.setItems(enderecos);

        grid.addColumn(SetEnderecos::getArea_imovel).setHeader("TipoImovel").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getEndereco_imovel).setHeader("Endereço").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getNumero_imovel).setHeader("Numero").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getBairro_imovel).setHeader("Bairro").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getCep_imovel).setHeader("CEP").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getCidade_imovel).setHeader("Cidade").setAutoWidth(true);
        grid.addColumn(e -> {
            SetEstado estado = estadoService.findById(1);
            return estado != null ? estado.getUf_estado() : "N/A";

        }).setHeader("UF").setAutoWidth(true);
        grid.addColumn(SetEnderecos::getTelefone_local).setHeader("Telefone").setAutoWidth(true);

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
            //SetEstado UF = comboEnderecosUF.getValue();
            String Telefone = fieldEnderecosTelefone.getValue();
            String PagGuia = fieldEnderecosPagGuia.getValue();
            String Reponsavel = fieldEnderecosReponsavel.getValue();
            String Regiao = comboEnderecosRegiao.toString();
            String PontodeReferencia = fieldEnderecosPontodeReferencia.getValue();



            if (!fieldEnderecosCEP.isEmpty()) {
                Endereco endereco =
                    new Endereco(CEP,TipoImovel,Area,Endereço,Numero,Complemento,Bairro,Cidade,new SetEstado(),Telefone,PagGuia,Reponsavel,Regiao,PontodeReferencia);
                SetEnderecos ed = new SetEnderecos();
                ed.setTelefone_local(endereco.getFieldEnderecosTelefone());
                ed.setNumero_imovel(endereco.getFieldEnderecosNumero());
                ed.setNome_responsavel(endereco.getFieldEnderecosReponsavel());
                ed.setPonto_referencia(endereco.getFieldEnderecosPontodeReferencia());
                ed.setBairro_imovel(endereco.getFieldEnderecosBairro());
                ed.setEndereco_imovel(endereco.getFieldEnderecosEndereço());
                enderecos.add(ed);
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

    private Div createFormCadastroEmpresa(SetCliente cliente) {
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

        dataField.setValue(cliente.getData_inclusao().toLocalDate());
        nomeField.setValue(cliente.getNome_cliente());
        administradora.setValue(cliente.getAdministradora_cliente());
        contatoField.setValue(cliente.getCargo_contato_cliente());
        horaField.setValue(cliente.getHora_ligacao_cliente());
        telefoneField.setValue(cliente.getTelefone_cliente());
        faxField.setValue(cliente.getFax_cliente());
        celularField.setValue(cliente.getCelular_cliente());
        internetEmailField.setValue(cliente.getEmail_cliente());
        FJField.setValue(cliente.getPf_pj_cliente());
        CGCCPFField.setValue(cliente.getCpf_cgc_cliente());
        inscEstatualField.setValue(cliente.getIest_cliente());
        observacaoField.setValue(cliente.getObservacoes_cliente());

        configureCelularField();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(dataField,nomeField,administradora,contatoField,tipoMidia,horaField,nomeIndicacaoField,telefoneField,faxField,celularField,internetEmailField,FJField,CGCCPFField,inscEstatualField,observacaoField);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroAgendamento(SetCliente cliente) {
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

        SetResponsavelAgendamento agendamento = responsavelAgendamentoService.find(cliente.getId_cliente());
        if (agendamento != null) {
            nomeAgendamentoField.setValue(agendamento.getNome_agendamento());
            telefoneAgendamentoField.setValue(agendamento.getTelefone_fixo());
            faxAgendamentoField.setValue(agendamento.getFax());
            celularAgendamentoField.setValue(agendamento.getTelefone_celular());
            internetEmailAgendamentoField.setValue(agendamento.getEmail());
            CGCCPFAgendamentoField.setValue(agendamento.getCgc_cpf());
            inscEstatualAgendamentoField.setValue(agendamento.getInscricao_estatual());
            observacaoAgendamentoField.setValue(agendamento.getObservacao());
        }
        configureCelularField();
        formLayout.setWidthFull();
        formLayout.add(nomeAgendamentoField,contatoAgendamentoField,telefoneAgendamentoField,faxAgendamentoField,celularAgendamentoField,internetEmailAgendamentoField,CGCCPFAgendamentoField,inscEstatualAgendamentoField,observacaoAgendamentoField);


        div.setSizeFull();

        return div;
    }

    private Div createFormCadastroAprovacao(SetCliente cliente) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        // Create two separate sections with a border and title
        VerticalLayout section1 = createSectionAprovacao("Cadastro Aprovador",cliente);

        verticalLayout.add(section1);
        Div div = new Div(verticalLayout);
        div.setSizeFull();
        return div;
    }

    private Div createFormCadastroCobranca(SetCliente cliente) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        // Create two separate sections with a border and title
        VerticalLayout section2 = createSectionCobranca("Cadastro Cobrança", cliente);

        verticalLayout.add(section2);
        Div div = new Div(verticalLayout);
        div.setSizeFull();
        return div;
    }

    private VerticalLayout createSectionAprovacao(String title, SetCliente cliente) {
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

        SetResponsavelAprovacao aprovacao = responsavelAprovacaoService.find(cliente.getId_cliente());
        if (aprovacao != null) {
            nomeAprovacaoField.setValue(aprovacao.getNome_aprovacao());
            telefoneAprovacaoField.setValue(aprovacao.getTelefone_fixo());
            faxAprovacaoField.setValue(aprovacao.getFax());
            celularAprovacaoField.setValue(aprovacao.getTelefone_celular());
            internetEmailAprovacaoField.setValue(aprovacao.getEmail());
            CGCCPFAprovacaoField.setValue(aprovacao.getCgc_cpf());
            inscEstatualAprovacaoField.setValue(aprovacao.getInscricao_estatual());
            observacaoAprovacaoField.setValue(aprovacao.getObservacao());
        }


        formLayout.setWidthFull();
        formLayout.add(nomeAprovacaoField,contatoAprovacaoField,telefoneAprovacaoField,faxAprovacaoField,celularAprovacaoField,internetEmailAprovacaoField,CGCCPFAprovacaoField,inscEstatualAprovacaoField,observacaoAprovacaoField);

        section.add(titleLayout, formLayout);

        return section;
    }

    private VerticalLayout createSectionCobranca(String title,SetCliente cliente) {
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

        SetResponsavelCobranca cobranca = responsavelCobrancaService.find(cliente.getId_cliente());
        if (cobranca != null) {
            nomeCobrancaField.setValue(cobranca.getNome_cobranca());
            telefoneCobrancaField.setValue(cobranca.getTelefone_fixo());
            faxCobrancaField.setValue(cobranca.getFax());
            celularCobrancaField.setValue(cobranca.getTelefone_celular());
            internetEmailCobrancaField.setValue(cobranca.getEmail());
            CGCCPFCobrancaField.setValue(cobranca.getCgc_cpf());
            inscEstatualCobrancaField.setValue(cobranca.getInscricao_estatual());
            observacaoCobrancaField.setValue(cobranca.getObservacao());
        }

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
        // Lógica para salvar o cadastro
        close();
    }
}
