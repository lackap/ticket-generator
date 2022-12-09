package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
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

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private ParametrageAdminTabsView parametrageAdminTabs;

    @Autowired
    private SecurityService securityService;

    private ComboBox<Evenement> evenement;

    @PostConstruct
    public void init() throws IOException {
        headerView.customizeHeader("Gestion des paramétrages administrateur");
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        parametrageAdminTabs.updateEvent(applicationUser.getEvenement());
        add(parametrageAdminTabs);

    }

}
