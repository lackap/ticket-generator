package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.view.HeaderView;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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
    private ParametrageAdminTabsView parametrageAdminTabs;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() throws IOException {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        headerView.customizeHeader("Paramétrage " + applicationUser.getEvenement().getIntitule());
        add(headerView);
        setAlignItems(FlexComponent.Alignment.CENTER);
        parametrageAdminTabs.updateEvent(applicationUser.getEvenement());
        add(parametrageAdminTabs);

    }

}
