package br.com.onetec.application.views.main.estoque;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "estoque", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ESTOQUE_NAV_TITLE)
@PermitAll
public class EstoqueView extends VerticalLayout {



        public EstoqueView() {
            TabSheet tabSheet = new TabSheet();
            tabSheet.add("Movimento",
                    new Div(new Text("This is the Payment tab content")));
            tabSheet.add("Produtos e Materiais",
                    new Div(new Text("This is the Payment tab content")));
            tabSheet.add("Veiculos",
                    new Div(new Text("This is the Shipping tab content")));

            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        }
}
