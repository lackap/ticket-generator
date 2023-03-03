package com.forum.ticketgenerator.view.login;

import com.forum.ticketgenerator.constants.Role;
import com.forum.ticketgenerator.exception.UserCreationException;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.database.UserCreationService;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Route("createAccount")
@PageTitle("Création de compte")
@PermitAll
@AnonymousAllowed
@UIScope
public class AccountCreationView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private HeaderView headerView;

    private ComboBox<String> selectRole;

    private TextField name;

    private PasswordField password;

    private TextField displayedName;

    private Text validationMessage;

    private Upload uploadLogo;

    private H1 pageTitle;

    private byte[] logo;

    private Role roleToCreate;

    @Autowired
    private UserCreationService userCreationService;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {
        add(headerView);
        setAlignItems(Alignment.CENTER);
        selectRole = new ComboBox<>();
        selectRole.setLabel("Role");

        selectRole.addValueChangeListener( event -> {
            if (selectRole.getValue().equals(Role.ENTREPRISE.name()) || selectRole.getValue().equals(Role.FORMATION.name())) {
                displayedName.setVisible(true);
                uploadLogo.setVisible(true);
            } else {
                displayedName.setVisible(false);
                uploadLogo.setVisible(false);
            }
        });
        name = new TextField();
        name.setLabel("Identifiant : ");
        password = new PasswordField();
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
        uploadLogo.setVisible(false);


        validationMessage = new Text("");
        Button validationButton = new Button("Créer le compte");
        validationButton.addClickListener(buttonClickEvent -> {
            try {
                userCreationService.createUser(name.getValue(), password.getValue(), selectRole.getValue(), displayedName.getValue(), logo);

                UI.getCurrent().navigate("home");
            } catch (UserCreationException e) {
                this.validationMessage.setText(e.getErrorMessage());
            }
        });
        pageTitle = new H1();
        add(pageTitle, validationMessage, selectRole, name, password, displayedName, uploadLogo, validationButton);

    }

    private List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        if (roleToCreate == null) {
            ApplicationUser applicationUser = securityService.getAuthenticatedUser();
            if (applicationUser != null) {
                if (applicationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()))) {
                    return Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList());
                }
            }
            roles.add(Role.USER.name());
        } else {
            roles.add(roleToCreate.name());
        }
        return roles;
    }

    private String getViewTitle () {
        String title = "Création de compte ";
        if (roleToCreate != null) {
            title += roleToCreate.getValue();
        }
        return title;

    }

    @Override
    public void setParameter (BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s != null) {
            roleToCreate = Role.valueOf(s);
        }
        selectRole.setItems(getRoles());
        if (roleToCreate != null) {
            selectRole.setValue(roleToCreate.name());
        }
        pageTitle.setText(getViewTitle());
    }
}
