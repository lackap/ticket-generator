package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
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

    protected TextField intituleEvenement;

    protected TextField labelSecteurActivite;

    private Grid<Evenement> grid;

    @PostConstruct
    public void init() throws IOException {
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        intituleEvenement = new TextField();
        intituleEvenement.setLabel("Nom évènement : ");
        labelSecteurActivite = new TextField();
        labelSecteurActivite.setLabel("Label a utiliser pour les secteurs d'activité : ");
        configureGrid();
        grid.setItems(getItems());
        Button ajoutButton = new Button("Ajouter");
        ajoutButton.addClickListener(event -> {
            Evenement item = save();
            grid.setItems(getItems());
            grid.getDataProvider().refreshAll();
            ApplicationUser applicationUser = securityService.getAuthenticatedUser();
            applicationUser.setEvenement(item);
            UI.getCurrent().navigate(ParametrageAdminView.class);
        });
        add(new H3("Paramétrage evenement"), intituleEvenement, ajoutButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(Evenement.class, false);
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé");
        grid.addColumn("intitule");
        grid.getColumnByKey("labelSecteurActivité").setHeader("Label secteur activité");
        this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent(item, null);
            deleteButtonComponent.addClickListener(event -> {
                modelServiceFactory.getEvenementService().supprimer(item);
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
    }

    protected List<Evenement> getItems () {
        return modelServiceFactory.getEvenementService().searchAllEvenement();
    }

    protected Evenement save () {
        return modelServiceFactory.getEvenementService().enregistrer(intituleEvenement.getValue(), labelSecteurActivite.getValue());
    }
}
