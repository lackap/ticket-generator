package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AjoutFamilleMetierView extends AAddParametrageView<FamilleMetier> {

    @Override
    protected IParametrageService<FamilleMetier> getParametrageService () {
        return modelServiceFactory.getFamilleMetierService();
    }
}
