package com.forum.ticketgenerator.model;

public class PosteMatching {
    private String nom;
    private String stand;
    private String intitule;
    private String secteurActivite;

    public String getNom () {
        return nom;
    }

    public void setNom (String nom) {
        this.nom = nom;
    }

    public String getStand () {
        return stand;
    }

    public void setStand (String stand) {
        this.stand = stand;
    }

    public String getIntitule () {
        return intitule;
    }

    public void setIntitule (String intitule) {
        this.intitule = intitule;
    }

    public String getSecteurActivite () {
        return secteurActivite;
    }

    public void setSecteurActivite (String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }
}
