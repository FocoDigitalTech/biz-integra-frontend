package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.fornecedorcontatoservice.FornecedorContatoService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.setoratuacaoservice.SetorAtuacaoService;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.CrudGrid;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class FornecedorCadastroModal extends Dialog {



    private Button saveButton;
    private  Button cancelButton;

    //Cadastro Fornecedor
    private Div cadastroFornecedor;
    private DatePicker data_cadastro;
    private ComboBox<SetSetorAtuacao> id_setoratuacao;
    private ComboBox<String> tipo_naturezajuridica;
    private TextField numero_naturezajuridica;
    private TextField razaosocial_fornecedor;
    private TextField nomefantasia_fornecedor;
    private TextField cep_fornecedor;
    private TextField endereco_fornecedor;
    private TextField numero_endereco;
    private TextField bairro_fornecedor;
    private TextField cidade_fornecedor;
    private ComboBox<SetEstado> id_estado;
    private TextField telefone_fornecedor;
    private TextField email_fornecedor;
    private TextField nomecontato_fornecedor;
    private TextField cargocontato_fornecedor;
    private TextField inscicaoestadual_fornecedor;
    private TextArea observacao_fornecedor;


    //Cadastro Contatos Fornecedor
    private Div cadastroFornecedorContatos;
    private TextField nome_fornecedorcontato;
    private TextField cargo_fornecedorcontato;
    private TextField departamento_fornecedorcontato;
    private TextField telefone_fornecedorcontato;
    private TextField email_fornecedorcontato;
    private TextArea observacoes_fornecedorcontato;


    private EstadoService estadoService ;
    private UtilitySystemConfigService service;
    private SetorAtuacaoService setorAtuacaoService;
    private FornecedorService fornecedorService;
    private FornecedorContatoService fornecedorContatoService;

    private List<SetFornecedorContato> listaContatosFornecedor;
    private static List<SetEstado> estadoList;

    @Autowired
    @Lazy
    FornecedorDiv fornecedorDiv;

    @Autowired
    public void initServices(EstadoService serviceEstado, UtilitySystemConfigService service,
                             SetorAtuacaoService setorAtuacaoService1,
                             FornecedorService fornecedorService1,
                             FornecedorContatoService fornecedorContatoService1) {
        this.estadoService = serviceEstado;
        this.service = service;
        this.fornecedorContatoService = fornecedorContatoService1;
        this.fornecedorService = fornecedorService1;
        this.setorAtuacaoService = setorAtuacaoService1;
        //configurações dos fields:
        UI.getCurrent().access(() -> {
            service.configureCEPField(cep_fornecedor);
            service.configureTelefoneResidencialField(telefone_fornecedor);
            service.configuraCalendario(data_cadastro);
        });
    }


    @Autowired
    public FornecedorCadastroModal(EstadoService serviceEstado, UtilitySystemConfigService service,
                                   SetorAtuacaoService setorAtuacaoService1,
                                   FornecedorService fornecedorService1,
                                   FornecedorContatoService fornecedorContatoService1) {
        this.estadoService = serviceEstado;
        this.service = service;
        this.fornecedorContatoService = fornecedorContatoService1;
        this.fornecedorService = fornecedorService1;
        this.setorAtuacaoService = setorAtuacaoService1;

        UI.getCurrent().access(() -> {
            setHeaderTitle("Cadastro Dados Fornecedor");

            id_estado = new ComboBox<>("UF");
            id_estado.setItems(getUFList());
            id_estado.setItemLabelGenerator(SetEstado::getUf_estado);


            addClassName("cadastro-modal");
            saveButton = new Button("Salvar", eventbe -> save());
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));

            Tabs tabs = new Tabs();
            Tab tab1 = new Tab("Orçamento");
            Tab tab2 = new Tab("Contrato");

            tabs.add(tab1, tab2);

            cadastroFornecedor = createFormCadastroFuncionario();
            cadastroFornecedorContatos = createFormCadastroFornecedorContatos();

            Div content = new Div(cadastroFornecedor, cadastroFornecedorContatos);
            content.setSizeFull();
            cadastroFornecedor.setVisible(true);
            cadastroFornecedorContatos.setVisible(false);

            tabs.addSelectedChangeListener(event -> {
                cadastroFornecedor.setVisible(false);
                cadastroFornecedorContatos.setVisible(false);

                Tab selectedTab = tabs.getSelectedTab();
                if (selectedTab.equals(tab1)) {
                    cadastroFornecedor.setVisible(true);
                } else if (selectedTab.equals(tab2)) {
                    cadastroFornecedorContatos.setVisible(true);
                }
            });

            Div contentTabs = new Div(cadastroFornecedor, cadastroFornecedorContatos);
            contentTabs.setSizeFull();

            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add();

            // Criando o layout do rodapé e ajustando o alinhamento dos botões

            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(tabs, contentTabs);
            add(layout);
        });
    }

    private void save() {

        try {
            SetFornecedor fornecedor = new SetFornecedor();
            if (id_estado.isEmpty()) {
                id_estado.setRequiredIndicatorVisible(true);
                id_estado.setErrorMessage("Campo obrigatório");
                id_estado.setInvalid(true);
            }else if (id_setoratuacao.isEmpty()) {
                id_setoratuacao.setRequiredIndicatorVisible(true);
                id_setoratuacao.setErrorMessage("Campo obrigatório");
                id_setoratuacao.setInvalid(true);
            }else if (data_cadastro.isEmpty()) {
                data_cadastro.setRequiredIndicatorVisible(true);
                data_cadastro.setErrorMessage("Campo obrigatório");
                data_cadastro.setInvalid(true);
            }else if  (tipo_naturezajuridica.isEmpty()) {
                tipo_naturezajuridica.setRequiredIndicatorVisible(true);
                tipo_naturezajuridica.setErrorMessage("Campo obrigatório");
                tipo_naturezajuridica.setInvalid(true);
            }else if (numero_naturezajuridica.isEmpty()) {
                numero_naturezajuridica.setRequiredIndicatorVisible(true);
                numero_naturezajuridica.setErrorMessage("Campo obrigatório");
                numero_naturezajuridica.setInvalid(true);
            }else if (razaosocial_fornecedor.isEmpty()) {
                razaosocial_fornecedor.setRequiredIndicatorVisible(true);
                razaosocial_fornecedor.setErrorMessage("Campo obrigatório");
                razaosocial_fornecedor.setInvalid(true);
            } else if (nomefantasia_fornecedor.isEmpty()) {
                nomefantasia_fornecedor.setRequiredIndicatorVisible(true);
                nomefantasia_fornecedor.setErrorMessage("Campo obrigatório");
                nomefantasia_fornecedor.setInvalid(true);
            } else {

                if (id_estado.getValue() != null) {
                    fornecedor.setId_estado(id_estado.getValue().getId_estado());
                }
                if (id_setoratuacao.getValue() != null) {
                    fornecedor.setId_setoratuacao(id_setoratuacao.getValue().getId_setoratuacao());
                }

                // Define os valores dos campos no objeto SetFuncionario
                fornecedor.setData_cadastro(data_cadastro.getValue());
                fornecedor.setTipo_naturezajuridica(tipo_naturezajuridica.getValue());
                fornecedor.setNumero_naturezajuridica(numero_naturezajuridica.getValue());
                fornecedor.setNomecontato_fornecedor(nomecontato_fornecedor.getValue());
                fornecedor.setRazaosocial_fornecedor(razaosocial_fornecedor.getValue());
                fornecedor.setNomefantasia_fornecedor(nomefantasia_fornecedor.getValue());
                fornecedor.setCep_fornecedor(cep_fornecedor.getValue());
                fornecedor.setEndereco_fornecedor(endereco_fornecedor.getValue());
                fornecedor.setNumero_endereco(numero_endereco.getValue());
                fornecedor.setBairro_fornecedor(bairro_fornecedor.getValue());
                fornecedor.setCidade_fornecedor(cidade_fornecedor.getValue());
                fornecedor.setTelefone_fornecedor(telefone_fornecedor.getValue());
                fornecedor.setEmail_fornecedor(email_fornecedor.getValue());
                fornecedor.setCargocontato_fornecedor(cargocontato_fornecedor.getValue());
                fornecedor.setInscicaoestadual_fornecedor(inscicaoestadual_fornecedor.getValue());
                fornecedor.setObservacao_fornecedor(observacao_fornecedor.getValue());
                fornecedor.setData_inclusao(LocalDateTime.now());
                fornecedor.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                fornecedor.setAtivo("S");
                fornecedorService.save(fornecedor);
                service.notificaSucesso("Cadastrado com sucesso");
                fornecedorDiv.refreshGrid();
                close();
            }
        } catch (Exception e){
            Notification.show("Erro ao Salvar");
        }


    }

    private List<SetEstado> getUFList() {
        estadoList = estadoService.listAll();
        return estadoList;
    }

    private Div createFormCadastroFuncionario() {

        nomecontato_fornecedor = new TextField("Nome Responsável");
        nomefantasia_fornecedor = new TextField("Nome Fornecedor");
        numero_naturezajuridica = new TextField("Número CNPJ");
        data_cadastro = new DatePicker("Data");
        endereco_fornecedor = new TextField("Endereço");
        bairro_fornecedor = new TextField("Bairro");
        cidade_fornecedor = new TextField("Cidade");
        numero_endereco = new TextField("N°");
        cep_fornecedor = new TextField("CEP");
        tipo_naturezajuridica = new ComboBox<>("Natureza Juridica");
        id_setoratuacao = new ComboBox<>("Setor Atuação");
        razaosocial_fornecedor = new TextField("Razão Social");
        id_estado = new ComboBox<>("UF");
        telefone_fornecedor = new TextField("Telefone");
        email_fornecedor = new TextField("E-mail");
        cargocontato_fornecedor = new TextField("Cargo");
        inscicaoestadual_fornecedor = new TextField("Incrição Estadual");
        observacao_fornecedor = new TextArea("Observações");
        id_estado.setItems(estadoService.listAll());
        id_estado.setItemLabelGenerator(SetEstado::getUf_estado);

        id_setoratuacao.setItems(setorAtuacaoService.listAll());
        id_setoratuacao.setItemLabelGenerator(SetSetorAtuacao::getDescricao_setoratuacao);

        tipo_naturezajuridica.setItems(List.of("Pessoa Fisica","Pessoa Juridica"));
        tipo_naturezajuridica.addValueChangeListener(event -> {
            if ("Pessoa Fisica".equals(event.getValue())) {
                numero_naturezajuridica.clear();
                numero_naturezajuridica.setLabel("Número CPF");
                service.configureCPFField(numero_naturezajuridica);

            } else if ("Pessoa Juridica".equals(event.getValue())) {
                numero_naturezajuridica.clear();
                numero_naturezajuridica.setLabel("Número CNPJ");
                service.configureCNPJTextField(numero_naturezajuridica);
            }
        });


        cep_fornecedor.addBlurListener(event -> buscarCep());
         FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(data_cadastro,
                id_setoratuacao,
                tipo_naturezajuridica,
                numero_naturezajuridica,
                nomecontato_fornecedor,
                razaosocial_fornecedor,
                nomefantasia_fornecedor,
                cep_fornecedor,
                endereco_fornecedor,
                numero_endereco,
                bairro_fornecedor,
                cidade_fornecedor,
                id_estado,
                telefone_fornecedor,
                email_fornecedor,
                cargocontato_fornecedor,
                inscicaoestadual_fornecedor,
                observacao_fornecedor);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }


    private Grid<SetFornecedorContato> gridContatos;

    private Div createFormCadastroFornecedorContatos() {
        listaContatosFornecedor = new ArrayList<>();

        gridContatos = new Grid<>();
        gridContatos.addColumn(SetFornecedorContato::getNome_fornecedorcontato)
                .setHeader("Nome")
                .setSortable(true)
                .setAutoWidth(true);
        gridContatos.addColumn(SetFornecedorContato::getCargo_fornecedorcontato)
                .setHeader("Cargo")
                .setSortable(true)
                .setAutoWidth(true);
        gridContatos.addColumn(SetFornecedorContato::getEmail_fornecedorcontato)
                .setHeader("E-mail")
                .setSortable(true)
                .setAutoWidth(true);
        gridContatos.addColumn(SetFornecedorContato::getTelefone_fornecedorcontato)
                .setHeader("Telefone")
                .setSortable(true)
                .setAutoWidth(true);

          nome_fornecedorcontato = new TextField("Nome");
          cargo_fornecedorcontato = new TextField("Cargo");
          departamento_fornecedorcontato = new TextField("Departamento");
          telefone_fornecedorcontato = new TextField("Telefone");
          email_fornecedorcontato = new TextField("E-mail");
          observacoes_fornecedorcontato = new TextArea("Observções");

          service.configureTelefoneResidencialField(telefone_fornecedorcontato);



        Button saveButton = new Button("Adicionar Endereço", event -> {
            if (nome_fornecedorcontato.isEmpty()) {
                nome_fornecedorcontato.setRequiredIndicatorVisible(true);
                nome_fornecedorcontato.setErrorMessage("Campo obrigatório");
                nome_fornecedorcontato.setInvalid(true);
            }else if (email_fornecedorcontato.isEmpty()) {
                email_fornecedorcontato.setRequiredIndicatorVisible(true);
                email_fornecedorcontato.setErrorMessage("Campo obrigatório");
                email_fornecedorcontato.setInvalid(true);
            } else if (cargo_fornecedorcontato.isEmpty()) {
                cargo_fornecedorcontato.setRequiredIndicatorVisible(true);
                cargo_fornecedorcontato.setErrorMessage("Campo obrigatório");
                cargo_fornecedorcontato.setInvalid(true);
            } else {

                SetFornecedorContato contato = new SetFornecedorContato();
                contato.setNome_fornecedorcontato(nome_fornecedorcontato.getValue());
                contato.setCargo_fornecedorcontato(cargo_fornecedorcontato.getValue());
                contato.setDepartamento_fornecedorcontato(departamento_fornecedorcontato.getValue());
                contato.setTelefone_fornecedorcontato(telefone_fornecedorcontato.getValue());
                contato.setEmail_fornecedorcontato(email_fornecedorcontato.getValue());
                contato.setObservacoes_fornecedorcontato(observacoes_fornecedorcontato.getValue());
                listaContatosFornecedor.add(contato);
                Notification.show("Contato Adicionado!");
                nome_fornecedorcontato.clear();
                cargo_fornecedorcontato.clear();
                departamento_fornecedorcontato.clear();
                telefone_fornecedorcontato.clear();
                email_fornecedorcontato.clear();
                observacoes_fornecedorcontato.clear();
                gridContatos.setItems(listaContatosFornecedor);
            }


        });

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_fornecedorcontato,
                cargo_fornecedorcontato,
                departamento_fornecedorcontato,
                telefone_fornecedorcontato,
                email_fornecedorcontato,
                observacoes_fornecedorcontato,saveButton);

        VerticalLayout layout = new VerticalLayout(formLayout, gridContatos);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        Div div = new Div();
        div.add(layout);

        return div;
    }


    private void buscarCep() {
        EApiEnderecoResponse response = service.buscarCep(cep_fornecedor);
        endereco_fornecedor.setValue(response.getLogradouro());
        //complemento_funcionario.setValue(response.get);
        bairro_fornecedor.setValue(response.getBairro());
        cidade_fornecedor.setValue(response.getLocalidade());
        id_estado.setValue(service.configuraUF(estadoList, response.getUf()));
    }






}
