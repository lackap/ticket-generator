package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.exception.DiplomeCreationException;
import com.forum.ticketgenerator.exception.PosteCreationException;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.FormationRepository;
import com.forum.ticketgenerator.service.model.IFormationModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationModelService implements IFormationModelService {

    @Autowired
    private FormationRepository formationRepository;

    @Override
    @Transactional
    public void addDiplome(String nomCentre, String intituleDiplome, FamilleMetier familleMetier) throws DiplomeCreationException {
        if (intituleDiplome == null) {
            throw new DiplomeCreationException("L'intitule de poste doit être renseigné.");
        }
        if (familleMetier == null) {
            throw new DiplomeCreationException("La famille métier doit être renseigné.");
        }
        Diplome diplome = new Diplome();
        diplome.setIntituleDiplome(intituleDiplome);
        diplome.setFamilleMetier(familleMetier);

        Formation formation = formationRepository.findByNomCentre(nomCentre);
        formation.getDiplomes().add(diplome);
        formationRepository.save(formation);

    }

    @Override
    @Transactional
    public Formation searchFromFormationName(String formationName) {
        return formationRepository.findByNomCentre(formationName);
    }

    @Override
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

    @Override
    @Transactional
    public List<String> getDiplomesLabels(String centreFormation) throws IOException {
        Formation formation = formationRepository.findByNomCentre(centreFormation);
        List<String> diplomesLabels = new ArrayList<>();
        diplomesLabels.addAll(formation.getDiplomes().stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList()));
        return diplomesLabels.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
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
}
