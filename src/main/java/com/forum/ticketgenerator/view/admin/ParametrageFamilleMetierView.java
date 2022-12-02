package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.service.model.ModelServiceFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "parametrageFamilleMetier")
@PermitAll
@UIScope
public class ParametrageFamilleMetierView extends AParametrageView<FamilleMetier> {

    @Autowired
    private ModelServiceFactory modelServiceFactory;

    @Override
    protected String getTitle () {
        return "Paramétrage famille métier";
    }

    @Override
    protected Class<FamilleMetier> getGridType () {
        return FamilleMetier.class;
    }

    @Override
    protected List<FamilleMetier> getItems () {
        return modelServiceFactory.getFamilleMetierService().searchAllFamilleMetier();
    }

    @Override
    protected void save () {
        modelServiceFactory.getFamilleMetierService().enregistrer(valueToAdd.getValue());
    }
}
