package br.com.onetec.application.views.main.configuracoessistema;


import br.com.onetec.application.service.departamentoservice.DepartamentoService;
import br.com.onetec.application.service.funcionarioservice.FuncionarioService;
import br.com.onetec.application.views.MainLayout;
import br.com.onetec.application.views.main.administrativo.div.FornecedorDiv;
import br.com.onetec.application.views.main.administrativo.div.FuncionarioDiv;
import br.com.onetec.application.views.main.administrativo.modal.DepartamentoCadastroModal;
import br.com.onetec.application.views.main.configuracoessistema.div.*;
import br.com.onetec.cross.constants.ViewsTitleConst;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "configuracoes", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.CONFIGURATION_NAV_TITLE)
@PermitAll
@UIScope
public class ConfiguracoesSistemaView extends VerticalLayout {


        private TipoMidiaDiv tipomidiaDiv;
        private TipoImovelDiv tipoimovelDiv;
        private RegiaoDiv regiaoDiv;
        private TipoAtendimentoDiv tipoAtendimentoDiv;
        private SituacaoCadastroDiv situacaoCadastroDiv;
        private SetorAtuacaoDiv setorAtuacaoDiv;
        private ServicoDiv servicoDiv;
        private PragasDiv pragasDiv;


        @Autowired
        public void initServices(TipoMidiaDiv tipomidiaDiv1,
                                 TipoImovelDiv tipoimovelDiv1,
                                 RegiaoDiv regiaoDiv1,
                                 TipoAtendimentoDiv tipoAtendimentoDiv1,
                                 SituacaoCadastroDiv situacaoCadastroDiv1,
                                 SetorAtuacaoDiv setorAtuacaoDiv1,
                                 ServicoDiv servicoDiv1,
                                 PragasDiv pragasDiv) {
            this.tipomidiaDiv = tipomidiaDiv1;
            this.tipoimovelDiv = tipoimovelDiv1;
            this.regiaoDiv = regiaoDiv1;
            this.tipoAtendimentoDiv = tipoAtendimentoDiv1;
            this.situacaoCadastroDiv = situacaoCadastroDiv1;
            this.setorAtuacaoDiv = setorAtuacaoDiv1;
            this.servicoDiv = servicoDiv1;
            this.pragasDiv = pragasDiv;
        }


        @Autowired
        public ConfiguracoesSistemaView(TipoMidiaDiv tipomidiaDiv1,
                                        TipoImovelDiv tipoimovelDiv1,
                                        RegiaoDiv regiaoDiv1) {
            this.tipomidiaDiv = tipomidiaDiv1;
            this.tipoimovelDiv = tipoimovelDiv1;
            this.regiaoDiv = regiaoDiv1;
            UI.getCurrent().access(() -> {
                setSizeFull();
                TabSheet tabSheet = new TabSheet();
                tabSheet.setSizeFull();
                tabSheet.add("Tipos de Midia",
                        tipomidiaDiv);
                tabSheet.add("Tipos de Imóvel",
                        tipoimovelDiv);
                tabSheet.add("Regiões",
                        regiaoDiv);
                tabSheet.add("Tipo de Atendimento",
                        tipoAtendimentoDiv);
                tabSheet.add("Situação Cadastro",
                        situacaoCadastroDiv);
                tabSheet.add("Ramo Atividade",
                        setorAtuacaoDiv);
                tabSheet.add("Serviços",
                        servicoDiv);
                tabSheet.add("Pragas",
                        pragasDiv);
                tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
                add(tabSheet);
            });
        }

}
