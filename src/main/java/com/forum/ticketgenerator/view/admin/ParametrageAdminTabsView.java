package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class ParametrageAdminTabsView extends VerticalLayout {

    @Autowired
    private ParametrageFamilleMetierView parametrageFamilleMetier;

    @Autowired
    private ParametrageNiveauView parametrageNiveauView;

    @Autowired
    private ParametrageTypeContratView parametrageTypeContrat;

    @Autowired
    private ParametrageSecteurActiviteView parametrageSecteurActivite;

    @Autowired
    private ParametrageComportementEvenementView parametrageComportementEvenement;

    @Autowired
    private ParametrageLienEntrepriseSecteurView parametrageLienEntrepriseSecteur;

    @Autowired
    private SecurityService securityService;

    private VerticalLayout content;
    private Tab familleMetierTab;
    private Tab niveauTab;
    private Tab typeContratTab;
    private Tab secteurActiviteTab;
    private Tab comportementTab;
    private Tab lienEntrepriseSecteurTab;

    @PostConstruct
    public void init() throws IOException {
        familleMetierTab = new Tab("Famille métier");
        niveauTab = new Tab("Niveau");
        typeContratTab = new Tab("Type contrat");
        secteurActiviteTab = new Tab(securityService.getAuthenticatedUser().getEvenement().getLabelSecteurActivite());
        comportementTab = new Tab("Comportement Evenement");
        lienEntrepriseSecteurTab = new Tab("Lien entreprise / " + securityService.getAuthenticatedUser().getEvenement().getLabelSecteurActivite());
        Tabs tabs = new Tabs(familleMetierTab, niveauTab, typeContratTab, secteurActiviteTab,
                comportementTab, lienEntrepriseSecteurTab);
        tabs.addSelectedChangeListener(
                event -> setContent(event.getSelectedTab()));
        content = new VerticalLayout();
        content.setSpacing(false);
        content.add(parametrageFamilleMetier);
        tabs.setSelectedTab(familleMetierTab);
        add(tabs);
        add(content);
    }

    public void updateEvent(Evenement evenement) {
        this.parametrageFamilleMetier.setEvenement(evenement);
        this.parametrageNiveauView.setEvenement(evenement);
        this.parametrageSecteurActivite.setEvenement(evenement);
        this.parametrageTypeContrat.setEvenement(evenement);
    }

    private void setContent(Tab tab) {
        content.removeAll();
        if (tab == null) {
            return;
        }
        if (tab.equals(familleMetierTab)) {
            content.add(parametrageFamilleMetier);
        } else if (tab.equals(niveauTab)) {
            content.add(parametrageNiveauView);
        } else if (tab.equals(typeContratTab)) {
            content.add(parametrageTypeContrat);
        } else if (tab.equals(secteurActiviteTab)) {
            content.add(parametrageSecteurActivite);
        } else if (tab.equals(comportementTab)) {
            content.add(parametrageComportementEvenement);
        } else if (tab.equals(lienEntrepriseSecteurTab))  {
            parametrageLienEntrepriseSecteur.update();
            content.add(parametrageLienEntrepriseSecteur);
        }
    }
}
