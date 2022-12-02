package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.event.SearchResultEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.DatabaseModelService;
import com.forum.ticketgenerator.view.ticket.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route(value = "entreprise")
@PermitAll
@UIScope
public class EntrepriseView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private EntrepriseHeaderView entrepriseHeaderView;

    @Autowired
    private AddPosteView addPosteView;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DatabaseModelService databaseModelService;

    private Grid<PosteMatching> grid;

    private Text recherche;

    @PostConstruct
    public void init() throws IOException {

        add(headerView);
        ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
        addPosteView.addListener(ReloadEvent.class, event -> {
            grid.setItems(databaseModelService.searchFromEntrepriseName(userDetails.getTicketUser().getDisplayName()));
            grid.getDataProvider().refreshAll();
        });

        configureGrid();
        grid.setItems(databaseModelService.searchFromEntrepriseName(userDetails.getTicketUser().getDisplayName()));
        add(entrepriseHeaderView, new H2("Ajouter un poste"), addPosteView, grid);
    }


    private void configureGrid() {
        recherche  = new Text("");
        add(recherche);
        grid = new Grid<>(PosteMatching.class, false);
        grid.addColumn("nom");
        grid.getColumnByKey("nom").setHeader("Entreprise");
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé de poste");
        grid.addColumn("stand");
        grid.getColumnByKey("stand").setHeader("N° de stand");
    }
}
