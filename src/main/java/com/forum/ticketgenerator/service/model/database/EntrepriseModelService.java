package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.EntrepriseRepository;
import com.forum.ticketgenerator.service.model.IEntrepriseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseModelService implements IEntrepriseModelService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional
    public void addPoste(String nomEntreprise, String intitule, FamilleMetier familleMetier, Niveau niveau, TypeContrat typeContrat) {
        Poste poste = new Poste();
        poste.setIntitule(intitule);
        poste.setFamilleMetier(familleMetier);
        poste.setNiveau(niveau);
        poste.setTypeContrat(typeContrat);

        Entreprise entreprise = entrepriseRepository.findByNom(nomEntreprise);
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
    public List<String> getFamilleMetierEntreprises() throws IOException {
        Iterable<Entreprise> entreprises = entrepriseRepository.findAll();
        List<String> famillesMetier = new ArrayList<>();
        entreprises.forEach(
                entreprise -> {
                    famillesMetier.addAll(entreprise.getPostes().stream().map(poste -> poste.getFamilleMetier().getIntitule()).collect(Collectors.toList()));
                });
        return famillesMetier.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
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

    @Override
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


    @Override
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
}
