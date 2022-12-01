package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatabaseModelService implements IModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseModelService.class);

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private FamilleMetierRepository familleMetierRepository;

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Autowired
    private NiveauRepository niveauRepository;

    @Transactional
    public List<String> getAllDiplomesLabels() throws IOException {
        Iterable<Formation> formations = formationRepository.findAll();
        List<String> diplomesLabels = new ArrayList<>();
        formations.forEach(
                formation -> {
                    diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
                }
        );
        return diplomesLabels.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Transactional
    public List<String> getDiplomesLabels(String centreFormation) throws IOException {
        Iterable<Formation> formations = formationRepository.findByNomCentre(centreFormation);
        List<String> diplomesLabels = new ArrayList<>();
        formations.forEach(
                formation -> {
                diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
        });
        return diplomesLabels.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Transactional
    public List<String> getCentreFormationLabels() throws IOException {
        Iterable<Formation> formations = formationRepository.findAll();
        List<String> centreFormationLabels = new ArrayList<>();
        formations.forEach(
                formation -> {
                    centreFormationLabels.add(formation.getNomCentre());
        } );
        List<String> sortedList = new ArrayList<>();
        sortedList.add(ApplicationConstants.AUCUN_DIPLOME);
        sortedList.addAll(centreFormationLabels.stream().sorted(Comparator.naturalOrder()).distinct().collect(Collectors.toList()));
        return sortedList;
    }

    @Transactional
    public List<String> getFamilleMetierEntreprises() throws IOException {
        Iterable<Entreprise> entreprises = entrepriseRepository.findAll();
        List<String> famillesMetier = new ArrayList<>();
        entreprises.forEach(
                entreprise -> {
                    famillesMetier.addAll(entreprise.getPostes().stream().map(poste -> poste.getFamilleMetier().getIntitule()).collect(Collectors.toList()));
        });
        return famillesMetier.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Transactional
    public List<String> getSecteursActivitesEntreprises() throws IOException {
        Iterable<Entreprise> entreprises = entrepriseRepository.findAll();
        List<String> secteurs = new ArrayList<>();
        entreprises.forEach(
                entreprise -> {
                    secteurs.addAll(entreprise.getSecteursActivite().stream().map(SecteurActivite::getName).collect(Collectors.toList()));
                }
        );
        return secteurs.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

    }

    @Transactional
    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) throws IOException {

        List<String> famillesMetier = getFamillesMetierFromFormation(nomCentre, intituleFormation);
        List<PosteMatching> postesMatching = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            List<Entreprise> entreprises = entrepriseRepository.findByPostesFamilleMetierIn(famillesMetier);
            entreprises.forEach(
                    entreprise -> {
                        for (Poste poste : entreprise.getPostes()) {
                            if (famillesMetier.contains(StringUtils.deleteWhitespace(poste.getFamilleMetier().getIntitule()))) {
                                postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                            }
                        }
                    });
        }
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    private List<String> getFamillesMetierFromFormation(String nomCentre, String intituleFormation) throws IOException {
        List<String> famillesMetier = new ArrayList<>();
        if (ApplicationConstants.AUCUN_DIPLOME.equals(nomCentre)) {
            Iterable<Formation> formations = formationRepository.findByDiplomesIntituleDiplome(intituleFormation);
            formations.forEach(
                    formation -> {
                    famillesMetier.addAll(formation.getDiplomes().stream()
                            .map(di -> di.getMetiersDebouche().stream()
                                    .map(MetierDebouche::getFamilleMetier)
                                    .collect(Collectors.toList()))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()));
            });
        } else {
            Iterable<Formation> formations = formationRepository.findByNomCentre(nomCentre);
            formations.forEach(
                    formation -> {
                    List<String> metiers = formation.getDiplomes().stream()
                            .map(diplome -> diplome.getMetiersDebouche().stream().map(MetierDebouche::getFamilleMetier).collect(Collectors.toList()))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                    famillesMetier.addAll(metiers);
                 });
        }
        return famillesMetier.stream().distinct().map(StringUtils::deleteWhitespace).collect(Collectors.toList());
    }

    @Transactional
    public List<PosteMatching> searchFromFamilleMetier(String familleMetier) throws IOException {
        List<PosteMatching> postesMatching = new ArrayList<>();

        List<Entreprise> entreprises = entrepriseRepository.findByPostesFamilleMetier(familleMetier);
        entreprises.forEach(
                entreprise -> {
            for (Poste poste : entreprise.getPostes()) {
                postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
            }
        });
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }


    @Transactional
    public List<PosteMatching> searchFromSecteurActivite(String secteur) throws IOException {
        List<PosteMatching> postesMatching = new ArrayList<>();
        List<Entreprise> entreprises = entrepriseRepository.findBySecteursActiviteName(secteur);
        entreprises.forEach(
                entreprise -> {
                    for (Poste poste : entreprise.getPostes()) {
                        postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                    }
                });
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    @Transactional
    public List<PosteMatching> searchFromEntrepriseName(String entrepriseName) {
        List<PosteMatching> postesMatching = new ArrayList<>();

        Entreprise entreprise = entrepriseRepository.findByNom(entrepriseName);
        for (Poste poste : entreprise.getPostes()) {
            postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
        }
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    @Transactional
    public List<FamilleMetier> searchAllFamilleMetier() {
        Iterable<FamilleMetier> famillesMetier = familleMetierRepository.findAll();
        List<FamilleMetier> famillesMetierList = new ArrayList<>();
        famillesMetier.forEach(familleMetier -> famillesMetierList.add(familleMetier));
        return famillesMetierList;
    }

    @Transactional
    public List<Niveau> searchAllNiveau() {
        Iterable<Niveau> niveaux = niveauRepository.findAll();
        List<Niveau> niveauList = new ArrayList<>();
        niveaux.forEach(niveau -> niveauList.add(niveau));
        return niveauList;
    }

    @Transactional
    public List<TypeContrat> searchAllFamilleTypeContrat() {
        Iterable<TypeContrat> typesContrat = typeContratRepository.findAll();
        List<TypeContrat> typesContratList = new ArrayList<>();
        typesContrat.forEach(typeContrat -> typesContratList.add(typeContrat));
        return typesContratList;
    }

}