package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.Entity;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Formule extends Entite {
	private static final long serialVersionUID = 1L;

	private String nom;

	private Double prixParNuit;

	public Formule() {
		super();
	}

	public Formule(final String nom, final Double prixParNuit) {
		super();
		this.nom = nom;
		this.prixParNuit = prixParNuit;
	}

	public Double calculerMontantTotal(final long nbNuits) {
		return this.prixParNuit * nbNuits;
	}

	public String getNom() {
		return this.nom;
	}

	public Double getPrixParNuit() {
		return this.prixParNuit;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setPrixParNuit(final Double prixParNuit) {
		this.prixParNuit = prixParNuit;
	}

}
