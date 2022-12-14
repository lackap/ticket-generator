package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AjoutNiveauView extends AAddParametrageView<Niveau>{


    @Override
    protected IParametrageService<Niveau> getParametrageService () {
        return modelServiceFactory.getNiveauService();
    }
}
