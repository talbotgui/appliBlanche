package com.guillaumetalbot.applicationblanche.metier.dto;

public class FactureDto {

	private Double montantRestantDu;
	private Double montantTotal;

	private String pdf;

	public FactureDto() {
		super();
	}

	public FactureDto(final Double montantTotal, final Double montantRestantDu, final String pdf) {
		super();
		this.montantTotal = montantTotal;
		this.montantRestantDu = montantRestantDu;
		this.pdf = pdf;
	}

	public Double getMontantRestantDu() {
		return this.montantRestantDu;
	}

	public Double getMontantTotal() {
		return this.montantTotal;
	}

	public String getPdf() {
		return this.pdf;
	}

	public void setMontantRestantDu(final Double montantRestantDu) {
		this.montantRestantDu = montantRestantDu;
	}

	public void setMontantTotal(final Double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public void setPdf(final String pdf) {
		this.pdf = pdf;
	}

}
