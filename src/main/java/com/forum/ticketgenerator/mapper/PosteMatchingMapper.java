package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Poste;
import com.forum.ticketgenerator.model.PosteMatching;
import org.apache.commons.collections.CollectionUtils;

public class PosteMatchingMapper {

    public static PosteMatching map(Entreprise entreprise, Poste poste) {
        PosteMatching posteMatching = new PosteMatching();
        posteMatching.setIntitule(poste.getIntitule());
        posteMatching.setNom(entreprise.getNom());
        posteMatching.setStand(entreprise.getStand());
        if (poste.getSecteurActivite() != null) {
            posteMatching.setSecteurActivite(poste.getSecteurActivite().getIntitule());
            posteMatching.setSecteurActiviteColor(poste.getSecteurActivite().getCouleur());
        }
        posteMatching.setNiveau(poste.getNiveau().getIntitule());
        posteMatching.setTypeContrat(poste.getTypeContrat().getIntitule());
        posteMatching.setFamilleMetier(poste.getFamilleMetier().getIntitule());
        return posteMatching;
    }
}
