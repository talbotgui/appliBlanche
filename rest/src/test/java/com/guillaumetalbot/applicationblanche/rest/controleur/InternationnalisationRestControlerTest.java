package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InternationnalisationRestControlerTest extends BaseTestClass {

	@Test
	public void test01GetLibelleFr() {
		final Map<String, String> aRetourner = new HashMap<>();
		aRetourner.put("clef1", "valeur1");
		aRetourner.put("clef2", "valeur2");
		final String langue = "fr";

		// ARRANGE
		Mockito.doReturn(aRetourner).when(this.libelleService).listerParLangue(langue);

		// ACT
		final ParameterizedTypeReference<Map<String, String>> typeRetour = new ParameterizedTypeReference<Map<String, String>>() {
		};
		final ResponseEntity<Map<String, String>> response = this.getREST().exchange(this.getURL() + "/i18n/" + langue, HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().size(), aRetourner.size());
		Mockito.verify(this.libelleService).listerParLangue(langue);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}
}
