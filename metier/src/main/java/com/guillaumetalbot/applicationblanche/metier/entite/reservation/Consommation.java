package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Consommation extends Entite {
	private static final long serialVersionUID = 1L;

	private LocalDate dateCreation;

	private Double prixPaye;

	// ManyToOne chargé en mémoire systématiquement car une consommation n'a pas de sens sans le produit
	@ManyToOne
	@JoinColumn(name = "PRODUIT_ID")
	private Produit produit;

	private Integer quantite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESERVATION_ID")
	@JsonBackReference
	private Reservation reservation;

	public Consommation() {
		super();
	}

	public Consommation(final Reservation reservation, final Produit produit, final Double prixPaye, final Integer quantite) {
		super();
		this.reservation = reservation;
		this.produit = produit;
		this.prixPaye = prixPaye;
		this.quantite = quantite;
	}

	public Consommation(final Reservation reservation, final Produit produit, final Double prixPaye, final Integer quantite,
			final LocalDate dateCreation) {
		super();
		this.reservation = reservation;
		this.produit = produit;
		this.prixPaye = prixPaye;
		this.quantite = quantite;
		this.dateCreation = dateCreation;
	}

	public Double calculerMontantTotal() {
		return this.prixPaye * this.quantite;
	}

	public LocalDate getDateCreation() {
		return this.dateCreation;
	}

	public Double getPrixPaye() {
		return this.prixPaye;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public Integer getQuantite() {
		return this.quantite;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setDateCreation(final LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setPrixPaye(final Double prixPaye) {
		this.prixPaye = prixPaye;
	}

	public void setProduit(final Produit produit) {
		this.produit = produit;
	}

	public void setQuantite(final Integer quantite) {
		this.quantite = quantite;
	}

	public void setReservation(final Reservation reservation) {
		this.reservation = reservation;
	}

}
