package com.guillaumetalbot.applicationblanche.metier.dto;

import java.time.LocalDate;

public class LignePaiementDto {

	private final String dateCreation;

	private final String montant;

	private final String moyenDePaiement;

	public LignePaiementDto(final LocalDate dateCreation, final Double montant, final String moyenDePaiement) {
		super();
		this.dateCreation = dateCreation.format(LigneDeFacturePdfDto.DATE_FORMATTER);
		this.montant = LigneDeFacturePdfDto.NUMBER_FORMATTER.format(montant);
		this.moyenDePaiement = moyenDePaiement;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public String getMontant() {
		return this.montant;
	}

	public String getMoyenDePaiement() {
		return this.moyenDePaiement;
	}

}
