package com.forum.ticketgenerator.view.login;

import com.forum.ticketgenerator.constants.Roles;
import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.UserCreationException;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.model.TicketUserService;
import com.forum.ticketgenerator.view.ticket.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Route("createAccount")
@PageTitle("Création de compte")
@PermitAll
@AnonymousAllowed
@UIScope
public class AccountCreationView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    private ComboBox<String> selectRole;

    private TextField name;

    private TextField password;

    private TextField displayedName;

    private Button validationButton;

    private Text validationMessage;

    private Upload uploadLogo;

    private byte[] logo;

    @Autowired
    private TicketUserService ticketUserService;

    @PostConstruct
    public void init() {
        add(headerView);
        setAlignItems(Alignment.CENTER);
        selectRole = new ComboBox<>();
        selectRole.setLabel("Role");
        selectRole.setItems(
                Arrays.stream(Roles.values()).map(role -> role.name()).collect(Collectors.toList()));
        selectRole.addValueChangeListener( event -> {
            if (selectRole.getValue() == Roles.ENTREPRISE.name() || selectRole.getValue() == Roles.FORMATION.name()) {
                displayedName.setVisible(true);
            } else {
                displayedName.setVisible(false);
            }
        });
        name = new TextField();
        name.setLabel("Identifiant : ");
        password = new TextField();
        password.setLabel("Mot de passe : ");
        displayedName = new TextField();
        displayedName.setLabel("Désignation : ");
        displayedName.setVisible(false);

        FileBuffer memoryBufferLogo = new FileBuffer();
        uploadLogo = new Upload(memoryBufferLogo);
        uploadLogo.setUploadButton(new Button("Charger le logo : "));
        uploadLogo.setDropLabel(new Label("Déposer le fichier ici"));
        uploadLogo.addSucceededListener(event -> {
            try {
                BufferedImage inputImage = ImageIO.read(memoryBufferLogo.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                logo = pngContent.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        validationMessage = new Text("");
        validationButton = new Button("Créer le compte");
        validationButton.addClickListener(buttonClickEvent -> {
            try {
                ticketUserService.createUser(name.getValue(), password.getValue(), selectRole.getValue(), displayedName.getValue(), logo);

                UI.getCurrent().navigate("home");
            } catch (UserCreationException e) {
                this.validationMessage.setText(e.getErrorMessage());
            }
        });
        add(new H1("Création de compte"), validationMessage, selectRole, name, password, displayedName, uploadLogo, validationButton);

    }
}
