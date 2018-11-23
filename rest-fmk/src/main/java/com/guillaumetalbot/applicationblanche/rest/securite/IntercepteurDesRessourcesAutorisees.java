package com.guillaumetalbot.applicationblanche.rest.securite;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.guillaumetalbot.applicationblanche.rest.securite.jwt.AuthenticationToken;

public class IntercepteurDesRessourcesAutorisees implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(IntercepteurDesRessourcesAutorisees.class);

	/**
	 * Calcul de la clef de sécurité protégeant une méthode de controleur REST
	 *
	 * @param nomBean
	 *            Nom de la classe
	 * @param methode
	 *            Méthode à protéger
	 * @param controleursRestSuffix
	 *            Suffixe des controleurs REST
	 * @return valeur de la clef de sécurité (clef de la ressource)
	 */
	public static String calculerClefDeSecuriteDepuisMethode(final String nomBean, final Method methode, final String controleursRestSuffix) {
		return nomBean.replaceFirst(controleursRestSuffix, "") + "." + methode.getName();
	}

	/** Suffixe des controleurs REST */
	private final String controleursRestSuffix;

	public IntercepteurDesRessourcesAutorisees(final String controleursRestSuffix) {
		super();
		this.controleursRestSuffix = controleursRestSuffix;
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		// Si l'authentification est l'anonyme, c'est que la requête est autorisée à tous. Donc on laisse passer
		if (AnonymousAuthenticationToken.class.isInstance(SecurityContextHolder.getContext().getAuthentication())) {
			return true;
		}

		// Vérification du type de token d'authentification
		if (!AuthenticationToken.class.isInstance(SecurityContextHolder.getContext().getAuthentication())) {
			LOG.error("Le token d'authentification n'est ni anonyme ni celui de l'application");
			this.renvoyerUneErreurDeSecurite(response);
			return false;
		}

		// Recupération des données de l'utilsateur connecté (objet instancié dans JWTAuthenticationFilter)
		final AuthenticationToken auth = (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		if (HandlerMethod.class.isInstance(handler)) {
			final HandlerMethod hm = (HandlerMethod) handler;

			// Création de la clef de sécurité de la méthode appelée
			final String clefMethodeAppelee = calculerClefDeSecuriteDepuisMethode(hm.getBeanType().getSimpleName(), hm.getMethod(),
					this.controleursRestSuffix);

			// Vérification si l'utilisateur a l'autorisation d'accéder à cette ressource (ou si la méthode est la gestion des erreurs)
			final boolean result = auth.getRessourcesAutorisees().contains(clefMethodeAppelee.toUpperCase());

			// Log
			LOG.debug("L'utilisateur {} tente d'accéder à {} : autorisé={}", auth.getPrincipal(), clefMethodeAppelee, result);

			if (!result) {
				this.renvoyerUneErreurDeSecurite(response);
			}

			return result;
		} else {
			LOG.warn("Le HANDLER n'est pas un HandlerMethod (la route '{}' existe-t-elle bien ?", request.getRequestURI());
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		}
	}

	private void renvoyerUneErreurDeSecurite(final HttpServletResponse response) {
		response.setStatus(HttpStatus.FORBIDDEN.value());
	}

}
