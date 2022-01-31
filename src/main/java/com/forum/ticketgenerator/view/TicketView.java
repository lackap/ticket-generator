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
    private SearchView searchView;

    @Autowired
    private SearchService searchService;

    private Grid<PosteMatching> grid;

    public TicketView() {

    }

    @PostConstruct
    public void init() {

        configureGrid();
        add(searchView);
        searchView.addListener(SearchFormationEvent.class, event -> {
            grid.setItems(event.getPostesMatching());
            grid.getDataProvider().refreshAll();

        });
        add(grid);
    }


    private void configureGrid() {
        grid = new Grid<>(PosteMatching.class);
        PosteMatching posteMatching1 = new PosteMatching();
        posteMatching1.setStand(1);
        posteMatching1.setNiveau("BAC");
        posteMatching1.setIntitule("Toto");
        posteMatching1.setContrat("CDI");
        posteMatching1.setNom("Benjamin");
        List<PosteMatching> posteMatchingList = new ArrayList<>();
        posteMatchingList.add(posteMatching1);
        grid.setItems(posteMatchingList);
    }
}
