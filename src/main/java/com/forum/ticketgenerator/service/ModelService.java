package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.mapper.EntrepriseMapper;
import com.forum.ticketgenerator.mapper.FormationMapper;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.*;
import com.forum.ticketgenerator.utils.EncodingUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.collections.CollectionUtils;
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


    private Map<String, Entreprise> loadEntreprises (Reader reader) throws IOException {
        Map<String, Entreprise> entreprises = new HashMap<>();
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        for (String[] posteValues : csvReader.readAll()) {
            Entreprise entreprise = EntrepriseMapper.map(posteValues);
        }
        return entreprises;
    }

    public List<String> getAllDiplomesLabels() throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getFormationsFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getFormationsFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> diplomesLabels = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
        }
        csvReader.close();
        return diplomesLabels.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getDiplomesLabels(String centreFormation) throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getFormationsFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getFormationsFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> diplomesLabels = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            if (centreFormation.equals(formation.getNomCentre())) {
                diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
            }
        }
        csvReader.close();
        return diplomesLabels.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getCentreFormationLabels() throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getFormationsFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getFormationsFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> centreFormationLabels = new ArrayList<>();
        centreFormationLabels.add(ApplicationConstants.AUCUN_DIPLOME);
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            centreFormationLabels.add(formation.getNomCentre());
        }
        csvReader.close();
        return centreFormationLabels.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getFamilleMetierEntreprises() throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getEntreprisesFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getEntreprisesFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> famillesMetier = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            famillesMetier.addAll(entreprise.getPostes().stream().map(Poste::getFamilleMetier).collect(Collectors.toList()));
        }
        csvReader.close();
        return famillesMetier.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public List<String> getSecteursActivitesEntreprises() throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getEntreprisesFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getEntreprisesFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> secteurs = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            secteurs.addAll(entreprise.getSecteursActivite());
        }
        csvReader.close();
        return secteurs.stream().distinct().collect(Collectors.toList());

    }

    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) throws IOException {

        List<String> famillesMetier = getFamillesMetierFromFormation(nomCentre, intituleFormation);
        List<PosteMatching> postesMatching = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getEntreprisesFile());
            Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getEntreprisesFile()), charset);
            CSVReader csvReader =
                    new CSVReaderBuilder(reader).
                            withSkipLines(1).withCSVParser(new CSVParser(';')).
                            build();
            String[] csvDatas = null;
            while ((csvDatas = csvReader.readNext()) != null) {
                Entreprise entreprise = EntrepriseMapper.map(csvDatas);
                for (Poste poste : entreprise.getPostes()) {
                    if (famillesMetier.contains(poste.getFamilleMetier())) {
                        postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                    }
                }
            }
            csvReader.close();
        }
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    private List<String> getFamillesMetierFromFormation(String nomCentre, String intituleFormation) throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getFormationsFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getFormationsFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<String> famillesMetier = new ArrayList<>();
        if (ApplicationConstants.AUCUN_DIPLOME.equals(nomCentre)) {
            while ((csvDatas = csvReader.readNext()) != null) {
                Formation formation = FormationMapper.map(csvDatas);
                famillesMetier.addAll(formation.getDiplomes().stream()
                        .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                        .map(di -> di.getMetiersDebouche().stream()
                                .map(MetierDebouche::getFamilleMetier)
                                .collect(Collectors.toList()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
            }
        } else {
            while ((csvDatas = csvReader.readNext()) != null) {
                Formation formation = FormationMapper.map(csvDatas);
                if (nomCentre.equals(formation.getNomCentre())) {
                    Optional<List<String>> metiers = formation.getDiplomes().stream()
                            .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                            .findFirst()
                            .map(diplome -> diplome.getMetiersDebouche().stream().map(MetierDebouche::getFamilleMetier).collect(Collectors.toList()));
                    if (metiers.isPresent()) {
                        famillesMetier.addAll(metiers.get());
                    }
                 }
            }
        }
        csvReader.close();
        return famillesMetier.stream().distinct().collect(Collectors.toList());
    }

    public List<PosteMatching> searchFromFamilleMetier(String familleMetier) throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getEntreprisesFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getEntreprisesFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<PosteMatching> postesMatching = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            for (Poste poste : entreprise.getPostes()) {
                if (poste.getFamilleMetier().contains(familleMetier)) {
                    postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                }
            }
        }
        Model.getInstance().setPostesMatching(postesMatching);
        csvReader.close();
        return postesMatching;
    }


    public List<PosteMatching> searchFromSecteurActivite(String secteur) throws IOException {
        String charset = EncodingUtils.getEncodingToUse(Model.getInstance().getEntreprisesFile());
        Reader reader = new InputStreamReader(new FileInputStream(Model.getInstance().getEntreprisesFile()), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        String[] csvDatas = null;
        List<PosteMatching> postesMatching = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            if (entreprise.getSecteursActivite().contains(secteur)) {
                for (Poste poste : entreprise.getPostes()) {
                    postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                }
            }
        }
        Model.getInstance().setPostesMatching(postesMatching);
        csvReader.close();
        return postesMatching;
    }
}