package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.button.Button;
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
import java.util.Locale;

@Component
@UIScope
public class ParametrageLienEntrepriseSecteurView extends VerticalLayout {

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
        ajoutEntrepriseSecteur.update();
    }

    protected void configureGrid(Evenement evenement) {
        grid = new Grid<>(EntrepriseDTO.class, false);
        grid.addColumn("nom").setHeader("Dénomination").setFlexGrow(1);
        Grid.Column<EntrepriseDTO> colorColumn = grid.addComponentColumn(entrepriseDTO -> {
            Button button = new Button();
            button.setHeight("10px");
            button.setWidth("10px");
            if (entrepriseDTO.getSecteurActivite() != null) {
                button.getElement().getStyle().set("background", entrepriseDTO.getSecteurActivite().getCouleur().toLowerCase(Locale.ROOT));
                button.getElement().getStyle().set("border-style", "none");
            }
            return button;
        }).setWidth("5%").setFlexGrow(0);
        grid.addColumn("secteurActivite.intitule").setHeader(evenement.getLabelSecteurActivite()).setWidth("20%").setFlexGrow(0);
        grid.addColumn("stand").setHeader("Stand").setWidth("15%").setFlexGrow(0);
        grid.setNestedNullBehavior(Grid.NestedNullBehavior.ALLOW_NULLS);

    }
}
