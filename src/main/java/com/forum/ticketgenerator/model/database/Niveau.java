package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "NIVEAU")
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intitule;
    @ManyToOne
    private Evenement evenement;
}
