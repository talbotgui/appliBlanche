package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;

@Service
@Transactional
public class ReservationParametresServiceImpl implements ReservationParametresService {

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
	public Collection<Chambre> listerChambres() {
		return this.chambreRepo.listerChambres();
	}

	@Override
	public Collection<Formule> listerFormules() {
		return this.formuleRepo.listerFormules();
	}

	@Override
	public Collection<Option> listerOptions() {
		return this.optionRepo.listerOptions();
	}

	@Override
	public Collection<Produit> listerProduits() {
		return this.produitRepo.listerProduits();
	}

	@Override
	public String sauvegarderChambre(final Chambre chambre) {

		// Validation pas de chambre en double
		this.chambreRepo.rechercherChambreParNom(chambre.getNom()).ifPresent(c -> {
			if (!c.getId().equals(chambre.getId())) {
				throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "chambre", "nom", chambre.getNom());
			}
		});

		return this.chambreRepo.save(chambre).getReference();
	}

	@Override
	public String sauvegarderFormule(final Formule formule) {

		// Trim du nom pour éviter les problèmes de frappe
		formule.setNom(formule.getNom().trim());

		// Arrondit du prix
		formule.setPrixParNuit(Math.floor(formule.getPrixParNuit() * 100) / 100);

		// Validation pas de produit en double
		this.formuleRepo.rechercherFormuleManagedParNom(formule.getNom()).ifPresent(f -> {
			if (!f.getId().equals(formule.getId())) {
				throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "formule", "nom", formule.getNom());
			}
		});

		return this.formuleRepo.save(formule).getReference();
	}

	@Override
	public String sauvegarderOption(final Option option) {

		// Trim du nom pour éviter les problèmes de frappe
		option.setNom(option.getNom().trim());

		// Arrondit du prix
		option.setPrix(Math.floor(option.getPrix() * 100) / 100);

		// Validation pas de produit en double
		this.optionRepo.rechercherOptionManagedParNom(option.getNom()).ifPresent(o -> {
			if (!o.getId().equals(o.getId())) {
				throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "option", "nom", option.getNom());
			}
		});

		return this.optionRepo.save(option).getReference();
	}

	@Override
	public String sauvegarderProduit(final Produit produit) {

		// Trim du nom pour éviter les problèmes de frappe
		produit.setNom(produit.getNom().trim());

		// Arrondit du prix
		produit.setPrix(Math.floor(produit.getPrix() * 100) / 100);

		// Validation pas de produit en double
		this.produitRepo.rechercherProduitManagedParNom(produit.getNom()).ifPresent(p -> {
			if (!p.getId().equals(produit.getId())) {
				throw new BusinessException(BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE, "produit", "nom", produit.getNom());
			}
		});

		return this.produitRepo.save(produit).getReference();
	}

	@Override
	public void supprimerChambre(final String reference) {
		final Long id = Entite.extraireIdentifiant(reference, Chambre.class);

		// Suppression impossible si des réservations sont associées
		if (this.reservationRepo.compterReservationsDeLaChambre(id) > 0) {
			throw new BusinessException(BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES, "Reservation");
		}

		this.chambreRepo.deleteById(id);
	}

	@Override
	public void supprimerFormule(final String reference) {
		final Long id = Entite.extraireIdentifiant(reference, Formule.class);

		// Suppression impossible si des Reservation sont associées
		if (this.reservationRepo.compterReservationPourCetteFormule(id) > 0) {
			throw new BusinessException(BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES, "Reservation");
		}

		this.formuleRepo.deleteById(id);
	}

	@Override
	public void supprimerOption(final String reference) {
		final Long id = Entite.extraireIdentifiant(reference, Option.class);

		// Suppression impossible si des OptionReservee sont associées
		if (this.optionRepo.compterOptionReserveePourCetteOption(id) > 0) {
			throw new BusinessException(BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES, "OptionReservee");
		}

		this.optionRepo.deleteById(id);
	}

	@Override
	public void supprimerProduit(final String referenceProduit) {
		final Long idProduit = Entite.extraireIdentifiant(referenceProduit, Produit.class);

		// Suppression impossible si des consommations sont associées
		if (this.consommationRepo.compterConsommationDuProduit(idProduit) > 0) {
			throw new BusinessException(BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES, "Consommation");
		}

		this.produitRepo.deleteById(idProduit);
	}
}
