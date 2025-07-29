package br.com.onetec.application.views.main.configuracoessistema.div;


import br.com.onetec.application.service.codigonumeracaoservice.CodigoNumeracaoService;
import br.com.onetec.application.service.userservice.UsuarioService;
import br.com.onetec.application.views.main.configuracoessistema.modal.CodigoNumeracaoModal;
import br.com.onetec.cross.constants.ModalMessageConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import br.com.onetec.infra.db.model.SetCodigoNumeracao;
import br.com.onetec.infra.db.model.SetUsuarios;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@UIScope
public class CodigoNumeracaoDiv extends Div{

    private Grid<SetCodigoNumeracao> grid;

    private UtilitySystemConfigService service;

    private CodigoNumeracaoService pragaService;

    private UsuarioService usuarioService;

    private Button btnExcluir;

    private CodigoNumeracaoModal pragaCadastroModal;

    @Autowired
    public void initServices(CodigoNumeracaoService pragaService1,
                             UtilitySystemConfigService service1,
                             UsuarioService usuarioService1,
                             CodigoNumeracaoModal pragaCadastroModal1) {
        this.pragaService = pragaService1;
        this.service = service1;
        this.usuarioService = usuarioService1;
        this.pragaCadastroModal = pragaCadastroModal1;
    }


    @Autowired
    public CodigoNumeracaoDiv( ) {
        UI.getCurrent().access(() -> {
            add(telaDiv());
        });

    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        com.vaadin.flow.component.Component gridFuncionario = createGrid();
        VerticalLayout layout = new VerticalLayout(gridFuncionario);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }

    public void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }


    private void deleta(SetCodigoNumeracao item) {
        try {
            pragaService.delete(item);
            service.notificaSucesso(ModalMessageConst.DELETE_SUCCESS);
            btnExcluir.setVisible(false);
            refreshGrid();
        } catch (Exception e){
            service.notificaErro(ModalMessageConst.ERROR_DELETE);
        }
    }

    private com.vaadin.flow.component.Component createGrid() {

        //departamentoService.list(null,null);
        grid = new Grid<>(SetCodigoNumeracao.class, false);
        grid.addColumn(SetCodigoNumeracao::getId_codigonumeracao)
                .setHeader("ID")
                //.setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(SetCodigoNumeracao::getOrcamento_codigonumeracao)
                .setHeader("Código Orçamento")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCodigoNumeracao::getContrato_codigonumeracao)
                .setHeader("Código Contrato")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCodigoNumeracao::getCliente_codigonumeracao)
                .setHeader("Código Cliente")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(SetCodigoNumeracao::getOrdemservico_codigonumeracao)
                .setHeader("Código OS")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(data -> {
            if (Objects.nonNull(data.getData_alteracao())){
                return UtilitySystemConfigService.
                        getDataFormatada(data.getData_alteracao());
            } else {
                return "";
            }
        })
                .setHeader("Data de Alteração")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(midia -> {
            SetUsuarios usuarios = usuarioService.findById(midia.getId_usuario());
            return usuarios == null ? "N/A" : usuarios.getNome_usuario();
        })
                .setHeader("Usuario")
                .setSortable(true)
                .setAutoWidth(true);



        grid.setItems(pragaService.findAll());

        grid.addItemClickListener(event -> {
            // Configura o botão "Deletar" para deletar o item clicado
            openCadastroModal(event.getItem());
        });

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }


    private void openCadastroModal(SetCodigoNumeracao item) {
        UI.getCurrent().access(() -> {
            pragaCadastroModal.setCodigo(item);
            pragaCadastroModal.open();
        });
    }
}
