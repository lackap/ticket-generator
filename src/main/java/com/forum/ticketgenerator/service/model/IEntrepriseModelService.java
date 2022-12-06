package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.exception.PosteCreationException;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;

import java.io.IOException;
import java.util.List;

public interface IEntrepriseModelService {
    void addPoste(String nomEntreprise, String intitule, FamilleMetier familleMetier, Niveau niveau, TypeContrat typeContrat, Evenement evenement) throws PosteCreationException;

    List<FamilleMetier> getFamilleMetierEntreprises() throws IOException;

    List<SecteurActivite> getSecteursActivitesEntreprises() throws IOException;

    List<PosteMatching> searchFromSecteurActivite(SecteurActivite secteur, Evenement evenement) throws IOException;

    List<PosteMatching> searchFromEntrepriseName(String entrepriseName);

    List<PosteMatching> searchFromEntrepriseNameAndEvenement(String entrepriseName, Evenement evenement);

    List<EntrepriseDTO> searchAllEntreprise(Evenement evenement);

}
