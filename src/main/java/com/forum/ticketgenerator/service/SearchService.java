package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {

    public SearchService() {
    }

    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) {
        Formation formation = Model.getInstance().getFormations().get(nomCentre);
        List<String> famillesMetier = formation.getDiplomes().stream()
                .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                .findFirst()
                .map(diplome -> diplome.getMetiersDebouche().stream().map(metier -> metier.getFamilleMetier()).collect(Collectors.toList()))
                .get();
        List<PosteMatching> postesMatching = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            for (Entreprise entreprise : Model.getInstance().getEntreprises().values()) {
                for (Poste poste : entreprise.getPostes()) {
                    if (famillesMetier.contains(poste.getFamilleMetier())) {
                        postesMatching.add(createPosteMatching(entreprise, poste));
                    }
                }
            }
        }
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }

    public List<PosteMatching> searchFromFamilleMetier(String familleMetier) {
        List<PosteMatching> postesMatching = new ArrayList<>();
        for (Entreprise entreprise : Model.getInstance().getEntreprises().values()) {
            for (Poste poste : entreprise.getPostes()) {
                if (familleMetier.equals(poste.getFamilleMetier())) {
                    postesMatching.add(createPosteMatching(entreprise, poste));
                }
            }
        }
        return postesMatching;
    }


    public List<PosteMatching> searchFromSecteurActivite(String secteur) {
        List<PosteMatching> postesMatching = new ArrayList<>();
        for (Entreprise entreprise : Model.getInstance().getEntreprises().values()) {
            if (entreprise.getSecteursActivite().contains(secteur)) {
                for (Poste poste : entreprise.getPostes()) {
                    postesMatching.add(createPosteMatching(entreprise, poste));
                }
            }
        }
        return postesMatching;
    }

    private PosteMatching createPosteMatching(Entreprise entreprise, Poste poste) {
        PosteMatching posteMatching = new PosteMatching();
        posteMatching.setContrat(poste.getContrat());
        posteMatching.setIntitule(poste.getIntitule());
        posteMatching.setNom(entreprise.getNom());
        posteMatching.setNiveau(poste.getNiveau());
        posteMatching.setStand(entreprise.getStand());
        return posteMatching;
    }
}
