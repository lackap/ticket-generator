package com.forum.ticketgenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Entreprise {
	
	private String nom;
	private String stand;
	private List<String> secteursActivite;
	private List<Poste> postes;

	public Entreprise() {
		this.secteursActivite = new ArrayList<>();
		this.postes = new ArrayList<>();
	}

	public String getNom () {
		return nom;
	}

	public void setNom (String nom) {
		this.nom = nom;
	}

	public String getStand () {
		return stand;
	}

	public void setStand (String stand) {
		this.stand = stand;
	}

	public List<String> getSecteursActivite () {
		return secteursActivite;
	}

	public void setSecteursActivite (List<String> secteursActivite) {
		this.secteursActivite = secteursActivite;
	}

	public List<Poste> getPostes () {
		return postes;
	}

	public void setPostes (List<Poste> postes) {
		this.postes = postes;
	}
}