package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.event.SearchFormationEvent;
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
