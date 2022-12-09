package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntrepriseMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseMapper.class);

    public static Entreprise map(String[] csvDatas) {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(csvDatas[1]);
        entreprise.setStand(csvDatas[2]);
        SecteurActivite secteurActivite = new SecteurActivite();
        secteurActivite.setIntitule(csvDatas[0]);
        entreprise.setSecteurActivite(secteurActivite);
        Poste poste = new Poste();
        poste.setIntitule(csvDatas[3]);
        FamilleMetier familleMetier = new FamilleMetier();
        familleMetier.setIntitule(csvDatas[4]);
        poste.setFamilleMetier(familleMetier);
        TypeContrat typeContrat = new TypeContrat();
        typeContrat.setIntitule(csvDatas[5]);
        poste.setTypeContrat(typeContrat);
        Niveau niveau = new Niveau();
        niveau.setIntitule(csvDatas[6]);
        poste.setNiveau(niveau);
        entreprise.getPostes().add(poste);
        return entreprise;
    }
}
