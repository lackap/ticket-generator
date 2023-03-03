package com.forum.ticketgenerator.view.evenement;

import com.forum.ticketgenerator.constants.Role;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.view.HeaderView;
import com.forum.ticketgenerator.view.admin.ParametrageEvenementView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
            if (Role.ADMIN.name().equals(grantedAuthority.getAuthority())) {
                Button creerEvenement = new Button("Créer évènement");
                creerEvenement.addClickListener(event -> UI.getCurrent().navigate(ParametrageEvenementView.class));
                add(creerEvenement);
            }

        }



    }
}
