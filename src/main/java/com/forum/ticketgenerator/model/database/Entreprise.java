package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
	@ManyToMany
	private Set<SecteurActivite> secteursActivite;
	@OneToMany(mappedBy = "entreprise", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Poste> postes = new ArrayList<>();

}