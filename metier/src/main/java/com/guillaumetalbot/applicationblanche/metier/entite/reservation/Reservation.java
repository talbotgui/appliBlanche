package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guillaumetalbot.applicationblanche.exception.BusinessException;
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
	@JsonManagedReference
	private Set<Consommation> consommations = new HashSet<>();

	private LocalDate dateDebut;

	private LocalDate dateFin;

	private EtatReservation etatCourant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FORMULE_ID")
	private Formule formule;

	private Long nombrePersonnes = 2L;

	/**
	 * Association en lecture seule directement vers les options. Mais, pour manipuler les ajouts/retraits, il faut passer par la création/suppression
	 * d'OptionReservee.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "OPTION_RESERVEE", //
			joinColumns = { @JoinColumn(name = "RESERVATION_ID") }, //
			inverseJoinColumns = { @JoinColumn(name = "OPTION_ID") })
	private Set<Option> options = new HashSet<>();

	@OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Paiement> paiements = new HashSet<>();

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

	/**
	 * Calcul du montant déjà payé
	 *
	 * @return
	 */
	public Double calculerMontantPaye() {
		if (Hibernate.isInitialized(this.paiements)) {
			Double montant = 0.0;
			for (final Paiement p : this.paiements) {
				montant += p.getMontant();
			}
			return montant;
		} else {
			return null;
		}
	}

	/**
	 * Calcul du montant restant dû.
	 *
	 * @return
	 */
	public Double calculerMontantRestantDu() {
		return this.calculerMontantTotal() - this.calculerMontantPaye();
	}

	public Double calculerMontantTotal() {
		// Si les attributs ne sont pas chargés, renvoi NULL
		if (!Hibernate.isInitialized(this.getChambre()) || !Hibernate.isInitialized(this.getFormule()) || !Hibernate.isInitialized(this.getOptions())
				|| !Hibernate.isInitialized(this.getConsommations())) {
			return null;
		}

		// Pré-calculs
		final long nbNuits = this.calculerNombreNuits();
		final long nbPersonnes = this.nombrePersonnes;

		// Initialisation du prix
		Double montantTotal = 0D;

		// Ajout du prix de la formule
		montantTotal += this.formule.calculerMontantTotal(nbNuits);

		// Ajout des options
		if (this.options != null) {
			for (final Option o : this.options) {
				montantTotal += o.calculerMontantTotal(nbNuits, nbPersonnes);
			}
		}

		// Ajout des consommations
		if (this.consommations != null) {
			for (final Consommation c : this.consommations) {
				montantTotal += c.getPrixPaye() * c.getQuantite();
			}
		}

		return montantTotal;
	}

	/** Calcul du nombre de nuits */
	public long calculerNombreNuits() {
		return this.dateDebut.until(this.dateFin, ChronoUnit.DAYS);
	}

	/**
	 * Validation et changement de l'état courant de la réservation.
	 *
	 * @param etatDemande
	 */
	public void changerEtatCourant(final EtatReservation etatDemande) {
		// Validation de la transition
		if (!this.validerTransitionEtat(etatDemande)) {
			throw new BusinessException(BusinessException.TRANSITION_ETAT_IMPOSSIBLE, this.etatCourant.name(), etatDemande.name());
		}
		this.etatCourant = etatDemande;
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

	public EtatReservation getEtatCourant() {
		return this.etatCourant;
	}

	public Formule getFormule() {
		return this.formule;
	}

	public Long getNombrePersonnes() {
		return this.nombrePersonnes;
	}

	public Set<Option> getOptions() {
		return this.options;
	}

	public Set<Paiement> getPaiements() {
		return this.paiements;
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

	public void setEtatCourant(final EtatReservation etatCourant) {
		this.etatCourant = etatCourant;
	}

	public void setFormule(final Formule formule) {
		this.formule = formule;
	}

	public void setNombrePersonnes(final Long nombrePersonnes) {
		this.nombrePersonnes = nombrePersonnes;
	}

	public void setOptions(final Set<Option> options) {
		this.options = options;
	}

	public void setPaiements(final Set<Paiement> paiements) {
		this.paiements = paiements;
	}

	/**
	 * Méthode permettant de valider une transition d'état
	 *
	 * @param etatDemande
	 * @return
	 */
	private boolean validerTransitionEtat(final EtatReservation etatDemande) {

		// Tous les états permettent de passer à ANNULER
		if (EtatReservation.ANNULEE.equals(etatDemande)) {
			return true;
		}

		// Transition possible depuis ENREGISTREE
		if (EtatReservation.ENREGISTREE.equals(this.etatCourant)) {
			return EtatReservation.EN_COURS.equals(etatDemande);
		}

		// Transition possible depuis EN_COURS
		else if (EtatReservation.EN_COURS.equals(this.etatCourant)) {
			return EtatReservation.FACTUREE.equals(etatDemande);
		}

		// Transition possible depuis FACTUREE
		else if (EtatReservation.FACTUREE.equals(this.etatCourant)) {
			return EtatReservation.TERMINEE.equals(etatDemande) || EtatReservation.EN_COURS.equals(etatDemande);
		}

		// TERMINEE est un état final
		else if (EtatReservation.TERMINEE.equals(this.etatCourant)) {
			return false;
		}

		// etat incohérent
		else {
			return false;
		}
	}

}
