package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Niveau;

import java.util.List;

public interface IEvenementModelService {

    List<Evenement> searchAllEvenement();
    Evenement enregistrer(String evenement, String labelSecteurActivite, byte[] affiche) throws ModelCreationException;
    public Evenement mettreAJourParametres (Evenement evenement, String labelSecteurActivite, boolean displaySecteur,
                                            boolean displayNiveau, boolean displayContrat, byte[] affiche) throws ModelCreationException;
    void supprimer(Evenement evenement);
}
