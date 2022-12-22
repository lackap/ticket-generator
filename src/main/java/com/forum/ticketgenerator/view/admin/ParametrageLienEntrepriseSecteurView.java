package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class ParametrageLienEntrepriseSecteurView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    @Autowired
    private AjoutEntrepriseSecteurView ajoutEntrepriseSecteur;

    @Autowired
    private SecurityService securityService;

    protected Grid<EntrepriseDTO> grid;

    @PostConstruct
    public void init () throws IOException {
        setAlignItems(FlexComponent.Alignment.CENTER);
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        configureGrid(applicationUser.getEvenement());
        ajoutEntrepriseSecteur.addListener(ReloadEvent.class,
                event -> grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(applicationUser.getEvenement())));
        add(ajoutEntrepriseSecteur, grid);
    }

    public void update() {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(applicationUser.getEvenement()));
        grid.getDataProvider().refreshAll();
    }

    protected void configureGrid(Evenement evenement) {
        grid = new Grid<>(EntrepriseDTO.class, false);
        grid.addColumn("nom").setHeader("Dénomination");
        grid.addColumn("secteurActivite.intitule").setHeader(evenement.getLabelSecteurActivite());
        grid.setNestedNullBehavior(Grid.NestedNullBehavior.ALLOW_NULLS);

    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(applicationUser.getEvenement()));
    }
}
