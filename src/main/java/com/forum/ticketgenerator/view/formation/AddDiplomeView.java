package com.forum.ticketgenerator.view.formation;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.DiplomeCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class AddDiplomeView extends HorizontalLayout {

    private TextField intituleDiplome;
    private ComboBox<FamilleMetier> familleMetier;
    private Button ajoutButton;

    @Autowired
    private ModelServiceFactory modelServiceFactory;
    @Autowired
    private SecurityService securityService;

    private Evenement evenement;

    @PostConstruct
    public void init() {

        setAlignItems(Alignment.CENTER);
        intituleDiplome = new TextField();
        intituleDiplome.setLabel("Intitulé de diplome : ");

        familleMetier = new ComboBox<>();
        familleMetier.setLabel("Famille métier : ");
        familleMetier.setItemLabelGenerator(FamilleMetier::getIntitule);

        ajoutButton = new Button();
        ajoutButton.setText("Ajouter diplome");
        ajoutButton.addClickListener(event -> {
            try {
                ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
                modelServiceFactory.getFormationService().addDiplome(userDetails.getTicketUser().getDisplayName(), intituleDiplome.getValue(),
                        familleMetier.getValue(), this.evenement);
                fireEvent(new ReloadEvent(ajoutButton, null,false));
            } catch (DiplomeCreationException p) {
                fireEvent(new ReloadEvent(ajoutButton, p.getErrorMessage(), false));
            }
        });
        add(intituleDiplome, familleMetier, ajoutButton);

    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        familleMetier.setItems(modelServiceFactory.getFamilleMetierService().searchParEvenement(evenement));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
