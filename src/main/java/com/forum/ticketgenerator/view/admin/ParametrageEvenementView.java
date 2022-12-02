package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "parametrageEvenement")
@PermitAll
@UIScope
public class ParametrageEvenementView extends AParametrageView<Evenement> {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Override
    protected String getTitle () {
        return "Paramétrage evenement";
    }

    @Override
    protected Class<Evenement> getGridType () {
        return Evenement.class;
    }

    @Override
    protected List<Evenement> getItems () {
        return modelServiceFactory.getEvenementService().searchAllEvenement();
    }

    @Override
    protected void save () {
        modelServiceFactory.getEvenementService().enregistrer(valueToAdd.getValue());
    }
}
