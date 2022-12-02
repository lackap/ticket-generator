package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.TypeContrat;

import java.util.List;

public interface ITypeContratModelService {

    public List<TypeContrat> searchAllTypeContrat();
    void enregistrer(String typeContrat);
}
