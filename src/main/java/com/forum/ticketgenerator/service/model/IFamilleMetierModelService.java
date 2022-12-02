package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.FamilleMetier;

import java.util.List;

public interface IFamilleMetierModelService {

    public List<FamilleMetier> searchAllFamilleMetier();
    void enregistrer(String familleMetier);
}
