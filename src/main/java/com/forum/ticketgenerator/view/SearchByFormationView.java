package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.event.SearchEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class SearchByFormationView extends ASearchByLayout {

    private final static String SEARCH_LABEL = "Recherche par formation";

    private ComboBox<String> selectCentre;

    private ComboBox<String> selectDiplome;

    private Button buttonSearchFormation;

    @PostConstruct
    public void init(){
        Div titre = new Div();
        titre.setWidth("100%");
        titre.getElement().getStyle().set("font-size", "20px");
        titre.getElement().getStyle().set("font-weight", "bold");
        titre.getElement().getStyle().set("text-align", "center");
        Span span = new Span();
        Text html = new Text(SEARCH_LABEL);
        span.add(html);
        titre.add(span);
        add(titre);
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
        selectDiplome.addValueChangeListener(event -> buttonSearchFormation.setEnabled(!StringUtils.isEmpty(selectDiplome.getValue())));
        add(selectDiplome);
        buttonSearchFormation = new Button();
        buttonSearchFormation.setText("Chercher depuis formation");
        buttonSearchFormation.setEnabled(false);
        buttonSearchFormation.addClickListener(event -> fireEvent(new SearchEvent(buttonSearchFormation, false,
                searchService.searchFromFormation(selectCentre.getValue(), selectDiplome.getValue()),
                SEARCH_LABEL + " " + selectCentre.getValue() + " / " + selectDiplome.getValue())));
        add(buttonSearchFormation);
    }

}
