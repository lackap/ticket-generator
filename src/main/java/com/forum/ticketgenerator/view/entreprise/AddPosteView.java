package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.*;
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
public class AddPosteView extends HorizontalLayout {

    private TextField intitulePoste;
    private ComboBox<FamilleMetier> familleMetier;
    private ComboBox<Niveau> niveau;
    private ComboBox<TypeContrat> typeContrat;
    private ComboBox<SecteurActivite> secteurActivite;
    private Button ajoutButton;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private SecurityService securityService;

    private Evenement evenement;

    @PostConstruct
    public void init() {

        setAlignItems(Alignment.CENTER);

        intitulePoste = new TextField();
        intitulePoste.setLabel("Intitulé de poste : ");

        familleMetier = new ComboBox<>();
        familleMetier.setLabel("Famille métier : ");
        familleMetier.setItemLabelGenerator(FamilleMetier::getIntitule);

        niveau = new ComboBox<>();
        niveau.setLabel("Niveau : ");
        niveau.setItemLabelGenerator(Niveau::getIntitule);

        typeContrat = new ComboBox<>();
        typeContrat.setLabel("Type de contrat : ");
        typeContrat.setItemLabelGenerator(TypeContrat::getIntitule);

        secteurActivite = new ComboBox<>();
        secteurActivite.setItemLabelGenerator(SecteurActivite::getIntitule);

        ajoutButton = new Button();
        ajoutButton.setText("Ajouter poste");
        ajoutButton.addClickListener(event -> {
            try {
                ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
                modelServiceFactory.getEntrepriseService().addPoste(userDetails.getTicketUser().getDisplayName(), intitulePoste.getValue(),
                        familleMetier.getValue(), niveau.getValue(), typeContrat.getValue(), secteurActivite.getValue(), evenement);
                fireEvent(new ReloadEvent(ajoutButton, null,false));
            } catch (ModelCreationException p) {
                fireEvent(new ReloadEvent(ajoutButton, p.getErrorMessage(), false));
            }
        });

    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        niveau.setItems(modelServiceFactory.getNiveauService().searchParEvenement(this.evenement));
        typeContrat.setItems(modelServiceFactory.getTypeContratService().searchParEvenement(this.evenement));
        familleMetier.setItems(modelServiceFactory.getFamilleMetierService().searchParEvenement(evenement));
        secteurActivite.setItems(modelServiceFactory.getSecteurService().searchParEvenement(evenement));
        if (evenement != null && evenement.getLabelSecteurActivité() != null) {
            secteurActivite.setLabel(evenement.getLabelSecteurActivité() + " :");
        } else {
            secteurActivite.setLabel("Secteur activité : ");
        }
        add(intitulePoste, familleMetier);
        if (evenement.getDisplayNiveau()) {
            add(niveau);
        }
        if (evenement.getDisplayTypeContrat()) {
            add(typeContrat);
        }
        if (evenement.getDisplaySecteur()) {
            add(secteurActivite);
        }
        add(ajoutButton);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
