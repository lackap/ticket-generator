package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "parametrageNiveau")
@PermitAll
@UIScope
public class ParametrageNiveauView extends AParametrageView<Niveau> {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Override
    protected String getTitle () {
        return "Paramétrage des niveaux de contrats";
    }

    @Override
    protected Class<Niveau> getGridType () {
        return Niveau.class;
    }

    @Override
    protected List<Niveau> getItems () {
        return modelServiceFactory.getNiveauService().searchAllNiveau();
    }

    @Override
    protected void save () {
        modelServiceFactory.getNiveauService().enregistrer(valueToAdd.getValue());
    }
}
