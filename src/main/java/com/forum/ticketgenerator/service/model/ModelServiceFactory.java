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
    private EntrepriseModelService entrepriseModelService;

    @Autowired
    private FormationModelService formationModelService;

    @Autowired
    private FamilleMetierModelService familleMetierModelService;

    @Autowired
    private NiveauModelService niveauModelService;

    @Autowired
    private TypeContratModelService typeContratModelService;

    @Autowired
    private EvenementModelService evenementModelService;

    @Autowired
    private PosteModelService posteModelService;

    public IModelService getService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return null;
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

    public IEvenementModelService getEvenementService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return evenementModelService;
        }

    }

    public IPosteModelService getPosteService() {
        switch (modelType) {
            case "csv":
                return null;
            default :
                return posteModelService;
        }

    }
}
