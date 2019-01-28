package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ChambreRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ConsommationRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.FormuleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.MoyenDePaiementRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.OptionRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.PaiementRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ProduitRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ReservationRepository;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ChambreRepository chambreRepo;

	@Autowired
	private ConsommationRepository consommationRepo;

	@Autowired
	private FormuleRepository formuleRepo;

	@Autowired
	private MoyenDePaiementRepository moyenDePaiementRepo;

	@Autowired
	private OptionRepository optionRepo;

	@Autowired
	private PaiementRepository paiementRepo;

	@Autowired
	private ProduitRepository produitRepo;

	@Autowired
	private ReservationRepository reservationRepo;

	@Override
	public void changeEtatReservation(final String referenceReservation, final EtatReservation etatDemande) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Chargement de la réservation
		final Reservation reservation = this.reservationRepo.findById(idReservation)//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Reservation.class, referenceReservation));

		// Validation et changement d'état
		reservation.changerEtatCourant(etatDemande);

		// Sauvegarde
		this.reservationRepo.save(reservation);
	}

	@Override
	public Reservation chargerReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		return this.reservationRepo.chargerReservationFetchChambreFormuleOptionsConsommationPaiements(idReservation)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Reservation.class, referenceReservation));
	}

	@Override
	public void modifierQuantiteConsommation(final String referenceReservation, final String referenceConsommation, final Integer quantite) {

		// Validation que la réservation existe et est en cours (date et statut)
		this.validerReservationExistanteEtEnCoursParDateEtStatut(referenceReservation);

		// Rechercher consommation
		final Long idConsommation = Entite.extraireIdentifiant(referenceConsommation, Consommation.class);
		final Optional<Consommation> consommationOpt = this.consommationRepo.findById(idConsommation);
		final Consommation consommation = consommationOpt//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Consommation.class, referenceConsommation));

		// Si c'est une suppression
		if (consommation.getQuantite() < 2) {
			this.consommationRepo.deleteById(idConsommation);
		}

		// Sinon, modification de la quantite
		else {
			consommation.setQuantite(consommation.getQuantite() + quantite);
		}
	}

	@Override
	public Collection<Consommation> rechercherConsommationsDuneReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);
		return this.consommationRepo.rechercherConsommationsDuneReservation(idReservation);
	}

	@Override
	public Collection<Paiement> rechercherPaiementsDuneReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);
		return this.paiementRepo.rechercherPaiementsDuneReservation(idReservation);
	}

	@Override
	public Collection<Reservation> rechercherReservations(final EtatReservation etat, final boolean fetchTout) {
		if (!fetchTout) {
			return this.reservationRepo.rechercherReservationsParEtatFetchChambre(etat);
		} else if (!EtatReservation.FACTUREE.equals(etat)) {
			return this.reservationRepo.rechercherReservationsParEtatFetchChambreFormuleOptions(etat);
		} else {
			return this.reservationRepo.rechercherReservationsParEtatFetchChambreFormuleOptions(etat, PageRequest.of(0, 20));
		}
	}

	@Override
	public Collection<Reservation> rechercherReservations(final LocalDate dateDebut, final LocalDate dateFin) {
		return this.reservationRepo.rechercherReservations(dateDebut, dateFin);
	}

	@Override
	public String sauvegarderConsommation(final Consommation consommation) {

		// Vérifier le produit
		final Long idProduit = Entite.extraireIdentifiant(consommation.getProduit().getReference(), Produit.class);
		final Produit produit = this.produitRepo.findById(idProduit)//
				.orElseThrow(
						() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Produit.class, consommation.getProduit().getReference()));

		// Validation que la réservation existe et est en cours (date et statut)
		final Long idReservation = Entite.extraireIdentifiant(consommation.getReservation().getReference(), Reservation.class);
		this.validerReservationExistanteEtEnCoursParDateEtStatut(consommation.getReservation().getReference());

		// Si le prix de la consommation n'est pas renseigné (pas de remise), on prend le prix du produit
		if (consommation.getPrixPaye() == null) {
			consommation.setPrixPaye(produit.getPrix());
		}

		// Si une consommation existe avec cette réservation et ce produit à ce prix, la quantite est incrémentée
		final Consommation consommationExistante = this.consommationRepo.rechercherConsommationsDuneReservationManaged(idReservation, idProduit,
				consommation.getPrixPaye());
		if (consommationExistante != null) {
			consommationExistante.setQuantite(consommationExistante.getQuantite() + 1);

			// Inutile d'appeler la méthode SAVE car l'objet consommationExistante est MANAGED
			return consommationExistante.getReference();
		}

		// Si elle n'existe pas
		else {
			consommation.setDateCreation(LocalDate.now());
			return this.consommationRepo.save(consommation).getReference();
		}
	}

	@Override
	public String sauvegarderPaiement(final Paiement paiement) {

		// Init de la date
		paiement.setDateCreation(LocalDate.now());

		// Vérifier le moyen
		final String refMdp = paiement.getMoyenDePaiement().getReference();
		final Long idMdp = Entite.extraireIdentifiant(refMdp, MoyenDePaiement.class);
		final MoyenDePaiement mdp = this.moyenDePaiementRepo.findById(idMdp)//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, MoyenDePaiement.class, refMdp));

		// Vérifier le montant
		if (paiement.getMontant() == null && mdp.getMontantAssocie() == null) {
			throw new BusinessException(BusinessException.ERREUR_AUCUN_MONTANT);
		}

		// Validation que la réservation existe (on peut payer quand on veut)
		this.validerReservationExistante(paiement.getReservation().getReference());

		// Sauvegarde
		return this.paiementRepo.save(paiement).getReference();
	}

	@Override
	public String sauvegarderReservation(final Reservation reservation) {

		// Validation des dates
		if (reservation.getDateDebut() == null || reservation.getDateFin() == null //
				|| reservation.getDateDebut().isAfter(reservation.getDateFin()) //
				|| reservation.getDateDebut().equals(reservation.getDateFin())) {
			throw new BusinessException(BusinessException.RESERVATION_DATES_INCOHERENTES);
		}

		// Trim du nom du client pour éviter les problèmes de frappe
		reservation.setClient(reservation.getClient().trim());

		// Gestion de l'état
		if (reservation.getId() == null && reservation.getReference() == null) {
			reservation.setEtatCourant(EtatReservation.ENREGISTREE);
		}

		// Validation de la chambre
		if (reservation.getChambre() == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "chambre", reservation.getChambre().getReference());
		}
		final Long idChambre = Entite.extraireIdentifiant(reservation.getChambre().getReference(), Chambre.class);
		final Chambre chambre = this.chambreRepo.findById(idChambre).orElseThrow(
				() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Chambre.class, reservation.getChambre().getReference()));

		// Validation de la formule
		if (reservation.getFormule() == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, Formule.class, reservation.getFormule().getReference());
		}
		final Long idFormule = Entite.extraireIdentifiant(reservation.getFormule().getReference(), Formule.class);
		if (!this.formuleRepo.findById(idFormule).isPresent()) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, Formule.class, reservation.getFormule().getReference());
		}

		// Validation des options
		if (reservation.getOptions() != null) {
			for (final Option o : reservation.getOptions()) {
				final Long idOption = Entite.extraireIdentifiant(o.getReference(), Option.class);
				if (!this.optionRepo.findById(idOption).isPresent()) {
					throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "option", o.getReference());
				}
			}
		}

		// Vérification qu'aucune reservation n'existe à ces dates
		final Long nbReservations = this.reservationRepo.compterReservationsDeLaChambre(reservation.getDateDebut(), reservation.getDateFin(),
				chambre.getId(), reservation.getId());
		if (nbReservations > 0) {
			throw new BusinessException(BusinessException.RESERVATION_DEJA_EXISTANTE);
		}

		return this.reservationRepo.save(reservation).getReference();
	}

	@Override
	public void supprimerConsommation(final String referenceReservation, final String referenceConsommation) {
		final Long idConsommation = Entite.extraireIdentifiant(referenceConsommation, Consommation.class);
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Valide les ids entre eux
		if (!this.consommationRepo.getIdReservationByIdConsommation(idConsommation).equals(idReservation)) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, Reservation.class, referenceReservation);
		}

		this.consommationRepo.deleteById(idConsommation);
	}

	@Override
	public void supprimerPaiement(final String referenceReservation, final String referencePaiement) {
		final Long idPaiement = Entite.extraireIdentifiant(referencePaiement, Paiement.class);
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Valide les ids entre eux
		if (!this.paiementRepo.getIdReservationByIdPaiement(idPaiement).equals(idReservation)) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, Reservation.class, referenceReservation);
		}

		this.paiementRepo.deleteById(idPaiement);
	}

	@Override
	public void supprimerReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Suppression impossible si des consommations sont associées
		if (this.consommationRepo.compterConsommationDeLaReservation(idReservation) > 0) {
			throw new BusinessException(BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES, "Consommation");
		}

		this.reservationRepo.deleteById(idReservation);
	}

	/**
	 * Validation que la réservation existe
	 *
	 * @param referenceReservation
	 * @return
	 */
	private Reservation validerReservationExistante(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);
		final Optional<Reservation> resaOpt = this.reservationRepo.findById(idReservation);
		return resaOpt//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, Reservation.class, referenceReservation));
	}

	/**
	 * Validation que la réservation est bien en cours par son existance, ses dates et son statut
	 *
	 * @param referenceReservation Reference de la réservation
	 */
	private void validerReservationExistanteEtEnCoursParDateEtStatut(final String referenceReservation) {

		// Vérifier la présence de la réservation
		final Reservation reservation = this.validerReservationExistante(referenceReservation);

		// Vérifier le statut de la réservation
		if (!EtatReservation.EN_COURS.equals(reservation.getEtatCourant())) {
			throw new BusinessException(BusinessException.RESERVATION_PAS_EN_COURS, referenceReservation);
		}

	}
}
