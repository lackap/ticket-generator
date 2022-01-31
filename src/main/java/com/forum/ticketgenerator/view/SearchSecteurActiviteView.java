package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.SearchService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SearchSecteurActiviteView extends VerticalLayout {

    @Autowired
    private ModelService modelService;

    @Autowired
    private SearchService searchService;

    private Select<String> selectSecteurActivite;

    private Button buttonSearchSecteur;

    public SearchSecteurActiviteView () {

    }

    @PostConstruct
    public void init(){
        selectSecteurActivite = new Select<>();
        selectSecteurActivite.setLabel("Secteur activité");
        selectSecteurActivite.setEnabled(true);
        selectSecteurActivite.setItems(modelService.getSecteursActivitesEntreprises());
        selectSecteurActivite.addValueChangeListener(event -> {
            if (StringUtils.isEmpty(selectSecteurActivite.getValue())) {
                buttonSearchSecteur.setEnabled(false);
            } else {
                buttonSearchSecteur.setEnabled(true);
            }
        });
        add(selectSecteurActivite);
        buttonSearchSecteur = new Button();
        buttonSearchSecteur.setText("Chercher par secteur d'activité");
        buttonSearchSecteur.setEnabled(false);
        buttonSearchSecteur.addClickListener(event -> {
            fireEvent(new FormationEvent(buttonSearchSecteur, false,
                    searchService.searchFromSecteurActivite(selectSecteurActivite.getValue())));
        });
        add(buttonSearchSecteur);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}

