package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.SearchService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class SearchIntitulePosteView  extends VerticalLayout {

    @Autowired
    private ModelService modelService;

    @Autowired
    private SearchService searchService;

    private ComboBox<String> selectFamilleMetier;

    private Button buttonSearchPoste;

    public SearchIntitulePosteView() {

    }

    @PostConstruct
    public void init(){
        selectFamilleMetier = new ComboBox<>();
        selectFamilleMetier.setLabel("Famille Métier");
        selectFamilleMetier.setEnabled(true);
        selectFamilleMetier.setItems(modelService.getFamilleMetierEntreprises());
        selectFamilleMetier.addValueChangeListener(event -> {
            if (StringUtils.isEmpty(selectFamilleMetier.getValue())) {
                buttonSearchPoste.setEnabled(false);
            } else {
                buttonSearchPoste.setEnabled(true);
            }
        });
        add(selectFamilleMetier);
        buttonSearchPoste = new Button();
        buttonSearchPoste.setText("Chercher par famille métier");
        buttonSearchPoste.setEnabled(false);
        buttonSearchPoste.addClickListener(event -> {
            fireEvent(new FormationEvent(buttonSearchPoste, false,
                    searchService.searchFromFamilleMetier(selectFamilleMetier.getValue())));
        });
        add(buttonSearchPoste);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}

