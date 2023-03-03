package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.event.SearchEvent;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Formation;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
@UIScope
public class SearchByFormationView extends ASearchByLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchByFormationView.class);

    private final static String SEARCH_LABEL = "Recherche par formation";

    private ComboBox<Formation> selectCentre;

    private ComboBox<Diplome> selectDiplome;

    private Button buttonSearchFormation;

    private Boolean hasFormations;

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
        selectCentre.setItemLabelGenerator(Formation::getNomCentre);
        List<Formation> formations = modelServiceFactory.getFormationService().getCentresFormation(getEvenement());
        selectCentre.setItems(formations);
        hasFormations = formations.size() > 0;
        selectCentre.addValueChangeListener(event -> {
            if (selectCentre.getValue() == null) {
                selectDiplome.setEnabled(false);
                selectDiplome.setValue(null);
            } else {
                selectDiplome.setEnabled(true);
                if (ApplicationConstants.AUCUN_DIPLOME.equals(selectCentre.getValue().getNomCentre())) {
                    try {
                        selectDiplome.setItems(modelServiceFactory.getFormationService().getAllDiplomes());
                    } catch (IOException e) {
                        LOGGER.error("Erreur lors de la récupération de tout les libellés de diplomes", e);
                    }
                } else {
                    try {
                        selectDiplome.setItems(modelServiceFactory.getFormationService().getDiplomes(selectCentre.getValue().getNomCentre()));
                    } catch (IOException e) {
                        LOGGER.error("Erreur lors de la récupération des libellés de diplomes du centre " + selectCentre.getValue(), e);
                    }
                }
            }
        });
        add(selectCentre);
        selectDiplome = new ComboBox<>();
        selectDiplome.setLabel("Diplome");
        selectDiplome.setEnabled(false);
        selectDiplome.addValueChangeListener(event -> buttonSearchFormation.setEnabled(selectDiplome.getValue() != null));
        selectDiplome.setItemLabelGenerator(Diplome::getIntituleDiplome);
        add(selectDiplome);
        buttonSearchFormation = new Button();
        buttonSearchFormation.setText("Chercher depuis formation");
        buttonSearchFormation.setEnabled(false);
        buttonSearchFormation.addClickListener(event -> {
            try {
                fireEvent(new SearchEvent(buttonSearchFormation, false,
                        modelServiceFactory.getPosteService().searchFromFamilleMetier(selectDiplome.getValue().getFamilleMetier(), getEvenement()),
                        SEARCH_LABEL + " " + selectCentre.getValue().getNomCentre() + " / " + selectDiplome.getValue().getIntituleDiplome()));
            } catch (IOException e) {
                LOGGER.error("Erreur lors de la recherche des diplomes liés a " + selectCentre.getValue() + " / " + selectDiplome.getValue(), e);
            }
        });
        add(buttonSearchFormation);
    }

    public boolean hasFormations() {
        return hasFormations;
    }
}
