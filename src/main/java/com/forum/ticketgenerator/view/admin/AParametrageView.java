package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.forum.ticketgenerator.view.entreprise.AddPosteView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AParametrageView<T> extends VerticalLayout {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected Text resultatInsertion;

    protected Grid<T> grid;

    protected Evenement evenement;


    @PostConstruct
    public void init() throws IOException {
        setAlignItems(FlexComponent.Alignment.CENTER);
        configureGrid();
        grid.setItems(getItems());
        getParametrageView().addListener(ReloadEvent.class, event -> {
            ReloadEvent reloadEvent = (ReloadEvent) event;
            if (reloadEvent.getErrorMessage() == null) {
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
            } else {
                resultatInsertion.setText(reloadEvent.getErrorMessage());
            }
        });
        resultatInsertion = new Text("");
        add(getComponentsToDisplay());
    }

    protected void configureGrid() {
        grid = new Grid<>(getGridType(), false);
        grid.addColumn("intitule").setHeader("Intitulé").setAutoWidth(true).setFlexGrow(1);
        addCustomColumns();
        Grid.Column<T> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                getParametrageService().delete(item);
                grid.setItems(getItems());
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    protected void addCustomColumns() {

    }

    private Component[] getComponentsToDisplay() {
        List<Component> fields = new ArrayList<>();
        fields.add(new H3(getTitle()));
        fields.add(resultatInsertion);
        fields.add(getParametrageView());
        fields.add(grid);
        return fields.toArray(new Component[0]);
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        getParametrageView().setEvenement(evenement);
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }

    protected List<T> getItems () {
        return getParametrageService().searchParEvenement(this.evenement);
    }

    protected abstract Class<T> getGridType();

    protected abstract AAddParametrageView getParametrageView();

    protected abstract IParametrageService<T> getParametrageService();

    protected abstract String getTitle();
}
