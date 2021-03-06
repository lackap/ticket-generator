package com.forum.ticketgenerator.view;

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
    private SearchByIntitulePosteView searchIntitulePosteView;

    @Autowired
    private SearchBySecteurActiviteView searchSecteurActiviteView;

    public SearchView () {
    }


    @PostConstruct
    public void init() {
        setWidth("100%");
        addListener(SearchEvent.class, event -> fireEvent(new SearchResultEvent(event.getSource(), false, event.getPostesMatching(), event.getLabel())));
        formationView.addListener(SearchEvent.class, this::fireEvent);
        searchIntitulePosteView.addListener(SearchEvent.class, this::fireEvent);
        searchSecteurActiviteView.addListener(SearchEvent.class, this::fireEvent);

        add(formationView);
        add(searchIntitulePosteView);
        add(searchSecteurActiviteView);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
