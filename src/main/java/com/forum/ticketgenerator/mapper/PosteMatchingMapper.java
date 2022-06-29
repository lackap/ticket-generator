package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.Entreprise;
import com.forum.ticketgenerator.model.Poste;
import com.forum.ticketgenerator.model.PosteMatching;

public class PosteMatchingMapper {

    public static PosteMatching map(Entreprise entreprise, Poste poste) {
        PosteMatching posteMatching = new PosteMatching();
        posteMatching.setContrat(poste.getContrat());
        posteMatching.setIntitule(poste.getIntitule());
        posteMatching.setNom(entreprise.getNom());
        posteMatching.setNiveau(poste.getNiveau());
        posteMatching.setStand(entreprise.getStand());
        return posteMatching;
    }
}
