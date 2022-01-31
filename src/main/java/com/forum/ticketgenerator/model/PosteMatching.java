package com.forum.ticketgenerator.model;

public class PosteMatching {
    private String nom;
    private Integer stand;
    private String intitule;
    private String contrat;
    private String niveau;

    public String getNom () {
        return nom;
    }

    public void setNom (String nom) {
        this.nom = nom;
    }

    public Integer getStand () {
        return stand;
    }

    public void setStand (Integer stand) {
        this.stand = stand;
    }

    public String getIntitule () {
        return intitule;
    }

    public void setIntitule (String intitule) {
        this.intitule = intitule;
    }

    public String getContrat () {
        return contrat;
    }

    public void setContrat (String contrat) {
        this.contrat = contrat;
    }

    public String getNiveau () {
        return niveau;
    }

    public void setNiveau (String niveau) {
        this.niveau = niveau;
    }
}
