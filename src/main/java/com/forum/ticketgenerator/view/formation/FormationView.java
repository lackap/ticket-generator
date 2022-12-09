package com.forum.ticketgenerator.view.formation;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Formation;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.ParametersView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route(value = "formation")
@PermitAll
@UIScope
public class FormationView extends ParametersView {

    @Autowired
    private AddDiplomeView addDiplomeView;


    @Autowired
    private ModelServiceFactory modelServiceFactory;

    private Grid<Diplome> grid;

    private Text posteCreationResult;

    private ComboBox<Evenement> evenement;

    @PostConstruct
    public void init() throws IOException {

        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
        addDiplomeView.addListener(ReloadEvent.class, event -> {
            if (event.getErrorMessage() == null) {
                grid.setItems(modelServiceFactory.getFormationService().searchFromFormationNameAndEvenement(
                        userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
                grid.getDataProvider().refreshAll();
            } else {
                posteCreationResult.setText(event.getErrorMessage());
            }
        });

        configureGrid();
        evenement = new ComboBox<>();
        evenement.setLabel("Sélectionner un évènement : ");
        evenement.setItems(modelServiceFactory.getEvenementService().searchAllEvenement());
        evenement.setItemLabelGenerator(Evenement::getIntitule);
        evenement.addValueChangeListener(event -> {
            addDiplomeView.setEvenement(evenement.getValue());
            grid.setItems(modelServiceFactory.getFormationService().searchFromFormationNameAndEvenement(
                    userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
        });
        add(evenement, new H2("Ajouter un diplome"), addDiplomeView, grid);
    }

    private void configureGrid() {
        grid = new Grid<>(Diplome.class, false);
        grid.addColumn("intituleDiplome");
        grid.getColumnByKey("intituleDiplome").setHeader("Intitulé de poste");
        grid.addColumn("familleMetier.intitule");
        grid.getColumnByKey("familleMetier.intitule").setHeader("Famille metier");
    }

    @Override
    protected String getTitlePrefix() {
        return "Paramétrage des diplômes ";
    }
}
