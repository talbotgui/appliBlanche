package com.guillaumetalbot.applicationblanche.exception;

import java.io.Serializable;

import com.guillaumetalbot.applicationblanche.exception.ExceptionId.ExceptionLevel;

public class BusinessException extends BaseException {

	public static final ExceptionId ERREUR_LOGIN = new ExceptionId("ERREUR_LOGIN", "Erreur de connexion", ExceptionLevel.ERROR, 403);

	public static final ExceptionId ERREUR_LOGIN_MDP = new ExceptionId("ERREUR_LOGIN_MDP",
			"Identifiant et/ou mot de passe trop court ({0} caractères minimum)", ExceptionLevel.ERROR, 400);

	public static final ExceptionId ERREUR_LOGIN_VEROUILLE = new ExceptionId("ERREUR_LOGIN_VEROUILLE",
			"Erreur de connexion - le compte est verrouillé", ExceptionLevel.ERROR, 403);

	public static final ExceptionId ERREUR_SHA = new ExceptionId("ERREUR_SHA", "Erreur de cryptage", ExceptionLevel.ERROR, 500);

	public static final ExceptionId OBJET_DEJA_EXISTANT = new ExceptionId("OBJET_DEJA_EXISTANT",
			"Objet de type {0} avec l'identifiant {1} déjà existant", ExceptionLevel.ERROR, 400);

	public static final ExceptionId OBJET_NON_EXISTANT = new ExceptionId("OBJET_NON_EXISTANT", "Objet de type {0} avec l'identifiant {1} inexistant",
			ExceptionLevel.ERROR, 404);

	/** Default UID. */
	private static final long serialVersionUID = 1L;

	public BusinessException(final ExceptionId exceptionId) {
		super(exceptionId);
	}

	public BusinessException(final ExceptionId pExceptionId, final Serializable... pParameters) {
		super(pExceptionId, pParameters);
	}

	public BusinessException(final ExceptionId exceptionId, final Throwable nestedException) {
		super(exceptionId, nestedException);
	}

	public BusinessException(final ExceptionId pExceptionId, final Throwable pNestedException, final Serializable... pParameters) {
		super(pExceptionId, pNestedException, pParameters);
	}

}
