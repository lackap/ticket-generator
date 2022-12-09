package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteButtonComponent<T> extends Button {

    private T objectToDelete;

    private IParametrageService<T> parametrageService;

    public DeleteButtonComponent(T objectToDelete, IParametrageService<T> parametrageService) {
        this.objectToDelete = objectToDelete;
        this.parametrageService = parametrageService;
        this.setText("Supprimer");
    }
}
