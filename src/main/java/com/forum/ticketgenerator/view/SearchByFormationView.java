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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class SearchByFormationView extends ASearchByLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchByFormationView.class);

    private final static String SEARCH_LABEL = "Recherche par formation";

    private ComboBox<String> selectCentre;

    private ComboBox<String> selectDiplome;

    private Button buttonSearchFormation;

    @PostConstruct
    public void init() throws IOException {
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
                    try {
                        selectDiplome.setItems(modelService.getAllDiplomesLabels());
                    } catch (IOException e) {
                        LOGGER.error("Erreur lors de la r??cup??ration de tout les libell??s de diplomes", e);
                    }
                } else {
                    try {
                        selectDiplome.setItems(modelService.getDiplomesLabels((selectCentre.getValue())));
                    } catch (IOException e) {
                        LOGGER.error("Erreur lors de la r??cup??ration des libell??s de diplomes du centre " + selectCentre.getValue(), e);
                    }
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
        buttonSearchFormation.addClickListener(event -> {
            try {
                fireEvent(new SearchEvent(buttonSearchFormation, false,
                        modelService.searchFromFormation(selectCentre.getValue(), selectDiplome.getValue()),
                        SEARCH_LABEL + " " + selectCentre.getValue() + " / " + selectDiplome.getValue()));
            } catch (IOException e) {
                LOGGER.error("Erreur lors de la recherche des diplomes li??s a " + selectCentre.getValue() + " / " + selectDiplome.getValue(), e);
            }
        });
        add(buttonSearchFormation);
    }

}
