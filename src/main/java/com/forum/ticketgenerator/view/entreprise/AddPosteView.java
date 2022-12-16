package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class AddPosteView extends HorizontalLayout implements BeforeEnterObserver {

    private TextField intitulePoste;
    private ComboBox<FamilleMetier> familleMetier;
    private ComboBox<Niveau> niveau;
    private ComboBox<TypeContrat> typeContrat;
    private Button ajoutButton;

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {

        setAlignItems(Alignment.CENTER);
        ApplicationUser userDetails = securityService.getAuthenticatedUser();

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

        ajoutButton = new Button();
        ajoutButton.setText("Ajouter poste");
        ajoutButton.addClickListener(event -> {
            try {
                modelServiceFactory.getEntrepriseService().addPoste(userDetails.getTicketUser().getDisplayName(), intitulePoste.getValue(),
                        familleMetier.getValue(), niveau.getValue(), typeContrat.getValue(), userDetails.getEvenement());
                fireEvent(new ReloadEvent(ajoutButton, null,false));
            } catch (ModelCreationException p) {
                fireEvent(new ReloadEvent(ajoutButton, p.getErrorMessage(), false));
            }
        });

        add(intitulePoste, familleMetier);
        if (userDetails.getEvenement().getDisplayNiveau()) {
            add(niveau);
        }
        if (userDetails.getEvenement().getDisplayTypeContrat()) {
            add(typeContrat);
        }
        add(ajoutButton);

    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        ApplicationUser userDetails = securityService.getAuthenticatedUser();
        niveau.setItems(modelServiceFactory.getNiveauService().searchParEvenement(userDetails.getEvenement()));
        typeContrat.setItems(modelServiceFactory.getTypeContratService().searchParEvenement(userDetails.getEvenement()));
        familleMetier.setItems(modelServiceFactory.getFamilleMetierService().searchParEvenement(userDetails.getEvenement()));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
