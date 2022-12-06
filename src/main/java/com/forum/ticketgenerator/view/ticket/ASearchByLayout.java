package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public class ASearchByLayout extends VerticalLayout {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    @Autowired
    protected SecurityService securityService;

    public ASearchByLayout () {
        getStyle().set("border", "1px solid black");
        setAlignItems(Alignment.CENTER);
    }

    protected Evenement getEvenement() {
        return securityService.getAuthenticatedUser().getEvenement();
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
