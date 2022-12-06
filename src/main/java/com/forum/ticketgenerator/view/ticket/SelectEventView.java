package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.SearchResultEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@Route(value = "select-event")
@UIScope
@PermitAll
public class SelectEventView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    private ComboBox<Evenement> evenement;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {

        add(headerView);
        setAlignItems(Alignment.CENTER);
        evenement = new ComboBox<>();
        evenement.setLabel("Sélectionner un évènement : ");
        evenement.setItems(modelServiceFactory.getEvenementService().searchAllEvenement());
        evenement.setItemLabelGenerator(Evenement::getIntitule);
        evenement.addValueChangeListener(event -> {
            securityService.getAuthenticatedUser().setEvenement(evenement.getValue());
            UI.getCurrent().navigate(TicketView.class);
        });
        add(evenement);
    }
}
