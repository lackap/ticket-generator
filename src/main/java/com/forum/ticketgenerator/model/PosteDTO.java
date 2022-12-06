package com.forum.ticketgenerator.model;

import com.forum.ticketgenerator.model.database.*;
import lombok.Data;

@Data
public class PosteDTO {
    private long id;
    private String intitule;
    private TypeContrat typeContrat;
    private NiveauDTO niveau;
    private EvenementDTO evenement;
    private FamilleMetierDTO familleMetier;

    @Override
    public boolean equals(Object o ) {
        if (o instanceof PosteDTO) {
            return this.getId() == ((PosteDTO) o).getId();
        }
        return false;
    }
}
