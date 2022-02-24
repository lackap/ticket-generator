package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.event.SearchFormationEvent;
import com.forum.ticketgenerator.model.Entreprise;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SearchView extends HorizontalLayout {

    @Autowired
    private FormationView formationView;

    @Autowired
    private SearchIntitulePosteView searchIntitulePosteView;

    @Autowired
    private SearchSecteurActiviteView searchSecteurActiviteView;

    public SearchView () {
    }


    @PostConstruct
    public void init() {
        setWidth("100%");
        addListener(FormationEvent.class, event -> {
            fireEvent(new SearchFormationEvent(event.getSource(), false, event.getPostesMatching()));
        });
        formationView.addListener(FormationEvent.class, event -> {
            fireEvent(event);
        });
        searchIntitulePosteView.addListener(FormationEvent.class, event -> {
            fireEvent(event);
        });
        searchSecteurActiviteView.addListener(FormationEvent.class, event -> {
            fireEvent(event);
        });

        add(formationView);
        add(searchIntitulePosteView);
        add(searchSecteurActiviteView);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
