package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Classe permettant le passage des paramètres JWT entre les composants Java */
@Component
public class ParametresJwt {

	@Value("${security.jwt.expirationTime}")
	private long expirationTime;

	@Value("${security.jwt.headerKey}")
	private String headerKey;

	@Value("${security.jwt.secret}")
	private String secret;

	@Value("${security.jwt.tokenPrefix}")
	private String tokenPrefix;

	@Value("${security.jwt.urlConnexion}")
	private String urlConnexion;

	public long getExpirationTime() {
		return this.expirationTime;
	}

	public String getHeaderKey() {
		return this.headerKey;
	}

	public String getSecret() {
		return this.secret;
	}

	public String getTokenPrefix() {
		return this.tokenPrefix;
	}

	public String getUrlConnexion() {
		return this.urlConnexion;
	}
}
