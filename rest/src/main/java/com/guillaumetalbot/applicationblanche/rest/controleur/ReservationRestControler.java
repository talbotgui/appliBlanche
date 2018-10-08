package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.time.LocalDate;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;
import com.guillaumetalbot.applicationblanche.metier.service.ReservationService;

@RestController
@RequestMapping("/v1")
public class ReservationRestControler {

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/chambres")
	public Collection<Chambre> listerChambres() {
		return this.reservationService.listerChambres();
	}

	@GetMapping("/produits")
	Collection<Produit> listerProduits() {
		return this.reservationService.listerProduits();
	}

	@GetMapping("/reservations/{referenceReservation}/consommations")
	Collection<Consommation> rechercherConsommationsDuneReservation(@PathVariable("referenceReservation") final String referenceReservation) {
		return this.reservationService.rechercherConsommationsDuneReservation(referenceReservation);
	}

	@GetMapping("/reservations")
	Collection<Reservation> rechercherReservations(//
			@RequestParam(name = "dateDebut", required = true) @DateTimeFormat(iso = ISO.DATE) final LocalDate dateDebut, //
			@RequestParam(name = "dateFin", required = true) @DateTimeFormat(iso = ISO.DATE) final LocalDate dateFin) {
		return this.reservationService.rechercherReservations(dateDebut, dateFin);
	}

	@PostMapping("/reservations/{referenceReservation}/consommations")
	String sauvegarderConsommation(@PathVariable("referenceReservation") final String referenceReservation, //
			@RequestBody final Consommation consommation) {

		// TODO: controles de surface
		// TODO: controle de la ref et de ses dates vis-à-vis de la date du jour

		final String reference = this.reservationService.sauvegarderConsommation(consommation);
		return '"' + reference + '"';
	}

	@PostMapping("/produits")
	String sauvegarderProduit(@RequestBody final Produit produit) {

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

		final String reference = this.reservationService.sauvegarderProduit(produit);
		return '"' + reference + '"';
	}

	@PostMapping("/reservations")
	String sauvegarderReservation(@RequestBody final Reservation reservation) {

		// Contrôles de surface
		if (reservation.getChambre() == null || StringUtils.isEmpty(reservation.getChambre().getReference())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "chambre.reference");
		}
		if (StringUtils.isEmpty(reservation.getClient())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "client");
		}
		if (reservation.getDateDebut() == null || reservation.getDateFin() == null || reservation.getDateDebut().isAfter(reservation.getDateFin())) {
			throw new RestException(RestException.ERREUR_DEUX_PARAMETRES_INCOHERENTS, "dateDebut", "dateFin");
		}

		final String reference = this.reservationService.sauvegarderReservation(reservation);
		return '"' + reference + '"';
	}

	@DeleteMapping("/reservations/{referenceReservation}/consommations/{referenceConsommation}")
	void supprimerConsommation(@PathVariable("referenceReservation") final String referenceReservation, //
			@PathVariable("referenceConsommation") final String referenceConsommation) {
				
		// TODO: valider les references l'une vis-à-vis de l'autre
		
		// Sauvegarde
		this.reservationService.supprimerConsommation(referenceConsommation);
	}

	@DeleteMapping("/produits/{referenceProduit}")
	void supprimerProduit(@PathVariable("referenceProduit") final String referenceProduit) {
		this.reservationService.supprimerProduit(referenceProduit);
	}

	@DeleteMapping("/reservations/{referenceReservation}")
	void supprimerReservation(@PathVariable("referenceReservation") final String referenceReservation) {
		this.reservationService.supprimerReservation(referenceReservation);
	}

}
