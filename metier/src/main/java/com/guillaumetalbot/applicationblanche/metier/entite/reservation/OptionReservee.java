package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "OPTION_RESERVEE")
public class OptionReservee implements Serializable {
	private static final String SEPARATEUR_REFERENCE_DOUBLE = "|";

	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private OptionReserveeId id;

	public Option getOption() {
		if (this.id == null) {
			return null;
		} else {
			return this.id.getOption();
		}
	}

	public String getReference() {
		String referenceReservation = "";
		String referenceOption = "";
		if (this.id != null && this.id.getReservation() != null) {
			referenceReservation = this.id.getReservation().getReference();
		}
		if (this.id != null && this.id.getOption() != null) {
			referenceOption = this.id.getOption().getReference();
		}
		return referenceReservation + SEPARATEUR_REFERENCE_DOUBLE + referenceOption;

	}

	public Reservation getReservation() {
		if (this.id == null) {
			return null;
		} else {
			return this.id.getReservation();
		}
	}

	public void setOption(final Option option) {
		if (this.id == null) {
			this.id = new OptionReserveeId();
		}
		this.id.setOption(option);
	}

	public void setReference(final String reference) {
		if (reference.indexOf(SEPARATEUR_REFERENCE_DOUBLE) == -1) {
			return;
		}
		if (this.id == null) {
			this.id = new OptionReserveeId(new Reservation(), new Option());
		}
		this.id.getReservation().setReference(reference.split(SEPARATEUR_REFERENCE_DOUBLE)[0]);
		this.id.getOption().setReference(reference.split(SEPARATEUR_REFERENCE_DOUBLE)[1]);
	}

	public void setReservation(final Reservation reservation) {
		if (this.id == null) {
			this.id = new OptionReserveeId();
		}
		this.setReservation(reservation);
	}

}
