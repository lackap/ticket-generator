package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Poste;
import com.forum.ticketgenerator.model.database.SecteurActivite;

import java.util.Optional;

public class PosteMatchingMapper {

    public static PosteMatching map(Entreprise entreprise, Poste poste) {
        PosteMatching posteMatching = new PosteMatching();
        posteMatching.setIntitule(poste.getIntitule());
        posteMatching.setNom(entreprise.getNom());
        posteMatching.setStand(entreprise.getStand());
        if (entreprise.getSecteursActivite() != null) {
            Optional<SecteurActivite> secteurActivite = entreprise.getSecteursActivite().stream()
                            .filter(secteur -> secteur.getEvenement().equals(poste.getEvenement()))
                                    .findFirst();
            if (secteurActivite.isPresent()) {
                posteMatching.setSecteurActivite(secteurActivite.get().getIntitule());
                posteMatching.setSecteurActiviteColor(secteurActivite.get().getCouleur());
            }
        }
        if (poste.getNiveau() != null) {
            posteMatching.setNiveau(poste.getNiveau().getIntitule());
        }
        if (poste.getTypeContrat() != null) {
            posteMatching.setTypeContrat(poste.getTypeContrat().getIntitule());
        }
        if (poste.getFamilleMetier() != null) {
            posteMatching.setFamilleMetier(poste.getFamilleMetier().getIntitule());
        }
        return posteMatching;
    }
}
