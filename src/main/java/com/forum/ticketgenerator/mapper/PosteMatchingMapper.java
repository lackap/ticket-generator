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
        if (CollectionUtils.isNotEmpty(entreprise.getSecteursActivite())) {
            posteMatching.setSecteurActivite(entreprise.getSecteursActivite().stream().findFirst().get().getName());
        }
        return posteMatching;
    }
}
