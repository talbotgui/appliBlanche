package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.facture.FactureRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.reservation.ReservationRepository;
import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.facture.Facture;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@Service
public class FactureServiceImpl implements FactureService {

	@Autowired
	private ExportService exportService;

	@Autowired
	private FactureRepository factureRepo;

	@Autowired
	private ReservationRepository reservationRepo;

	@Override
	public FactureDto facturer(final String referenceReservation) {
		// Valider la référence
		final Long idReservation = Entite.extraireIdentifiant(referenceReservation, Reservation.class);

		// Charger la réservation
		final Reservation reservation = this.reservationRepo.chargerReservationFetchChambreFormuleOptionsConsommationManaged(idReservation)//
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "reservation", referenceReservation));

		// Prochain numéro de facture
		final Long numeroDeFactureSuivant = this.genererNumeroDeFacture();

		// Generation du pdf
		final byte[] pdf = this.exportService.genererPdfFactureReservation(reservation, numeroDeFactureSuivant);

		// Changement d'état de la réservation (update assuré car l'objet est MANAGED dans la session Hibernate)
		reservation.changerEtatCourant(EtatReservation.FACTUREE);

		// Creation de la facture
		this.factureRepo.save(new Facture(numeroDeFactureSuivant, pdf, reservation));

		// renvoi de l'objet
		final String pdfEnString = Base64.getEncoder().encodeToString(pdf);
		return new FactureDto(reservation.calculerMontantTotal(), reservation.calculerMontantRestantDu(), pdfEnString);
	}

	/**
	 * Générer un nouveau numero de facture
	 *
	 * @return
	 */
	private Long genererNumeroDeFacture() {
		Long numeroSuivant = this.factureRepo.getMaximumNumeroActuel();
		if (numeroSuivant == null) {
			numeroSuivant = 1L;
		} else {
			numeroSuivant++;
		}
		return numeroSuivant;
	}
}
