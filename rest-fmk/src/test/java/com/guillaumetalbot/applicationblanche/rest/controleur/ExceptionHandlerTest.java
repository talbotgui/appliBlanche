package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.rest.controleur.test.ControlerTestUtil;

public class ExceptionHandlerTest extends BaseTestClass {

	@Test
	public void test01MissingServletRequestParameterException() {

		// ARRANGE

		// ACT : appel à une méthode attendant un paramètre dans la requête
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest();
		final Map<String, Object> uriVars = new HashMap<String, Object>();
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/roles", requestParam, Void.class, uriVars);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(HttpClientErrorException.BadRequest.class, thrown.getClass());
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), ((HttpClientErrorException) thrown).getRawStatusCode());
		Mockito.verifyNoMoreInteractions(this.getListeServices());
	}
}
