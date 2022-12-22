package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ParametrageTypeContratView extends AParametrageView<TypeContrat> {

    @Autowired
    private AjoutTypeContratView ajoutTypeContrat;

    @Override
    protected String getTitle () {
        return "Paramétrage des types de contrat";
    }

    @Override
    protected Class<TypeContrat> getGridType () {
        return TypeContrat.class;
    }

    @Override
    protected AjoutTypeContratView getParametrageView () {
        return ajoutTypeContrat;
    }

    @Override
    protected IParametrageService<TypeContrat> getParametrageService () {
        return modelServiceFactory.getTypeContratService();
    }
}
