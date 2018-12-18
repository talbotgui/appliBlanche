package com.guillaumetalbot.applicationblanche.metier.dto;

public class FactureDto {

	private Double montantTotal;

	private String pdf;

	public FactureDto() {
		super();
	}

	public FactureDto(final Double montantTotal, final String pdf) {
		super();
		this.montantTotal = montantTotal;
		this.pdf = pdf;
	}

	public Double getMontantTotal() {
		return this.montantTotal;
	}

	public String getPdf() {
		return this.pdf;
	}

	public void setMontantTotal(final Double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public void setPdf(final String pdf) {
		this.pdf = pdf;
	}

}
