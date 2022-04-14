package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.SearchEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class SearchBySecteurActiviteView extends ASearchByLayout {

    private final static String SEARCH_LABEL = "Recherche par secteur d'activité";

    private Select<String> selectSecteurActivite;

    private Button buttonSearchSecteur;

    public SearchBySecteurActiviteView () {

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
        selectSecteurActivite = new Select<>();
        selectSecteurActivite.setLabel("Secteur activité");
        selectSecteurActivite.setEnabled(true);
        selectSecteurActivite.setItems(modelService.getSecteursActivitesEntreprises());
        selectSecteurActivite.addValueChangeListener(event -> buttonSearchSecteur.setEnabled(!StringUtils.isEmpty(selectSecteurActivite.getValue())));
        add(selectSecteurActivite);
        buttonSearchSecteur = new Button();
        buttonSearchSecteur.setText("Chercher par secteur d'activité");
        buttonSearchSecteur.setEnabled(false);
        buttonSearchSecteur.addClickListener(event -> fireEvent(new SearchEvent(buttonSearchSecteur, false,
                searchService.searchFromSecteurActivite(selectSecteurActivite.getValue()),
                SEARCH_LABEL + " " + selectSecteurActivite.getValue())));
        add(buttonSearchSecteur);
    }
}

