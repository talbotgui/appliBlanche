package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;

public interface ReservationParametresService {

	Collection<Chambre> listerChambres();

	Collection<Formule> listerFormules();

	Collection<Option> listerOptions();

	Collection<Produit> listerProduits();

	String sauvegarderChambre(Chambre chambre);

	String sauvegarderFormule(Formule formule);

	String sauvegarderOption(Option option);

	String sauvegarderProduit(Produit produit);

	void supprimerChambre(final String reference);

	void supprimerFormule(final String reference);

	void supprimerOption(final String reference);

	void supprimerProduit(String referenceProduit);

}