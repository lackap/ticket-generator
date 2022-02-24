package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.SearchFormationEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.SearchService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route
public class TicketView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private SearchView searchView;

    @Autowired
    private PdfGenerationView pdfGenerationView;

    @Autowired
    private SearchService searchService;

    private Grid<PosteMatching> grid;

    public TicketView() {

    }

    @PostConstruct
    public void init() {

        configureGrid();
        add(headerView);
        add(searchView);
        searchView.addListener(SearchFormationEvent.class, event -> {
            Model.getInstance().setPostesMatching(event.getPostesMatching());
            grid.setItems(event.getPostesMatching());
            grid.getDataProvider().refreshAll();

        });
        add(pdfGenerationView);
        add(grid);
    }


    private void configureGrid() {

        grid = new Grid<>(PosteMatching.class, false);
        grid.addColumn("nom");
        grid.addColumn("intitule");
        grid.addColumn("contrat");
        grid.addColumn("niveau");
        grid.addColumn("stand");
    }
}
