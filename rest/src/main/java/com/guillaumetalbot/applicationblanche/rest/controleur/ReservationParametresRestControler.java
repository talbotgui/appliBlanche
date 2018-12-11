package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.service.ReservationParametresService;

@RestController
@RequestMapping("/v1")
public class ReservationParametresRestControler {

	@Autowired
	private ReservationParametresService reservationParametresService;

	@GetMapping("/chambres")
	public Collection<Chambre> listerChambres() {
		return this.reservationParametresService.listerChambres();
	}

	@GetMapping("/formules")
	public Collection<Formule> listerFormules() {
		return this.reservationParametresService.listerFormules();
	}

	@GetMapping("/moyensDePaiement")
	public Collection<MoyenDePaiement> listerMoyensDePaiement() {
		return this.reservationParametresService.listerMoyensDePaiement();
	}

	@GetMapping("/options")
	public Collection<Option> listerOptions() {
		return this.reservationParametresService.listerOptions();
	}

	@GetMapping("/produits")
	public Collection<Produit> listerProduits() {
		return this.reservationParametresService.listerProduits();
	}

	@PostMapping("/chambres")
	public String sauvegarderChambre(@RequestBody final Chambre chambre) {

		// Contrôles de surface
		if (StringUtils.isEmpty(chambre.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}

		final String reference = this.reservationParametresService.sauvegarderChambre(chambre);
		return '"' + reference + '"';
	}

	@PostMapping("/formules")
	public String sauvegarderFormule(@RequestBody final Formule formule) {

		// Contrôles de surface
		if (StringUtils.isEmpty(formule.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}
		if (formule.getPrixParNuit() == null || formule.getPrixParNuit() < 0) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "prixParNuit");
		}

		final String reference = this.reservationParametresService.sauvegarderFormule(formule);
		return '"' + reference + '"';
	}

	@PostMapping("/moyensDePaiement")
	public String sauvegarderMoyenDePaiement(@RequestBody final MoyenDePaiement mdp) {

		// Contrôles de surface
		if (StringUtils.isEmpty(mdp.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}

		final String reference = this.reservationParametresService.sauvegarderMoyenDePaiement(mdp);
		return '"' + reference + '"';
	}

	@PostMapping("/options")
	public String sauvegarderOption(@RequestBody final Option option) {

		// Contrôles de surface
		if (StringUtils.isEmpty(option.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}
		if (option.getPrix() == null || option.getPrix() < 0) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "prix");
		}

		// Pour être certain d'avoir une valeur
		option.setParNuit(option.getParNuit() != null && option.getParNuit());
		option.setParPersonne(option.getParPersonne() != null && option.getParPersonne());

		final String reference = this.reservationParametresService.sauvegarderOption(option);
		return '"' + reference + '"';
	}

	@PostMapping("/produits")
	public String sauvegarderProduit(@RequestBody final Produit produit) {

		// Contrôles de surface
		if (StringUtils.isEmpty(produit.getCouleur())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "couleur");
		}
		if (StringUtils.isEmpty(produit.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}
		if (produit.getPrix() == null) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "prix");
		}

		final String reference = this.reservationParametresService.sauvegarderProduit(produit);
		return '"' + reference + '"';
	}

	@DeleteMapping("/chambres/{referenceChambre}")
	public void supprimerChambre(@PathVariable("referenceChambre") final String referenceChambre) {
		this.reservationParametresService.supprimerChambre(referenceChambre);
	}

	@DeleteMapping("/formules/{referenceFormule}")
	public void supprimerFormule(@PathVariable("referenceFormule") final String referenceFormule) {
		this.reservationParametresService.supprimerFormule(referenceFormule);
	}

	@DeleteMapping("/moyensDePaiement/{referenceMoyenDePaiement}")
	public void supprimerMoyenDePaiement(@PathVariable("referenceMoyenDePaiement") final String referenceMoyenDePaiement) {
		this.reservationParametresService.supprimerMoyenDePaiement(referenceMoyenDePaiement);
	}

	@DeleteMapping("/options/{referenceOption}")
	public void supprimerOption(@PathVariable("referenceOption") final String referenceOption) {
		this.reservationParametresService.supprimerOption(referenceOption);
	}

	@DeleteMapping("/produits/{referenceProduit}")
	public void supprimerProduit(@PathVariable("referenceProduit") final String referenceProduit) {
		this.reservationParametresService.supprimerProduit(referenceProduit);
	}

}
