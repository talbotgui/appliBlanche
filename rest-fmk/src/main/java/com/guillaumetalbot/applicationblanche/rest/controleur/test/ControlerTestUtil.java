package com.guillaumetalbot.applicationblanche.rest.controleur.test;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class ControlerTestUtil {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ControlerTestUtil.class);

	/** Intercepteur pour logger chaque requête */
	private static final ClientHttpRequestInterceptor LOG_INTERCEPTOR = (request, body, execution) -> {
		if (LOG.isInfoEnabled()) {
			LOG.info("Request : {URI={}, method={}, headers={}, body={}}", request.getURI(), request.getMethod().name(), request.getHeaders(),
					new String(body));
		}
		final ClientHttpResponse response = execution.execute(request, body);
		LOG.info("Response : {code={}}", response.getStatusCode());
		return response;
	};

	public static MultiValueMap<String, Object> creeMapParamRest(final Object... params) {
		final MultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<>();
		if (params != null) {
			if (params.length % 2 != 0) {
				throw new IllegalArgumentException("Le nombre de parametres doit être pair");
			}
			for (int i = 1; i < params.length; i += 2) {
				if (!String.class.isInstance(params[i - 1])) {
					throw new IllegalArgumentException("Les parametres impair doivent être des String");
				}
				requestParam.add((String) params[i - 1], params[i]);
			}
		}
		return requestParam;
	}

	public static HttpEntity<?> creerHeaders(final String accept) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, accept);
		return new HttpEntity<>(headers);
	}

	/** Log interceptor for all HTTP requests. */
	public static List<ClientHttpRequestInterceptor> getRestInterceptors() {
		return Arrays.asList(LOG_INTERCEPTOR);
	}

	private ControlerTestUtil() {
		// Classe utilitaire
	}
}
