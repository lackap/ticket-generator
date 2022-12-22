package com.forum.ticketgenerator.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class ReloadEvent extends ComponentEvent<Component> {

    private final String errorMessage;

    public ReloadEvent (Component source, String errorMessage, boolean fromClient) {
        super(source, fromClient);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage () {
        return errorMessage;
    }
}
