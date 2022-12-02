package com.forum.ticketgenerator.event;

import com.forum.ticketgenerator.model.PosteMatching;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.upload.Upload;
import lombok.Data;

import java.util.List;

public class ReloadEvent extends ComponentEvent<Component> {

    private String errorMessage;

    public ReloadEvent (Component source, String errorMessage, boolean fromClient) {
        super(source, fromClient);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage () {
        return errorMessage;
    }
}
