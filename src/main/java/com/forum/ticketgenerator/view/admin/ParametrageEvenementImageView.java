package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@UIScope
public class ParametrageEvenementImageView extends VerticalLayout {

    @Autowired
    private SecurityService securityService;

    private Div imagesDiv;

    private Image affiche;

    @PostConstruct
    public void init() throws IOException {
        imagesDiv = new Div();
        imagesDiv.setHeight("500px");
        reload();
        add(imagesDiv);
        setAlignItems(Alignment.CENTER);
    }

    public void reload() {
        Evenement evenement = securityService.getAuthenticatedUser().getEvenement();
        if (evenement.getAffiche() != null) {
            imagesDiv.removeAll();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String filename = "myfilename-" + df.format(new Date()) + ".png";
            StreamResource resource = new StreamResource(filename, () -> new ByteArrayInputStream(evenement.getAffiche()));
            affiche = new Image(resource, "Logo");
            affiche.setHeight("100%");
            affiche.setWidth("100%");
            imagesDiv.add(affiche);
        }
    }

}
