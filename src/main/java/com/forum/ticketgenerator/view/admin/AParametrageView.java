package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
        add(routerLink, valueToAdd, ajoutButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(getGridType(), false);
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé");
    }

    protected abstract String getTitle();

    protected abstract Class<T> getGridType();

    protected abstract List<T> getItems();

    protected abstract void save();
}
