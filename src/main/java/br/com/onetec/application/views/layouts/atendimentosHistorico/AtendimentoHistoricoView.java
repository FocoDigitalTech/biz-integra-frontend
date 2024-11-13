package br.com.onetec.application.views.layouts.atendimentosHistorico;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.OrcamentoDiv;
import br.com.onetec.application.views.layouts.atendimentosHistorico.div.ServicosExecutadosDiv;
import br.com.onetec.application.views.main.financeiro.div.TipoEventoFinanceiroDiv;
import br.com.onetec.infra.db.model.SetCliente;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "atendimentos_historico",layout = MainLayout.class)
@PermitAll
@UIScope
public class AtendimentoHistoricoView extends  Div {

    private OrcamentoDiv orcamentoDiv;
    private ServicosExecutadosDiv servicosExecutadosDiv;
    private TipoEventoFinanceiroDiv tipoEventoFinanceiroDiv;



    @Autowired
    public void initServices(OrcamentoDiv orcamentoDiv1,
                             ServicosExecutadosDiv servicosExecutadosDiv1){
            this.servicosExecutadosDiv = servicosExecutadosDiv1;
            this.orcamentoDiv = orcamentoDiv1;

    }

    @Autowired
    public AtendimentoHistoricoView(OrcamentoDiv orcamentoDiv1){
        UI.getCurrent().access(() -> {
            this.orcamentoDiv = orcamentoDiv1;
            setSizeFull();
            SetCliente entidade = (SetCliente) UI.getCurrent().getSession().getAttribute("cliente");
            if (entidade == null) {
                add(new Div("Cliente não encontrado na sessão."));
                return;
            }

            TabSheet tabSheet = new TabSheet();
            tabSheet.add("Orçamento e Dados Financeiros",
                    orcamentoDiv);
//            tabSheet.add("Serviços Executados",
//                    this.servicosExecutadosDiv);
//            tabSheet.add("Contatos",
//                    new Div());
            tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);

            add(tabSheet);
        });
    }



//    @Override
//    public void afterNavigation(AfterNavigationEvent event) {
//        //new AtendimentoHistoricoView();
//       // UI.getCurrent().getPage().executeJs("location.reload();");
//    }
//
//    @PostConstruct
//    public void init() {
//        // Este método será chamado após a injeção de dependências
//        // e após a construção da view.
//        if(SetClienteTransiction.isRecarregaPagina()) {
//            SetClienteTransiction.setRecarregaPagina(false);
//           // UI.getCurrent().getPage().executeJs("location.reload();");
//            UI.getCurrent().getElement().executeJs("setTimeout(() => { this.$0.callMethod(); }, 5000)", this);
//            System.out.println("Teste");
//            // Aqui você pode executar a lógica que deseja após a navegação
//        }
//    }

}
