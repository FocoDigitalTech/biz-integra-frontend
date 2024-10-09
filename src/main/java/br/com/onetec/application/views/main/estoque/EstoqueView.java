package br.com.onetec.application.views.main.estoque;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.estoque.div.MovimentoDiv;
import br.com.onetec.application.views.main.estoque.div.ProdutosDiv;
import br.com.onetec.application.views.main.estoque.div.VeiculosDiv;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.cross.utilities.UtilitySystemConfigService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "estoque", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ESTOQUE_NAV_TITLE)
@PermitAll
@UIScope
public class EstoqueView extends VerticalLayout implements BeforeEnterListener {

    private ProdutosDiv produtosDiv;

    private MovimentoDiv movimentoDiv;

    private VeiculosDiv veiculosDiv;

    private UtilitySystemConfigService systemConfigService;


    @Autowired
    public void initServices(ProdutosDiv produtosDiv1,MovimentoDiv movimentoDiv1,VeiculosDiv veiculosDiv1,
    UtilitySystemConfigService systemConfigService1){
        this.produtosDiv = produtosDiv1;
        this.movimentoDiv = movimentoDiv1;
        this.systemConfigService = systemConfigService1;
        this.veiculosDiv = veiculosDiv1;
    }

    @Autowired
    public EstoqueView(UtilitySystemConfigService systemConfigService1) {

        UI.getCurrent().access(() -> {
            setSizeFull();
            TabSheet tabSheet = new TabSheet();
            tabSheet.setSizeFull();

            tabSheet.add("Movimento", movimentoDiv);
            tabSheet.add("Produtos e Materiais", produtosDiv);
            tabSheet.add("Veiculos", veiculosDiv);

            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Aqui você executa a verificação de segurança antes de o usuário acessar a view
        systemConfigService.validateSecurityAccessView(UsuarioAutenticadoConfig.getUser());
    }
}



