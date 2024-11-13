package br.com.onetec.application.views.main.seguranca.div;

import br.com.onetec.application.model.Endereco;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.dadosempresaservice.DadosEmpresaService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.service.grupousuarioservice.GrupoUsuarioService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.service.utilservices.ApiEnderecoService;
import br.com.onetec.application.views.main.seguranca.modal.GrupoUsuarioCadastroModal;
import br.com.onetec.cross.utilities.CustomizedComboBox;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@UIScope
public class DadosEmpresaDiv extends Div {

    private UtilitySystemConfigService service;
    private UsuarioService usuarioService;
    private GrupoUsuarioCadastroModal grupoUsuarioCadastroModal;
    private FuncionarioService funcionarioService;
    private GrupoUsuarioService grupoUsuarioService;
    private DadosEmpresaService dadosEmpresaService;
    private EstadoService estadoService;

    //controles
    private Button btnExcluir;
    private Button btnSalvar;

    //formulario
    private TextField nome_dadosempresa;
    private TextField endereco_dadosempresa;
    private TextField bairro_dadosempresa;
    private TextField cep_dadosempresa;
    private TextField cidade_dadosempresa;
    private ComboBox<SetEstado> estado_dadosempresa;
    private TextField telefone_dadosempresa;
    private TextField celular_dadosempresa;
    private TextField email_dadosempresa;
    private TextField cnpj_dadosempresa;
    private DatePicker dataestoque_dadosempresa;
    private TextField nomequimico_dadosempresa;
    private TextField numeroalvaraquimico_dadosempresa;
    private TextField telefonequimico_dadosempresa;
    private TextField celularquimico_dadosempresa;
    private TextField emailquimico_dadosempresa;

    private List<SetEstado> estadoList = new ArrayList<>();
    SetDadosEmpresa setDadosEmpresa;
    private ApiEnderecoService enderecoService;

    @Autowired
    public void initServices(UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             GrupoUsuarioCadastroModal grupoUsuarioCadastroModal1,
                             FuncionarioService funcionarioService1,
                             GrupoUsuarioService grupoUsuarioService1,
                             EstadoService estadoService1,
                             DadosEmpresaService dadosEmpresaService1,
                             ApiEnderecoService enderecoService1) {
        ;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.grupoUsuarioCadastroModal = grupoUsuarioCadastroModal1;
        this.funcionarioService = funcionarioService1;
        this.grupoUsuarioService = grupoUsuarioService1;
        this.estadoService = estadoService1;
        this.dadosEmpresaService = dadosEmpresaService1;
        this.enderecoService = enderecoService1;

        estadoList = estadoService.listAll();

    }

    @Autowired
    public DadosEmpresaDiv() {
        UI.getCurrent().access(() -> {
            add(telaDiv());
            configuraTela();
        });
    }



    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");
        Div div = new Div();

        nome_dadosempresa = new TextField("Nome");
        endereco_dadosempresa = new TextField("Endereço");
        bairro_dadosempresa = new TextField("Bairro");
        cep_dadosempresa = new TextField("CEP");
        cidade_dadosempresa = new TextField("Cidade");
        estado_dadosempresa = new ComboBox<>("UF");
        telefone_dadosempresa = new TextField("Telefone Fixo");
        celular_dadosempresa = new TextField("Celular");
        email_dadosempresa = new TextField("E-mail");
        cnpj_dadosempresa = new TextField("CNPJ");
        dataestoque_dadosempresa = new DatePicker("Data Inicio Estoque");
        nomequimico_dadosempresa = new TextField("Quimico Responsável");
        numeroalvaraquimico_dadosempresa = new TextField("Alvara CV's");
        telefonequimico_dadosempresa = new TextField("Telefone Quimico");
        celularquimico_dadosempresa = new TextField("Celular Quimico");
        emailquimico_dadosempresa = new TextField("E-mail Quimico");

        service.configureCEPField(cep_dadosempresa);
        service.configureCelularField(celular_dadosempresa);
        service.configureCelularField(celularquimico_dadosempresa);
        service.configureCNPJTextField(cnpj_dadosempresa);
        service.configuraCalendario(dataestoque_dadosempresa);
        service.configureTelefoneResidencialField(telefone_dadosempresa);
        service.configureTelefoneResidencialField(telefonequimico_dadosempresa);

        estadoList = estadoService.listAll();
        estado_dadosempresa.setItems(estadoList);
        estado_dadosempresa.setItemLabelGenerator(SetEstado::getUf_estado);

        Button buscaEnderecosCEPButton = new Button("Buscar CEP", e -> buscarCep());
        HorizontalLayout fieldEnderecosCEPCustomized =
                new CustomizedComboBox().customizeEnderecosCEP(cep_dadosempresa,buscaEnderecosCEPButton);


