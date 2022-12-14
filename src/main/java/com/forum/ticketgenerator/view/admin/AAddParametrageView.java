package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AAddParametrageView<V>  extends HorizontalLayout {

    @Autowired
    protected ModelServiceFactory modelServiceFactory;

    protected TextField valueToAdd;

    protected Evenement evenement;

    protected Button ajoutButton;

    @PostConstruct
    public void init() throws IOException {
        setAlignItems(FlexComponent.Alignment.CENTER);
        valueToAdd = new TextField();
        valueToAdd.setLabel("Valeur : ");
        add(getComponentsToDisplay());
    }

    protected V executeButtonAction() {
        try {
            V item = save();
            fireEvent(new ReloadEvent(ajoutButton, null,false));
            return item;
        } catch (ModelCreationException e) {
            fireEvent(new ReloadEvent(ajoutButton, e.getErrorMessage(),false));
            return null;
        }
    }

    private Component[] getComponentsToDisplay() {
        ajoutButton = new Button("Ajouter");
        ajoutButton.addClickListener(event -> {
            executeButtonAction();
        });
        List<Component> fields = new ArrayList<>();
        fields.add(valueToAdd);
        fields.addAll(getCustomComponents());
        fields.add(ajoutButton);
        return fields.toArray(new Component[0]);
    }

    protected List<Component> getCustomComponents() {
        return new ArrayList<>();
    }

    protected abstract IParametrageService<V> getParametrageService();

    protected V save () throws ModelCreationException {
        return getParametrageService().enregistrer(valueToAdd.getValue(), this.evenement);
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
