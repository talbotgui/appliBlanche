package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.Entity;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Produit extends Entite {
	private static final long serialVersionUID = 1L;

	private String couleur;

	private String nom;

	private Double prix;

	public Produit() {
		super();
	}

	public Produit(final String nom, final Double prix, final String couleur) {
		super();
		this.nom = nom;
		this.prix = prix;
		this.couleur = couleur;
	}

	public String getCouleur() {
		return this.couleur;
	}

	public String getNom() {
		return this.nom;
	}

	public Double getPrix() {
		return this.prix;
	}

	public void setCouleur(final String couleur) {
		this.couleur = couleur;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setPrix(final Double prix) {
		this.prix = prix;
	}

}
