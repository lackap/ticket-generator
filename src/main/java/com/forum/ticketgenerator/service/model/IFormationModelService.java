package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.exception.DiplomeCreationException;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Formation;

import java.io.IOException;
import java.util.List;

public interface IFormationModelService {
    void addDiplome (String nomCentre, String intituleDiplome, FamilleMetier familleMetier, Evenement evenement) throws DiplomeCreationException;

    List<Diplome> getAllDiplomes () throws IOException;

    List<Diplome> getDiplomes (String centreFormation) throws IOException;

    List<Formation> getCentresFormation (Evenement evenement) throws IOException;

    List<Diplome> searchFromFormationNameAndEvenement (String formationName, Evenement evenement);

    void supprimerFormation(Formation formation);

    public List<Formation> getCentresFormation ();

}
