package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.exception.DiplomeCreationException;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Formation;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IFormationModelService {
    void addDiplome (String nomCentre, String intituleDiplome, FamilleMetier familleMetier, Evenement evenement) throws DiplomeCreationException;

    List<Diplome> getAllDiplomes () throws IOException;

    List<Diplome> getDiplomes (String centreFormation) throws IOException;

    List<Formation> getCentresFormation () throws IOException;

    public List<Diplome> searchFromFormationNameAndEvenement (String formationName, Evenement evenement);

}
