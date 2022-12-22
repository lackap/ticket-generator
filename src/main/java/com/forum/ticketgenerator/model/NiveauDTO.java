package com.forum.ticketgenerator.model;

import lombok.Data;

@Data
public class NiveauDTO {
    private long id;
    private String intitule;

    @Override
    public boolean equals(Object o ) {
        if (o instanceof FamilleMetierDTO) {
            return this.getId() == ((FamilleMetierDTO) o).getId();
        }
        return false;
    }
}
