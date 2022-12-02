package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Formation;

public class FormationMapper {

    public static Formation map(String[] csvDatas) {
        Formation formation = new Formation();
        formation.setNomCentre(csvDatas[1]);
        Diplome diplome;
        diplome = new Diplome();
        diplome.setIntituleDiplome(csvDatas[2]);
        FamilleMetier familleMetier = new FamilleMetier();
        familleMetier.setIntitule(csvDatas[5]);
        diplome.setFamilleMetier(familleMetier);
        formation.getDiplomes().add(diplome);
        return formation;
    }
}
