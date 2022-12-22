package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ParametrageFamilleMetierView extends AParametrageView<FamilleMetier> {

    @Autowired
    private AjoutFamilleMetierView ajoutFamilleMetierView;

    @Override
    protected String getTitle () {
        return "Paramétrage famille métier";
    }

    @Override
    protected Class<FamilleMetier> getGridType () {
        return FamilleMetier.class;
    }

    @Override
    protected AjoutFamilleMetierView getParametrageView () {
        return ajoutFamilleMetierView;
    }

    @Override
    protected IParametrageService<FamilleMetier> getParametrageService () {
        return modelServiceFactory.getFamilleMetierService();
    }
}
