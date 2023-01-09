package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.exception.DiplomeCreationException;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Formation;
import com.forum.ticketgenerator.repository.FormationRepository;
import com.forum.ticketgenerator.service.model.IFormationModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationModelService implements IFormationModelService {

    @Autowired
    private FormationRepository formationRepository;

    @Override
    @Transactional
    public void addDiplome(String nomCentre, String intituleDiplome, FamilleMetier familleMetier, Evenement evenement) throws DiplomeCreationException {
        if (intituleDiplome == null) {
            throw new DiplomeCreationException("L'intitule de poste doit être renseigné.");
        }
        if (familleMetier == null) {
            throw new DiplomeCreationException("La famille métier doit être renseigné.");
        }
        Diplome diplome = new Diplome();
        diplome.setIntituleDiplome(intituleDiplome);
        diplome.setFamilleMetier(familleMetier);
        diplome.setEvenement(evenement);

        Formation formation = formationRepository.findByNomCentre(nomCentre);
        formation.getDiplomes().add(diplome);
        formationRepository.save(formation);

    }

    @Override
    @Transactional
    public List<Diplome> searchFromFormationNameAndEvenement(String formationName, Evenement evenement) {
        List<Diplome> diplomes = new ArrayList<>();
        Formation formation = formationRepository.findByNomCentreAndDiplomesEvenement(formationName, evenement);
        if (formation != null) {
            for (Diplome diplome : formation.getDiplomes()) {
                if (evenement.equals(diplome.getEvenement())) {
                    diplomes.add(diplome);
                }
            }
        }
        return diplomes;
    }

    @Override
    @Transactional
    public void supprimerFormation (Formation formation) {
        formationRepository.delete(formation);
    }

    @Override
    @Transactional
    public List<Diplome> getAllDiplomes () throws IOException {
        Iterable<Formation> formations = formationRepository.findAll();
        List<Diplome> diplomesLabels = new ArrayList<>();
        formations.forEach(
                formation -> diplomesLabels.addAll(formation.getDiplomes())
        );
        return diplomesLabels.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Diplome> getDiplomes (String centreFormation) {
        Formation formation = formationRepository.findByNomCentre(centreFormation);
        List<Diplome> diplomesLabels = new ArrayList<>(formation.getDiplomes());
        return diplomesLabels.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Formation> getCentresFormation (Evenement evenement) throws IOException {
        Iterable<Formation> formations = formationRepository.findByDiplomesEvenement(evenement);
        List<Formation> centreFormationLabels = new ArrayList<>();
        formations.forEach(centreFormationLabels::add);
        List<Formation> sortedList = new ArrayList<>();
        Formation formation = new Formation();
        formation.setNomCentre(ApplicationConstants.AUCUN_DIPLOME);
        sortedList.add(formation);
        sortedList.addAll(centreFormationLabels.stream().distinct().collect(Collectors.toList()));
        return sortedList;
    }

    @Override
    @Transactional
    public List<Formation> getCentresFormation () {
        Iterable<Formation> formations = formationRepository.findAll();
        List<Formation> centreFormationLabels = new ArrayList<>();
        formations.forEach(centreFormationLabels::add);
        return centreFormationLabels;
    }
}
