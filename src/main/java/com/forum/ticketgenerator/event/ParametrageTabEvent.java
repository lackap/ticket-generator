package com.forum.ticketgenerator.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class ParametrageTabEvent extends ComponentEvent<Component> {

    private final Component component;

    public ParametrageTabEvent (Component source, boolean fromClient, Component component) {
        super(source, fromClient);
        this.component = component;
    }

    public Component getComponent() {
        return this.component;
    }
}
