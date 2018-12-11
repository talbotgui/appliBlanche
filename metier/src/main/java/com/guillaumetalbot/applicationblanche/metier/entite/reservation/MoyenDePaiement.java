package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.Entity;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class MoyenDePaiement extends Entite {
	private static final long serialVersionUID = 1L;

	/** Montant associé (pour les box notamment) */
	private Double montantAssocie;

	private String nom;

	public MoyenDePaiement() {
		super();
	}

	public MoyenDePaiement(final String nom, final Double montantAssocie) {
		super();
		this.nom = nom;
		this.montantAssocie = montantAssocie;
	}

	public Double getMontantAssocie() {
		return this.montantAssocie;
	}

	public String getNom() {
		return this.nom;
	}

	public void setMontantAssocie(final Double montantAssocie) {
		this.montantAssocie = montantAssocie;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

}
