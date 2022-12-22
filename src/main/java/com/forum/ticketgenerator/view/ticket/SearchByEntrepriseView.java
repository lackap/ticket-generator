package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.SearchEvent;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class SearchByEntrepriseView extends ASearchByLayout implements BeforeEnterObserver {

    private final static String SEARCH_LABEL = "Recherche par entreprise";

    private Select<EntrepriseDTO> selectEntreprise;

    private Button buttonSearchEntreprise;

    public SearchByEntrepriseView () {

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
        selectEntreprise = new Select<>();
        selectEntreprise.setLabel("Entreprise");
        selectEntreprise.setEnabled(true);
        selectEntreprise.setItemLabelGenerator(EntrepriseDTO::getNom);
        selectEntreprise.addValueChangeListener(event -> buttonSearchEntreprise.setEnabled(selectEntreprise.getValue() != null));
        add(selectEntreprise);
        buttonSearchEntreprise = new Button();
        buttonSearchEntreprise.setText("Chercher par entreprise");
        buttonSearchEntreprise.setEnabled(false);
        buttonSearchEntreprise.addClickListener(event -> fireEvent(new SearchEvent(buttonSearchEntreprise, false,
                modelServiceFactory.getEntrepriseService().searchFromEntrepriseNameAndEvenement(selectEntreprise.getValue().getNom(),
                        getEvenement()),
                SEARCH_LABEL + " " + selectEntreprise.getValue().getNom())));
        add(buttonSearchEntreprise);
    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        selectEntreprise.setItems(modelServiceFactory.getEntrepriseService().searchAllEntrepriseWithPosteInEvent(getEvenement()));
    }
}

