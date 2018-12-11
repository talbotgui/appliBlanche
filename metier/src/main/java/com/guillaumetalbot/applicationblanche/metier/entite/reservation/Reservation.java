package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import org.hibernate.Hibernate;

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
	private Collection<Option> options = new HashSet<>();

	@OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
	private final Collection<Paiement> paiements = new HashSet<>();

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
		final long nbNuits = this.dateDebut.until(this.dateFin, ChronoUnit.DAYS);
		final long nbPersonnes = this.nombrePersonnes;

		// Initialisation du prix
		Double montantTotal = 0D;

		// Ajout du prix de la formule
		montantTotal += this.formule.getPrixParNuit() * nbNuits;

		// Ajout des options
		if (this.options != null) {
			for (final Option o : this.options) {
				long multiplicateur = 0;
				if (o.getParNuit() && o.getParPersonne()) {
					multiplicateur = nbNuits * nbPersonnes;
				} else if (o.getParNuit()) {
					multiplicateur = nbNuits;
				} else if (o.getParPersonne()) {
					multiplicateur = nbPersonnes;
				}
				montantTotal += o.getPrix() * multiplicateur;
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

	public void setEtatCourant(final EtatReservation etatCourant) {
		this.etatCourant = etatCourant;
	}

	public void setFormule(final Formule formule) {
		this.formule = formule;
	}

	public void setNombrePersonnes(final Long nombrePersonnes) {
		this.nombrePersonnes = nombrePersonnes;
	}

	public void setOptions(final Collection<Option> options) {
		this.options = options;
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

		// Transition possible depuis EN_COURS
		else if (EtatReservation.FACTUREE.equals(this.etatCourant)) {
			return EtatReservation.TERMINEE.equals(etatDemande);
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
