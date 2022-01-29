package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchService {

    public SearchService() {
    }

    public List<Entreprise> searchFromFormation(String nomCentre, String intituleFormation) {
        Formation formation = Model.getInstance().getFormations().get(nomCentre);
        List<String> famillesMetier = formation.getDiplomes().stream()
                .filter(di -> di.getIntituleDiplome().equals(intituleFormation))
                .findFirst()
                .map(diplome -> diplome.getMetiersDebouche().stream().map(metier -> metier.getFamilleMetier()).collect(Collectors.toList()))
                .get();
        List<Entreprise> entreprisesMatch = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(famillesMetier)) {
            for (Entreprise entreprise : Model.getInstance().getEntreprises().values()) {
                Entreprise entrepriseToAdd = new Entreprise();
                for (Poste poste : entreprise.getPostes()) {
                    if (famillesMetier.contains(poste.getFamilleMetier())) {
                        entrepriseToAdd.getPostes().add(poste);
                    }
                }
                if (CollectionUtils.isNotEmpty(entrepriseToAdd.getPostes())) {
                    entreprisesMatch.add(entrepriseToAdd);
                }
            }
        }
        return entreprisesMatch;
    }
}
