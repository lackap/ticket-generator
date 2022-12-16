package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;

import java.util.Optional;

public class EntrepriseDTOMapper {
    public static EntrepriseDTO map(Entreprise entreprise, Evenement evenement) {
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(entrepriseDTO.getId());
        entrepriseDTO.setStand(entreprise.getStand());
        entrepriseDTO.setNom(entreprise.getNom());
        if (evenement != null) {
            Optional<SecteurActivite> secteur = entreprise.getSecteursActivite().stream()
                    .filter(secteurActivite -> secteurActivite.getEvenement().equals(evenement))
                    .findFirst();
            if (secteur.isPresent()) {
                entrepriseDTO.setSecteurActivite(secteur.get());
            }
        }
        return entrepriseDTO;
    }
}
