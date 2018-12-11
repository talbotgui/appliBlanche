package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ReservationService {

	void changeEtatReservation(String referenceReservation, EtatReservation etat);

	FactureDto facturer(String referenceReservation);

	void modifierQuantiteConsommation(String referenceReservation, String referenceConsommation, Integer quantite);

	Collection<Consommation> rechercherConsommationsDuneReservation(String referenceReservation);

	Collection<Paiement> rechercherPaiementsDuneReservation(String referenceReservation);

	Collection<Reservation> rechercherReservations(EtatReservation etat, boolean fetchTout);

	Collection<Reservation> rechercherReservations(LocalDate dateDebut, LocalDate dateFin);

	String sauvegarderConsommation(Consommation consommation);

	String sauvegarderPaiement(Paiement paiement);

	String sauvegarderReservation(Reservation reservation);

	void supprimerConsommation(String referenceReservation, String referenceConsommation);

	void supprimerPaiement(String referenceReservation, String referencePaiement);

	void supprimerReservation(String referenceReservation);
}