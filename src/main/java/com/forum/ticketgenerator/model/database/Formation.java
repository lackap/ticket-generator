package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "FORMATION")
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomCentre;
    @OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Diplome> diplomes = new ArrayList<>();
}
