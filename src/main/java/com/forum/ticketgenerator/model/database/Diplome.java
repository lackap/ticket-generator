package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "DIPLOME")
public class Diplome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intituleDiplome;
    private String niveau;
    @OneToMany
    private List<MetierDebouche> metiersDebouche;
}
