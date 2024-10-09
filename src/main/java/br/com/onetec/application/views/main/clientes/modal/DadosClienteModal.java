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
import br.com.onetec.application.views.main.clientes.ClientesView;
import br.com.onetec.application.views.main.financeiro.div.CondicaoPagamentoDiv;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class DadosClienteModal extends Dialog {

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
    private EmailField internetEmailField;
    private ComboBox<String> FJField;
    private TextField CGCCPFField;
    private TextField inscEstatualField;
    private TextArea observacaoField;
    //Agendamento
    private TextField nomeAgendamentoField;
    private TextField nomesocialAgendamentoField;
    private TextField telefoneAgendamentoField;
    private TextField celularAgendamentoField;
    private EmailField internetEmailAgendamentoField;
    private TextArea observacaoAgendamentoField;
    //Aprovacao
    private TextField nomeAprovacaoField;
    private TextField nomesocialAprovacaoField;
    private TextField telefoneAprovacaoField;
    private TextField celularAprovacaoField;
    private EmailField internetEmailAprovacaoField;
    private TextArea observacaoAprovacaoField;
    //Cobranca
    private TextField nomeCobrancaField;
    private TextField nomesocialCobrancaField;
    private TextField telefoneCobrancaField;
    private TextField celularCobrancaField;
    private EmailField internetEmailCobrancaField;
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
    Button atendimentoButton;
    Button excluirBtn;

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
    private UtilitySystemConfigService service;

    private ResponsavelCobrancaService responsavelCobrancaService;
    private ResponsavelAgendamentoService responsavelAgendamentoService;
    private ResponsavelAprovacaoService responsavelAprovacaoService;

    private TipoMidiaService tipomidiaService;

    private TipoImovelService tipoimovelService;
    private RegiaoService regiaoService;
    private ApiEnderecoService apiEnderecoService;
    private List<SetEstado> estadoList = new ArrayList<>();
    private SetCliente cliente;
    private List<SetTipoMidia> listamidia;

    private SetResponsavelCobranca setResponsavelCobranca;
    private SetResponsavelAprovacao setResponsavelAprovacao;
    private SetResponsavelAgendamento setResponsavelAgendamento;

    @Autowired
    @Lazy
    ClientesView clienteView;

    public void setCliente(SetCliente cliente1) {
        this.cliente = cliente1;

        UI.getCurrent().access(() -> {
            nomeField.setValue(cliente.getNome_cliente());
            telefoneField.setValue(cliente.getTelefone_cliente());
            celularField.setValue(cliente.getCelular_cliente());
            dataField.setValue(cliente.getData_inclusao().toLocalDate());
            horaField.setValue(cliente.getHora_ligacao_cliente());
            contatoField.setValue(cliente.getNome_contato_cliente());
            administradora.setValue(cliente.getAdministradora_cliente());
            tipoMidia.setValue(listamidia.stream()
                    .filter(midia -> midia.getId_tipomidia().equals(cliente.getId_anuncio()))
                    .findFirst().orElse(null));
            //midiaAntigaField.setValue();
            internetEmailField.setValue(cliente.getEmail_cliente());
            FJField.setValue(cliente.getTipo_naturezajuridica());

            inscEstatualField.setValue(cliente.getIest_cliente());
            observacaoField.setValue(cliente.getObservacoes_cliente());
            // Aqui você pode adicionar a lógica de manipulação do cliente
            // Por exemplo, popular os campos do modal com os dados do cliente
            // ou realizar qualquer outra manipulação necessária antes de abrir o modal.
            //Agendamento
            setResponsavelAgendamento = responsavelAgendamentoService.find(cliente.getId_cliente());
            nomeAgendamentoField.setValue(setResponsavelAgendamento.getNome_agendamento());
            nomesocialAgendamentoField.setValue(setResponsavelAgendamento.getNome_social());
            telefoneAgendamentoField.setValue(setResponsavelAgendamento.getTelefone_fixo());
            celularAgendamentoField.setValue(setResponsavelAgendamento.getTelefone_celular());
            internetEmailAgendamentoField.setValue(setResponsavelAgendamento.getEmail());
            observacaoAgendamentoField.setValue(setResponsavelAgendamento.getObservacao());
            //Aprovacao
            setResponsavelAprovacao = responsavelAprovacaoService.find(cliente.getId_cliente());
            nomeAprovacaoField.setValue(setResponsavelAprovacao.getNome_aprovacao());
            nomesocialAprovacaoField.setValue(setResponsavelAprovacao.getNome_social());
            telefoneAprovacaoField.setValue(setResponsavelAprovacao.getTelefone_fixo());
            celularAprovacaoField.setValue(setResponsavelAprovacao.getTelefone_celular());
            internetEmailAprovacaoField.setValue(setResponsavelAprovacao.getEmail());
            observacaoAprovacaoField.setValue(setResponsavelAprovacao.getObservacao());
            //Cobranca

            setResponsavelCobranca = responsavelCobrancaService.find(cliente1.getId_cliente());
            nomeCobrancaField.setValue(setResponsavelCobranca.getNome_cobranca());
            nomesocialCobrancaField.setValue(setResponsavelCobranca.getNome_social());
            telefoneCobrancaField.setValue(setResponsavelCobranca.getTelefone_fixo());
            celularCobrancaField.setValue(setResponsavelCobranca.getTelefone_celular());
            internetEmailCobrancaField.setValue(setResponsavelCobranca.getEmail());
            observacaoCobrancaField.setValue(setResponsavelCobranca.getObservacao());
            //Enderecos
            List<SetEnderecos> enderecosLista = enderecoService.findAllClienteId(cliente.getId_cliente());
            List<SetTipoImovel> listaimovel = tipoimovelService.findAllImovel();
            List<SetEstado> listauf = estadoService.listAll();
            List<SetRegiao> listaregiao = regiaoService.findAllRegiao();
            enderecosLista.forEach(e -> {
                SetTipoImovel imovel = listaimovel.stream()
                        .filter(midia -> midia.getId_tipoimovel().equals(e.getId_tipoimovel()))
                        .findFirst().orElse(null);
                SetEstado uf = listauf.stream()
                        .filter(midia -> midia.getId_estado().equals(e.getId_estado()))
                        .findFirst().orElse(null);
                SetRegiao regiao = listaregiao.stream()
                        .filter(midia -> midia.getId_regiao().equals(e.getId_regiao()))
                        .findFirst().orElse(null);
                enderecos.add(new Endereco(e.getCep_imovel(), imovel, e.getArea_imovel(),
                        e.getEndereco_imovel(), e.getNumero_imovel(), e.getComplemento_imovel(),
                        e.getBairro_imovel(), e.getCidade_imovel(),
                        uf, e.getTelefone_local(), e.getPagina_guia(),
                        e.getEndereco_imovel(), regiao, e.getPonto_referencia()));
                grid.setItems(enderecos);
            });
            CGCCPFField.setValue(cliente.getCpf_cgc_cliente());
        });

    }

    private void atendimentoAbrir(SetCliente cliente) {
        close();
        // Armazena o cliente na sessão do usuário
        UI.getCurrent().getSession().setAttribute("cliente", cliente);
        // Navega para a rota da view AtendimentoHistoricoView
        UI.getCurrent().navigate("atendimentos_historico");
    }

    @Autowired
    public void initServices (ClientesService clientesService,
                              EstadoService estadoService,
                              UsuarioService usuarioService,
                              EnderecoService enderecoService,
                              ResponsavelCobrancaService responsavelCobrancaService,
                              ResponsavelAgendamentoService responsavelAgendamentoService,
                              ResponsavelAprovacaoService responsavelAprovacaoService,
                              UtilitySystemConfigService service1, TipoMidiaService tipomidiaService1,
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
        UI.getCurrent().access(() -> {
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
                }
                if (value.length() <= 7) {
                    fieldEnderecosEndereço.clear();
                    //complemento_funcionario.setValue(response.get);
                    fieldEnderecosBairro.clear();
                    fieldEnderecosCidade.clear();
                    comboEnderecosUF.clear();
                }
            });


            listamidia = tipomidiaService.findAllMidia();
            estadoList = estadoService.listAll();
            tipoMidia.setItems(tipomidiaService.findAllMidia());
            tipoMidia.setItemLabelGenerator(SetTipoMidia::getDescricao_tipomidia);
            comboEnderecosTipoImovel.setItems(tipoimovelService.findAllImovel());
            comboEnderecosTipoImovel.setItemLabelGenerator(SetTipoImovel::getDescricao_tipoimovel);
            comboEnderecosRegiao.setItems(regiaoService.findAllRegiao());
            comboEnderecosRegiao.setItemLabelGenerator(SetRegiao::getDescricao_regiao);
            comboEnderecosUF.setItems(estadoList);
            comboEnderecosUF.setItemLabelGenerator(SetEstado::getUf_estado);
        });
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
    public DadosClienteModal() {

        UI.getCurrent().access(() -> {
            addClassName("cadastro-modal");

            saveButton = new Button("Salvar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            atendimentoButton = new Button("Atendimento e Histórico", event -> atendimentoAbrir(cliente));
            excluirBtn = new Button("Excluir", event -> excluirCliente(cliente));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

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

            Div content = new Div(cadastroEmpresa, cadastroAgendamento, cadastroAprovacao, cadastroEnderecos);
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
                } else if (selectedTab.equals(tab4)) {
                    cadastroCobranca.setVisible(true);
                } else if (selectedTab.equals(tab5)) {
                    cadastroEnderecos.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroEmpresa, cadastroAgendamento, cadastroAprovacao, cadastroEnderecos, cadastroCobranca);
            contentTabs.setSizeFull();

            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            atendimentoButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            excluirBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

            // Criando o layout do rodapé e ajustando o alinhamento dos botões
            HorizontalLayout footerLayout = new HorizontalLayout();
            footerLayout.setWidthFull();
            footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);// Alinha o conteúdo

            // Adiciona o botão "Excluir" ao lado esquerdo e os outros ao lado direito
            footerLayout.add(excluirBtn); // Alinha à esquerda
            HorizontalLayout rightButtons = new HorizontalLayout(saveButton, atendimentoButton, cancelButton);
            footerLayout.add(rightButtons); // Alinha à direita

            getFooter().add(footerLayout);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    private void excluirCliente(SetCliente cliente) {
        try {
            clientesService.logicalDelete(cliente,UsuarioAutenticadoConfig.getUser());
            service.notificaSucesso("Excluido com sucesso");
            clienteView.refreshGrid();
            close();
        } catch (Exception e){
            service.notificaErro("Erro ao excluir.");
        }
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
        grid.setItems(enderecos);
//        grid.addColumn(endereco -> {
//            String descricao_tipoimovel = endereco.getComboEnderecosTipoImovel().getDescricao_tipoimovel();
//            return descricao_tipoimovel != null ? descricao_tipoimovel : "N/A";
//        })
//                .setHeader("TipoImovel")
//                .setAutoWidth(true);
        grid.addColumn("fieldEnderecosEndereço").setHeader("Endereço").setAutoWidth(true);
        grid.addColumn("fieldEnderecosNumero").setHeader("Numero").setAutoWidth(true);
        grid.addColumn("fieldEnderecosBairro").setHeader("Bairro").setAutoWidth(true);
        grid.addColumn("fieldEnderecosCEP").setHeader("CEP").setAutoWidth(true);
        grid.addColumn("fieldEnderecosCidade").setHeader("Cidade").setAutoWidth(true);
        //grid.addColumn("comboEnderecosUF").setHeader("UF").setAutoWidth(true);
        grid.addColumn(endereco -> {
            String uf_estado = endereco.getComboEnderecosUF().getUf_estado();
            return uf_estado != null ? uf_estado : "N/A";
        })
                .setHeader("UF")
                .setAutoWidth(true);
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
            SetTipoImovel TipoImovel = comboEnderecosTipoImovel.getValue();
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
            SetRegiao Regiao = comboEnderecosRegiao.getValue();
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
        Accordion accordion = new Accordion();
        AccordionPanel customDetailsPanel = accordion.add("Cadastrar Novo Endereço",
                formLayout);
        customDetailsPanel.addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                customDetailsPanel.setSummaryText("Cadastrar Novo Endereço");
            }
        });

        //formLayout.set(FlexComponent.Alignment.AUTO);

        VerticalLayout layout = new VerticalLayout(customDetailsPanel, grid);
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

        internetEmailField = new EmailField("E-mail");

        // Cria o botão para abrir o cliente de e-mail
        Button openEmailButton = new Button(VaadinIcon.ENVELOPE.create());
        openEmailButton.addClickListener(event -> {
            String email = internetEmailField.getValue();
            if (!email.isEmpty()) {
                // Abre o cliente de e-mail configurado no sistema do usuário
                getUI().ifPresent(ui ->
                        ui.getPage().executeJs("window.location.href = 'mailto:' + $0;", email)
                );
            }
        });
        internetEmailField.setSuffixComponent(openEmailButton);

        Icon whatsappIcon = VaadinIcon.PHONE.create();
        whatsappIcon.setColor("green");

        // Torna o ícone clicável com um Button
        Button whatsappButton = new Button(whatsappIcon);
        whatsappButton.addClickListener(event -> {
            // Ação ao clicar no ícone
            String phoneNumber = service.removeMascara(celularField.getValue());

            // Você pode abrir o WhatsApp Web com o número do telefone, por exemplo:
            getUI().ifPresent(ui -> ui.getPage().open("https://wa.me/55" + phoneNumber));
        });

        // Adiciona o botão com o ícone como sufixo no TextField
        celularField.setSuffixComponent(whatsappButton);

        service.configureCelularField(celularField);
        service.configureEmailField(internetEmailField);
        service.configureTelefoneResidencialField(telefoneField);




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
        internetEmailAgendamentoField = new EmailField("E-mail");
        observacaoAgendamentoField = new TextArea("Observação");

        // Cria o botão para abrir o cliente de e-mail
        Button openEmailButton = new Button(VaadinIcon.ENVELOPE.create());
        openEmailButton.addClickListener(event -> {
            String email = internetEmailAgendamentoField.getValue();
            if (!email.isEmpty()) {
                // Abre o cliente de e-mail configurado no sistema do usuário
                getUI().ifPresent(ui ->
                        ui.getPage().executeJs("window.location.href = 'mailto:' + $0;", email)
                );
            }
        });
        internetEmailAgendamentoField.setSuffixComponent(openEmailButton);

        Icon whatsappIcon = VaadinIcon.PHONE.create();
        whatsappIcon.setColor("green");

        // Torna o ícone clicável com um Button
        Button whatsappButton = new Button(whatsappIcon);
        whatsappButton.addClickListener(event -> {
            // Ação ao clicar no ícone
            String phoneNumber = service.removeMascara(celularAgendamentoField.getValue());

            // Você pode abrir o WhatsApp Web com o número do telefone, por exemplo:
            getUI().ifPresent(ui -> ui.getPage().open("https://wa.me/55" + phoneNumber));
        });

        // Adiciona o botão com o ícone como sufixo no TextField
        celularAgendamentoField.setSuffixComponent(whatsappButton);

        service.configureCelularField(celularAgendamentoField);
        service.configureEmailField(internetEmailAgendamentoField);
        service.configureTelefoneResidencialField(telefoneAgendamentoField);

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
        internetEmailAprovacaoField = new EmailField("E-mail Aprovador");
        observacaoAprovacaoField = new TextArea("Observação Aprovador");

        // Cria o botão para abrir o cliente de e-mail
        Button openEmailButton = new Button(VaadinIcon.ENVELOPE.create());
        openEmailButton.addClickListener(event -> {
            String email = internetEmailAprovacaoField.getValue();
            if (!email.isEmpty()) {
                // Abre o cliente de e-mail configurado no sistema do usuário
                getUI().ifPresent(ui ->
                        ui.getPage().executeJs("window.location.href = 'mailto:' + $0;", email)
                );
            }
        });
        internetEmailAprovacaoField.setSuffixComponent(openEmailButton);

        Icon whatsappIcon = VaadinIcon.PHONE.create();
        whatsappIcon.setColor("green");

        // Torna o ícone clicável com um Button
        Button whatsappButton = new Button(whatsappIcon);
        whatsappButton.addClickListener(event -> {
            // Ação ao clicar no ícone
            String phoneNumber = service.removeMascara(celularAprovacaoField.getValue());

            // Você pode abrir o WhatsApp Web com o número do telefone, por exemplo:
            getUI().ifPresent(ui -> ui.getPage().open("https://wa.me/55" + phoneNumber));
        });

        // Adiciona o botão com o ícone como sufixo no TextField
        celularAprovacaoField.setSuffixComponent(whatsappButton);

        service.configureCelularField(celularAprovacaoField);
        service.configureEmailField(internetEmailAprovacaoField);
        service.configureTelefoneResidencialField(telefoneAprovacaoField);

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
        internetEmailCobrancaField = new EmailField("E-mail Cobrança");
        observacaoCobrancaField = new TextArea("Observação Cobrança");


        // Cria o botão para abrir o cliente de e-mail
        Button openEmailButton = new Button(VaadinIcon.ENVELOPE.create());
        openEmailButton.addClickListener(event -> {
            String email = internetEmailCobrancaField.getValue();
            if (!email.isEmpty()) {
                // Abre o cliente de e-mail configurado no sistema do usuário
                getUI().ifPresent(ui ->
                        ui.getPage().executeJs("window.location.href = 'mailto:' + $0;", email)
                );
            }
        });
        internetEmailCobrancaField.setSuffixComponent(openEmailButton);


        Icon whatsappIcon = VaadinIcon.PHONE.create();
        whatsappIcon.setColor("green");

        // Torna o ícone clicável com um Button
        Button whatsappButton = new Button(whatsappIcon);
        whatsappButton.addClickListener(event -> {
            // Ação ao clicar no ícone
            String phoneNumber = service.removeMascara(celularCobrancaField.getValue());

            // Você pode abrir o WhatsApp Web com o número do telefone, por exemplo:
            getUI().ifPresent(ui -> ui.getPage().open("https://wa.me/55" + phoneNumber));
        });

        // Adiciona o botão com o ícone como sufixo no TextField
        celularCobrancaField.setSuffixComponent(whatsappButton);

        service.configureCelularField(celularCobrancaField);
        service.configureEmailField(internetEmailCobrancaField);
        service.configureTelefoneResidencialField(telefoneCobrancaField);

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

    private void save() {
        Cliente dto = newCliente();
        dto.setId_cliente(cliente.getId_cliente());
        SetCliente cliente = clientesService.update(dto);
        SetResponsavelAgendamento agendamento = newPessoaAgendamento(cliente.getId_cliente());
        agendamento.setId_responsavelagendamento(setResponsavelAgendamento.getId_responsavelagendamento());
        SetResponsavelAprovacao aprovacao = newPessoaAprovacao(cliente.getId_cliente());
        aprovacao.setId_responsavelaprovacao(setResponsavelAprovacao.getId_responsavelaprovacao());
        SetResponsavelCobranca cobranca = newPessoaCobranca(cliente.getId_cliente());
        cobranca.setId_responsavelcobranca(setResponsavelCobranca.getId_responsavelcobranca());
        responsavelCobrancaService.update(cobranca);
        responsavelAgendamentoService.update(agendamento);
        responsavelAprovacaoService.update(aprovacao);
        if (enderecos.size() > 0) {
            enderecoService.save(enderecos, cliente.getId_cliente(), 1);
        }
        close();
    }

    private SetResponsavelCobranca newPessoaCobranca(Integer id_cliente) {
        SetResponsavelCobranca cobranca = new SetResponsavelCobranca();
        cobranca.setNome_cobranca(nomeCobrancaField.getValue());
        cobranca.setTelefone_fixo(service.removeMascara(telefoneCobrancaField.getValue()));
        cobranca.setTelefone_celular(service.removeMascara(celularCobrancaField.getValue()));
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
        aprovacao.setTelefone_fixo(service.removeMascara(telefoneAprovacaoField.getValue()));
        aprovacao.setNome_social(nomesocialAprovacaoField.getValue());
        aprovacao.setTelefone_celular(service.removeMascara(celularAprovacaoField.getValue()));
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
        agendamento.setTelefone_fixo(service.removeMascara(telefoneAgendamentoField.getValue()));
        agendamento.setTelefone_celular(service.removeMascara(celularAgendamentoField.getValue()));
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
        cliente.setTelefoneField(service.removeMascara(telefoneField.getValue()));
        cliente.setCelularField(service.removeMascara(celularField.getValue()));

        cliente.setDataField(dataField.getValue());
        cliente.setHoraField(horaField.getValue());
        cliente.setContatoField(contatoField.getValue());
        cliente.setAdministradora(administradora.getValue().toString());
        cliente.setTipoMidia(tipoMidia.getValue().getId_tipomidia());
        cliente.setNomeIndicacaoField(midiaAntigaField.getValue());
        cliente.setInternetEmailField(internetEmailField.getValue());
        cliente.setFJField(FJField.getValue());
        cliente.setCGCCPFField(service.removeMascara(CGCCPFField.getValue()));
        cliente.setInscEstatualField(inscEstatualField.getValue());
        cliente.setObservacaoField(observacaoField.getValue());
        return cliente;
    }
