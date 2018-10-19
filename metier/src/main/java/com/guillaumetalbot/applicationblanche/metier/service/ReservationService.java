package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ReservationService {

	void changeEtatReservation(String referenceReservation, EtatReservation etat);

	Collection<Consommation> rechercherConsommationsDuneReservation(String referenceReservation);

	Collection<Reservation> rechercherReservations(LocalDate dateDebut, LocalDate dateFin);

	String sauvegarderConsommation(Consommation consommation);

	String sauvegarderReservation(Reservation reservation);

	void supprimerConsommation(String referenceReservation, String referenceConsommation);

	void supprimerReservation(String referenceReservation);
}