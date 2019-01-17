package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.HashMap;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public class FactureRestControlerTest extends BaseTestClass {

	@Test
	public void test01Facturer() {

		// ARRANGE
		final String ref = Entite.genererReference(Reservation.class, 1L);
		final FactureDto toReturn = new FactureDto(2.2, 2.2, "unPdf");
		Mockito.doReturn(toReturn).when(this.factureService).facturer(ref);

		// ACT
		final FactureDto dto = this.getREST().postForObject(this.getURL() + "/v1/reservations/" + ref + "/facturer", null, FactureDto.class,
				new HashMap<String, String>());

		// ASSERT
		Mockito.verify(this.factureService).facturer(ref);
		Mockito.verifyNoMoreInteractions(this.factureService);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getPdf(), toReturn.getPdf());
		Assert.assertEquals(dto.getMontantRestantDu(), toReturn.getMontantRestantDu());
		Assert.assertEquals(dto.getMontantTotal(), toReturn.getMontantTotal());
	}
}
