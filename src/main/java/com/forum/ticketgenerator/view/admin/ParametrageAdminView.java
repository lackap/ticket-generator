package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route(value = "admin")
@PermitAll
@UIScope
public class ParametrageAdminView extends VerticalLayout {

    @Autowired
    private HeaderView headerView;

    @PostConstruct
    public void init() throws IOException {
        headerView.customizeHeader("Gestion des paramétrages administrateurs");
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink familleMetierLink = new RouterLink("Gerer les Familles métier", ParametrageFamilleMetierView.class);
        RouterLink typeContratLink = new RouterLink("Gerer les types de contrat", ParametrageTypeContratView.class);
        RouterLink niveauLink = new RouterLink("Gerer les niveaux d'études", ParametrageNiveauView.class);
        RouterLink evenementsLink = new RouterLink("Gerer les évènements", ParametrageEvenementView.class);
        add(familleMetierLink, typeContratLink, niveauLink, evenementsLink);

    }

}
