package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Component
@UIScope
public class ParametrageEvenementMainView extends HorizontalLayout {

    @Autowired
    private ParametrageComportementEvenementView comportementEvenementView;

    @Autowired
    private ParametrageEvenementImageView imageEvenementView;


    @PostConstruct
    public void init() throws IOException {
        add(comportementEvenementView);
        comportementEvenementView.addListener(ReloadEvent.class,
                event -> imageEvenementView.reload());
        add(imageEvenementView);
    }
}
