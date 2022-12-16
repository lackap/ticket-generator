package com.forum.ticketgenerator.view.evenement;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@UIScope
public class EventListView extends HorizontalLayout {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {
        List<Evenement> evenements = modelServiceFactory.getEvenementService().searchAllEvenement();
        Integer count = 0;
        for (Evenement evenement : evenements) {
            EventView eventView = new EventView(evenement, securityService);
            eventView.setWidth("25%");
            add(eventView);
            if (count >= 2) {
                break;
            }
            count++;
        }

    }
}
