package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.rest.dto.ElementMonitoring;
import com.jamonapi.MonitorFactory;

public class MonitoringRestControlerTest extends BaseTestClass {

	@Test
	public void test01GetData() {

		// ARRANGE (la seule ligne attendue est celle du controlleur
		MonitorFactory.getMap().entrySet().clear();
		final int nbLignesDuMonitoringAttendus = 1;

		// ACT
		final ParameterizedTypeReference<Collection<ElementMonitoring>> typeRetour = new ParameterizedTypeReference<Collection<ElementMonitoring>>() {
		};
		final ResponseEntity<Collection<ElementMonitoring>> response = this.getREST().exchange(this.getURL() + "/monitoring", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().size(), nbLignesDuMonitoringAttendus);
	}
}
