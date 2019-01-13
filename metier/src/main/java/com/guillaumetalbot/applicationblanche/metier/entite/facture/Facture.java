package com.guillaumetalbot.applicationblanche.metier.entite.facture;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@Entity
public class Facture extends Entite {
	private static final long serialVersionUID = 1L;

	private Long numero;

	@Lob
	private byte[] pdf;

	@ManyToOne(fetch = FetchType.LAZY)
	private Reservation reservation;

	public Facture() {
		super();
	}

	public Facture(final Long numero, final byte[] pdf, final Reservation reservation) {
		super();
		this.numero = numero;
		this.pdf = pdf;
		this.reservation = reservation;
	}

	public Long getNumero() {
		return this.numero;
	}

	public byte[] getPdf() {
		return this.pdf;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setNumero(final Long numero) {
		this.numero = numero;
	}

	public void setPdf(final byte[] pdf) {
		this.pdf = pdf;
	}

	public void setReservation(final Reservation reservation) {
		this.reservation = reservation;
	}

}
