package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OptionReserveeId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPTION_ID")
	private Option option;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESERVATION_ID")
	private Reservation reservation;

	public OptionReserveeId() {
		super();
	}

	public OptionReserveeId(final Reservation reservation, final Option option) {
		super();
		this.option = option;
		this.reservation = reservation;
	}

	public Option getOption() {
		return this.option;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setOption(final Option option) {
		this.option = option;
	}

	public void setReservation(final Reservation reservation) {
		this.reservation = reservation;
	}

}
