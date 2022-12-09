package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
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
public class ParametrageNiveauView extends AParametrageView<Niveau> {

    @Override
    protected String getTitle () {
        return "Paramétrage des niveaux de contrats";
    }

    @Override
    protected Class<Niveau> getGridType () {
        return Niveau.class;
    }

    @Override
    protected IParametrageService<Niveau> getParametrageService () {
        return modelServiceFactory.getNiveauService();
    }
}