//    public DadosClienteModal(SetCliente cliente, ClientesService clientesService, EstadoService estadoService, UsuarioService usuarioService, EnderecoService enderecoService, ResponsavelCobrancaService responsavelCobrancaService, ResponsavelAgendamentoService responsavelAgendamentoService, ResponsavelAprovacaoService responsavelAprovacaoService) {
//        new Title("Detalhes do Cliente");
//        this.clientesService = clientesService;
//        this.estadoService = estadoService;
//        this.usuarioService = usuarioService;
//        this.enderecoService =  enderecoService;
//        this.responsavelCobrancaService = responsavelCobrancaService;
//        this.responsavelAgendamentoService = responsavelAgendamentoService;
//        this.responsavelAprovacaoService = responsavelAprovacaoService;
//
//        UI.getCurrent().access(() -> {
//
//            saveButton = new Button("Salvar", eventbe -> save());
//            cancelButton = new Button("Cancelar", event -> close());
//            atendimentoButton = new Button("Atendimento e Histórico", event -> atendimentoAbrir(cliente
//            ));
//            tabs = new Tabs();
//            Tab tab1 = new Tab("Dados Empresa");
//            Tab tab2 = new Tab("Dados Agendamento");
//            Tab tab3 = new Tab("Aprovação");
//            Tab tab4 = new Tab("Cobrança");
//            Tab tab5 = new Tab("Dados Endereços");
//
//            tabs.add(tab1, tab2, tab3, tab4, tab5);
//            dadosEmpresa = createFormCadastroEmpresa(cliente);
//            dadosAgendamento = createFormCadastroAgendamento(cliente);
//            dadosCobranca = createFormCadastroCobranca(cliente);
//            dadosAprovacao = createFormCadastroAprovacao(cliente);
//            dadosEnderecos = createFormEnderecos(cliente);
//            dadosHistoricoAtendimento = createFormHistoricoAtendimento();
//
//            Div content = new Div(dadosEmpresa,
//                    dadosAgendamento,
//                    dadosCobranca,
//                    dadosAprovacao,
//                    dadosEnderecos,
//                    dadosHistoricoAtendimento);
//            content.setSizeFull();
//            dadosEmpresa.setVisible(true);
//            dadosAgendamento.setVisible(false);
//            dadosCobranca.setVisible(false);
//            dadosAprovacao.setVisible(false);
//            dadosEnderecos.setVisible(false);
//            dadosHistoricoAtendimento.setVisible(false);
//
//            tabs.addSelectedChangeListener(event -> {
//                dadosEmpresa.setVisible(false);
//                dadosAgendamento.setVisible(false);
//                dadosCobranca.setVisible(false);
//                dadosAprovacao.setVisible(false);
//                dadosEnderecos.setVisible(false);
//                dadosHistoricoAtendimento.setVisible(false);
//
//                Tab selectedTab = tabs.getSelectedTab();
//                if (selectedTab.equals(tab1)) {
//                    dadosEmpresa.setVisible(true);
//                } else if (selectedTab.equals(tab2)) {
//                    dadosAgendamento.setVisible(true);
//                } else if (selectedTab.equals(tab3)) {
//                    dadosAprovacao.setVisible(true);
//                } else if (selectedTab.equals(tab4)) {
//                    dadosCobranca.setVisible(true);
//                } else if (selectedTab.equals(tab5)) {
//                    dadosEnderecos.setVisible(true);
//                }
//            });
//
//            Div contentTabs = new Div(dadosEmpresa,
//                    dadosAgendamento,
//                    dadosAprovacao,
//                    dadosCobranca,
//                    dadosEnderecos,
//                    dadosHistoricoAtendimento);
//            contentTabs.setSizeFull();
//            atendimentoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//            getFooter().add(saveButton, cancelButton,atendimentoButton);
//            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
//            add(layout);
//        });
//    }
}
