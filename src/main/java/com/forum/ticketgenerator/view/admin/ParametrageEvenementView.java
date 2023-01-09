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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Route(value = "parametrageEvenement")
@PermitAll
@UIScope
public class ParametrageEvenementView extends VerticalLayout {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private HeaderView headerView;

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected Text resultatInsertion;

    protected TextField intituleEvenement;

    protected TextField labelSecteurActivite;

    private byte[] affiche;

    private Grid<Evenement> grid;

    @PostConstruct
    public void init() throws IOException {
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        intituleEvenement = new TextField();
        intituleEvenement.setLabel("Nom évènement : ");
        labelSecteurActivite = new TextField();
        labelSecteurActivite.setLabel("Label a utiliser pour les secteurs d'activité : ");
        FileBuffer memoryBufferLogo = new FileBuffer();
        Upload uploadAffiche = new Upload(memoryBufferLogo);
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
        configureGrid();
        grid.setItems(getItems());
        Button ajoutButton = new Button("Ajouter");
        ajoutButton.addClickListener(event -> {
            try {
                Evenement item = save();
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
                ApplicationUser applicationUser = securityService.getAuthenticatedUser();
                applicationUser.setEvenement(item);
                UI.getCurrent().navigate(ParametrageAdminView.class);
            } catch (ModelCreationException e) {
                resultatInsertion.setText(e.getErrorMessage());
            }
        });
        resultatInsertion = new Text("");
        add(new H3("Paramétrage evenement"), resultatInsertion, intituleEvenement, labelSecteurActivite, uploadAffiche, ajoutButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(Evenement.class, false);
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé");
        grid.addColumn("labelSecteurActivite");
        grid.getColumnByKey("labelSecteurActivite").setHeader("Label secteur activité");
        Grid.Column<Evenement> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                modelServiceFactory.getEvenementService().supprimer(item);
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    protected List<Evenement> getItems () {
        return modelServiceFactory.getEvenementService().searchAllEvenement();
    }

    protected Evenement save () throws ModelCreationException {
        return modelServiceFactory.getEvenementService().enregistrer(intituleEvenement.getValue(), labelSecteurActivite.getValue(), affiche);
    }
}
