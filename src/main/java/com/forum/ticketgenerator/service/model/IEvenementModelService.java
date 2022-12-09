package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Niveau;

import java.util.List;

public interface IEvenementModelService {

    List<Evenement> searchAllEvenement();
    Evenement enregistrer(String evenement, String labelSecteurActivite);
    void supprimer(Evenement evenement);
}
