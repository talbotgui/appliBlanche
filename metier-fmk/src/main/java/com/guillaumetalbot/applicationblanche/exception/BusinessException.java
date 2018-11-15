package com.guillaumetalbot.applicationblanche.exception;

import java.io.Serializable;

import com.guillaumetalbot.applicationblanche.exception.ExceptionId.ExceptionLevel;

public class BusinessException extends BaseException {

	public static final ExceptionId ERREUR_LOGIN = new ExceptionId("ERREUR_LOGIN", "Erreur de connexion", ExceptionLevel.WARNING, 403);

	public static final ExceptionId ERREUR_LOGIN_MDP = new ExceptionId("ERREUR_LOGIN_MDP",
			"Identifiant et/ou mot de passe trop court ({{0}} caractères minimum)", ExceptionLevel.WARNING, 400);

	public static final ExceptionId ERREUR_LOGIN_VEROUILLE = new ExceptionId("ERREUR_LOGIN_VEROUILLE",
			"Erreur de connexion - le compte est verrouillé", ExceptionLevel.WARNING, 403);

	public static final ExceptionId ERREUR_ROLE_NOM = new ExceptionId("ERREUR_ROLE_NOM", "Nom du role trop court ({{0}} caractères minimum)",
			ExceptionLevel.INFORMATION, 400);

	public static final ExceptionId ERREUR_SHA = new ExceptionId("ERREUR_SHA", "Erreur de cryptage", ExceptionLevel.ERROR, 500);

	public static final ExceptionId OBJET_DEJA_EXISTANT = new ExceptionId("ERREUR_OBJET_DEJA_EXISTANT",
			"Objet de type {{0}} avec l'identifiant {{1}} déjà existant", ExceptionLevel.INFORMATION, 400);

	public static final ExceptionId OBJET_FONCTIONNELEMENT_EN_DOUBLE = new ExceptionId("ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE",
			"L'objet de type '{{0}}' existe déjà avec l'attribut '{{1}}' et la valeur '{{2}}'", ExceptionLevel.INFORMATION, 400);

	public static final ExceptionId OBJET_NON_EXISTANT = new ExceptionId("ERREUR_OBJET_NON_EXISTANT",
			"Objet de type {{0}} et de référence {{1}} inexistant", ExceptionLevel.INFORMATION, 404);

	public static final ExceptionId REFERENCE_NON_VALIDE = new ExceptionId("ERREUR_REFERENCE_NON_VALIDE", "Référence {{0}} non valide",
			ExceptionLevel.INFORMATION, 404);

	public static final ExceptionId RESERVATION_DATES_INCOHERENTES = new ExceptionId("RESERVATION_DATES_INCOHERENTES",
			"Les dates de début et fin de cette réservation ne sont pas cohérentes", ExceptionLevel.INFORMATION, 400);

	public static final ExceptionId RESERVATION_DEJA_EXISTANTE = new ExceptionId("RESERVATION_DEJA_EXISTANTE",
			"Une reservation existe déjà à ces dates", ExceptionLevel.INFORMATION, 400);
	public static final ExceptionId RESERVATION_PAS_EN_COURS = new ExceptionId("RESERVATION_PAS_EN_COURS",
			"La réservation '{{0}}' n'est pas en cours", ExceptionLevel.INFORMATION, 400);

	/** Default UID. */
	private static final long serialVersionUID = 1L;

	public static final ExceptionId SUPPRESSION_IMPOSSIBLE_OBJETS_LIES = new ExceptionId("SUPPRESSION_IMPOSSIBLE_OBJETS_LIES",
			"Suppression impossible car un ou plusieurs objets de type '{{0}}' sont liés", ExceptionLevel.INFORMATION, 400);

	public static final ExceptionId TRANSITION_ETAT_IMPOSSIBLE = new ExceptionId("TRANSITION_ETAT_IMPOSSIBLE",
			"Impossible de passer de l'état '{{0}}' à l'état '{{1}}'.", ExceptionLevel.INFORMATION, 400);

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
