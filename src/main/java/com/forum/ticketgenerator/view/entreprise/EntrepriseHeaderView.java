package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@UIScope
public class EntrepriseHeaderView extends HorizontalLayout implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() throws IOException {

    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
        String user = "user";
        Image logo = null;
        if (userDetails != null && userDetails.getTicketUser() != null) {
            user = userDetails.getTicketUser().getDisplayName();
            logo = generateImage(userDetails.getTicketUser().getLogo());
            if (logo != null) {
                logo.setWidth("75px");
                logo.setHeight("75px");
            }
        }
        if (logo != null) {
            add(new H1(user), logo);
        } else {
            add(new H1(user));
        }
    }



    private Image generateImage(byte[] logo) {
        if (logo != null) {
            StreamResource resource = new StreamResource("myimage.jpg", () -> new ByteArrayInputStream(logo));
            return new Image(resource, "Logo");
        }
        return null;
    }
}
