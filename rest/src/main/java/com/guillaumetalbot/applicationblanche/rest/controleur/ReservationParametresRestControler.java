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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
public class ReservationParametresRestControler {

	@Autowired
	private ReservationParametresService reservationParametresService;

	@GetMapping("/chambres")
	@ApiOperation(value = "Lister les chambres", notes = "")
	public Collection<Chambre> listerChambres() {
		return this.reservationParametresService.listerChambres();
	}

	@GetMapping("/formules")
	@ApiOperation(value = "Lister les fomules", notes = "")
	public Collection<Formule> listerFormules() {
		return this.reservationParametresService.listerFormules();
	}

	@GetMapping("/moyensDePaiement")
	@ApiOperation(value = "Lister les moyens de paiement", notes = "")
	public Collection<MoyenDePaiement> listerMoyensDePaiement() {
		return this.reservationParametresService.listerMoyensDePaiement();
	}

	@GetMapping("/options")
	@ApiOperation(value = "Lister les options", notes = "")
	public Collection<Option> listerOptions() {
		return this.reservationParametresService.listerOptions();
	}

	@GetMapping("/produits")
	@ApiOperation(value = "Lister les produits", notes = "")
	public Collection<Produit> listerProduits() {
		return this.reservationParametresService.listerProduits();
	}

	@PostMapping("/chambres")
	@ApiOperation(value = "Sauvegarder une chambre", notes = "")
	public String sauvegarderChambre(@RequestBody final Chambre chambre) {

		// Contrôles de surface
		if (StringUtils.isEmpty(chambre.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}

		final String reference = this.reservationParametresService.sauvegarderChambre(chambre);
		return '"' + reference + '"';
	}

	@PostMapping("/formules")
	@ApiOperation(value = "Sauvegarder une formule", notes = "")
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
	@ApiOperation(value = "Sauvegarder un moyen de paiement", notes = "")
	public String sauvegarderMoyenDePaiement(@RequestBody final MoyenDePaiement mdp) {

		// Contrôles de surface
		if (StringUtils.isEmpty(mdp.getNom())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nom");
		}

		final String reference = this.reservationParametresService.sauvegarderMoyenDePaiement(mdp);
		return '"' + reference + '"';
	}

	@PostMapping("/options")
	@ApiOperation(value = "Sauvegarder une option", notes = "")
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
	@ApiOperation(value = "Sauvegarder un produit", notes = "")
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
	@ApiOperation(value = "Supprimer une chambre", notes = "")
	public void supprimerChambre(@PathVariable("referenceChambre") final String referenceChambre) {
		this.reservationParametresService.supprimerChambre(referenceChambre);
	}

	@DeleteMapping("/formules/{referenceFormule}")
	@ApiOperation(value = "Supprimer une formule", notes = "")
	public void supprimerFormule(@PathVariable("referenceFormule") final String referenceFormule) {
		this.reservationParametresService.supprimerFormule(referenceFormule);
	}

	@DeleteMapping("/moyensDePaiement/{referenceMoyenDePaiement}")
	@ApiOperation(value = "Supprimer un moyen de paiement", notes = "")
	public void supprimerMoyenDePaiement(@PathVariable("referenceMoyenDePaiement") final String referenceMoyenDePaiement) {
		this.reservationParametresService.supprimerMoyenDePaiement(referenceMoyenDePaiement);
	}

	@DeleteMapping("/options/{referenceOption}")
	@ApiOperation(value = "Supprimer une option", notes = "")
	public void supprimerOption(@PathVariable("referenceOption") final String referenceOption) {
		this.reservationParametresService.supprimerOption(referenceOption);
	}

	@DeleteMapping("/produits/{referenceProduit}")
	@ApiOperation(value = "Supprimer un produit", notes = "")
	public void supprimerProduit(@PathVariable("referenceProduit") final String referenceProduit) {
		this.reservationParametresService.supprimerProduit(referenceProduit);
	}

}
