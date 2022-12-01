package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelFactory {

    @Value( "${model.loading.type}" )
    private String modelType;

    @Autowired
    private ModelService modelService;

    @Autowired
    private DatabaseModelService databaseModelService;

    public IModelService getService() {
        switch (modelType) {
            case "csv":
                return modelService;
            default :
                return databaseModelService;
        }

    }
}
