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
}