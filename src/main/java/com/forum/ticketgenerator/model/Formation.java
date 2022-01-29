package com.forum.ticketgenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private String nomCentre;
    private List<Diplome> diplomes;

    public Formation() {
        this.diplomes = new ArrayList<>();
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
