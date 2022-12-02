package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.service.model.database.EntrepriseModelService;
import com.forum.ticketgenerator.view.ParametersView;
import com.forum.ticketgenerator.view.ticket.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        configureGrid();
        evenement = new ComboBox<>();
        evenement.setLabel("Sélectionner un évènement : ");
        evenement.setItems(modelServiceFactory.getEvenementService().searchAllEvenement());
        evenement.setItemLabelGenerator(Evenement::getIntitule);
        evenement.addValueChangeListener(event -> {
            ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
            addPosteView.setEvenement(evenement.getValue());
            grid.setItems(modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(
                    userDetails.getTicketUser().getDisplayName(), evenement.getValue()));
        });
        add(evenement, new H2("Ajouter un poste"), posteCreationResult, addPosteView, grid);
    }


    private void configureGrid() {
        grid = new Grid<>(PosteMatching.class, false);
        grid.addColumn("nom");
        grid.getColumnByKey("nom").setHeader("Entreprise");
        grid.addColumn("intitule");
        grid.getColumnByKey("intitule").setHeader("Intitulé de poste");
        grid.addColumn("stand");
        grid.getColumnByKey("stand").setHeader("N° de stand");
    }

    @Override
    protected String getTitlePrefix() {
        return "Paramétrage des offres ";
    }
}
