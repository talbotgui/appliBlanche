package com.guillaumetalbot.applicationblanche.metier.dto;

public class FactureDto {

	private Double montantTotal;

	private byte[] pdf;

	public FactureDto() {
		super();
	}

	public FactureDto(final Double montantTotal, final byte[] pdf) {
		super();
		this.montantTotal = montantTotal;
		this.pdf = pdf;
	}

	public Double getMontantTotal() {
		return this.montantTotal;
	}

	public byte[] getPdf() {
		return this.pdf;
	}

	public void setMontantTotal(final Double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public void setPdf(final byte[] pdf) {
		this.pdf = pdf;
	}

}
