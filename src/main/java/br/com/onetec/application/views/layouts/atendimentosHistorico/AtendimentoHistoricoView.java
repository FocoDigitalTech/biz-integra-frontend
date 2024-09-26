package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.AdministrativoView;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.application.views.main.administrativo.div.FuncionarioDiv;
import br.com.onetec.application.views.main.administrativo.modal.DepartamentoCadastroModal;
import br.com.onetec.application.views.main.financeiro.div.*;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetFuncionario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Route(value = "atendimentos_historico",layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class AtendimentoHistoricoView extends VerticalLayout {

    private LancamentoFinanceiroDiv lancamentoFinanceiro;
    private CondicaoPagamentoDiv condicaoPagamentoDiv;
    private TipoEventoFinanceiroDiv tipoEventoFinanceiroDiv;
    

    @Autowired
    public void initServices(){
    }

    @Autowired
    public AtendimentoHistoricoView() {
        UI.getCurrent().access(() -> {
            SetCliente entidade = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");

            TabSheet tabSheet = new TabSheet();
            tabSheet.add("Orçamento e Dados Financeiros",
                    new Div());
            tabSheet.add("Serviços Executados",
                    new Div());
            tabSheet.add("Outros",
                    new Div());
            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        });
    }

}
