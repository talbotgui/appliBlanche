package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.client.AdresseRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.client.ClientRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.client.DemandeRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.client.DossierRepository;
import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private AdresseRepository adresseRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private DemandeRepository demandeRepo;

	@Autowired
	private DossierRepository dossierRepo;

	@Override
	public Client chargerClientAvecAdresseEtDossiersReadonly(final String refClient) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);
		return this.clientRepo.chargerClientAvecAdresseEtDossiersReadonly(idClient);
	}

	@Override
	public Client chargerClientReadonly(final String refClient) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);
		return this.clientRepo.chargerClientReadonly(idClient);
	}

	@Override
	public Collection<Client> listerClients() {
		return this.clientRepo.listerClients();
	}

	@Override
	public Page<ClientDto> listerClientsDto(final Pageable requete) {
		return this.clientRepo.listerClientsDto(requete);
	}

	@Override
	public String sauvegarderAdresse(final String refClient, final Adresse adresse) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);
		final Long idAdresse = Entite.extraireIdentifiant(adresse.getReference(), Adresse.class);

		// Chargement du client avec son adresse
		final Client client = this.clientRepo.chargerClientAvecAdresse(idClient);

		// Validation des données
		if (client == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", refClient);
		}
		if (adresse.getReference() != null && (client.getAdresse() == null || !client.getAdresse().getId().equals(idAdresse))) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Adresse", adresse.getReference());
		}

		// Modification des données
		client.setAdresse(adresse);

		// Sauvegardes
		this.adresseRepo.save(adresse);
		this.clientRepo.save(client);

		return adresse.getReference();
	}

	@Override
	public String sauvegarderClient(final String refClient, final String nom) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);

		Client client;
		if (idClient == null) {
			client = new Client(nom);
		} else {
			client = this.clientRepo.findById(idClient)
					.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", refClient));
			client.setNom(nom);
		}
		this.clientRepo.save(client);
		return client.getReference();
	}

	@Override
	public String sauvegarderDemande(final String refDossier, final Demande demande) {
		final Long idDossier = Entite.extraireIdentifiant(refDossier, Dossier.class);
		final Long idDemande = Entite.extraireIdentifiant(demande.getReference(), Demande.class);

		// Validation existance du dossier
		final Dossier dossier = this.dossierRepo.findById(idDossier)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Dossier", refDossier));

		// Création de la demande
		if (idDemande == null) {
			demande.setDossier(dossier);
		}

		// mAj
		else {
			final Demande demandeExistante = this.demandeRepo.chargerDemandeAvecDossier(idDemande);
			if (demandeExistante == null || demandeExistante.getDossier() == null || !demandeExistante.getDossier().getId().equals(idDossier)) {
				throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Demande", demande.getReference());
			}
		}

		// Sauvegarde
		this.demandeRepo.save(demande);
		return demande.getReference();
	}

	@Override
	public String sauvegarderDossier(final String refClient, final Dossier dossier) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);
		final Long idDossier = Entite.extraireIdentifiant(dossier.getReference(), Dossier.class);

		// Validation existance du client
		final Client client = this.clientRepo.findById(idClient)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", refClient));

		// Création du dossier
		if (idDossier == null) {
			dossier.setDateCreation(LocalDateTime.now());
			dossier.setClient(client);
		}

		// mAj
		else {
			final Dossier dossierExistant = this.dossierRepo.chargerDossierAvecClient(idDossier);
			if (dossierExistant == null || dossierExistant.getClient() == null || !dossierExistant.getClient().getId().equals(idClient)) {
				throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Dossier", dossier.getId());
			}
		}

		// Sauvegarde
		this.dossierRepo.save(dossier);
		return dossier.getReference();
	}

	@Override
	public void supprimerClient(final String refClient) {
		final Long idClient = Entite.extraireIdentifiant(refClient, Client.class);
		this.clientRepo.deleteById(idClient);
	}
}
