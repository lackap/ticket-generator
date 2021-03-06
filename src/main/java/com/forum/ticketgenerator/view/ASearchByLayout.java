package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public class ASearchByLayout extends VerticalLayout {

    @Autowired
    protected ModelService modelService;

    public ASearchByLayout () {
        getStyle().set("border", "1px solid black");
        setAlignItems(Alignment.CENTER);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
