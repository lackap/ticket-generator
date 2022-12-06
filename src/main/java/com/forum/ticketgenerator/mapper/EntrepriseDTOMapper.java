package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.database.Entreprise;

public class EntrepriseDTOMapper {
    public static EntrepriseDTO map(Entreprise entreprise) {
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(entrepriseDTO.getId());
        entrepriseDTO.setStand(entreprise.getStand());
        entrepriseDTO.setNom(entreprise.getNom());
        return entrepriseDTO;
    }
}
