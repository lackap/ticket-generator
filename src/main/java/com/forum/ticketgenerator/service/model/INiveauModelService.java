package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.Niveau;

import java.util.List;

public interface INiveauModelService {

    List<Niveau> searchAllNiveau();
    void enregistrer(String niveau);
}
