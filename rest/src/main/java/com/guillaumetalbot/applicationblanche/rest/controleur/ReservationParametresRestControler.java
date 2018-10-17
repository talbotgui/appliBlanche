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

	@DeleteMapping("/produits/{referenceProduit}")
	public void supprimerProduit(@PathVariable("referenceProduit") final String referenceProduit) {
		this.reservationParametresService.supprimerProduit(referenceProduit);
	}

}
