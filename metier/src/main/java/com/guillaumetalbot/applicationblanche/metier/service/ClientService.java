package com.guillaumetalbot.applicationblanche.metier.service;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

public interface ClientService {

	Long sauvegarderAdresse(Long idClient, Adresse adresse);

	Long sauvegarderClient(Long id, String nom);

	Long sauvegarderDemande(Long idDossier, Demande demande);

	Long sauvegarderDossier(Long idClient, Dossier dossier);

}