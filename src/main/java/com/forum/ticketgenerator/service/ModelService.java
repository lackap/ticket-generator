package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.model.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);

    public Map<String, Entreprise> loadEntreprises (Reader reader) throws IOException {
        Map<String, Entreprise> entreprises = new HashMap<>();
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')). // Skiping firstline as it is header
                        build();
        for (String[] posteValues : csvReader.readAll()) {
            String nomSecteursActivite = posteValues[0];
            String nomEntreprise = posteValues[1];
            String stand = posteValues[2];
            Entreprise entreprise = entreprises.get(nomEntreprise);
            if (entreprise == null) {
                entreprise = new Entreprise();
                entreprise.setNom(nomEntreprise);
                try {
                    entreprise.setStand(Integer.valueOf(stand));
                } catch (NumberFormatException e) {
                    LOGGER.info("Stand n'a pas de valeur valide pour l'entreprise " + nomEntreprise );
                }
                List<String> secteursActivite = new ArrayList<>();
                secteursActivite.add(nomSecteursActivite);
                entreprise.setSecteursActivite(secteursActivite);
                entreprises.put(nomEntreprise, entreprise);
            }
            Poste poste = new Poste();
            poste.setIntitule(posteValues[3]);
            poste.setFamilleMetier(posteValues[4]);
            poste.setContrat(posteValues[5]);
            poste.setNiveau(posteValues[6]);
            entreprise.getPostes().add(poste);
        }
        return entreprises;
    }

    public Map<String, Entreprise> loadEntreprises (String entrepriseFile) throws IOException {
        return loadEntreprises(new InputStreamReader(new FileInputStream(entrepriseFile)));
    }

    public Map<String, Formation> loadFormations (String formationFile) throws IOException {
        return loadFormations(new InputStreamReader(new FileInputStream(formationFile)));
    }

    public Map<String, Formation> loadFormations (Reader reader ) throws IOException {
        Map<String, Formation> formations = new HashMap<>();
        CSVReader csvReader = new CSVReaderBuilder(reader).
                withSkipLines(1).withCSVParser(new CSVParser(';')). // Skiping firstline as it is header
                        build();
        Diplome diplomePrecedent = null;
        formations.put(ApplicationConstants.AUCUN_DIPLOME, new Formation(ApplicationConstants.AUCUN_DIPLOME));
        for (String[] diplomeValues : csvReader.readAll()) {
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

    public List<String> getAllDiplomes() {
        return Model.getInstance().getFormations().values().stream().
                map(formation -> formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
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
                .distinct()
                .sorted((p1, p2) -> p1.compareTo(p2)).collect(Collectors.toList());
    }

    public List<String> getSecteursActivitesEntreprises() {
        return Model.getInstance().getEntreprises()
                .values()
                .stream()
                .map(entreprise -> entreprise.getSecteursActivite())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

    }
}