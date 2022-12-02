package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.service.model.csv.CsvModelService;
import com.forum.ticketgenerator.service.model.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelServiceFactory {

    @Value( "${model.loading.type}" )
    private String modelType;

    @Autowired
    private CsvModelService modelService;

    @Autowired
    private DatabaseModelService databaseModelService;

    @Autowired
    private EntrepriseModelService entrepriseModelService;

    @Autowired
    private FormationModelService formationModelService;

    @Autowired
    private FamilleMetierModelService familleMetierModelService;

    @Autowired
    private NiveauModelService niveauModelService;

    @Autowired
    private TypeContratModelService typeContratModelService;

    public IModelService getService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return databaseModelService;
        }

    }

    public IEntrepriseModelService getEntrepriseService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return entrepriseModelService;
        }

    }

    public IFormationModelService getFormationService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return formationModelService;
        }

    }

    public IFamilleMetierModelService getFamilleMetierService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return familleMetierModelService;
        }

    }

    public ITypeContratModelService getTypeContratService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return typeContratModelService;
        }

    }

    public INiveauModelService getNiveauService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return niveauModelService;
        }

    }
}
