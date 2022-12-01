package com.forum.ticketgenerator.model.database;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POSTE")
public class Poste {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String intitule;
	@ManyToOne
	private TypeContrat typeContrat;
	@ManyToOne
	private Niveau niveau;
	@ManyToOne
	private Evenement evenement;
	@ManyToOne
	private FamilleMetier familleMetier;
}