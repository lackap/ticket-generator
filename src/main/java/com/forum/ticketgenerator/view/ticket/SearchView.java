package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.SearchEvent;
import com.forum.ticketgenerator.event.SearchResultEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class SearchView extends HorizontalLayout {

    @Autowired
    private SearchByFormationView formationView;

    @Autowired
    private SearchByFamilleMetierView searchIntitulePosteView;

    @Autowired
    private SearchByEntrepriseView searchView;

    public SearchView () {
    }


    @PostConstruct
    public void init() {
        setWidth("100%");
        addListener(SearchEvent.class, event -> fireEvent(new SearchResultEvent(event.getSource(), false, event.getPostesMatching(), event.getLabel())));
        formationView.addListener(SearchEvent.class, this::fireEvent);
        searchIntitulePosteView.addListener(SearchEvent.class, this::fireEvent);
        searchView.addListener(SearchEvent.class, this::fireEvent);

        add(searchView);
        add(searchIntitulePosteView);
        if (formationView.hasFormations()) {
            add(formationView);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
