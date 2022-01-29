package com.forum.ticketgenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Diplome {
    private String intituleDiplome;
    private String niveau;
    private List<MetierDebouche> metiersDebouche;

    public Diplome() {
        this.metiersDebouche = new ArrayList<>();
    }

    public String getIntituleDiplome () {
        return intituleDiplome;
    }

    public void setIntituleDiplome (String intituleDiplome) {
        this.intituleDiplome = intituleDiplome;
    }

    public String getNiveau () {
        return niveau;
    }

    public void setNiveau (String niveau) {
        this.niveau = niveau;
    }

    public List<MetierDebouche> getMetiersDebouche () {
        return metiersDebouche;
    }

    public void setMetiersDebouche (List<MetierDebouche> metiersDebouche) {
        this.metiersDebouche = metiersDebouche;
    }
}
