package com.forum.ticketgenerator.model;

public class Poste {
	private String intitule;
	private String familleMetier;
	private String contrat;
	private String niveau;

	public String getIntitule () {
		return intitule;
	}

	public void setIntitule (String intitule) {
		this.intitule = intitule;
	}

	public String getFamilleMetier () {
		return familleMetier;
	}

	public void setFamilleMetier (String familleMetier) {
		this.familleMetier = familleMetier;
	}

	public String getContrat () {
		return contrat;
	}

	public void setContrat (String contrat) {
		this.contrat = contrat;
	}

	public String getNiveau () {
		return niveau;
	}

	public void setNiveau (String niveau) {
		this.niveau = niveau;
	}
}