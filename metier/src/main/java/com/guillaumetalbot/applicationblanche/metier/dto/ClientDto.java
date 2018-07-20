package com.guillaumetalbot.applicationblanche.metier.dto;

import java.time.LocalDateTime;

import com.guillaumetalbot.applicationblanche.metier.entite.IdentifiableParReference;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;

public class ClientDto extends DtoAvecReference {
	private static final long serialVersionUID = 1L;

	private LocalDateTime dateCreationDernierDossier;

	private int nbDemandes;
	private int nbDossiers;
	private String nomClient;
	private String ville;

	public ClientDto() {
		super();
	}

	public ClientDto(final Long id, final String nomClient, final String ville, final Long nbDemandes, final Long nbDossiers,
			final LocalDateTime dateCreationDernierDossier) {
		super(id);
		this.nomClient = nomClient;
		this.ville = ville;
		this.nbDemandes = nbDemandes.intValue();
		this.nbDossiers = nbDossiers.intValue();
		this.dateCreationDernierDossier = dateCreationDernierDossier;
	}

	@Override
	protected Class<? extends IdentifiableParReference> getClasseEntiteCorrespondante() {
		return Client.class;
	}

	public LocalDateTime getDateCreationDernierDossier() {
		return this.dateCreationDernierDossier;
	}

	public int getNbDemandes() {
		return this.nbDemandes;
	}

	public int getNbDossiers() {
		return this.nbDossiers;
	}

	public String getNomClient() {
		return this.nomClient;
	}

	public String getVille() {
		return this.ville;
	}

	public void setDateCreationDernierDossier(final LocalDateTime dateCreationDernierDossier) {
		this.dateCreationDernierDossier = dateCreationDernierDossier;
	}

	public void setNbDemandes(final int nbDemandes) {
		this.nbDemandes = nbDemandes;
	}

	public void setNbDossiers(final int nbDossiers) {
		this.nbDossiers = nbDossiers;
	}

	public void setNomClient(final String nomClient) {
		this.nomClient = nomClient;
	}

	public void setVille(final String ville) {
		this.ville = ville;
	}
}
