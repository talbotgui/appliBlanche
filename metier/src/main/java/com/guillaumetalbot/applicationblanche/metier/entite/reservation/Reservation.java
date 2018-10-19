package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Reservation extends Entite {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHAMBRE_ID")
	private Chambre chambre;

	private String client;

	@OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
	private Set<Consommation> consommations = new HashSet<>();

	private LocalDate dateDebut;

	private LocalDate dateFin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FORMULE_ID")
	private Formule formule;

	/**
	 * Association en lecture seule directement vers les options. Mais, pour manipuler les ajouts/retraits, il faut passer par la création/suppression
	 * d'OptionReservee.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "OPTION_RESERVEE", //
			joinColumns = { @JoinColumn(name = "RESERVATION_ID") }, //
			inverseJoinColumns = { @JoinColumn(name = "OPTION_ID") })
	private Collection<Option> options;

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

	public Reservation(final String client, final Chambre chambre, final LocalDate dateDebut, final LocalDate dateFin, final Formule formule) {
		this(client, chambre, dateDebut, dateFin);
		this.formule = formule;
	}

	public Chambre getChambre() {
		return this.chambre;
	}

	public String getClient() {
		return this.client;
	}

	public Set<Consommation> getConsommations() {
		return MutableUtil.getMutable(this.consommations);
	}

	public LocalDate getDateDebut() {
		return this.dateDebut;
	}

	public LocalDate getDateFin() {
		return this.dateFin;
	}

	public Formule getFormule() {
		return this.formule;
	}

	public Collection<Option> getOptions() {
		return this.options;
	}

	public void setChambre(final Chambre chambre) {
		this.chambre = chambre;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public void setConsommations(final Set<Consommation> consommations) {
		this.consommations = new HashSet<>(consommations);
	}

	public void setDateDebut(final LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(final LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public void setFormule(final Formule formule) {
		this.formule = formule;
	}

	public void setOptions(final Collection<Option> options) {
		this.options = options;
	}

}
