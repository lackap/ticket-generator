package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.SearchEvent;
import com.forum.ticketgenerator.model.database.FamilleMetier;
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

@Component
@UIScope
public class SearchByFamilleMetierView extends ASearchByLayout implements BeforeEnterObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchByFamilleMetierView.class);

    private final static String SEARCH_LABEL = "Recherche par famille métier";

    private ComboBox<FamilleMetier> selectFamilleMetier;

    private Button buttonSearchPoste;

    public SearchByFamilleMetierView () {

    }

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
        selectFamilleMetier = new ComboBox<>();
        selectFamilleMetier.setLabel("Famille Métier");
        selectFamilleMetier.setEnabled(true);
        selectFamilleMetier.setItemLabelGenerator(FamilleMetier::getIntitule);
        selectFamilleMetier.addValueChangeListener(event -> buttonSearchPoste.setEnabled(selectFamilleMetier.getValue() != null));
        add(selectFamilleMetier);
        buttonSearchPoste = new Button();
        buttonSearchPoste.setText("Chercher par famille métier");
        buttonSearchPoste.setEnabled(false);
        buttonSearchPoste.addClickListener(event -> {
            try {
                fireEvent(new SearchEvent(buttonSearchPoste, false,
                        modelServiceFactory.getPosteService().searchFromFamilleMetier(selectFamilleMetier.getValue(), getEvenement()),
                        SEARCH_LABEL + " " + selectFamilleMetier.getValue().getIntitule()));
            } catch (IOException e) {
                LOGGER.error("Erreur lors de la recherche depuis la famille métier " + selectFamilleMetier.getValue(), e);
            }
        });
        add(buttonSearchPoste);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        try {
            selectFamilleMetier.setItems(modelServiceFactory.getEntrepriseService().getFamilleMetierEntreprises(getEvenement()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

