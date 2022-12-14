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
	@OneToOne
	private SecteurActivite secteurActivite;
	@ManyToOne(fetch = FetchType.LAZY)
	private Entreprise entreprise;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Poste )) return false;
		return id == ((Poste) o).getId();
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}