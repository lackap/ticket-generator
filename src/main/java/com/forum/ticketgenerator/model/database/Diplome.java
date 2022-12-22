package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DIPLOME")
public class Diplome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intituleDiplome;
    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private FamilleMetier familleMetier;
}
