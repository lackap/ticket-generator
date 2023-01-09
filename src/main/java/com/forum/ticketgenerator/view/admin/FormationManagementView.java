package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Formation;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.login.AccountCreationView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class FormationManagementView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected Grid<Formation> grid;

    private Button addAccountButton;

    @PostConstruct
    public void init() throws IOException {
        setAlignItems(Alignment.CENTER);
        addAccountButton = new Button("Ajouter une formation");
        addAccountButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountCreationView.class);
        });
        configureGrid();
        grid.setItems(modelServiceFactory.getFormationService().getCentresFormation());
        add(addAccountButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(Formation.class, false);
        grid.addColumn("nomCentre").setHeader("Nom du centre de formation").setAutoWidth(true).setFlexGrow(1);
        Grid.Column<Formation> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                modelServiceFactory.getFormationService().supprimerFormation(item);
                grid.setItems(modelServiceFactory.getFormationService().getCentresFormation());
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        grid.setItems(modelServiceFactory.getFormationService().getCentresFormation());
    }
}
