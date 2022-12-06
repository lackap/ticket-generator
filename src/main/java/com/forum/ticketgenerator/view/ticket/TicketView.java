package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.SearchResultEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@Route(value = "ticket")
@UIScope
@PermitAll
public class TicketView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private SearchView searchView;

    @Autowired
    private PdfGenerationView pdfGenerationView;

    private Grid<PosteMatching> grid;
    private Text recherche;

    private ComboBox<Evenement> evenement;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {

        add(headerView);
        add(searchView);
        searchView.addListener(SearchResultEvent.class, event -> {
            Model.getInstance().setPostesMatching(event.getPostesMatching());
            grid.setItems(event.getPostesMatching());
            grid.getDataProvider().refreshAll();
            recherche.setText(event.getLabel());
        });
        add(pdfGenerationView);
        configureGrid();
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
        add(grid);
    }
}
