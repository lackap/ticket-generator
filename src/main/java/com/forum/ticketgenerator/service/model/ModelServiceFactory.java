package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelServiceFactory {

    @Value( "${model.loading.type}" )
    private String modelType;

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

    @Autowired
    private SecteurActiviteModelService secteurActiviteModelService;

    public IModelService getService() {
        switch (modelType) {
            default :
                return null;
        }

    }

    public IEntrepriseModelService getEntrepriseService() {
        switch (modelType) {
            default :
                return entrepriseModelService;
        }

    }

    public IFormationModelService getFormationService() {
        switch (modelType) {
            default :
                return formationModelService;
        }

    }

    public IParametrageService<FamilleMetier> getFamilleMetierService() {
        switch (modelType) {
            default :
                return familleMetierModelService;
        }

    }

    public IParametrageService<TypeContrat> getTypeContratService() {
        switch (modelType) {
            default :
                return typeContratModelService;
        }

    }

    public IParametrageService<Niveau> getNiveauService() {
        switch (modelType) {
            default :
                return niveauModelService;
        }
    }

    public IEvenementModelService getEvenementService() {
        switch (modelType) {
            default :
                return evenementModelService;
        }

    }

    public IPosteModelService getPosteService() {
        switch (modelType) {
            default :
                return posteModelService;
        }

    }

    public IParametrageService<SecteurActivite> getSecteurService() {
        switch (modelType) {
            default :
                return secteurActiviteModelService;
        }

    }
}
