package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.constants.ApplicationConstants;
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
public class SearchByFormationView extends ASearchByLayout {

    private ComboBox<String> selectCentre;

    private ComboBox<String> selectDiplome;

    private Button buttonSearchFormation;

    @PostConstruct
    public void init(){
        selectCentre = new ComboBox<>();
        selectCentre.setLabel("Centre de formation");
        selectCentre.setItems(modelService.getCentreFormationLabels());
        selectCentre.addValueChangeListener(event -> {
            if (StringUtils.isEmpty(selectCentre.getValue())) {
                selectDiplome.setEnabled(false);
                selectDiplome.setValue("");
            } else {
                selectDiplome.setEnabled(true);
                if (ApplicationConstants.AUCUN_DIPLOME.equals(selectCentre.getValue())) {
                    selectDiplome.setItems(modelService.getAllDiplomes());
                } else {
                    selectDiplome.setItems(modelService.getAllFormations().get(selectCentre.getValue()).getDiplomeLabels());
                }
            }
        });
        add(selectCentre);
        selectDiplome = new ComboBox<>();
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

}
