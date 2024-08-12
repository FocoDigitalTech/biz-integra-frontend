package br.com.onetec.application.views.main.relatorios;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
