package com.guillaumetalbot.applicationblanche.exception;

import java.io.Serializable;

import com.guillaumetalbot.applicationblanche.exception.ExceptionId.ExceptionLevel;

public class RestException extends BaseException {

	public static final ExceptionId ERREUR_FORMAT_DATE = new ExceptionId("ERREUR_FORMAT_DATE",
			"Le format de la date est incorrect (format='{0}', valeur='{1}').", ExceptionLevel.ERROR, 400);

	public static final ExceptionId ERREUR_FORMAT_NOMBRE = new ExceptionId("ERREUR_FORMAT_NOMBRE",
			"Le format du nombre est incorrect (valeur='{0}').", ExceptionLevel.ERROR, 400);

	public static final ExceptionId ERREUR_IO = new ExceptionId("ERREUR_IO", "Erreur d'écriture durant le traitement.", ExceptionLevel.ERROR, 500);

	public static final ExceptionId ERREUR_VALEUR_PARAMETRE = new ExceptionId("ERREUR_VALEUR_PARAMETRE",
			"Le parametre '{0}' ne peut prendre que les valeurs '{1}' et pas la valeur '{2}'.", //
			ExceptionLevel.ERROR, 400);

	public static final ExceptionId OBJET_INEXISTANTE = new ExceptionId("OBJET_INEXISTANTE", "Objet inexistant", ExceptionLevel.ERROR, 404);

	/** Default UID. */
	private static final long serialVersionUID = 1L;

	public RestException(final ExceptionId pExceptionId) {
		super(pExceptionId);
	}

	public RestException(final ExceptionId pExceptionId, final Exception pNestedException) {
		super(pExceptionId, pNestedException);
	}

	public RestException(final ExceptionId pExceptionId, final Serializable... pParameters) {
		super(pExceptionId, pParameters);
	}

	public RestException(final ExceptionId pExceptionId, final Throwable pNestedException, final Serializable... pParameters) {
		super(pExceptionId, pNestedException, pParameters);
	}
}
