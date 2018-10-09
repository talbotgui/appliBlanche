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
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ProduitRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ReservationRepository;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
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
	private ProduitRepository produitRepo;

	@Autowired
	private ReservationRepository reservationRepo;

	@Override
	public Collection<Chambre> listerChambres() {
		return this.chambreRepo.listerChambres();
	}

	@Override
	public Collection<Produit> listerProduits() {
		return this.produitRepo.listerProduits();
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
	public String sauvegarderChambre(final Chambre chambre) {

		// Validation pas de chambre en double
		this.chambreRepo.rechercherChambreParNom(chambre.getNom()).ifPresent((p) -> {
			throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "chambre", "nom", chambre.getNom());
		});

		return this.chambreRepo.save(chambre).getReference();
	}

	@Override
	public String sauvegarderConsommation(final Consommation consommation) {

		// Vérifier le produit
		final Long idProduit = Entite.extraireIdentifiant(consommation.getProduit().getReference(), Produit.class);
		final Optional<Produit> produit = this.produitRepo.findById(idProduit);
		produit.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Produit", consommation.getProduit().getReference()));

		// Vérifier la présence de la réservation
		final Long idReservation = Entite.extraireIdentifiant(consommation.getReservation().getReference(), Reservation.class);
		final Optional<Reservation> resaOpt = this.reservationRepo.findById(idReservation);
		resaOpt.orElseThrow(
				() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Reservation", consommation.getReservation().getReference()));

		// Vérifier les dates de la réservation
		final Reservation reservation = resaOpt.get();
		if (reservation.getDateDebut().isAfter(LocalDate.now()) || reservation.getDateFin().isBefore(LocalDate.now())) {
			throw new BusinessException(BusinessException.RESERVATION_PAS_EN_COURS, consommation.getReservation().getReference());
		}

		// Si le prix de la consommation n'est pas renseigné (pas de remise), on prend le prix du produit
		if (consommation.getPrixPaye() == null) {
			consommation.setPrixPaye(produit.get().getPrix());
		}

		return this.consommationRepo.save(consommation).getReference();
	}

	@Override
	public String sauvegarderProduit(final Produit produit) {

		// Trim du nom pour éviter les problèmes de frappe
		produit.setNom(produit.getNom().trim());

		// Arrondit du prix
		produit.setPrix(Math.floor(produit.getPrix() * 100) / 100);

		// Validation pas de produit en double
		this.produitRepo.rechercherProduitParNom(produit.getNom()).ifPresent((p) -> {
			throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "produit", "nom", produit.getNom());
		});

		return this.produitRepo.save(produit).getReference();
	}

	@Override
	public String sauvegarderReservation(final Reservation reservation) {

		// Trim du nom du client pour éviter les problèmes de frappe
		reservation.setClient(reservation.getClient().trim());

		// Validation de la chambre
		final Long idChambre = Entite.extraireIdentifiant(reservation.getChambre().getReference(), Chambre.class);
		this.chambreRepo.findById(idChambre)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "chambre", reservation.getChambre().getReference()));

		return this.reservationRepo.save(reservation).getReference();
	}

	@Override
	public void supprimerConsommation(final String referenceConsommation) {
		final Long idConsommation = Entite.extraireIdentifiant(referenceConsommation, Consommation.class);
		this.consommationRepo.deleteById(idConsommation);
	}

	@Override
	public void supprimerProduit(final String referenceProduit) {
		final Long idProduit = Entite.extraireIdentifiant(referenceProduit, Produit.class);
		this.produitRepo.deleteById(idProduit);
	}

	@Override
	public void supprimerReservation(final String referenceReservation) {
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);
		this.reservationRepo.deleteById(idReservation);
	}
}
