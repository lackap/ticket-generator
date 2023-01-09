package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class AjoutEntrepriseSecteurView extends VerticalLayout {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private SecurityService securityService;

    private ComboBox<EntrepriseDTO> entreprise;
    private ComboBox<SecteurActivite> secteurActivite;

    public AjoutEntrepriseSecteurView()  {
    }


    @PostConstruct
    public void init() throws IOException {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        entreprise = new ComboBox<>();
        entreprise.setLabel("Dénomination");
        entreprise.setItemLabelGenerator(EntrepriseDTO::getNom);
        secteurActivite = new ComboBox<>();
        secteurActivite.setLabel(applicationUser.getEvenement().getLabelSecteurActivite());
        secteurActivite.setItemLabelGenerator(SecteurActivite::getIntitule);
        Button addButton = new Button("Assigner secteur");
        addButton.addClickListener(event -> {
            modelServiceFactory.getEntrepriseService().ajouterSecteurActivite(entreprise.getValue().getNom(), secteurActivite.getValue());
            fireEvent(new ReloadEvent(addButton, null,false));
        });
        update();
        add(entreprise, secteurActivite, addButton);
    }

    public void update() {
        ApplicationUser applicationUser = securityService.getAuthenticatedUser();
        entreprise.setItems(modelServiceFactory.getEntrepriseService().searchAllEntreprise(applicationUser.getEvenement()));
        secteurActivite.setItems(modelServiceFactory.getSecteurService().searchParEvenement(applicationUser.getEvenement()));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
