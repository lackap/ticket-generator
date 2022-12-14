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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route(value = "entreprise")
@PermitAll
@UIScope
public class EntrepriseView extends ParametersView {

    @Autowired
    private HeaderView headerView;

    @Autowired
    private AddPosteView addPosteView;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    private Grid<PosteMatching> grid;

    private Text posteCreationResult;

    private ComboBox<Evenement> evenement;

    @PostConstruct
    public void init() throws IOException {

        add(headerView);
        setAlignItems(Alignment.CENTER);
        posteCreationResult = new Text("");
        addPosteView.addListener(ReloadEvent.class, event -> {
            if (event.getErrorMessage() == null) {
                ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
                grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                        userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
                grid.getDataProvider().refreshAll();
            } else {
                posteCreationResult.setText(event.getErrorMessage());
            }
        });
        grid = new Grid<>(PosteMatching.class, false);
        evenement = new ComboBox<>();
        evenement.setLabel("Sélectionner un évènement : ");
        evenement.setItems(modelServiceFactory.getEvenementService().searchAllEvenement());
        evenement.setItemLabelGenerator(Evenement::getIntitule);
        evenement.addValueChangeListener(event -> {
            ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
            addPosteView.setEvenement(evenement.getValue());
            configureGrid();
            grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                    userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
        });
        add(evenement, new H2("Ajouter un poste"), posteCreationResult, addPosteView, grid);
    }


    private void configureGrid() {
        grid.addColumn("nom").setHeader("Entreprise").setWidth("15%").setFlexGrow(0);
        grid.addColumn("intitule").setHeader("Intitulé de poste").setAutoWidth(true).setFlexGrow(1);
        grid.addColumn("familleMetier").setHeader("Famille métier").setWidth("12%").setFlexGrow(0);
        if (evenement.getValue().getDisplayNiveau()) {
            grid.addColumn("niveau")
                    .setHeader("Niveau").setWidth("12%").setFlexGrow(0);;
        }
        if (evenement.getValue().getDisplayTypeContrat()) {
            grid.addColumn("typeContrat")
                    .setHeader("Type de contrat").setWidth("12%").setFlexGrow(0);;
        }
        if (evenement.getValue().getDisplaySecteur()) {
            grid.addColumn("secteurActivite")
                    .setHeader(evenement.getValue().getLabelSecteurActivité()).setWidth("12%").setFlexGrow(0);;
        }

        grid.addColumn("stand").setHeader("N° de stand").setWidth("12%").setFlexGrow(0);
        Grid.Column<PosteMatching> deleteColumn = this.grid.addComponentColumn(item -> {
            DeleteButtonComponent deleteButtonComponent = new DeleteButtonComponent();
            deleteButtonComponent.addClickListener(event -> {
                ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
                modelServiceFactory.getEntrepriseService().supprimerPoste(userDetails.getTicketUser().getDisplayName(),
                        evenement.getValue(), item);
                grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                        userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
                grid.getDataProvider().refreshAll();
            });
            return deleteButtonComponent;
        });
        deleteColumn.setWidth("5%").setFlexGrow(0);
    }

    @Override
    protected String getTitlePrefix() {
        return "Paramétrage des offres ";
    }
}
