package br.com.onetec.application.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "looby", layout = MainLayout.class)
@PageTitle("Lobby")
@PermitAll
public class LobbyView extends VerticalLayout {


    private final TextField channelNameField;
    private final Button addChannelButton;

    public LobbyView() {
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


