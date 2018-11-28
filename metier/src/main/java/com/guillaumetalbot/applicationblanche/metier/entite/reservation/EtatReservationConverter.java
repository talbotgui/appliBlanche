package com.guillaumetalbot.applicationblanche.metier.entite.reservation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EtatReservationConverter implements AttributeConverter<EtatReservation, Integer> {

	@Override
	public Integer convertToDatabaseColumn(final EtatReservation etat) {
		return etat.getNumero();
	}

	@Override
	public EtatReservation convertToEntityAttribute(final Integer dbData) {
		return EtatReservation.getEtatReservation(dbData);
	}

}
