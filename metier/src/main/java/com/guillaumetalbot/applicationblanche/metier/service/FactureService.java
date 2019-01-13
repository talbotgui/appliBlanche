package com.guillaumetalbot.applicationblanche.metier.service;

import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;

public interface FactureService {

	FactureDto facturer(String referenceReservation);

}
