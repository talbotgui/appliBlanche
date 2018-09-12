package com.guillaumetalbot.applicationblanche.metier.entite;

public class EntiteDeTest1 extends Entite {
	private static final long serialVersionUID = 1L;

	private String nom;
	private String prenom;

	public EntiteDeTest1() {
		super();
	}

	public EntiteDeTest1(final String nom, final String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getNom() {
		return this.nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}
}
