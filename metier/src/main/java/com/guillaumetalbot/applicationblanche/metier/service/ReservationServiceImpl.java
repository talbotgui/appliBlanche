package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ChambreRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ConsommationRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.FormuleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.OptionRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ProduitRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ReservationRepository;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
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
	private OptionRepository optionRepo;

	@Autowired
	private ProduitRepository produitRepo;

	@Autowired
	private ReservationRepository reservationRepo;

	@Override
	public void changeEtatReservation(final String referenceReservation, final EtatReservation etatDemande) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Chargement de la réservation
		final Reservation reservation = this.reservationRepo.findById(idReservation)//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "reservation", referenceReservation));

		// Validation et changement d'état
		reservation.changerEtatCourant(etatDemande);

		// Sauvegarde
		this.reservationRepo.save(reservation);
	}

	@Override
	public Collection<Consommation> rechercherConsommationsDuneReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);
		return this.consommationRepo.rechercherConsommationsDuneReservation(idReservation);
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
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Produit", consommation.getProduit().getReference()));

		// Vérifier la présence de la réservation
		final Long idReservation = Entite.extraireIdentifiant(consommation.getReservation().getReference(), Reservation.class);
		final Optional<Reservation> resaOpt = this.reservationRepo.findById(idReservation);
		final Reservation reservation = resaOpt//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Reservation",
						consommation.getReservation().getReference()));

		// Vérifier les dates de la réservation
		if (reservation.getDateDebut().isAfter(LocalDate.now()) || reservation.getDateFin().isBefore(LocalDate.now())) {
			throw new BusinessException(BusinessException.RESERVATION_PAS_EN_COURS, consommation.getReservation().getReference());
		}

		// Si le prix de la consommation n'est pas renseigné (pas de remise), on prend le prix du produit
		if (consommation.getPrixPaye() == null) {
			consommation.setPrixPaye(produit.getPrix());
		}

		return this.consommationRepo.save(consommation).getReference();
	}

	@Override
	public String sauvegarderReservation(final Reservation reservation) {

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
		final Chambre chambre = this.chambreRepo.findById(idChambre)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "chambre", reservation.getChambre().getReference()));

		// Validation de la formule
		if (reservation.getFormule() == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "formule", reservation.getFormule().getReference());
		}
		final Long idFormule = Entite.extraireIdentifiant(reservation.getFormule().getReference(), Formule.class);
		this.formuleRepo.findById(idFormule)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "formule", reservation.getFormule().getReference()));

		// Validation des options
		if (reservation.getOptions() != null) {
			for (final Option o : reservation.getOptions()) {
				final Long idOption = Entite.extraireIdentifiant(o.getReference(), Option.class);
				this.optionRepo.findById(idOption)
						.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "option", o.getReference()));
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
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Reservation", referenceReservation);
		}

		this.consommationRepo.deleteById(idConsommation);
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
}
