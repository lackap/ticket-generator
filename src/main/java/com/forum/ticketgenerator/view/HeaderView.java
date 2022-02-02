package com.forum.ticketgenerator.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.atmosphere.config.service.Post;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeaderView extends HorizontalLayout {

    private Text titre;

    public HeaderView() {}

    @PostConstruct
    public void init() {
        titre = new Text("Forum des entreprises");

        add(titre);

    }
}
