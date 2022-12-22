package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

public class ParametersView  extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected HeaderView headerView;

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        ApplicationUser userDetails = securityService.getAuthenticatedUser();
        Image logo;
        if (userDetails != null && userDetails.getTicketUser() != null) {
            logo = generateImage(userDetails.getTicketUser().getLogo());
            if (logo != null) {
                logo.setWidth("75px");
                logo.setHeight("75px");
            }
            headerView.customizeHeader(logo, getTitlePrefix() + userDetails.getTicketUser().getDisplayName());
        }
    }

    private Image generateImage(byte[] logo) {
        if (logo != null) {
            StreamResource resource = new StreamResource("myimage.jpg", () -> new ByteArrayInputStream(logo));
            return new Image(resource, "Logo");
        }
        return null;
    }

    protected String getTitlePrefix() {
        return "";
    }
}
