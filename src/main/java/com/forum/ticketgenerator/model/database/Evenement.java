package com.forum.ticketgenerator.model.database;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "EVENEMENT")
public class Evenement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String intitule;
	@OneToMany
	List<Poste> postes = new ArrayList<>();
}