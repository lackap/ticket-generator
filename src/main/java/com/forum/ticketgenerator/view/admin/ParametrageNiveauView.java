package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ParametrageNiveauView extends AParametrageView<Niveau> {

    @Autowired
    private AjoutNiveauView ajoutNiveau;

    @Override
    protected String getTitle () {
        return "Paramétrage niveaux";
    }

    @Override
    protected Class<Niveau> getGridType () {
        return Niveau.class;
    }

    @Override
    protected AjoutNiveauView getParametrageView () {
        return ajoutNiveau;
    }

    @Override
    protected IParametrageService<Niveau> getParametrageService () {
        return modelServiceFactory.getNiveauService();
    }
}
