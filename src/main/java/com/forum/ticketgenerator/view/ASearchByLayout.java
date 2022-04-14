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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ASearchByLayout extends VerticalLayout {

    @Autowired
    protected ModelService modelService;

    @Autowired
    protected SearchService searchService;

    public ASearchByLayout () {
        getStyle().set("border", "1px solid black");
        setHeightFull();
        setAlignItems(Alignment.CENTER);//puts button in horizontal  center
        setJustifyContentMode(JustifyContentMode.CENTER);//puts button in vertical center
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
