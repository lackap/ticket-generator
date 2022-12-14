package com.forum.ticketgenerator.model.database;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "EVENEMENT")
public class Evenement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String intitule;
	private String labelSecteurActivité;
	private Boolean displaySecteur;
	private Boolean displayNiveau;
	private Boolean displayTypeContrat;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] affiche;
}