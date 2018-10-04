package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consomation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ReservationService {

	Collection<Chambre> listerChambres();

	Collection<Produit> listerProduits();

	Collection<Consomation> rechercherConsomationsDuneReservation(String referenceReservation);

	Collection<Reservation> rechercherReservations(LocalDate dateDebut, LocalDate dateFin);

	String sauvegarderChambre(Chambre chambre);

	String sauvegarderConsomation(Consomation consomation);

	String sauvegarderProduit(Produit produit);

	String sauvegarderReservation(Reservation reservation);

	void supprimerConsomation(String referenceConsomation);

	void supprimerProduit(String referenceProduit);

	void supprimerReservation(String referenceReservation);
}