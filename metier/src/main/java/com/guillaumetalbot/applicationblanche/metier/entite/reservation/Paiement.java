package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Paiement extends Entite {
	private static final long serialVersionUID = 1L;

	private LocalDate dateCreation;

	private Double montant;

	// Un paiement n'a pas de sens sans son moyenDePaiement
	@ManyToOne
	@JoinColumn(name = "MOYEN_DE_PAIEMENT_ID")
	private MoyenDePaiement moyenDePaiement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESERVATION_ID")
	@JsonBackReference
	private Reservation reservation;

	public Paiement() {
		super();
	}

	public Paiement(final LocalDate dateCreation, final Double montant, final MoyenDePaiement moyenDePaiement, final Reservation reservation) {
		super();
		this.dateCreation = dateCreation;
		this.montant = montant;
		this.moyenDePaiement = moyenDePaiement;
		this.reservation = reservation;
	}

	public LocalDate getDateCreation() {
		return this.dateCreation;
	}

	public Double getMontant() {
		return this.montant;
	}

	public MoyenDePaiement getMoyenDePaiement() {
		return this.moyenDePaiement;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setDateCreation(final LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setMontant(final Double montant) {
		this.montant = montant;
	}

	public void setMoyenDePaiement(final MoyenDePaiement moyenDePaiement) {
		this.moyenDePaiement = moyenDePaiement;
	}

	public void setReservation(final Reservation reservation) {
		this.reservation = reservation;
	}

}
