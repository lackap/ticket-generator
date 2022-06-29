package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.Diplome;
import com.forum.ticketgenerator.model.Formation;
import com.forum.ticketgenerator.model.MetierDebouche;

public class FormationMapper {

    public static Formation map(String[] csvDatas) {
        Formation formation = new Formation();
        formation.setNomCentre(csvDatas[1]);
        Diplome diplome;
        diplome = new Diplome();
        diplome.setIntituleDiplome(csvDatas[2]);
        diplome.setNiveau(csvDatas[3]);
        formation.getDiplomes().add(diplome);
        MetierDebouche metierDebouche = new MetierDebouche();
        metierDebouche.setIntituleMetier(csvDatas[4]);
        metierDebouche.setFamilleMetier(csvDatas[5]);
        diplome.getMetiersDebouche().add(metierDebouche);
        return formation;
    }
}
