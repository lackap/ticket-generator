package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.ParametersView;
import com.forum.ticketgenerator.view.HeaderView;
import com.forum.ticketgenerator.view.admin.DeleteButtonComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route(value = "entreprise")
@PermitAll
@UIScope
public class EntrepriseView extends ParametersView implements BeforeEnterObserver {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private AddPosteView addPosteView;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    private Grid<PosteMatching> grid;

    private Text posteCreationResult;

    @PostConstruct
    public void init() throws IOException {

        add(headerView);
        setAlignItems(Alignment.CENTER);
        posteCreationResult = new Text("");

        ApplicationUser userDetails = securityService.getAuthenticatedUser();
        addPosteView.addListener(ReloadEvent.class, event -> {
            if (event.getErrorMessage() == null) {
                grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                        userDetails.getTicketUser().getDisplayName(), userDetails.getEvenement()));
                grid.getDataProvider().refreshAll();
            } else {
                posteCreationResult.setText(event.getErrorMessage());
            }
        });
        grid = new Grid<>(PosteMatching.class, false);
        configureGrid(userDetails.getEvenement());
        add(new H2("Ajouter un poste"), posteCreationResult, addPosteView, grid);
    }


    private void configureGrid(Evenement evenement) {
        grid.addColumn("nom").setHeader("Entreprise").setWidth("15%").setFlexGrow(0);
        grid.addColumn("intitule").setHeader("Intitulé de poste").setAutoWidth(true).setFlexGrow(1);
        grid.addColumn("familleMetier").setHeader("Famille métier").setWidth("12%").setFlexGrow(0);
        if (evenement.getDisplayNiveau()) {
            grid.addColumn("niveau")
                    .setHeader("Niveau").setWidth("12%").setFlexGrow(0);;
        }
        if (evenement.getDisplayTypeContrat()) {
            grid.addColumn("typeContrat")
                    .setHeader("Type de contrat").setWidth("12%").setFlexGrow(0);;
        }
        if (evenement.getDisplaySecteur()) {
            grid.addColumn("secteurActivite")
                    .setHeader(evenement.getLabelSecteurActivité()).setWidth("12%").setFlexGrow(0);;
        }

        grid.addColumn("stand").setHeader("N° de stand").setWidth("12%").setFlexGrow(0);
        Grid.Column<PosteMatching> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
                modelServiceFactory.getEntrepriseService().supprimerPoste(userDetails.getTicketUser().getDisplayName(),
                        evenement, item);
                grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                        userDetails.getTicketUser().getDisplayName(), evenement));
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);
        ApplicationUser userDetails = securityService.getAuthenticatedUser();
        grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                userDetails.getTicketUser().getDisplayName(), userDetails.getEvenement()));
    }

    @Override
    protected String getTitlePrefix() {
        return "Paramétrage des offres ";
    }
}
