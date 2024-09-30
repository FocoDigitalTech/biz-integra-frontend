package br.com.onetec.application.views.layouts;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "access-denied", layout = MainLayout.class)
@PageTitle("Acesso Negado")
@PermitAll
@Uses(Icon.class)
public class AccessDeniedView extends Div {

    public AccessDeniedView() {

        add(telaDiv());
    }

    private Div telaDiv() {
        /* constuir a tela funcionarios*/
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        VerticalLayout layout = new VerticalLayout(new H3("Você não tem permissão para acessar esta página."));
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;
    }
}
