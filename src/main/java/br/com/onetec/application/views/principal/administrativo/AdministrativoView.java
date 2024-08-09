package br.com.onetec.application.views.principal.administrativo;


import br.com.onetec.application.data.Clientes;
import br.com.onetec.application.data.Departamento;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.principal.clientes.ClientesView;
import br.com.onetec.application.views.principal.clientes.modal.DadosClienteModal;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@Route(value = "administrativo", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.ADMINISTRATIVO_NAV_TITLE)
@PermitAll
public class AdministrativoView extends Div {


    Grid<Departamento> grid;



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

        //filters = new ClientesView.Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout
                (createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
        Div div = new Div(layout);
        div.setSizeFull();

        return div;

    }



    private Component createGrid() {
        grid = new Grid<>(Departamento.class, false);
        grid.addColumn("codigo").setAutoWidth(true);
        grid.addColumn("descricao").setAutoWidth(true);
        grid.addColumn("responsavel").setAutoWidth(true);

        // Adiciona o listener de clique nos itens da grade
        grid.addItemClickListener(event -> new Div());



//        grid.setItems(query -> clientesService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
//                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }


}


