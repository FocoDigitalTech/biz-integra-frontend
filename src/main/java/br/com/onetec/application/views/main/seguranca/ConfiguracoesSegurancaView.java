package br.com.onetec.application.views.main.seguranca;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.seguranca.div.GrupoUsuariosDiv;
import br.com.onetec.application.views.main.seguranca.div.UsuariosDiv;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "seguranca", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.SEGURANCA_NAV_TITLE)
@PermitAll
public class ConfiguracoesSegurancaView extends VerticalLayout {



    private UsuariosDiv usuariosDiv;
    private GrupoUsuariosDiv situacaoCadastroDiv;
//    private PermissoesDiv setorAtuacaoDiv;


    @Autowired
    public void initServices(UsuariosDiv usuariosDiv1,
                            GrupoUsuariosDiv situacaoCadastroDiv1
//                             PermissoesDiv setorAtuacaoDiv1
    ) {
        this.usuariosDiv = usuariosDiv1;
        this.situacaoCadastroDiv = situacaoCadastroDiv1;
//        this.setorAtuacaoDiv = setorAtuacaoDiv1;
    }

    @Autowired
    public ConfiguracoesSegurancaView() {

        UI.getCurrent().access(() -> {
            setSizeFull();
            TabSheet tabSheet = new TabSheet();
            tabSheet.setSizeFull();
            tabSheet.add("Usuários",
                    usuariosDiv);
            tabSheet.add("Grupos de usuários",
                    situacaoCadastroDiv);
            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        });
    }

}
