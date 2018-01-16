package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.AdresseRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.ClientRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.DemandeRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.DossierRepository;
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
	public Long sauvegarderAdresse(final Long idClient, final Adresse adresse) {

		// Chargement du client avec son adresse
		final Client client = this.clientRepo.chargerClientAvecAdresse(idClient);

		// Validation des données
		if (client == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", idClient);
		}
		if ((adresse.getId() != null) && ((client.getAdresse() == null) || !client.getAdresse().getId().equals(adresse.getId()))) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Adresse", adresse.getId());
		}

		// Modification des données
		client.setAdresse(adresse);

		// Sauvegardes
		this.adresseRepo.save(adresse);
		this.clientRepo.save(client);

		return adresse.getId();
	}

	@Override
	public Long sauvegarderClient(final Long id, final String nom) {
		Client client;
		if (id == null) {
			client = new Client(nom);
		} else {
			client = this.clientRepo.findOne(id);
			if (client == null) {
				throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", id);
			}
			client.setNom(nom);
		}
		this.clientRepo.save(client);
		return client.getId();
	}

	@Override
	public Long sauvegarderDemande(final Long idDossier, final Demande demande) {
		// Validation existance du dossier
		final Dossier dossier = this.dossierRepo.findOne(idDossier);
		if (dossier == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Dossier", idDossier);
		}

		// Création de la demande
		if (demande.getId() == null) {
			demande.setDossier(dossier);
		}

		// mAj
		else {
			final Demande demandeExistante = this.demandeRepo.chargerDemandeAvecDossier(demande.getId());
			if ((demandeExistante == null) || (demandeExistante.getDossier() == null) || !demandeExistante.getDossier().getId().equals(idDossier)) {
				throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Demande", demande.getId());
			}
		}

		// Sauvegarde
		this.demandeRepo.save(demande);
		return demande.getId();
	}

	@Override
	public Long sauvegarderDossier(final Long idClient, final Dossier dossier) {

		// Validation existance du client
		final Client client = this.clientRepo.findOne(idClient);
		if (client == null) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Client", idClient);
		}

		// Création du dossier
		if (dossier.getId() == null) {
			dossier.setDateCreation(new Date());
			dossier.setClient(client);
		}

		// mAj
		else {
			final Dossier dossierExistant = this.dossierRepo.chargerDossierAvecClient(dossier.getId());
			if ((dossierExistant == null) || (dossierExistant.getClient() == null) || !dossierExistant.getClient().getId().equals(idClient)) {
				throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Dossier", dossier.getId());
			}
		}

		// Sauvegarde
		this.dossierRepo.save(dossier);
		return dossier.getId();
	}
}
