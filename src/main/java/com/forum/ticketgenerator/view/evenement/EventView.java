package com.forum.ticketgenerator.view.evenement;

import com.forum.ticketgenerator.constants.Roles;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.view.admin.ParametrageAdminView;
import com.forum.ticketgenerator.view.entreprise.EntrepriseView;
import com.forum.ticketgenerator.view.formation.FormationView;
import com.forum.ticketgenerator.view.ticket.TicketView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@UIScope
public class EventView extends VerticalLayout {

    private final Evenement evenement;

    private final SecurityService securityService;

    public EventView(Evenement evenement, SecurityService securityService) {
        this.evenement = evenement;
        this.securityService = securityService;
        setAlignItems(FlexComponent.Alignment.CENTER);
        add(new H2(evenement.getIntitule()));
        if (evenement.getAffiche() != null) {
            StreamResource resource = new StreamResource("myimage.jpg", () -> new ByteArrayInputStream(evenement.getAffiche()));
            Image affiche = new Image(resource, "Logo");
            affiche.setHeight("100%");
            affiche.setWidth("100%");
            affiche.getStyle().set("cursor", "pointer");
            affiche.addClickListener(event -> goToEvent());
            add(affiche);
        } else {
            Button goToEvent = new Button("Aller a l'évènement");
            goToEvent.addClickListener( event -> goToEvent());
            add(goToEvent);
        }
    }

    private void goToEvent() {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        applicationUser.setEvenement(evenement);
        for (GrantedAuthority grantedAuthority : securityService.getAuthenticatedUser().getAuthorities()) {
            if (Roles.USER.name().equals(grantedAuthority.getAuthority())) {
                UI.getCurrent().navigate(TicketView.class);
            }
            if (Roles.ADMIN.name().equals(grantedAuthority.getAuthority())) {
                UI.getCurrent().navigate(ParametrageAdminView.class);
            }
            if (Roles.ENTREPRISE.name().equals(grantedAuthority.getAuthority())) {
                UI.getCurrent().navigate(EntrepriseView.class);
            }
            if (Roles.FORMATION.name().equals(grantedAuthority.getAuthority())) {
                UI.getCurrent().navigate(FormationView.class);
            }
        }
    }
}
