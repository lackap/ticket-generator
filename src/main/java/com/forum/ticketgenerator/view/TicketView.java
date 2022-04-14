package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.event.SearchResultEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.service.SearchService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "ticket")
@RouteAlias(value = "")
@UIScope
public class TicketView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private SearchView searchView;

    @Autowired
    private LoadingView loadingView;

    @Autowired
    private PdfGenerationView pdfGenerationView;

    @Autowired
    private SearchService searchService;

    private Grid<PosteMatching> grid;
    private Text recherche;

    @PostConstruct
    public void init() {

        add(headerView);
        add(loadingView);
        loadingView.addListener(ReloadEvent.class, event -> UI.getCurrent().getPage().reload());
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
        grid.addColumn("contrat");
        grid.addColumn("niveau");
        grid.addColumn("stand");
        grid.getColumnByKey("stand").setHeader("N° de stand");
        add(grid);
    }
}
