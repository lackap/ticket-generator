package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class TicketView extends HorizontalLayout {

    @Autowired
    private FormationView formationView;

    public TicketView() {
    }

    @PostConstruct
    public void init() {
        add(formationView);
    }


}
