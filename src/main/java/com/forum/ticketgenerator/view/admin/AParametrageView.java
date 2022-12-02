package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Formation;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.ParametersView;
import com.forum.ticketgenerator.view.formation.AddDiplomeView;
import com.forum.ticketgenerator.view.ticket.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public abstract class AParametrageView<T> extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    protected TextField valueToAdd;

    private Grid<T> grid;



    @PostConstruct
    public void init() throws IOException {
        headerView.customizeHeader(getTitle());
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink routerLink = new RouterLink("Retourner au paramétrage", ParametrageAdminView.class);
        valueToAdd = new TextField();
        valueToAdd.setLabel("Valeur : ");
        configureGrid();
        grid.setItems(getItems());
        Button ajoutButton = new Button("Ajouter");
        ajoutButton.addClickListener(event -> {
            save();
            grid.setItems(getItems());
        });
        add(routerLink, new H2("Ajouter "), valueToAdd, ajoutButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(getGridType(), false);
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé");
    };

    protected abstract String getTitle();

    protected abstract Class<T> getGridType();

    protected abstract List<T> getItems();

    protected abstract void save();
}
