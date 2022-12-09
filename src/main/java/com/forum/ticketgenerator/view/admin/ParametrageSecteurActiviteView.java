package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.ColorAvailable;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.database.SecteurActiviteModelService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ParametrageSecteurActiviteView extends AParametrageView<SecteurActivite> {

    private ComboBox<ColorAvailable> colorAvailableComboBox;

    @Override
    protected String getTitle () {
        return "Paramétrage secteur d'activité";
    }

    @Override
    protected Class<SecteurActivite> getGridType () {
        return SecteurActivite  .class;
    }

    @Override
    protected IParametrageService<SecteurActivite> getParametrageService () {
        return modelServiceFactory.getSecteurService();
    }

    protected List<com.vaadin.flow.component.Component> getCustomComponents() {
        List<com.vaadin.flow.component.Component> components = new ArrayList<>();
        colorAvailableComboBox = new ComboBox<>();
        colorAvailableComboBox.setItems(ColorAvailable.values());
        colorAvailableComboBox.setItemLabelGenerator(ColorAvailable::getValue);
        components.add(colorAvailableComboBox);
        return components;
    }

    @Override
    protected void addCustomColumns() {
        grid.addColumn("couleur");
        grid.getColumnByKey("couleur").setHeader("Couleur associée");
    }

    @Override
    protected SecteurActivite save () {
        SecteurActiviteModelService secteurActiviteModelService = (SecteurActiviteModelService) getParametrageService();
        return secteurActiviteModelService.enregistrer(valueToAdd.getValue(), this.colorAvailableComboBox.getValue().getValue(), this.evenement);
    }
}
