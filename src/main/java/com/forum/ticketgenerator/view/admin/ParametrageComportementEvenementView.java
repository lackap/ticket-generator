package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import javafx.scene.control.CheckBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@UIScope
public class ParametrageComportementEvenementView extends VerticalLayout {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private HeaderView headerView;

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected TextField labelSecteurActivite;

    protected Text resultatInsertion;

    protected Checkbox displaySecteurActivite;
    protected Checkbox displayTypeContrat;
    protected Checkbox displayNiveau;

    private Upload uploadAffiche;

    private byte[] affiche;


    @PostConstruct
    public void init() throws IOException {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        add(headerView);
        labelSecteurActivite = new TextField();
        labelSecteurActivite.setWidth("50%");
        labelSecteurActivite.setLabel("Label a utiliser pour les secteurs d'activité : ");
        labelSecteurActivite.setValue(applicationUser.getEvenement().getLabelSecteurActivité());
        displaySecteurActivite = new Checkbox();
        displaySecteurActivite.setLabel("Afficher les secteurs d'activité");
        if (applicationUser.getEvenement().getDisplaySecteur() != null) {
            displaySecteurActivite.setValue(applicationUser.getEvenement().getDisplaySecteur());
        }
        displayNiveau = new Checkbox();
        displayNiveau.setLabel("Afficher les niveaux");
        if (applicationUser.getEvenement().getDisplayNiveau() != null) {
            displayNiveau.setValue(applicationUser.getEvenement().getDisplayNiveau());
        }
        displayTypeContrat = new Checkbox();
        displayTypeContrat.setLabel("Afficher les types de contrat");
        if (applicationUser.getEvenement().getDisplayTypeContrat() != null) {
            displayTypeContrat.setValue(applicationUser.getEvenement().getDisplayTypeContrat());
        }
        FileBuffer memoryBufferLogo = new FileBuffer();
        uploadAffiche = new Upload(memoryBufferLogo);
        uploadAffiche.setUploadButton(new Button("Charger l'affiche : "));
        uploadAffiche.setDropLabel(new Label("Déposer le fichier ici"));
        uploadAffiche.addSucceededListener(event -> {
            try {
                BufferedImage inputImage = ImageIO.read(memoryBufferLogo.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                affiche = pngContent.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button ajoutButton = new Button("Enregistrer");
        ajoutButton.addClickListener(event -> {
            Evenement item = null;
            try {
                item = save(applicationUser.getEvenement());
                applicationUser.setEvenement(item);
                resultatInsertion.setText("Les paramètres ont été modifiés");
            } catch (ModelCreationException e) {
                resultatInsertion.setText(e.getErrorMessage());
            }
        });
        resultatInsertion = new Text("");
        add(new H3("Paramétrage evenement"), resultatInsertion, labelSecteurActivite, displaySecteurActivite, displayNiveau, displayTypeContrat, uploadAffiche, ajoutButton);
    }


    protected List<Evenement> getItems () {
        return modelServiceFactory.getEvenementService().searchAllEvenement();
    }

    protected Evenement save (Evenement evenement) throws ModelCreationException {
        return modelServiceFactory.getEvenementService().mettreAJourParametres(
                evenement, labelSecteurActivite.getValue(), displaySecteurActivite.getValue(),
                displayNiveau.getValue(), displayTypeContrat.getValue(), affiche);
    }
}