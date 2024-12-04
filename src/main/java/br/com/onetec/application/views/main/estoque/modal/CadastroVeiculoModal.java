package br.com.onetec.application.views.main.estoque.modal;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.produtoservice.ProdutoService;
import br.com.onetec.application.service.tecnicoassistenteservice.TecnicoAssistenteService;
import br.com.onetec.application.service.veiculoservice.VeiculoService;
import br.com.onetec.application.views.main.estoque.div.MovimentoDiv;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetVeiculo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class CadastroVeiculoModal extends Dialog {

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    TecnicoAssistenteService tecnicoAssistenteService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    @Lazy
    MovimentoDiv movimentoDiv;

    private UtilitySystemConfigService service;

    private Button saveButton;
    private Button cancelButton;

    private TextField nome_veiculo;
    private TextField marca_veiculo;
    private TextField modelo_veiculo;
    private TextField combustivel_veiculo;
    private TextField placa_veiculo;
    private DatePicker datacompra_veiculo;
    private TextField nomeregistro_veiculo;
    private NumberField kminicial_veiculo;
    private TextField valorkm_veiculo;
    private DatePicker datavenda_veiculo;
    private TextField valorvenda_veiculo;
    private TextField valorcompra_veiculo;
    private TextField renavam_veiculo;


    @Autowired
    public CadastroVeiculoModal() {
        UI.getCurrent().access(() -> {
            saveButton = new com.vaadin.flow.component.button.Button("Salvar", eventbe -> {
                try {
                    save();
                } catch (Exception e) {
                }
            });
            service = new UtilitySystemConfigService();
            cancelButton = new Button("Cancelar", event -> service.askForConfirmation(this));
            addDialogCloseActionListener(event -> service.askForConfirmation(this));
            Div contentTabs = new Div(createFormCadastro());
            contentTabs.setSizeFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            getFooter().add(saveButton, cancelButton);
            VerticalLayout layout = new VerticalLayout(contentTabs);
            add(layout);
        });
    }


    private Div createFormCadastro() {
        service = new UtilitySystemConfigService();
        //id_classificacaoproduto = new ComboBox<String>("Nome ou Descrição");

        service = new UtilitySystemConfigService();
        nome_veiculo = new TextField("Nome/Apelido");
        marca_veiculo = new TextField("Marca");
        modelo_veiculo = new TextField("Modelo");
        combustivel_veiculo = new TextField("Combustivel");
        placa_veiculo = new TextField("Placa");
        datacompra_veiculo = new DatePicker("Data da Compra");
        nomeregistro_veiculo = new TextField("Nome Registrado Documento");
        kminicial_veiculo = new NumberField("Km Inicial");
        valorkm_veiculo = new TextField("Valor KM");
        datavenda_veiculo = new DatePicker("Data Venda");
        valorvenda_veiculo = new TextField("Valor Venda");
        valorcompra_veiculo = new TextField("Valor Compra");
        renavam_veiculo = new TextField("Renavam");

        service.configuraCalendario(datacompra_veiculo);
        service.configuraCalendario(datavenda_veiculo);
        service.formataMoedaBrasileira(valorkm_veiculo);
        service.formataMoedaBrasileira(valorvenda_veiculo);
        service.formataMoedaBrasileira(valorcompra_veiculo);


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(nome_veiculo,
                marca_veiculo,
                modelo_veiculo,
                combustivel_veiculo,
                placa_veiculo,
                datacompra_veiculo,
                nomeregistro_veiculo,
                kminicial_veiculo,
                valorkm_veiculo,
                datavenda_veiculo,
                valorvenda_veiculo,
                valorcompra_veiculo,
                renavam_veiculo);
        Div div = new Div(formLayout);
        div.setSizeFull();
        return div;
    }

    private void save() throws Exception {
        service = new UtilitySystemConfigService();
        // Lógica para salvar o cadastro
        SetVeiculo dto = new SetVeiculo();
        dto.setNome_veiculo(nome_veiculo.getValue());
        dto.setMarca_veiculo(marca_veiculo.getValue());
        dto.setModelo_veiculo(modelo_veiculo.getValue());
        dto.setCombustivel_veiculo(combustivel_veiculo.getValue());
        dto.setPlaca_veiculo(placa_veiculo.getValue());
        dto.setDatacompra_veiculo(datacompra_veiculo.getValue());
        dto.setNomeregistro_veiculo(nomeregistro_veiculo.getValue());
        dto.setKminicial_veiculo(kminicial_veiculo.getValue().toString());
        dto.setValorkm_veiculo(service.getValorBigDecimal(valorkm_veiculo.getValue()));
        dto.setDatavenda_veiculo(datavenda_veiculo.getValue());
        dto.setValorvenda_veiculo(service.getValorBigDecimal(valorvenda_veiculo.getValue()));
        dto.setValorcompra_veiculo(service.getValorBigDecimal(valorcompra_veiculo.getValue()));
        dto.setRenavam_veiculo(renavam_veiculo.getValue());
        dto.setAtivo("S");
        dto.setData_inclusao(LocalDateTime.now());
        dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());

        try {
            veiculoService.save(dto);
            movimentoDiv.refreshGrid();
            nome_veiculo.clear();
            marca_veiculo.clear();
            modelo_veiculo.clear();
            combustivel_veiculo.clear();
            placa_veiculo.clear();
            datacompra_veiculo.clear();
            nomeregistro_veiculo.clear();
            kminicial_veiculo.clear();
            valorkm_veiculo.clear();
            datavenda_veiculo.clear();
            valorvenda_veiculo.clear();
            valorcompra_veiculo.clear();
            renavam_veiculo.clear();
            service.notificaSucesso(ModalMessageConst.CREATE_SUCCESS);
            close();
        } catch (Exception e) {
            service.notificaErro(ModalMessageConst.ERROR_CREATE);
        }
    }
}