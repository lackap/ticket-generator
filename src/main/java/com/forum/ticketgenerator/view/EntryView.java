package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.constants.Role;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.view.admin.ParametrageAdminView;
import com.forum.ticketgenerator.view.entreprise.EntrepriseView;
import com.forum.ticketgenerator.view.evenement.SelectEventView;
import com.forum.ticketgenerator.view.formation.FormationView;
import com.forum.ticketgenerator.view.ticket.TicketView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.security.PermitAll;

@Route(value = "home")
@RouteAlias(value = "")
@UIScope
@PermitAll
public class EntryView extends Div implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        ApplicationUser userDetails = securityService.getAuthenticatedUser();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            if (userDetails.getEvenement() == null) {
                event.rerouteTo(SelectEventView.class);
            } else {
                if (Role.ENTREPRISE.name().equals(grantedAuthority.getAuthority())) {
                    event.rerouteTo(EntrepriseView.class);
                }
                if (Role.USER.name().equals(grantedAuthority.getAuthority())) {
                    event.rerouteTo(TicketView.class);
                }
                if (Role.FORMATION.name().equals(grantedAuthority.getAuthority())) {
                    event.rerouteTo(FormationView.class);
                }
                if (Role.ADMIN.name().equals(grantedAuthority.getAuthority())) {
                    event.rerouteTo(ParametrageAdminView.class);
                }
            }
        }
    }
}
