package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.ColorAvailable;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.forum.ticketgenerator.service.model.database.SecteurActiviteModelService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class ParametrageSecteurActiviteView extends AParametrageView<SecteurActivite> {

    @Autowired
    private AjoutSecteurActiviteView ajoutSecteurActivite;

    @Autowired
    private SecurityService securityService;

    @Override
    protected String getTitle () {
        return "Paramétrage " + securityService.getAuthenticatedUser().getEvenement().getLabelSecteurActivité();
    }

    @Override
    protected Class<SecteurActivite> getGridType () {
        return SecteurActivite  .class;
    }

    @Override
    protected AAddParametrageView getParametrageView () {
        return ajoutSecteurActivite;
    }

    @Override
    protected IParametrageService<SecteurActivite> getParametrageService () {
        return modelServiceFactory.getSecteurService();
    }

    @Override
    protected void addCustomColumns() {
        grid.addColumn("couleur").setWidth("15%").setHeader("Couleur associée").setFlexGrow(0);
    }

}
