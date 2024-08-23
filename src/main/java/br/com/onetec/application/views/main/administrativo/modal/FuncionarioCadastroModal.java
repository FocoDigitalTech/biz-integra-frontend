package br.com.onetec.application.views.main.administrativo.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Departamento;
import br.com.onetec.application.service.clientesservice.EstadoService;
import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.cross.utilities.Servicos;
import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


@Component
public class FuncionarioCadastroModal extends Dialog {


    private final FuncionarioService funcionarioService;
    private final DepartamentoService departamentoService;

    private final Button saveButton;
    private final Button cancelButton;
    private ComboBox<SetDepartamento> id_departamento;
    private TextField nome_funcionario;
    private TextField nome_carteira;
    private TextField endereco_funcionario;
    private TextField complemento_funcionario;
    private TextField bairro_funcionario;
    private TextField cep_funcionario;
    private TextField cidade_funcionario;
    private ComboBox<SetEstado> id_estado;
    private TextField celular_funcionario;
    private TextField rg_funcionario;
    private TextField cpf_funcionario;
    private TextField titulo_eleitor;
    private TextField reservista_militar;
    private TextField numero_ctps;
    private TextField serie_ctps;
    private TextField pis_funcionario;
    private TextField cnh_funcionario;
    private TextField numero_imovel;
    private DatePicker vencimento_cnh;
    private DatePicker data_admissao;


    private EstadoService estadoService ;

    private Servicos service;

    private static List<SetEstado> estadoList;

    @Autowired
    public void initServices(EstadoService serviceEstado, Servicos service) {
        this.estadoService = serviceEstado;
        this.service = service;
        //configurações dos fields:
        service.configureCEPField(cep_funcionario);
        service.configureCelularField(celular_funcionario);
        service.configureCPFField(cpf_funcionario);
        service.configuraCalendario(data_admissao);
        service.configuraCalendario(vencimento_cnh);
    }


    @Autowired
    public FuncionarioCadastroModal(FuncionarioService funcionarioService, DepartamentoService departamentoService,
                                    EstadoService estadoService) {
        this.funcionarioService = funcionarioService;
        this.departamentoService = departamentoService;
        this.estadoService = estadoService;
        id_estado = new ComboBox<>("UF");
        id_estado.setItems(getUFList());
        id_estado.setItemLabelGenerator(SetEstado::getUf_estado);




        addClassName("cadastro-modal");
        saveButton = new Button("Salvar", eventbe -> save());
        cancelButton = new Button("Cancelar", event -> close());

        Div contentTabs = new Div(createFormCadastroFuncionario());


        contentTabs.setSizeFull();

        VerticalLayout layout = new VerticalLayout(contentTabs, saveButton, cancelButton);
        add(layout);
    }

    private void save() {

        try {
            SetFuncionario funcionario = new SetFuncionario();

            SetDepartamento departamento = id_departamento.getValue();
            if (departamento != null) {
                funcionario.setId_departamento(departamento.getId_departamento());
            }
            if (id_estado.getValue() != null) {
                funcionario.setId_estado(id_estado.getValue().getId_estado());
            }

            // Define os valores dos campos no objeto SetFuncionario
            funcionario.setNome_funcionario(nome_funcionario.getValue());
            funcionario.setNome_carteira(nome_carteira.getValue());
            funcionario.setEndereco_funcionario(endereco_funcionario.getValue());
            funcionario.setComplemento_funcionario(complemento_funcionario.getValue());
            funcionario.setBairro_funcionario(bairro_funcionario.getValue());
            funcionario.setCep_funcionario(service.removeMascara(cep_funcionario.getValue()));
            funcionario.setCidade_funcionario(cidade_funcionario.getValue());
            funcionario.setCelular_funcionario(service.removeMascara(celular_funcionario.getValue()));
            funcionario.setRg_funcionario(rg_funcionario.getValue());
            funcionario.setCpf_funcionario(service.removeMascara(cpf_funcionario.getValue()));
            funcionario.setTitulo_eleitor(titulo_eleitor.getValue());
            funcionario.setReservista_militar(reservista_militar.getValue());
            funcionario.setNumero_ctps(numero_ctps.getValue());
            funcionario.setSerie_ctps(serie_ctps.getValue());
            funcionario.setPis_funcionario(pis_funcionario.getValue());
            funcionario.setCnh_funcionario(cnh_funcionario.getValue());
            funcionario.setVencimento_cnh(vencimento_cnh.getValue());
            funcionario.setData_admissao(data_admissao.getValue());
            funcionario.setNumeroimovel_funcionario(numero_imovel.getValue());
            funcionario.setData_inclusao(LocalDateTime.now());
            funcionario.setId_funcionario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            funcionario.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            funcionario.setAtivo("S");
            funcionarioService.save(funcionario);
            Notification.show("Salvo com sucesso");
            close();
        } catch (Exception e){
            Notification.show("Erro ao Salvar");
        }


    }

    private List<SetEstado> getUFList() {
        estadoList = estadoService.listAll();
        return estadoList;
    }

    private Div createFormCadastroFuncionario() {
        id_departamento = new ComboBox<SetDepartamento>("Departamento");
        id_departamento.setItems(departamentoService.findAllDepartamento());
        id_departamento.setItemLabelGenerator(SetDepartamento::getDescricao_departamento);

        nome_funcionario = new TextField("Nome");
        nome_carteira = new TextField("Carteira");
        celular_funcionario = new TextField("Celular");
        rg_funcionario = new TextField("Rg");
        cpf_funcionario = new TextField("CPF");

        titulo_eleitor = new TextField("N° Titulo Eleitor");
        reservista_militar = new TextField("N° Reservista");
        numero_ctps = new TextField("CTPS N°");
        serie_ctps = new TextField("Série");
        pis_funcionario = new TextField("N° Pis");
        cnh_funcionario = new TextField("Numero CNH");
        vencimento_cnh = new DatePicker("Vencimento");
        data_admissao = new DatePicker("Data Admissão");


        endereco_funcionario = new TextField("Endereço");
        complemento_funcionario = new TextField("Complemento");
        bairro_funcionario = new TextField("Bairro");

        cep_funcionario = new TextField("CEP");
        cep_funcionario.addBlurListener(event -> buscarCep());

        cidade_funcionario = new TextField("Cidade");
        numero_imovel = new TextField("N° Residencia");






        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(id_departamento,nome_funcionario,
                nome_carteira,celular_funcionario,rg_funcionario,
                cpf_funcionario,titulo_eleitor,reservista_militar,numero_ctps,serie_ctps,
                pis_funcionario,cnh_funcionario,vencimento_cnh,data_admissao,cep_funcionario,endereco_funcionario,numero_imovel,complemento_funcionario,bairro_funcionario,
                cidade_funcionario,id_estado);

        Div div = new Div(formLayout);
        div.setSizeFull();

        return div;
    }


    private void buscarCep() {
        EApiEnderecoResponse response = service.buscarCep(cep_funcionario);
            endereco_funcionario.setValue(response.getLogradouro());
            //complemento_funcionario.setValue(response.get);
            bairro_funcionario.setValue(response.getBairro());
            cidade_funcionario.setValue(response.getLocalidade());
            id_estado.setValue(service.configuraUF(estadoList, response.getUf()));
    }






}
