package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ENTREPRISE")
public class Entreprise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nom;
	private String stand;
	@OneToMany
	private List<SecteurActivite> secteursActivite = new ArrayList<>();
	@OneToMany
	private List<Poste> postes = new ArrayList<>();

}