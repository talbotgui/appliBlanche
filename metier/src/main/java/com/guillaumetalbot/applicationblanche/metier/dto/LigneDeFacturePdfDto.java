package com.guillaumetalbot.applicationblanche.metier.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public class LigneDeFacturePdfDto {

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("#0.00 €");

	private String categorie;
	private Boolean isPaiement;
	private String libelle;
	private String montantTotal;
	private String montantUnitaire;
	private String quantite;

	public LigneDeFacturePdfDto(final String typeDePaiement, final Double montant) {
		super();
		this.isPaiement = true;
		this.libelle = typeDePaiement;
		this.montantTotal = NUMBER_FORMATTER.format(montant);
	}

	public LigneDeFacturePdfDto(final String categorie, final String libelle, final Double montantUnitaire, final Long quantite,
			final Double montantTotal) {
		super();
		this.isPaiement = false;
		this.categorie = categorie;
		this.libelle = libelle;
		this.montantUnitaire = NUMBER_FORMATTER.format(montantUnitaire);
		this.quantite = Long.toString(quantite);
		this.montantTotal = NUMBER_FORMATTER.format(montantTotal);
	}

	public String getCategorie() {
		return this.categorie;
	}

	public Boolean getIsPaiement() {
		return this.isPaiement;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public String getMontantTotal() {
		return this.montantTotal;
	}

	public String getMontantUnitaire() {
		return this.montantUnitaire;
	}

	public String getQuantite() {
		return this.quantite;
	}

	public void setCategorie(final String categorie) {
		this.categorie = categorie;
	}

	public void setIsPaiement(final Boolean isPaiement) {
		this.isPaiement = isPaiement;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	public void setMontantTotal(final String montantTotal) {
		this.montantTotal = montantTotal;
	}

	public void setMontantUnitaire(final String montantUnitaire) {
		this.montantUnitaire = montantUnitaire;
	}

	public void setQuantite(final String quantite) {
		this.quantite = quantite;
	}

}
