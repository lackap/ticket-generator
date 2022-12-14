package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.ColorAvailable;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.database.SecteurActiviteModelService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
@UIScope
public class AjoutSecteurActiviteView extends AAddParametrageView<SecteurActivite> {

    private ComboBox<ColorAvailable> colorAvailableComboBox;

    @Override
    protected IParametrageService<SecteurActivite> getParametrageService () {
        return modelServiceFactory.getSecteurService();
    }

    @Override
    protected List<Component> getCustomComponents() {
        List<com.vaadin.flow.component.Component> components = new ArrayList<>();
        colorAvailableComboBox = new ComboBox<>();
        colorAvailableComboBox.setLabel("Couleur associée :");
        colorAvailableComboBox.setItems(ColorAvailable.values());
        colorAvailableComboBox.setItemLabelGenerator(ColorAvailable::getValue);
        components.add(colorAvailableComboBox);
        return components;
    }

    @Override
    protected SecteurActivite save () throws ModelCreationException {
        SecteurActiviteModelService secteurActiviteModelService = (SecteurActiviteModelService) getParametrageService();
        return secteurActiviteModelService.enregistrer(valueToAdd.getValue(), this.colorAvailableComboBox.getValue(), this.evenement);
    }
}
