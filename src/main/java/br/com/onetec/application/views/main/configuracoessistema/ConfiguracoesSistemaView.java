package br.com.onetec.application.views.main.configuracoessistema;


import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.application.views.main.administrativo.div.FuncionarioDiv;
import br.com.onetec.application.views.main.administrativo.modal.DepartamentoCadastroModal;
import br.com.onetec.application.views.main.configuracoessistema.div.RegiaoDiv;
import br.com.onetec.application.views.main.configuracoessistema.div.TipoImovelDiv;
import br.com.onetec.application.views.main.configuracoessistema.div.TipoMidiaDiv;
import br.com.onetec.cross.constants.ViewsTitleConst;
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
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "configuracoes", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.CONFIGURATION_NAV_TITLE)
@PermitAll
public class ConfiguracoesSistemaView extends VerticalLayout {


        private TipoMidiaDiv tipomidiaDiv;
        private TipoImovelDiv tipoimovelDiv;
        private RegiaoDiv regiaoDiv;


        @Autowired
        public void initServices(TipoMidiaDiv tipomidiaDiv1,
                                 TipoImovelDiv tipoimovelDiv1,
                                 RegiaoDiv regiaoDiv1) {
            this.tipomidiaDiv = tipomidiaDiv1;
            this.tipoimovelDiv = tipoimovelDiv1;
            this.regiaoDiv = regiaoDiv1;
        }


        @Autowired
        public ConfiguracoesSistemaView(TipoMidiaDiv tipomidiaDiv1,
                                        TipoImovelDiv tipoimovelDiv1,
                                        RegiaoDiv regiaoDiv1) {
            this.tipomidiaDiv = tipomidiaDiv1;
            this.tipoimovelDiv = tipoimovelDiv1;
            this.regiaoDiv = regiaoDiv1;

            TabSheet tabSheet = new TabSheet();
            tabSheet.add("Tipos de Midia",
                    tipomidiaDiv);
            tabSheet.add("Tipos de Imóvel",
                    tipoimovelDiv);
            tabSheet.add("Regiões",
                    regiaoDiv);
            tabSheet.add("Administradora",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.add("Tabelas de Serviço",
                    new Div(new Text("This is the Shipping tab content")));
            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
            add(tabSheet);
        }

}
