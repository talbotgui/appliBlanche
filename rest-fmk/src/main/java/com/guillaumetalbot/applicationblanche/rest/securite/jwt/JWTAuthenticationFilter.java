package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Filtre de sécurité vérifiant la présence d'un token JWT
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

	private static final String ERROR_SERVLET_PATH = "/error";

	private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private final ParametresJwt parametresJwt;

	public JWTAuthenticationFilter(final ParametresJwt parametresJwt) {
		super();
		this.parametresJwt = parametresJwt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequete = (HttpServletRequest) request;

		// Lecture de l'IP
		String ipClient = httpRequete.getHeader(HttpHeaders.X_FORWARDED_FOR);
		if (ipClient == null) {
			ipClient = httpRequete.getRemoteAddr();
		}

		// Récupération de la description de l'utilisateur connecté
		String token = httpRequete.getHeader(this.parametresJwt.getHeaderKey());
		if (token != null) {
			token = token.replace(this.parametresJwt.getTokenPrefix(), "");
			LOG.info("Requête {} avec un token JWT en entête (IP = {})", httpRequete.getServletPath(), ipClient);

			try {
				// parse du token
				final Jws<Claims> jws = Jwts.parser().setSigningKey(this.parametresJwt.getSecret()).parseClaimsJws(token);
				final String login = jws.getBody().getSubject();
				final Collection<String> ressourcesAutorisees = jws.getBody().get(JWTConnexionFilter.JWSTOKEN_KEY_RESSOURCES_AUTORISEES,
						ArrayList.class);
				final String ipClientAlaConnexion = jws.getBody().get(JWTConnexionFilter.JWSTOKEN_KEY_IP_CLIENT, String.class);

				// Si on a un login, que l'IP est bonne et que la ressource est autorisée
				if (login != null && ipClient.equals(ipClientAlaConnexion)) {
					// Sauvegarde de cette information dans le contexte de la requête
					final Authentication authentication = new AuthenticationToken(login, null, ressourcesAutorisees);
					SecurityContextHolder.getContext().setAuthentication(authentication);

					LOG.info("Requête {} avec le token JWT de {} et ses {} ressources autorisées", httpRequete.getServletPath(), login,
							ressourcesAutorisees.size());

				} else {
					LOG.warn("Requête {} avec token invalide en entête (pas de login)", httpRequete.getServletPath());
				}

			} catch (final JwtException e) {
				// En cas d'erreur de parse du token
				LOG.warn("Requête {} avec token invalide en entête ({})", httpRequete.getServletPath(), e.getMessage());
			}
		}

		// En cas d'erreur, on laisse faire Spring
		else if (ERROR_SERVLET_PATH.equals(httpRequete.getServletPath())) {
			LOG.debug("Requête {}", httpRequete.getServletPath());
		}

		else if (HttpMethod.OPTIONS.name().equals(httpRequete.getMethod())) {
			LOG.debug("Requête OPTIONS {}", httpRequete.getServletPath());
		}

		// On aurait du passer dans les autres cas
		else {
			SecurityContextHolder.getContext().setAuthentication(null);
			LOG.info("Requête {} sans token JWT en entête", httpRequete.getServletPath());
		}

		// Si la requête est celle permettant de vérifier que l'utilisateur est connecté (GET sur '/v1/utilisateurs/moi')
		// on renvoi 204 s'il n'est pas connecté
		if (HttpMethod.GET.name().equals(httpRequete.getMethod()) && "/v1/utilisateurs/moi".equals(httpRequete.getServletPath())
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			((HttpServletResponse) response).setStatus(HttpStatus.NO_CONTENT.value());
		}

		// Le filtre a fini son boulo. Si cette URL est protégée, Spring refusera l'accès à la ressource
		else {
			filterChain.doFilter(request, response);
		}
	}
}