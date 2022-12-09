package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AParametrageView<T> extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected TextField valueToAdd;

    protected Grid<T> grid;

    protected Evenement evenement;

    @PostConstruct
    public void init() throws IOException {
        setAlignItems(FlexComponent.Alignment.CENTER);
        valueToAdd = new TextField();
        valueToAdd.setLabel("Valeur : ");
        configureGrid();
        grid.setItems(getItems());

        add(getComponentsToDisplay());
    }

    protected void configureGrid() {
        grid = new Grid<>(getGridType(), false);
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé");
        addCustomColumns();
        this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent(item, null);
            deleteButtonComponent.addClickListener(event -> {
                getParametrageService().delete(item);
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
    }

    protected void addCustomColumns() {

    }

    private Component[] getComponentsToDisplay() {
        Button ajoutButton = new Button("Ajouter");
        ajoutButton.addClickListener(event -> {
            executeButtonAction();
        });
        List<Component> fields = new ArrayList<>();
        fields.add(new H3(getTitle()));
        fields.add(valueToAdd);
        fields.addAll(getCustomComponents());
        fields.add(ajoutButton);
        fields.add(grid);
        return fields.toArray(new Component[0]);
    }

    protected List<Component> getCustomComponents() {
        return new ArrayList<>();
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }

    protected T executeButtonAction() {
        T item = save();
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
        return item;
    }

    protected List<T> getItems () {
        return getParametrageService().searchParEvenement(this.evenement);
    }

    protected abstract String getTitle();

    protected abstract Class<T> getGridType();

    protected abstract IParametrageService<T> getParametrageService();

    protected T save () {
        return getParametrageService().enregistrer(valueToAdd.getValue(), this.evenement);
    }
}
