package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "ENTREPRISE")
public class Entreprise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nom;
	private String stand;
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<SecteurActivite> secteursActivite = new HashSet<>();
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<Poste> postes = new HashSet<>();

}