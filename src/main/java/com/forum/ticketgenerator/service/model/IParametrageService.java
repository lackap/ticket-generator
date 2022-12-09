package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;

import java.util.List;

public interface IParametrageService<T> {
    List<T> searchParEvenement(Evenement evenement);
    T enregistrer(String familleMetier, Evenement evenement);
    void delete(T item);
}