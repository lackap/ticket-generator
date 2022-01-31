package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.event.SearchFormationEvent;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.SearchService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.Format;

@Component
public class FormationView extends VerticalLayout {

    @Autowired
    private ModelService modelService;

    @Autowired
    private SearchService searchService;

    private Select<String> selectCentre;

    private Select<String> selectDiplome;

    private Button buttonSearchFormation;

    public FormationView() {

    }

    @PostConstruct
    public void init(){
        selectCentre = new Select<>();
        selectCentre.setLabel("Centre de formation");
        selectCentre.setItems(modelService.getCentreFormationLabels());
        selectCentre.addValueChangeListener(event -> {
            if (StringUtils.isEmpty(selectCentre.getValue())) {
                selectDiplome.setEnabled(false);
                selectDiplome.setValue("");
            } else {
                selectDiplome.setEnabled(true);
                selectDiplome.setItems(modelService.getAllFormations().get(selectCentre.getValue()).getDiplomeLabels());
            }
        });
        add(selectCentre);
        selectDiplome = new Select<>();
        selectDiplome.setLabel("Diplome");
        selectDiplome.setEnabled(false);
        selectDiplome.addValueChangeListener(event -> {
            if (StringUtils.isEmpty(selectDiplome.getValue())) {
                buttonSearchFormation.setEnabled(false);
            } else {
                buttonSearchFormation.setEnabled(true);
            }
        });
        add(selectDiplome);
        buttonSearchFormation = new Button();
        buttonSearchFormation.setText("Chercher depuis formation");
        buttonSearchFormation.setEnabled(false);
        buttonSearchFormation.addClickListener(event -> {
                fireEvent(new FormationEvent(buttonSearchFormation, false,
                        searchService.searchFromFormation(selectCentre.getValue(), selectDiplome.getValue())));
        });
        add(buttonSearchFormation);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
