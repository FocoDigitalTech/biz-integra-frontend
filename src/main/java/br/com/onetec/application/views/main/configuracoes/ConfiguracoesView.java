package br.com.onetec.application.views.main.configuracoes;


import br.com.onetec.application.views.MainLayout;
import br.com.onetec.cross.utilities.ViewsTitleConst;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "configuracoes", layout = MainLayout.class)
@PageTitle(ViewsTitleConst.CONFIGURATION_NAV_TITLE)
@PermitAll
public class ConfiguracoesView  extends VerticalLayout {


        private final TextField channelNameField;
        private final Button addChannelButton;

        public ConfiguracoesView() {
            setSizeFull();


            channelNameField = new TextField();
            channelNameField.setPlaceholder("New channel name");

            addChannelButton = new Button("Add channel");
            addChannelButton.setDisableOnClick(true);

            var toolbar = new HorizontalLayout(channelNameField, addChannelButton);
            toolbar.setWidthFull();
            toolbar.expand(channelNameField);
            add(toolbar);
        }
}
