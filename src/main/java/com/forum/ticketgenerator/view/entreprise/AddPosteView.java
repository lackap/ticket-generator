package com.forum.ticketgenerator.view.entreprise;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.event.SearchEvent;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.security.ApplicationUser;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.DatabaseModelService;
import com.forum.ticketgenerator.service.model.EntrepriseModelService;
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
    private Button ajoutButton;

    @Autowired
    private DatabaseModelService databaseModelService;
    @Autowired
    private EntrepriseModelService entrepriseModelService;
    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {

        intitulePoste = new TextField();
        intitulePoste.setLabel("Intitulé de poste : ");

        familleMetier = new ComboBox<>();
        familleMetier.setItems(databaseModelService.searchAllFamilleMetier());
        familleMetier.setLabel("Famille métier : ");
        familleMetier.setItemLabelGenerator(FamilleMetier::getIntitule);

        niveau = new ComboBox<>();
        niveau.setItems(databaseModelService.searchAllNiveau());
        niveau.setLabel("Niveau : ");
        niveau.setItemLabelGenerator(Niveau::getIntitule);

        typeContrat = new ComboBox<>();
        typeContrat.setItems(databaseModelService.searchAllFamilleTypeContrat());
        typeContrat.setLabel("Type de contrat : ");
        typeContrat.setItemLabelGenerator(TypeContrat::getIntitule);
        ajoutButton = new Button();
        ajoutButton.setText("Ajouter poste");
        ajoutButton.addClickListener(event -> {
            ApplicationUser userDetails = (ApplicationUser) securityService.getAuthenticatedUser();
            entrepriseModelService.addPoste(userDetails.getTicketUser().getDisplayName(), intitulePoste.getValue(),
                    familleMetier.getValue(), niveau.getValue(), typeContrat.getValue());
            fireEvent(new ReloadEvent(ajoutButton, false));
        });
        add(intitulePoste, familleMetier, niveau, typeContrat, ajoutButton);

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
