package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Reservation extends Entite {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "CHAMBRE_ID")
	private Chambre chambre;

	private String client;

	@OneToMany(mappedBy = "reservation")
	private Set<Consomation> consomations = new HashSet<>();

	private LocalDate dateDebut;

	private LocalDate dateFin;

	public Reservation() {
		super();
	}

	public Reservation(final String client, final Chambre chambre, final LocalDate dateDebut, final LocalDate dateFin) {
		super();
		this.client = client;
		this.chambre = chambre;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public Chambre getChambre() {
		return this.chambre;
	}

	public String getClient() {
		return this.client;
	}

	public Set<Consomation> getConsomations() {
		return new HashSet<>(this.consomations);
	}

	public LocalDate getDateDebut() {
		return this.dateDebut;
	}

	public LocalDate getDateFin() {
		return this.dateFin;
	}

	public void setChambre(final Chambre chambre) {
		this.chambre = chambre;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public void setConsomations(final Set<Consomation> consomations) {
		this.consomations = new HashSet<>(consomations);
	}

	public void setDateDebut(final LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(final LocalDate dateFin) {
		this.dateFin = dateFin;
	}

}
