package com.guillaumetalbot.applicationblanche.rest.controleur.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

import com.guillaumetalbot.applicationblanche.exception.RestException;

public class RestControlerUtils {

	/**
	 * Validation des paramètres de pagination puis renvoi d'une page.
	 *
	 * Si les deux paramètres sont absents, la page retournée est nulle.
	 *
	 * @param pageSize
	 *            Taille de la page.
	 * @param pageNumber
	 *            Numéro de la page.
	 * @return Requete de pagination.
	 */
	public static Pageable creerPageSiPossible(final Integer pageSize, final Integer pageNumber) {

		// aucun paramètre fourni donc pas de pagination
		if (pageSize == null && pageNumber == null) {
			return null;
		}

		// Si un seul des paramètres manque, erreur
		if (pageSize == null || pageNumber == null || pageSize < 0 || pageNumber < 0) {
			throw new RestException(RestException.ERREUR_PARAMETRES_PAGINATION_MANQUANT);
		}

		// renvoi d'une page
		return new QPageRequest(pageNumber, pageSize);
	}

	private RestControlerUtils() {
		// Constructeur pour bloquer l'instanciation de cette classe utilitaire
	}
}
