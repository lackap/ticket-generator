package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.service.model.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelServiceFactory {

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

    public IEntrepriseModelService getEntrepriseService() {
        return entrepriseModelService;
    }

    public IFormationModelService getFormationService() {
        return formationModelService;
    }

    public IParametrageService<FamilleMetier> getFamilleMetierService() {
        return familleMetierModelService;
    }

    public IParametrageService<TypeContrat> getTypeContratService() {
        return typeContratModelService;
    }

    public IParametrageService<Niveau> getNiveauService() {
        return niveauModelService;
    }

    public IEvenementModelService getEvenementService() {
        return evenementModelService;
    }

    public IPosteModelService getPosteService() {
        return posteModelService;
    }

    public IParametrageService<SecteurActivite> getSecteurService() {
        return secteurActiviteModelService;
    }
}
