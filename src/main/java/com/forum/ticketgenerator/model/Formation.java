package com.forum.ticketgenerator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Formation {
    private String nomCentre;
    private List<Diplome> diplomes;

    public Formation(String nomCentre) {
        this.nomCentre = nomCentre;
        this.diplomes = new ArrayList<>();
    }

    public Formation() {
        this.diplomes = new ArrayList<>();
    }

    public List<String> getDiplomeLabels() {
        return diplomes.stream().map(Diplome::getIntituleDiplome).collect(Collectors.toList());
    }

    public String getNomCentre () {
        return nomCentre;
    }

    public void setNomCentre (String nomCentre) {
        this.nomCentre = nomCentre;
    }

    public List<Diplome> getDiplomes () {
        return diplomes;
    }

    public void setDiplomes (List<Diplome> diplomes) {
        this.diplomes = diplomes;
    }
}
