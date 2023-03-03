package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@UIScope
public class ParametrageComportementEvenementView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private HeaderView headerView;

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected TextField labelTitreEvenement;
    protected TextField labelSecteurActivite;

    protected Text resultatInsertion;

    protected Checkbox displaySecteurActivite;
    protected Checkbox displayTypeContrat;
    protected Checkbox displayNiveau;

    private byte[] affiche;


    @PostConstruct
    public void init() throws IOException {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        add(headerView);
        labelTitreEvenement = new TextField();
        labelTitreEvenement.setWidth("50%");
        labelTitreEvenement.setLabel("Titre de l'évènement : ");
        labelSecteurActivite = new TextField();
        labelSecteurActivite.setWidth("50%");
        labelSecteurActivite.setLabel("Intitulé des couleurs : ");
        displaySecteurActivite = new Checkbox();
        displaySecteurActivite.setLabel("Afficher les codes couleurs");
        displayNiveau = new Checkbox();
        displayNiveau.setLabel("Afficher les niveaux");
        displayTypeContrat = new Checkbox();
        displayTypeContrat.setLabel("Afficher les types de contrat");
        FileBuffer memoryBufferLogo = new FileBuffer();
        Upload uploadAffiche = new Upload(memoryBufferLogo);
        uploadAffiche.setUploadButton(new Button("Modifier l'affiche de l'évènement : "));
        uploadAffiche.setDropLabel(new Label("Déposer le fichier ici"));
        uploadAffiche.addSucceededListener(event -> {
            try {
                BufferedImage inputImage = ImageIO.read(memoryBufferLogo.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                affiche = pngContent.toByteArray();
                applicationUser.getEvenement().setAffiche(affiche);
                fireEvent(new ReloadEvent(uploadAffiche, null,false));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button ajoutButton = new Button("Enregistrer");
        ajoutButton.addClickListener(event -> {
            try {
                Evenement item = save(applicationUser.getEvenement());
                applicationUser.setEvenement(item);
                resultatInsertion.setText("Les paramètres ont été modifiés");
            } catch (ModelCreationException e) {
                resultatInsertion.setText(e.getErrorMessage());
            }
        });
        resultatInsertion = new Text("");
        updateDisplay();
        add(new H3("Paramétrage evenement"), resultatInsertion, labelTitreEvenement, labelSecteurActivite, displaySecteurActivite, displayNiveau, displayTypeContrat, uploadAffiche, ajoutButton);
    }


    protected List<Evenement> getItems () {
        return modelServiceFactory.getEvenementService().searchAllEvenement();
    }

    protected Evenement save (Evenement evenement) throws ModelCreationException {
        return modelServiceFactory.getEvenementService().mettreAJourParametres(
                evenement, labelTitreEvenement.getValue(), labelSecteurActivite.getValue(), displaySecteurActivite.getValue(),
                displayNiveau.getValue(), displayTypeContrat.getValue(), affiche);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        updateDisplay();
    }

    private void updateDisplay() {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        labelSecteurActivite.setValue(applicationUser.getEvenement().getLabelSecteurActivite());
        labelTitreEvenement.setValue(applicationUser.getEvenement().getIntitule());
        if (applicationUser.getEvenement().getDisplaySecteur() != null) {
            displaySecteurActivite.setValue(applicationUser.getEvenement().getDisplaySecteur());
        }
        if (applicationUser.getEvenement().getDisplayNiveau() != null) {
            displayNiveau.setValue(applicationUser.getEvenement().getDisplayNiveau());
        }
        if (applicationUser.getEvenement().getDisplayTypeContrat() != null) {
            displayTypeContrat.setValue(applicationUser.getEvenement().getDisplayTypeContrat());
        }

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
