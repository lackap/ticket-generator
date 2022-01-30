package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.Format;

@Component
public class FormationView extends VerticalLayout {

    @Autowired
    private ModelService modelService;

    private Select<String> selectCentre;

    private Select<String> selectDiplome;

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
        add(selectDiplome);
    }


}
