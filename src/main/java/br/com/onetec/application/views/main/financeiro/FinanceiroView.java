package br.com.onetec.application.views.main.financeiro;

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


@Route(value = "financeiro", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.FINANCEIRO_NAV_TITLE)
@PermitAll
public class FinanceiroView extends VerticalLayout {




        public FinanceiroView() {
            TabSheet tabSheet = new TabSheet();
            tabSheet.add("Conta Corrente",
                    new Div(new Text("This is the Payment tab content")));
            tabSheet.add("Fluxo de Caixa",
                    new Div(new Text("This is the Payment tab content")));
            tabSheet.add("Condição de Pagamento",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Tipos de Pagamento",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Grupos",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Plano de Contas",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Sub-Contas",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Novo Plano de Contas",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        }
}
