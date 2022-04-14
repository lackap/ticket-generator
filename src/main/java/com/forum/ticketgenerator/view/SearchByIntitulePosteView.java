package com.forum.ticketgenerator.view;

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
public class SearchByIntitulePosteView extends ASearchByLayout {

    private final static String SEARCH_LABEL = "Recherche par famille métier";

    private ComboBox<String> selectFamilleMetier;

    private Button buttonSearchPoste;

    public SearchByIntitulePosteView () {

    }

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
        selectFamilleMetier = new ComboBox<>();
        selectFamilleMetier.setLabel("Famille Métier");
        selectFamilleMetier.setEnabled(true);
        selectFamilleMetier.setItems(modelService.getFamilleMetierEntreprises());
        selectFamilleMetier.addValueChangeListener(event -> buttonSearchPoste.setEnabled(!StringUtils.isEmpty(selectFamilleMetier.getValue())));
        add(selectFamilleMetier);
        buttonSearchPoste = new Button();
        buttonSearchPoste.setText("Chercher par famille métier");
        buttonSearchPoste.setEnabled(false);
        buttonSearchPoste.addClickListener(event -> fireEvent(new SearchEvent(buttonSearchPoste, false,
                searchService.searchFromFamilleMetier(selectFamilleMetier.getValue()),
                SEARCH_LABEL + " " + selectFamilleMetier.getValue())));
        add(buttonSearchPoste);
    }
}

