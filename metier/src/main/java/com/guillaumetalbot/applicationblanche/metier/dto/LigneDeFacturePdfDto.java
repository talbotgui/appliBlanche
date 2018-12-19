package com.guillaumetalbot.applicationblanche.metier.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LigneDeFacturePdfDto {

	private static final NumberFormat FORMATTER = new DecimalFormat("#0.00 €");

	private String categorie;
	private String libelle;
	private String montantTotal;
	private String montantUnitaire;
	private String quantite;

	public LigneDeFacturePdfDto(final String categorie, final String libelle, final Double montantUnitaire, final Long quantite,
			final Double montantTotal) {
		super();
		this.categorie = categorie;
		this.libelle = libelle;
		this.montantUnitaire = FORMATTER.format(montantUnitaire);
		this.quantite = Long.toString(quantite);
		this.montantTotal = FORMATTER.format(montantTotal);
	}

	public String getCategorie() {
		return this.categorie;
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
