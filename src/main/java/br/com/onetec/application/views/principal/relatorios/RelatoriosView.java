package br.com.onetec.application.views.principal.relatorios;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "relatorios", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.REPORT_NAV_TITLE)
@PermitAll
public class RelatoriosView extends VerticalLayout {




        public RelatoriosView() {
            new Div(new Text("This is the Payment tab content"));
        }
}
