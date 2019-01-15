package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.rest.controleur.test.PageablePourLesTest;
import com.guillaumetalbot.applicationblanche.rest.dto.ElementMonitoring;
import com.jamonapi.MonitorFactory;

public class MonitoringRestControlerTest extends BaseTestClass {

	@Test
	public void test01NonPagine() {

		// ARRANGE (la seule ligne attendue est celle du controlleur
		MonitorFactory.getMap().entrySet().clear();
		final int nbLignesDuMonitoringAttendus = 1;

		// ACT
		final String url = this.getURL() + "/monitoring";
		final ParameterizedTypeReference<PageablePourLesTest<ElementMonitoring>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<ElementMonitoring>>() {
		};
		final ResponseEntity<PageablePourLesTest<ElementMonitoring>> response = this.getREST().exchange(url, HttpMethod.GET, null, typeRetour);

		// ASSERT
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().getContent().size(), nbLignesDuMonitoringAttendus);
	}

	@Test
	public void test01Pagine() {

		// ARRANGE (la seule ligne attendue est celle du controlleur
		MonitorFactory.getMap().entrySet().clear();
		final int nbLignesDuMonitoringAttendus = 1;

		// ACT
		final String url = this.getURL() + "/monitoring?pageSize=2&pageNumber=0&triPar=nbAppels&ordreTri=true";
		final ParameterizedTypeReference<PageablePourLesTest<ElementMonitoring>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<ElementMonitoring>>() {
		};
		final ResponseEntity<PageablePourLesTest<ElementMonitoring>> response = this.getREST().exchange(url, HttpMethod.GET, null, typeRetour);

		// ASSERT
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().getContent().size(), nbLignesDuMonitoringAttendus);
	}
}
