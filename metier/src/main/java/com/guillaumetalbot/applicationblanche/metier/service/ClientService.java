package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

public interface ClientService {

	Client chargerClientAvecAdresseEtDossiersReadonly(String refClient);

	Client chargerClientReadonly(String refClient);

	Collection<Client> listerClients();

	Page<ClientDto> listerClientsDto(Pageable requete);

	String sauvegarderAdresse(String refClient, Adresse adresse);

	String sauvegarderClient(String ref, String nom);

	String sauvegarderDemande(String refDossier, Demande demande);

	String sauvegarderDossier(String refClient, Dossier dossier);

}