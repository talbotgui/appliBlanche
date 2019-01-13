package com.guillaumetalbot.applicationblanche.metier.service;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ExportService {

	byte[] genererPdfFactureReservation(Reservation reservation, Long numeroDeFacture);

}
