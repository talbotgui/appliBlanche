package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ReservationService {

	Collection<Chambre> listerChambres();

	Collection<Produit> listerProduits();

	Collection<Consommation> rechercherConsommationsDuneReservation(String referenceReservation);

	Collection<Reservation> rechercherReservations(LocalDate dateDebut, LocalDate dateFin);

	String sauvegarderChambre(Chambre chambre);

	String sauvegarderConsommation(Consommation consommation);

	String sauvegarderProduit(Produit produit);

	String sauvegarderReservation(Reservation reservation);

	void supprimerChambre(final String reference);

	void supprimerConsommation(String referenceConsommation);

	void supprimerProduit(String referenceProduit);

	void supprimerReservation(String referenceReservation);
}