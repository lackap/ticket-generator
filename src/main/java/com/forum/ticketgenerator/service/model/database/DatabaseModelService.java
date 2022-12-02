package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.*;
import com.forum.ticketgenerator.service.model.IModelService;
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

    @Transactional
    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) throws IOException {

        List<FamilleMetier> famillesMetier = getFamillesMetierFromFormation(nomCentre, intituleFormation);
        List<PosteMatching> postesMatching = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            List<Entreprise> entreprises = entrepriseRepository.findByPostesFamilleMetierIn(famillesMetier);
            entreprises.forEach(
                    entreprise -> {
                        for (Poste poste : entreprise.getPostes()) {
                            if (famillesMetier.contains(poste.getFamilleMetier())) {
                                postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                            }
                        }
                    });
        }
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    private List<FamilleMetier> getFamillesMetierFromFormation(String nomCentre, String intituleFormation) throws IOException {
        List<FamilleMetier> famillesMetier = new ArrayList<>();
        if (ApplicationConstants.AUCUN_DIPLOME.equals(nomCentre)) {
            Iterable<Formation> formations = formationRepository.findByDiplomesIntituleDiplome(intituleFormation);
            formations.forEach(
                    formation -> {
                        famillesMetier.addAll(formation.getDiplomes().stream()
                                .map(di -> di.getFamilleMetier())
                                .collect(Collectors.toList()));
                    });
        } else {
            Formation formation = formationRepository.findByNomCentre(nomCentre);
            List<FamilleMetier> metiers = formation.getDiplomes().stream()
                    .map(diplome -> diplome.getFamilleMetier())
                    .collect(Collectors.toList());
            famillesMetier.addAll(metiers);
        }
        return famillesMetier.stream().distinct().collect(Collectors.toList());
    }

}