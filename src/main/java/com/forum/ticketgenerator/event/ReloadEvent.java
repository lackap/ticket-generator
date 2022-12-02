package com.forum.ticketgenerator.event;

import com.forum.ticketgenerator.model.PosteMatching;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.upload.Upload;

import java.util.List;

public class ReloadEvent extends ComponentEvent<Component> {


    public ReloadEvent (Component source, boolean fromClient) {
        super(source, fromClient);
    }

}
