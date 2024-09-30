package br.com.onetec.application.views;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.views.layouts.AccessDeniedView;
import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.application.views.main.clientes.ClientesView;
import br.com.onetec.application.views.main.configuracoessistema.ConfiguracoesSistemaView;
import br.com.onetec.application.views.main.estoque.EstoqueView;
import br.com.onetec.application.views.main.financeiro.FinanceiroView;
import br.com.onetec.application.views.main.home.HomeView;
import br.com.onetec.application.views.main.relatorios.RelatoriosView;
import br.com.onetec.application.views.main.seguranca.ConfiguracoesSegurancaView;
import br.com.onetec.cross.constants.MenuNavItemVerticalTitleConst;
import br.com.onetec.cross.constants.ViewsTitleConst;
import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.model.SetUsuarios;
import br.com.onetec.infra.db.repository.ISetPermissaoRepository;
import br.com.onetec.infra.db.repository.IUsuariosRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HighlightCondition;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */

@Component
@UIScope
@PageTitle("Sistema Nagasaki")
public class MainLayout extends AppLayout {
    private final AuthenticationContext authenticationContext;

    private ISetPermissaoRepository setPermissaoRepository;


    private H2 viewTitle;
    private H2 titulo;

    public MainLayout(AuthenticationContext authenticationContext, IUsuariosRepository repository,
                      ISetPermissaoRepository setPermissaoRepository1) {
        this.authenticationContext = authenticationContext;
        this.repository = repository;
        this.setPermissaoRepository = setPermissaoRepository1;
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
        configuraUsuarioCorrente(authenticationContext.getPrincipalName());

        Avatar avatar = new Avatar(userName.substring(0, 2).toUpperCase());
        MenuBar menuBar = new MenuBar();
        //<theme-editor-local-classname>
        menuBar.addClassName("main-layout-menu-bar-1");
        MenuItem avatarMenu = menuBar.addItem(avatar);
        SubMenu subMenu = avatarMenu.getSubMenu();
        subMenu.addItem("Logout", event -> authenticationContext.logout());
        subMenu.addItem("Perfil", event -> {
            // Código para abrir o perfil do usuário
        });


        titulo = new H2("Nagasaki App");
        var header = new Header(toggle,titulo, viewTitle, menuBar);
        //<theme-editor-local-classname>
        header.addClassName("main-layout-header-1");
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.Padding.End.MEDIUM, LumoUtility.Width.FULL);



        addToNavbar(false, header);
    }

    private final IUsuariosRepository repository;

    private void configuraUsuarioCorrente(Optional<String> principalName) {
        SetUsuarios user = repository.findByusername(principalName.get());
        UsuarioAutenticadoConfig.setUser(user);
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
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_HOME, HomeView.class,
                VaadinIcon.HOME));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_CLIENTES, ClientesView.class,
                VaadinIcon.GROUP));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_ADMINISTRATIVO, AdministrativoView.class,
                VaadinIcon.BOOK));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_FINANCEIRO, FinanceiroView.class,
                VaadinIcon.MONEY));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_ESTOQUE, EstoqueView.class,
                VaadinIcon.STORAGE));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_RELATÓRIOS, RelatoriosView.class,
                    VaadinIcon.PAPERPLANE));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_SISTEMA, ConfiguracoesSistemaView.class,
                VaadinIcon.COG_O));
        nav.addItem(createNavItem(MenuNavItemVerticalTitleConst.NAME_SEGURANCA, ConfiguracoesSegurancaView.class,
                VaadinIcon.GROUP));

        nav.setCollapsible(false);

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
        titulo = new H2(route);
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


    private void handleClick(DomEvent event, Class<?> viewClass, String title) {
        System.out.println("Validando Acesso Para tela");
        if(!validateAccess(title)){
            UI.getCurrent().getPage().setLocation("access-denied");
        }
    }



    private boolean validateAccess(String nomeTela) {
        if (!nomeTela.equals("Home")) {
            List<SetPermissao> permissoes =
                    setPermissaoRepository.listAllById(UsuarioAutenticadoConfig.getUser().getId_grupousuario());
            for (SetPermissao permissao : permissoes) {
                if (permissao.getTela_view().equals("all")){
                    return true;
                }
                if (permissao.getNome_tela().contains(nomeTela)) {
                    if (permissao.getLeitura() == 1) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }


    private SideNavItem createNavItem(String title, Class<?> viewClass, VaadinIcon icon) {
         Class<?> a = viewClass.getEnclosingClass();
        // Create SideNavItem with title and icon, and add the anchor
        SideNavItem item = new SideNavItem(title, (Class<? extends com.vaadin.flow.component.Component>) viewClass, icon.create());
        item.getElement().addEventListener("click", event -> handleClick(event,viewClass,title));
        return item;
    }


}