        // Botão para salvar o endereço
        Button saveButton = new Button("Atualizar", event -> {
            setDadosEmpresa.setNome_dadosempresa(nome_dadosempresa.getValue());
            setDadosEmpresa.setEndereco_dadosempresa(endereco_dadosempresa.getValue());
            setDadosEmpresa.setBairro_dadosempresa(bairro_dadosempresa.getValue());
            setDadosEmpresa.setCep_dadosempresa(cep_dadosempresa.getValue());
            setDadosEmpresa.setCidade_dadosempresa(cidade_dadosempresa.getValue());
            setDadosEmpresa.setTelefone_dadosempresa(telefone_dadosempresa.getValue());
            setDadosEmpresa.setCelular_dadosempresa(celular_dadosempresa.getValue());
            setDadosEmpresa.setEmail_dadosempresa(email_dadosempresa.getValue());
            setDadosEmpresa.setCnpj_dadosempresa(cnpj_dadosempresa.getValue());
            setDadosEmpresa.setDataestoque_dadosempresa(dataestoque_dadosempresa.getValue());
            setDadosEmpresa.setNomequimico_dadosempresa(nomequimico_dadosempresa.getValue());
            setDadosEmpresa.setNumeroalvaraquimico_dadosempresa(numeroalvaraquimico_dadosempresa.getValue());
            setDadosEmpresa.setTelefonequimico_dadosempresa(telefonequimico_dadosempresa.getValue());
            setDadosEmpresa.setCelularquimico_dadosempresa(celularquimico_dadosempresa.getValue());
            setDadosEmpresa.setEmailquimico_dadosempresa(emailquimico_dadosempresa.getValue());

            try {
                dadosEmpresaService.update(setDadosEmpresa);
                service.notificaSucesso("Atualizado com Sucesso !");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        FormLayout formLayout =  new FormLayout( nome_dadosempresa,
                endereco_dadosempresa,
                bairro_dadosempresa,
                fieldEnderecosCEPCustomized,
                cidade_dadosempresa,
                estado_dadosempresa,
                telefone_dadosempresa,
                celular_dadosempresa,
                email_dadosempresa,
                cnpj_dadosempresa,
                dataestoque_dadosempresa,
                nomequimico_dadosempresa,
                numeroalvaraquimico_dadosempresa,
                telefonequimico_dadosempresa,
                celularquimico_dadosempresa,
                emailquimico_dadosempresa);
        formLayout.setWidthFull();

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout layout = new VerticalLayout(formLayout);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);


        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setWidthFull();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout rightButtons = new HorizontalLayout(saveButton);
        footerLayout.add(rightButtons); // Alinha à direita

        div.add(layout,footerLayout);
        div.setSizeFull();
        return div;
    }

    private void buscarCep() {
        try {
            estadoList = estadoService.listAll();
            service = new UtilitySystemConfigService();
            EApiEnderecoResponse response = service.buscarCepTeste(cep_dadosempresa,enderecoService);
            endereco_dadosempresa.setValue(response.getLogradouro());
            bairro_dadosempresa.setValue(response.getBairro());
            cidade_dadosempresa.setValue(response.getLocalidade());
            estado_dadosempresa.setValue(service.configuraUF(estadoList, response.getUf()));
        } catch (Exception e){
            service.notificaErro("CEP NÃO ENCONTRADO !");
        }
    }

    private void configuraTela() {
        setDadosEmpresa = dadosEmpresaService.getDados(1);
        SetDadosEmpresa item = setDadosEmpresa;
        nome_dadosempresa.setValue(item.getNome_dadosempresa());
        endereco_dadosempresa.setValue(item.getEndereco_dadosempresa());
        bairro_dadosempresa.setValue(item.getBairro_dadosempresa());
        cep_dadosempresa.setValue(item.getCep_dadosempresa());
        cidade_dadosempresa.setValue(item.getCidade_dadosempresa());
        telefone_dadosempresa.setValue(item.getTelefone_dadosempresa());
        celular_dadosempresa.setValue(item.getCelular_dadosempresa());
        email_dadosempresa.setValue(item.getEmail_dadosempresa());
        cnpj_dadosempresa.setValue(item.getCnpj_dadosempresa());
        dataestoque_dadosempresa.setValue(item.getDataestoque_dadosempresa());
        nomequimico_dadosempresa.setValue(item.getNomequimico_dadosempresa());
        numeroalvaraquimico_dadosempresa.setValue(item.getNumeroalvaraquimico_dadosempresa());
        telefonequimico_dadosempresa.setValue(item.getTelefonequimico_dadosempresa());
        celularquimico_dadosempresa.setValue(item.getCelularquimico_dadosempresa());
        emailquimico_dadosempresa.setValue(item.getEmailquimico_dadosempresa());
    }
}
