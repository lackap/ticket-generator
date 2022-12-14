package com.forum.ticketgenerator.view.admin;

import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.IParametrageService;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AjoutTypeContratView extends AAddParametrageView<TypeContrat> {

    @Override
    protected IParametrageService<TypeContrat> getParametrageService () {
        return modelServiceFactory.getTypeContratService();
    }
}
