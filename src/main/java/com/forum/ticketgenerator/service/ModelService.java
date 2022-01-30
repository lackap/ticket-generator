package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ModelService {

    public static Map<String, Entreprise> loadEntreprises (String entrepriseFile) throws IOException {
        Map<String, Entreprise> entreprises = new HashMap<>();
        CSVReader reader =
                new CSVReaderBuilder(new FileReader(entrepriseFile)).
                        withSkipLines(1).withCSVParser(new CSVParser(';')). // Skiping firstline as it is header
                        build();
        for (String[] posteValues : reader.readAll()) {
            String nomSecteursActivite = posteValues[0];
            String nomEntreprise = posteValues[1];
            Entreprise entreprise = entreprises.get(nomEntreprise);
            if (entreprise == null) {
                entreprise = new Entreprise();
                entreprise.setNom(nomEntreprise);
                List<String> secteursActivite = new ArrayList<>();
                secteursActivite.add(nomSecteursActivite);
                entreprise.setSecteursActivite(secteursActivite);
                entreprises.put(nomEntreprise, entreprise);
            }
            Poste poste = new Poste();
            poste.setIntitule(posteValues[2]);
            poste.setFamilleMetier(posteValues[3]);
            poste.setContrat(posteValues[4]);
            poste.setNiveau(posteValues[5]);
            entreprise.getPostes().add(poste);
        }
        return entreprises;
    }

    public static Map<String, Formation> loadFormations (String formationFile) throws IOException {
        Map<String, Formation> formations = new HashMap<>();
        CSVReader reader = new CSVReaderBuilder(new FileReader(formationFile)).
                withSkipLines(1).withCSVParser(new CSVParser(';')). // Skiping firstline as it is header
                        build();
        Diplome diplomePrecedent = null;
        for (String[] diplomeValues : reader.readAll()) {
            String nomCentre = diplomeValues[1];
            Diplome diplome = null;
            if (StringUtils.isEmpty(nomCentre)) {
                diplome = diplomePrecedent;
            } else {
                Formation formation = formations.get(nomCentre);
                if (formation == null) {
                    formation = new Formation();
                    formation.setNomCentre(nomCentre);
                    formations.put(nomCentre, formation);
                }
                diplome = new Diplome();
                diplome.setIntituleDiplome(diplomeValues[2]);
                diplome.setNiveau(diplomeValues[3]);
                formation.getDiplomes().add(diplome);
            }
            MetierDebouche metierDebouche = new MetierDebouche();
            metierDebouche.setIntituleMetier(diplomeValues[4]);
            metierDebouche.setFamilleMetier(diplomeValues[5]);
            diplome.getMetiersDebouche().add(metierDebouche);
            diplomePrecedent = diplome;
        }
        return formations;

    }

    public Map<String,Formation> getAllFormations() {
        return Model.getInstance().getFormations();
    }

    public List<String> getCentreFormationLabels() {
        return getAllFormations().values().stream().map(formation -> formation.getNomCentre()).collect(Collectors.toList());
    }

    public List<String> getFamilleMetierEntreprises() {
        return Model.getInstance().getEntreprises()
                .values()
                .stream()
                .map(entreprise -> entreprise.getPostes().stream().map(poste -> poste.getFamilleMetier()).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}