package br.com.onetec.cross.utilities;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.fornecedorservice.FornecedorService;
import br.com.onetec.application.service.setoratuacaoservice.SetorAtuacaoService;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFornecedorContato;
import br.com.onetec.infra.db.model.SetSetorAtuacao;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class AutoCrudFornecedorService {

    private static List<SetEstado> estadoList;
    private Button saveButton;
    private Button cancelButton;
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
    private EstadoService estadoService;
    private UtilitySystemConfigService service;
    private SetorAtuacaoService setorAtuacaoService;
    private FornecedorService fornecedorService;
    private List<SetFornecedorContato> listaContatosFornecedor;

    public void openFormDialog(ComboBox<SetFornecedor> id_fornecedor,
                               FornecedorService fornecedorService, EstadoService estadoService1,
                               SetorAtuacaoService setorAtuacaoService1) {
        UtilitySystemConfigService servico = new UtilitySystemConfigService();
        this.estadoService = estadoService1;
        this.service = servico;
        this.fornecedorService = fornecedorService;
        this.setorAtuacaoService = setorAtuacaoService1;
        // Cria o diálogo
        Dialog dialog = new Dialog();


        // Layout do formulário
        FormLayout formLayout = fornecedorCadastroModal(fornecedorService, id_fornecedor);


        // Botão para salvar os dados
        Button saveButton = new Button("Salvar", event -> {
            save(id_fornecedor);
            dialog.close();
        });

        // Botão para fechar o diálogo
        Button closeButton = new Button("Fechar", event -> dialog.close());

        // Adiciona o formulário e os botões ao diálogo
        dialog.add(formLayout);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton, closeButton);
        H2 title = new H2("Cadastro Fornecedor");
        dialog.getHeader().add(title);

        // Abre o diálogo
        dialog.open();
    }

    private FormLayout fornecedorCadastroModal
            (FornecedorService fornecedorService, ComboBox<SetFornecedor> id_fornecedor) {
        FormLayout formLayout = new FormLayout();
        UI.getCurrent().access(() -> {
            id_estado = new ComboBox<>("UF");
            id_estado.setItems(getUFList());
            id_estado.setItemLabelGenerator(SetEstado::getUf_estado);

            cadastroFornecedor = createFormCadastroFuncionario();

            Div contentTabs = new Div(cadastroFornecedor);
            contentTabs.setSizeFull();


            VerticalLayout layout = new VerticalLayout(contentTabs);
            formLayout.add(layout);
        });
        return formLayout;
    }

    private void save(ComboBox<SetFornecedor> id_fornecedor) {

        try {
            SetFornecedor fornecedor = new SetFornecedor();
            if (id_estado.isEmpty()) {
                id_estado.setRequiredIndicatorVisible(true);
                id_estado.setErrorMessage("Campo obrigatório");
                id_estado.setInvalid(true);
            } else if (id_setoratuacao.isEmpty()) {
                id_setoratuacao.setRequiredIndicatorVisible(true);
                id_setoratuacao.setErrorMessage("Campo obrigatório");
                id_setoratuacao.setInvalid(true);
            } else if (data_cadastro.isEmpty()) {
                data_cadastro.setRequiredIndicatorVisible(true);
                data_cadastro.setErrorMessage("Campo obrigatório");
                data_cadastro.setInvalid(true);
            } else if (tipo_naturezajuridica.isEmpty()) {
                tipo_naturezajuridica.setRequiredIndicatorVisible(true);
                tipo_naturezajuridica.setErrorMessage("Campo obrigatório");
                tipo_naturezajuridica.setInvalid(true);
            } else if (numero_naturezajuridica.isEmpty()) {
                numero_naturezajuridica.setRequiredIndicatorVisible(true);
                numero_naturezajuridica.setErrorMessage("Campo obrigatório");
                numero_naturezajuridica.setInvalid(true);
            } else if (razaosocial_fornecedor.isEmpty()) {
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
                var novaLista = fornecedorService.findAll();
                id_fornecedor.setItems(novaLista);
            }
        } catch (Exception e) {
            service.notificaErro("Por favor, preencha todos os campos obrigatórios.");
            e.printStackTrace();
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

        id_setoratuacao.addFocusListener(event -> {
            id_setoratuacao.setItems(setorAtuacaoService.listAll());
        });

        tipo_naturezajuridica.setItems(List.of("Pessoa Fisica", "Pessoa Juridica"));
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

    private void buscarCep() {
        EApiEnderecoResponse response = service.buscarCep(cep_fornecedor);
        endereco_fornecedor.setValue(response.getLogradouro());
        //complemento_funcionario.setValue(response.get);
        bairro_fornecedor.setValue(response.getBairro());
        cidade_fornecedor.setValue(response.getLocalidade());
        id_estado.setValue(service.configuraUF(estadoList, response.getUf()));
    }
}
