package com.forum.ticketgenerator.mapper;

import com.forum.ticketgenerator.model.Entreprise;
import com.forum.ticketgenerator.model.Poste;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseMapper.class);

    public static Entreprise map(String[] csvDatas) {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(csvDatas[1]);
        List<String> secteursActivite = new ArrayList<>();
        secteursActivite.add(csvDatas[0]);
        entreprise.setSecteursActivite(secteursActivite);
        Poste poste = new Poste();
        poste.setIntitule(csvDatas[3]);
        poste.setFamilleMetier(csvDatas[4]);
        poste.setContrat(csvDatas[5]);
        poste.setNiveau(csvDatas[6]);
        entreprise.getPostes().add(poste);
        return entreprise;
    }
}
