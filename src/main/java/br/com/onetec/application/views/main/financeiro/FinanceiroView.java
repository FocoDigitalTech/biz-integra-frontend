package br.com.onetec.application.views.main.financeiro;

import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.financeiro.div.*;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "financeiro", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.FINANCEIRO_NAV_TITLE)
@PermitAll
public class FinanceiroView extends VerticalLayout {

    private LancamentoFinanceiroDiv lancamentoFinanceiro;
    private CondicaoPagamentoDiv condicaoPagamentoDiv;
    private TipoEventoFinanceiroDiv tipoEventoFinanceiroDiv;
    private GrupoFinanceiroDiv grupoFinanceiroDiv;
    private EventoFinanceiroDiv eventoFinanceiroDiv;
    private ContaCorrenteDiv contaCorrenteDiv;
    private TipopagamentoDiv tipoPagamentoDiv;



        @Autowired
        public void initServices(CondicaoPagamentoDiv condicaoPagamentoDiv1,
                                 TipoEventoFinanceiroDiv tipoEventoFinanceiroDiv1,
                                 GrupoFinanceiroDiv grupoFinanceiroDiv1,
                                 EventoFinanceiroDiv eventoFinanceiroDiv1,
                                 ContaCorrenteDiv contaCorrenteDiv1,
                                 LancamentoFinanceiroDiv lancamentoFinanceiroDiv,
                                 TipopagamentoDiv tipoPagamentoDiv1){
            this.condicaoPagamentoDiv = condicaoPagamentoDiv1;
            this.tipoEventoFinanceiroDiv = tipoEventoFinanceiroDiv1;
            this.grupoFinanceiroDiv = grupoFinanceiroDiv1;
            this.eventoFinanceiroDiv = eventoFinanceiroDiv1;
            this.contaCorrenteDiv = contaCorrenteDiv1;
            this.lancamentoFinanceiro = lancamentoFinanceiroDiv;
            this.tipoPagamentoDiv = tipoPagamentoDiv1;
        }

        @Autowired
        public FinanceiroView(CondicaoPagamentoDiv condicaoPagamentoDiv1,
                              TipoEventoFinanceiroDiv tipoEventoFinanceiroDiv1,
                              GrupoFinanceiroDiv grupoFinanceiroDiv1,
                              EventoFinanceiroDiv eventoFinanceiroDiv1,
                              ContaCorrenteDiv contaCorrenteDiv1,
                              LancamentoFinanceiroDiv lancamentoFinanceiroDiv,
                              TipopagamentoDiv tipoPagamentoDiv1){
        this.condicaoPagamentoDiv = condicaoPagamentoDiv1;
        this.tipoEventoFinanceiroDiv = tipoEventoFinanceiroDiv1;
        this.grupoFinanceiroDiv = grupoFinanceiroDiv1;
        this.eventoFinanceiroDiv = eventoFinanceiroDiv1;
        this.contaCorrenteDiv = contaCorrenteDiv1;
        this.lancamentoFinanceiro = lancamentoFinanceiroDiv;
        this.tipoPagamentoDiv = tipoPagamentoDiv1;

            UI.getCurrent().access(() -> {
                setSizeFull();
                TabSheet tabSheet = new TabSheet();
                tabSheet.add("Lançamentos Financeiros",
                        lancamentoFinanceiro);
                tabSheet.add("Condição de Pagamento",
                        condicaoPagamentoDiv);
                tabSheet.add("Contas (Tipos Evento Financeiro)",
                        tipoEventoFinanceiroDiv);
                tabSheet.add("Grupos",
                        grupoFinanceiroDiv);
                tabSheet.add("Tipo Pagamento",
                        tipoPagamentoDiv);
                tabSheet.add("Sub-Contas (Evento Financeiro)",
                        eventoFinanceiroDiv);
//            tabSheet.add("Novo Plano de Contas",
//                    new Div(new Text("This is the Shipping tab content")));
                tabSheet.add("Conta Corrente",
                        contaCorrenteDiv);
                tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
                add(tabSheet);
            });
        }
}
