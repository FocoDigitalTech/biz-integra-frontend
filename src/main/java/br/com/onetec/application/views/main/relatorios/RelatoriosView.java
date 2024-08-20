package br.com.onetec.application.views.main.relatorios;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "relatorios", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.REPORT_NAV_TITLE)
@PermitAll
public class RelatoriosView extends VerticalLayout {




        @Autowired
        public RelatoriosView() {

        }
}
