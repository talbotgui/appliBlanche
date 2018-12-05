package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;
import com.guillaumetalbot.applicationblanche.metier.service.ReservationService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;
import com.guillaumetalbot.applicationblanche.rest.socket.SocketService;

@RestController
@RequestMapping("/v1")
public class ReservationRestControler {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private SocketService socketService;

	@PutMapping("/reservations/{referenceReservation}/etat")
	public void changeEtatReservation(@PathVariable("referenceReservation") final String referenceReservation,
			@RequestParam(name = "etat", required = true) final EtatReservation etat) {
		this.reservationService.changeEtatReservation(referenceReservation, etat);
	}

	@PostMapping("/reservations/{referenceReservation}/facturer")
	public FactureDto facturer(@PathVariable("referenceReservation") final String referenceReservation) {
		return this.reservationService.facturer(referenceReservation);
	}

	@PutMapping("/reservations/{referenceReservation}/consommations/{referenceConsommation}")
	public void modiferConsommation(@PathVariable("referenceReservation") final String referenceReservation, //
			@PathVariable("referenceConsommation") final String referenceConsommation, //
			@RequestParam("quantite") final Integer quantite) {
		this.reservationService.modifierQuantiteConsommation(referenceReservation, referenceConsommation, quantite);
	}

	@GetMapping("/reservations/{referenceReservation}/consommations")
	public Collection<Consommation> rechercherConsommationsDuneReservation(@PathVariable("referenceReservation") final String referenceReservation) {
		return this.reservationService.rechercherConsommationsDuneReservation(referenceReservation);
	}

	@GetMapping("/reservations")
	public Collection<Reservation> rechercherReservations(//
			@RequestParam(name = "dateDebut", required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate dateDebut, //
			@RequestParam(name = "dateFin", required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate dateFin, //
			@RequestParam(name = "etat", required = false) final EtatReservation etat, @RequestHeader("Accept") final String accept) {

		// L'entete permet de définir la profondeur des données recherchées
		final boolean toutesLesDonnees = RestControlerUtils.MIME_JSON_DETAILS.equals(accept);

		// recherche par date sans etat
		if (dateDebut != null && dateFin != null) {
			return this.reservationService.rechercherReservations(dateDebut, dateFin);
		}

		// recherche par état
		else if (etat != null) {
			return this.reservationService.rechercherReservations(etat, toutesLesDonnees);
		}

		// Sinon, aucune données en retour
		else {
			return new ArrayList<>();
		}
	}

	@PostMapping("/reservations/{referenceReservation}/consommations")
	public String sauvegarderConsommation(@PathVariable("referenceReservation") final String referenceReservation, //
			@RequestBody final Consommation consommation) {

		// Controles de surface
		if (consommation.getProduit() == null || StringUtils.isEmpty(consommation.getProduit().getReference())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "produit");
		}
		if (consommation.getQuantite() == null || consommation.getQuantite() <= 0) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "quantite");
		}

		// Si la référence de réservation n'est pas dans la consommation
		if (consommation.getReservation() == null) {
			consommation.setReservation(new Reservation());
		}
		if (StringUtils.isEmpty(consommation.getReservation().getReference())) {
			consommation.getReservation().setReference(referenceReservation);

		}

		final String reference = this.reservationService.sauvegarderConsommation(consommation);
		return '"' + reference + '"';
	}

	@PostMapping("/reservations")
	public String sauvegarderReservation(@RequestBody final Reservation reservation) {

		// Contrôles de surface
		if (reservation.getNombrePersonnes() == null || reservation.getNombrePersonnes() == 0) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "nombrePersonnes");
		}
		if (reservation.getChambre() == null || StringUtils.isEmpty(reservation.getChambre().getReference())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "chambre.reference");
		}
		if (reservation.getFormule() == null || StringUtils.isEmpty(reservation.getFormule().getReference())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "formule");
		}
		if (StringUtils.isEmpty(reservation.getClient())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "client");
		}
		if (reservation.getDateDebut() == null || reservation.getDateFin() == null || reservation.getDateDebut().isAfter(reservation.getDateFin())) {
			throw new RestException(RestException.ERREUR_DEUX_PARAMETRES_INCOHERENTS, "dateDebut", "dateFin");
		}

		// Sauvegarde de la réservation
		final String reference = this.reservationService.sauvegarderReservation(reservation);

		// Notification de tous les navigateurs connectés
		this.socketService.envoyerUnMessage(SocketConstantes.TOPIC_CREATION_RESERVATION, reservation);

		// Renvoi de la référence
		return '"' + reference + '"';
	}

	@DeleteMapping("/reservations/{referenceReservation}/consommations/{referenceConsommation}")
	public void supprimerConsommation(@PathVariable("referenceReservation") final String referenceReservation, //
			@PathVariable("referenceConsommation") final String referenceConsommation) {
		this.reservationService.supprimerConsommation(referenceReservation, referenceConsommation);
	}

	@DeleteMapping("/reservations/{referenceReservation}")
	public void supprimerReservation(@PathVariable("referenceReservation") final String referenceReservation) {
		this.reservationService.supprimerReservation(referenceReservation);
	}

}
