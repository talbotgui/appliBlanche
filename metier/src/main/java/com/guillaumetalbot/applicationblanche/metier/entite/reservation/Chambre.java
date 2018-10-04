package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.Entity;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Chambre extends Entite {
	private static final long serialVersionUID = 1L;

	private String nom;

	public Chambre() {
		super();
	}

	public Chambre(final String nom) {
		super();
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

}
