package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import java.util.List;

@Component
@UIScope
public class ParametrageTypeContratView extends AParametrageView<TypeContrat> {

    @Override
    protected String getTitle () {
        return "Paramétrage des types de contrat";
    }

    @Override
    protected Class<TypeContrat> getGridType () {
        return TypeContrat.class;
    }

    @Override
    protected IParametrageService<TypeContrat> getParametrageService () {
        return modelServiceFactory.getTypeContratService();
    }
}
