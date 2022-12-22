package com.forum.ticketgenerator.model;

import lombok.Data;

@Data
public class TypeContratDTO {
    private long id;
    private String intitule;

    @Override
    public boolean equals(Object o ) {
        if (o instanceof TypeContratDTO) {
            return this.getId() == ((TypeContratDTO) o).getId();
        }
        return false;
    }
}
