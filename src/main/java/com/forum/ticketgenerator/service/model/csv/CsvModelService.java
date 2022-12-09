package com.forum.ticketgenerator.service.model.csv;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.mapper.EntrepriseMapper;
import com.forum.ticketgenerator.mapper.FormationMapper;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.*;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.service.model.IModelService;
import com.forum.ticketgenerator.utils.EncodingUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvModelService {//implements IModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvModelService.class);

    public List<String> getAllDiplomesLabels() throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getFormationsFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> diplomesLabels = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
        }
        csvReader.close();
        return diplomesLabels.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public List<String> getDiplomesLabels(String centreFormation) throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getFormationsFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> diplomesLabels = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            if (centreFormation.equals(formation.getNomCentre())) {
                diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
            }
        }
        csvReader.close();
        return diplomesLabels.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public List<String> getCentreFormationLabels() throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getFormationsFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> centreFormationLabels = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Formation formation = FormationMapper.map(csvDatas);
            centreFormationLabels.add(formation.getNomCentre());
        }
        csvReader.close();
        List<String> sortedList = new ArrayList<>();
        sortedList.add(ApplicationConstants.AUCUN_DIPLOME);
        sortedList.addAll(centreFormationLabels.stream().sorted(Comparator.naturalOrder()).distinct().collect(Collectors.toList()));
        return sortedList;
    }

    public List<String> getFamilleMetierEntreprises() throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getEntreprisesFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> famillesMetier = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            famillesMetier.addAll(entreprise.getPostes().stream().map(poste -> poste.getFamilleMetier().getIntitule()).collect(Collectors.toList()));
        }
        csvReader.close();
        return famillesMetier.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public List<String> getSecteursActivitesEntreprises() throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getEntreprisesFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> secteurs = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            secteurs.add(entreprise.getSecteurActivite().getIntitule());
        }
        csvReader.close();
        return secteurs.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

    }

    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) throws IOException {

        List<String> famillesMetier = getFamillesMetierFromFormation(nomCentre, intituleFormation);
        List<PosteMatching> postesMatching = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            CSVReader csvReader =createCsvReader(Model.getInstance().getEntreprisesFile());
            if (csvReader == null) {
                return new ArrayList<>();
            }
            String[] csvDatas = null;
            while ((csvDatas = csvReader.readNext()) != null) {
                Entreprise entreprise = EntrepriseMapper.map(csvDatas);
                for (Poste poste : entreprise.getPostes()) {
                    if (famillesMetier.contains(StringUtils.deleteWhitespace(poste.getFamilleMetier().getIntitule()))) {
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
        CSVReader csvReader =createCsvReader(Model.getInstance().getFormationsFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<String> famillesMetier = new ArrayList<>();
        if (ApplicationConstants.AUCUN_DIPLOME.equals(nomCentre)) {
            while ((csvDatas = csvReader.readNext()) != null) {
                Formation formation = FormationMapper.map(csvDatas);
                famillesMetier.addAll(formation.getDiplomes().stream()
                        .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                        .map(di -> di.getFamilleMetier().getIntitule())
                        .collect(Collectors.toList()));
            }
        } else {
            while ((csvDatas = csvReader.readNext()) != null) {
                Formation formation = FormationMapper.map(csvDatas);
                if (nomCentre.equals(formation.getNomCentre())) {
                   Optional<String> metiers = formation.getDiplomes().stream()
                            .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                            .findFirst()
                            .map(diplome -> diplome.getFamilleMetier().getIntitule());
                    if (metiers.isPresent()) {
                        famillesMetier.add(metiers.get());
                    }
                 }
            }
        }
        csvReader.close();
        return famillesMetier.stream().distinct().map(StringUtils::deleteWhitespace).collect(Collectors.toList());
    }

    public List<PosteMatching> searchFromFamilleMetier(String familleMetier) throws IOException {
        CSVReader csvReader =createCsvReader(Model.getInstance().getEntreprisesFile());
        if (csvReader == null) {
            return new ArrayList<>();
        }
        String[] csvDatas = null;
        List<PosteMatching> postesMatching = new ArrayList<>();
        while ((csvDatas = csvReader.readNext()) != null) {
            Entreprise entreprise = EntrepriseMapper.map(csvDatas);
            for (Poste poste : entreprise.getPostes()) {
                if (poste.getFamilleMetier().getIntitule().equals(familleMetier)) {
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
            if (entreprise.getSecteurActivite().equals(secteur)) {
                for (Poste poste : entreprise.getPostes()) {
                    postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                }
            }
        }
        Model.getInstance().setPostesMatching(postesMatching);
        csvReader.close();
        return postesMatching;
    }

    private CSVReader createCsvReader(String file) throws IOException {
        if (file == null) {
            return null;
        }
        File fileToLoad = new File(file);
        if (!fileToLoad.exists()) {
            return null;
        }
        String charset = EncodingUtils.getEncodingToUse(file);
        Reader reader = new InputStreamReader(new FileInputStream(file), charset);
        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1).withCSVParser(new CSVParser(';')).
                        build();
        return csvReader;
    }
}