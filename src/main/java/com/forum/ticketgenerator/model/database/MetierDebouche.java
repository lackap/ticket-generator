package com.forum.ticketgenerator.model.database;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "METIER_DEBOUCHE")
public class MetierDebouche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intituleMetier;
    private String familleMetier;
}
