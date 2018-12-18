package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
// pour éviter le mot clef SQL
@Table(name = "OPTION_RESERVATION")
public class Option extends Entite {
	private static final long serialVersionUID = 1L;

	private String nom;
	private Boolean parNuit;
	private Boolean parPersonne;
	private Double prix;

	public Option() {
		super();
	}

	public Option(final String nom, final Double prix, final Boolean parNuit, final Boolean parPersonne) {
		super();
		this.nom = nom;
		this.parNuit = parNuit;
		this.parPersonne = parPersonne;
		this.prix = prix;
	}

	public Double calculerMontantTotal(final long nbNuits, final long nbPersonnes) {
		return this.prix * this.calculerMultiplicateurAuPrixUnitaire(nbNuits, nbPersonnes);
	}

	public long calculerMultiplicateurAuPrixUnitaire(final long nbNuits, final long nbPersonnes) {
		long multiplicateur = 0;
		if (this.getParNuit() && this.getParPersonne()) {
			multiplicateur = nbNuits * nbPersonnes;
		} else if (this.getParNuit()) {
			multiplicateur = nbNuits;
		} else if (this.getParPersonne()) {
			multiplicateur = nbPersonnes;
		}
		return multiplicateur;
	}

	public String getNom() {
		return this.nom;
	}

	public Boolean getParNuit() {
		return this.parNuit;
	}

	public Boolean getParPersonne() {
		return this.parPersonne;
	}

	public Double getPrix() {
		return this.prix;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setParNuit(final Boolean parNuit) {
		this.parNuit = parNuit;
	}

	public void setParPersonne(final Boolean parPersonne) {
		this.parPersonne = parPersonne;
	}

	public void setPrix(final Double prix) {
		this.prix = prix;
	}

}
