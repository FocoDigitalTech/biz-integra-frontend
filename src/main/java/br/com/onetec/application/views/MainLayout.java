package br.com.onetec.application.views;

import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.application.views.main.clientes.ClientesView;
import br.com.onetec.application.views.main.configuracoes.ConfiguracoesView;
import br.com.onetec.application.views.main.estoque.EstoqueView;
import br.com.onetec.application.views.main.financeiro.FinanceiroView;
import br.com.onetec.application.views.main.home.HomeView;
import br.com.onetec.application.views.main.relatorios.RelatoriosView;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private final AuthenticationContext authenticationContext;

    private H2 viewTitle;
    private H2 titulo;

    public MainLayout(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addNavbarContent();
        addDrawerContent();
    }

    private void addNavbarContent() {
        var toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        toggle.setTooltipText("Menu toggle");

        viewTitle = new H2("");
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE,
                LumoUtility.Flex.GROW);

/*        var logout = new Button("Logout " + authenticationContext.getPrincipalName().orElse(""),
                event -> authenticationContext.logout());*/

        String userName = authenticationContext.getPrincipalName().orElse("");
        Avatar avatar = new Avatar(userName);
        MenuBar menuBar = new MenuBar();
        MenuItem avatarMenu = menuBar.addItem(avatar);
        SubMenu subMenu = avatarMenu.getSubMenu();
        subMenu.addItem("Logout", event -> authenticationContext.logout());
        subMenu.addItem("Perfil", event -> {
            // Código para abrir o perfil do usuário
        });


        titulo = new H2("Nagasaki App");
        var header = new Header(toggle,titulo, viewTitle, menuBar);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.Padding.End.MEDIUM, LumoUtility.Width.FULL);

        addToNavbar(false, header);
    }


    private void addDrawerContent() {
        var appName = new Span("Menu Principal");
        appName.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD,
                LumoUtility.Height.XLARGE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToDrawer(appName, new Scroller(createSideNav()));
    }

    private SideNav createSideNav() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Home", HomeView.class,
                VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("Clientes", ClientesView.class,
                VaadinIcon.GROUP.create()));
        nav.addItem(new SideNavItem("Administrativo", AdministrativoView.class,
                VaadinIcon.BOOK.create()));
        nav.addItem(new SideNavItem("Financeiro", FinanceiroView.class,
                VaadinIcon.MONEY.create()));
        nav.addItem(new SideNavItem("Estoque", EstoqueView.class,
                VaadinIcon.STORAGE.create()));
        nav.addItem(new SideNavItem("Relatórios", RelatoriosView.class,
                VaadinIcon.PAPERPLANE.create()));
        nav.addItem(new SideNavItem("Configurações", ConfiguracoesView.class,
                VaadinIcon.HAMMER.create()));



        return nav;
    }
    private String getCurrentPageTitle() {
        if (getContent() == null) {
            return "";
        } else if (getContent() instanceof HasDynamicTitle titleHolder) {
            return titleHolder.getPageTitle();
        } else {
            var title = getContent().getClass().getAnnotation(PageTitle.class);
            return title == null ? "" : title.value();
        }
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        //viewTitle.setText(getCurrentPageTitle());
        updateTitulo();
    }

    private void updateTitulo() {
        String route = getCurrentPageTitle();
        switch (route) {
            case "Home":
                titulo.setText("Nagasaki App");
                break;
            case ViewsTitleConst.CLIENTES_NAV_TITLE:
                titulo.setText(ViewsTitleConst.CLIENTES_NAV_TITLE);
                break;
            case ViewsTitleConst.ADMINISTRATIVO_NAV_TITLE:
                titulo.setText(ViewsTitleConst.ADMINISTRATIVO_NAV_TITLE);
                break;
            case ViewsTitleConst.FINANCEIRO_NAV_TITLE:
                titulo.setText(ViewsTitleConst.FINANCEIRO_NAV_TITLE);
                break;
            case ViewsTitleConst.ESTOQUE_NAV_TITLE:
                titulo.setText(ViewsTitleConst.ESTOQUE_NAV_TITLE);
                break;
            case ViewsTitleConst.REPORT_NAV_TITLE:
                titulo.setText(ViewsTitleConst.REPORT_NAV_TITLE);
                break;
            case ViewsTitleConst.CONFIGURATION_NAV_TITLE:
                titulo.setText(ViewsTitleConst.CONFIGURATION_NAV_TITLE);
                break;
            default:
                titulo.setText(ViewsTitleConst.MAIN_NAV_TITLE);
                break;
        }
    }


}
