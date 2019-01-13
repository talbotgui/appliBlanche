package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.service.FactureService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
public class FactureRestControler {

	@Autowired
	private FactureService factureService;

	@PostMapping("/reservations/{referenceReservation}/facturer")
	@ApiOperation(value = "Facturer une réservation / un séjour", notes = "Changer l'état, valider les paiements et créer le document de facture")
	public FactureDto facturer(@PathVariable("referenceReservation") final String referenceReservation) {
		return this.factureService.facturer(referenceReservation);
	}
}
