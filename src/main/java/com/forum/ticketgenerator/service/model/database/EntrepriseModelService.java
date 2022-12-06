package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.exception.PosteCreationException;
import com.forum.ticketgenerator.mapper.EntrepriseDTOMapper;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.EntrepriseDTO;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.EntrepriseRepository;
import com.forum.ticketgenerator.service.model.IEntrepriseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseModelService implements IEntrepriseModelService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional
    public void addPoste(String nomEntreprise, String intitule, FamilleMetier familleMetier, Niveau niveau,
                         TypeContrat typeContrat, Evenement evenement) throws PosteCreationException {

        if (intitule == null) {
            throw new PosteCreationException("L'intitule de poste doit être renseigné.");
        }
        if (familleMetier == null) {
            throw new PosteCreationException("La famille métier doit être renseigné.");
        }
        if (evenement == null) {
            throw new PosteCreationException("L'évènement doit être renseigné.");
        }
        Poste poste = new Poste();
        poste.setIntitule(intitule);
        poste.setFamilleMetier(familleMetier);
        poste.setNiveau(niveau);
        poste.setTypeContrat(typeContrat);
        poste.setEvenement(evenement);

        Entreprise entreprise = entrepriseRepository.findByNom(nomEntreprise);
        poste.setEntreprise(entreprise);
        entreprise.getPostes().add(poste);
        entrepriseRepository.save(entreprise);

    }

    @Override
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

    @Override
    @Transactional
    public List<PosteMatching> searchFromEntrepriseNameAndEvenement (String entrepriseName, Evenement evenement) {
        List<PosteMatching> postesMatching = new ArrayList<>();

        Entreprise entreprise = entrepriseRepository.findByNomAndPostesEvenement(entrepriseName, evenement);
        if (entreprise != null) {
            for (Poste poste : entreprise.getPostes()) {
                if (evenement.equals(poste.getEvenement())) {
                    postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                }
            }
            Model.getInstance().setPostesMatching(postesMatching);
        }
        return postesMatching;
    }


    @Override
    @Transactional
    public List<FamilleMetier> getFamilleMetierEntreprises() {
        Iterable<Entreprise> entreprises = entrepriseRepository.findAll();
        List<FamilleMetier> famillesMetier = new ArrayList<>();
        entreprises.forEach(
                entreprise -> famillesMetier.addAll(entreprise.getPostes().stream().map(Poste::getFamilleMetier).collect(Collectors.toList())));
        return famillesMetier.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SecteurActivite> getSecteursActivitesEntreprises() {
        Iterable<Entreprise> entreprises = entrepriseRepository.findAll();
        List<SecteurActivite> secteurs = new ArrayList<>();
        entreprises.forEach(
                entreprise -> secteurs.addAll(entreprise.getSecteursActivite())
        );
        return secteurs.stream().distinct().collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<PosteMatching> searchFromSecteurActivite(SecteurActivite secteur, Evenement evenement) {
        List<PosteMatching> postesMatching = new ArrayList<>();
        List<Entreprise> entreprises = entrepriseRepository.findBySecteursActiviteNameAndPostesEvenement(secteur, evenement);
        entreprises.forEach(
                entreprise -> {
                    for (Poste poste : entreprise.getPostes()) {
                        if (poste.getEvenement().equals(evenement)) {
                            postesMatching.add(PosteMatchingMapper.map(entreprise, poste));
                        }
                    }
                });
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    @Override
    @Transactional
    public List<EntrepriseDTO> searchAllEntreprise(Evenement evenement) {
        List<Entreprise> entreprises = entrepriseRepository.findByPostesEvenement(evenement);
        return entreprises.stream().map(EntrepriseDTOMapper::map).collect(Collectors.toList());
    }
}
