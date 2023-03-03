package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SECTEUR_ACTIVITE")
public class SecteurActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intitule;
    @ManyToOne
    private Evenement evenement;
    private String couleur;
    private String couleurLabel;
}
