package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.constants.Role;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.login.AccountCreationView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class EntrepriseManagementView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected Grid<EntrepriseDTO> grid;

    private Button addAccountButton;

    @PostConstruct
    public void init() throws IOException {
        setAlignItems(FlexComponent.Alignment.CENTER);
        addAccountButton = new Button("Ajouter une entreprise");
        addAccountButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountCreationView.class, Role.ENTREPRISE.name());
        });
        configureGrid();
        grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(null));
        add(addAccountButton, grid);
    }

    protected void configureGrid() {
        grid = new Grid<>(EntrepriseDTO.class, false);
        grid.addColumn("nom").setHeader("Désignation").setAutoWidth(true).setFlexGrow(1);
        Grid.Column<EntrepriseDTO> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                modelServiceFactory.getEntrepriseService().supprimerEntreprise(item.getNom());
                grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(null));
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        grid.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(null));
    }
}
