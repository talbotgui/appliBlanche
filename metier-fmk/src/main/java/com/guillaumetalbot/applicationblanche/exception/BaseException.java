package com.guillaumetalbot.applicationblanche.exception;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * 400 : BAD REQUEST - La syntaxe de la requête est erronée.
 *
 * <p>
 * 409 : CONFLICT - La requête ne peut être traitée à l’état actuel
 * </p>
 */
public abstract class BaseException extends RuntimeException {

	private static final String MESSAGE_ERREUR_NB_PARAMETRE = "Le nombre de paramètres de l'exception ne correspond pas à celui dans le message";

	/** Default UID. */
	private static final long serialVersionUID = 1L;

	public static boolean equals(final Exception e, final ExceptionId id) {
		if (!BaseException.class.isInstance(e)) {
			return false;
		}
		return ((BaseException) e).getExceptionId().equals(id);
	}

	/** Exception identifier. */
	private final ExceptionId exceptionId;

	/** Message parameters. */
	private final Serializable[] parameters;

	/**
	 * Constructor.
	 *
	 * @param exceptionId Exception identifier.
	 */
	public BaseException(final ExceptionId exceptionId) {
		super();
		this.exceptionId = exceptionId;
		this.parameters = null;

		this.verifierNombreDeParametreParRapportAuMessage();
	}

	/**
	 * Constructor.
	 *
	 * @param pExceptionId Exception identifier.
	 * @param pParameters  Message parameters.
	 */
	public BaseException(final ExceptionId pExceptionId, final Serializable... pParameters) {
		this.exceptionId = pExceptionId;
		this.parameters = pParameters;

		this.verifierNombreDeParametreParRapportAuMessage();
	}

	/**
	 * Constructor.
	 *
	 * @param exceptionId     Exception identifier.
	 * @param nestedException Embedded exception.
	 */
	public BaseException(final ExceptionId exceptionId, final Throwable nestedException) {
		super(nestedException);
		this.exceptionId = exceptionId;
		this.parameters = null;

		this.verifierNombreDeParametreParRapportAuMessage();
	}

	/**
	 * Constructor.
	 *
	 * @param pExceptionId Exception identifier.
	 * @param pNested      Embedded exception.
	 * @param pParameters  Message parameters.
	 */
	public BaseException(final ExceptionId pExceptionId, final Throwable pNested, final Serializable... pParameters) {
		super(pNested);
		this.exceptionId = pExceptionId;
		this.parameters = pParameters;

		this.verifierNombreDeParametreParRapportAuMessage();
	}

	/**
	 * GETTER.
	 *
	 * @return Exception identifier.
	 */
	public ExceptionId getExceptionId() {
		return this.exceptionId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		String result;
		if (this.exceptionId == null) {
			result = super.getMessage();
		} else {
			String message = this.exceptionId.getDefaultMessage();
			if (message != null && this.parameters != null) {
				for (int i = 0; i < this.parameters.length; i++) {
					final String valeur = this.transformParameterToString(i);
					message = message.replace("{{" + i + "}}", valeur);
				}
			}
			result = message;
		}
		return result;
	}

	/**
	 * GETTER.
	 *
	 * @return Message parameters.
	 */
	public Object[] getParameters() {
		if (this.parameters != null) {
			return Arrays.copyOf(this.parameters, this.parameters.length);
		}
		return new Object[0];
	}

	public String toJson() {
		final JsonObjectBuilder detailsBuilder = Json.createObjectBuilder();
		int i = 0;
		for (final Object param : this.getParameters()) {
			String paramS = param.toString();
			if (Class.class.isInstance(param)) {
				paramS = ((Class<?>) param).getSimpleName();
			}
			detailsBuilder.add(Integer.toString(i), paramS);
			i++;
		}

		final JsonObject erreurJson = Json.createObjectBuilder()//
				.add("messageParDefaut", this.getMessage())//
				.add("codeException", this.getExceptionId().getId())//
				.add("httpStatus", this.getExceptionId().getHttpStatusCode())//
				.add("details", detailsBuilder.build())//
				.build();
		return erreurJson.toString();
	}

	/**
	 * Transforme le parameter[i] en string
	 *
	 * @param i L'index du paramètre
	 * @return la String
	 */
	private String transformParameterToString(final int i) {
		String valeur = "null";
		if (this.parameters[i] != null) {
			if (this.parameters[i].getClass().isArray()) {
				final List<String> valeurs = new ArrayList<>();
				for (final Object p : (Object[]) this.parameters[i]) {
					valeurs.add(p.toString());
				}
				valeur = valeurs.toString();
			} else if (Class.class.isInstance(this.parameters[i])) {
				valeur = ((Class<?>) this.parameters[i]).getSimpleName();
			} else {
				valeur = this.parameters[i].toString();
			}
		}
		return valeur;
	}

	private void verifierNombreDeParametreParRapportAuMessage() {
		final String message = this.exceptionId.getDefaultMessage();

		// Si des paramètres ont été fournis
		if (this.parameters != null) {
			final int indexDernierParametre = this.parameters.length - 1;
			final int positionDuDernierParametreUtilise = message.indexOf("{" + indexDernierParametre + "}");

			// si le message ne prevoit pas l'usage de tant de paramètres, erreur
			if (positionDuDernierParametreUtilise == -1) {
				throw new InvalidParameterException(MESSAGE_ERREUR_NB_PARAMETRE);
			}

			// Si le message attend plus de paramètres que ceux fournis
			if (message.indexOf('{', positionDuDernierParametreUtilise + 1) != -1) {
				throw new InvalidParameterException(MESSAGE_ERREUR_NB_PARAMETRE);
			}
		}

		// Si aucun paramètre n'a été fourni mais qu'il en faut, erreur
		else if (message.indexOf("{0}") != -1) {
			throw new InvalidParameterException(MESSAGE_ERREUR_NB_PARAMETRE);
		}
	}

}
