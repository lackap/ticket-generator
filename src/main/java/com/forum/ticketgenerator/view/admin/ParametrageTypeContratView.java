package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "parametrageTypeContrat")
@PermitAll
@UIScope
public class ParametrageTypeContratView extends AParametrageView<TypeContrat> {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Override
    protected String getTitle () {
        return "Paramétrage des types de contrat";
    }

    @Override
    protected Class<TypeContrat> getGridType () {
        return TypeContrat.class;
    }

    @Override
    protected List<TypeContrat> getItems () {
        return modelServiceFactory.getTypeContratService().searchAllTypeContrat();
    }

    @Override
    protected void save () {
        modelServiceFactory.getTypeContratService().enregistrer(valueToAdd.getValue());
    }
}
