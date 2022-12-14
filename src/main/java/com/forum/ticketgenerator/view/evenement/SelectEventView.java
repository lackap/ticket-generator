package com.forum.ticketgenerator.view.evenement;

import com.forum.ticketgenerator.constants.Roles;
import com.forum.ticketgenerator.event.SearchResultEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.forum.ticketgenerator.view.admin.ParametrageAdminView;
import com.forum.ticketgenerator.view.admin.ParametrageEvenementView;
import com.forum.ticketgenerator.view.entreprise.EntrepriseView;
import com.forum.ticketgenerator.view.formation.FormationView;
import com.forum.ticketgenerator.view.ticket.TicketView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

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

    @Autowired
    private EventListView eventListView;

    @PostConstruct
    public void init() {

        add(headerView);
        headerView.customizeHeader("Sélection d'un évènement : ");
        setAlignItems(Alignment.CENTER);
        add(eventListView);
        for (GrantedAuthority grantedAuthority : securityService.getAuthenticatedUser().getAuthorities()) {
            if (Roles.ADMIN.name().equals(grantedAuthority.getAuthority())) {
                Button creerEvenement = new Button("Créer évènement");
                creerEvenement.addClickListener(event -> {
                    UI.getCurrent().navigate(ParametrageEvenementView.class);
                });
                add(creerEvenement);
            }

        }



    }
}