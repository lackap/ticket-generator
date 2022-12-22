package com.forum.ticketgenerator.view.admin;

import com.vaadin.flow.component.html.Image;

public class DeleteButtonComponent extends Image {

    public DeleteButtonComponent() {
        super("img/delete.png", "Supprimer");
        setWidth("15px");
        setHeight("15px");
        getStyle().set("cursor", "pointer");
    }
}
