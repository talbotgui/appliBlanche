package com.guillaumetalbot.applicationblanche.rest.dto;

public class ElementMonitoring {

	private String clef;

	private Double nbAppels;
	private Double tempsCumule;
	private Double tempsMax;
	private Double tempsMin;
	private Double tempsMoyen;

	public ElementMonitoring() {
		super();
	}

	public ElementMonitoring(final String clef, final Double nbAppels, final Double tempsMin, final Double tempsMoyen, final Double tempsMax,
			final Double tempsCumule) {
		super();
		this.clef = clef;
		this.nbAppels = nbAppels;
		this.tempsMin = tempsMin;
		this.tempsMoyen = tempsMoyen;
		this.tempsMax = tempsMax;
		this.tempsCumule = tempsCumule;
	}

	public String getClef() {
		return this.clef;
	}

	public Double getNbAppels() {
		return this.nbAppels;
	}

	public Double getTempsCumule() {
		return this.tempsCumule;
	}

	public Double getTempsMax() {
		return this.tempsMax;
	}

	public Double getTempsMin() {
		return this.tempsMin;
	}

	public Double getTempsMoyen() {
		return this.tempsMoyen;
	}
}
