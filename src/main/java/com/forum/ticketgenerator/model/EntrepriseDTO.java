package com.forum.ticketgenerator.model;

import com.forum.ticketgenerator.model.database.SecteurActivite;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class EntrepriseDTO {
    private long id;
    private String nom;
    private String stand;
    private SecteurActivite secteurActivite;
    private Set<PosteDTO> postes = new HashSet<>();

    @Override
    public boolean equals(Object o ) {
        if (o instanceof EntrepriseDTO) {
            return this.getId() == ((EntrepriseDTO) o).getId();
        }
        return false;
    }
}
