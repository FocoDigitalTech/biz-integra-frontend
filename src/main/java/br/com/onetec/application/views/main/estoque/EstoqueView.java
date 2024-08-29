package br.com.onetec.application.views.main.estoque;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.estoque.div.MovimentoDiv;
import br.com.onetec.application.views.main.estoque.div.ProdutosDiv;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "estoque", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ESTOQUE_NAV_TITLE)
@PermitAll
@UIScope
public class EstoqueView extends VerticalLayout {

    private ProdutosDiv produtosDiv;

    private MovimentoDiv movimentoDiv;

    @Autowired
    public void initServices(ProdutosDiv produtosDiv1,MovimentoDiv movimentoDiv1){
        this.produtosDiv = produtosDiv1;
        this.movimentoDiv = movimentoDiv1;
    }

    @Autowired
    public EstoqueView() {
        UI.getCurrent().access(() -> {
            setSizeFull();
            TabSheet tabSheet = new TabSheet();
            tabSheet.setSizeFull();

            tabSheet.add("Movimento", movimentoDiv);
            tabSheet.add("Produtos e Materiais", produtosDiv);
            tabSheet.add("Veiculos", new Div(new Text("This is the Shipping tab content")));

            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        });
    }


}
