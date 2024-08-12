package br.com.onetec.application.views.main.administrativo;


import br.com.onetec.application.data.Departamento;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.filters.DepartamentoFilter;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route(value = "administrativo", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ADMINISTRATIVO_NAV_TITLE)
@PermitAll
public class AdministrativoView extends Div {


    private Grid<Departamento> grid;

    private DepartamentoFilter.Filters filtersDepartamento;


    public AdministrativoView() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Departamentos",
                departamentosDiv());
        tabSheet.add("Funcionarios",
                new Div(new Text("This is the Payment tab content")));
        tabSheet.add("Compras",
                new Div(new Text("This is the Shipping tab content")));
        tabSheet.add("Fornecedores",
                new Div(new Text("This is the Shipping tab content")));
        tabSheet.add("Tabelas de Serviço",
                new Div(new Text("This is the Shipping tab content")));
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
        add(tabSheet);
    }

    private Div departamentosDiv() {
        setSizeFull();
        addClassNames("telarelatorios-view");

        setSizeFull();
        addClassNames("telarelatorios-view");

        filtersDepartamento = new DepartamentoFilter.Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filtersDepartamento, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filtersDepartamento.getClassNames().contains("visible")) {
                filtersDepartamento.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filtersDepartamento.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }


    private Component createGrid() {
        grid = new Grid<>(Departamento.class, false);
        grid.addColumn("codigo").setAutoWidth(true);
        grid.addColumn("descricao").setAutoWidth(true);
        grid.addColumn("responsavel").setAutoWidth(true);

        // Adiciona o listener de clique nos itens da grade
        grid.addItemClickListener(event -> openDetalhesClienteModal(event.getItem()));


//        grid.setItems(query -> clientesService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
//                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void openDetalhesClienteModal(Departamento item) {
    }


}


