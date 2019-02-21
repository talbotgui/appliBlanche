package com.guillaumetalbot.applicationblanche.rest.erreur;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guillaumetalbot.applicationblanche.exception.BaseException;
import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.exception.ExceptionId.ExceptionLevel;
import com.guillaumetalbot.applicationblanche.exception.RestException;

@ControllerAdvice
public class AppExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AppExceptionHandler.class);

	private static final String LOG_MESSAGE = "Erreur traitée sur la requête {}";

	private static final String LOG_MESSAGE_AVEC_MESSAGE_EXCEPTION = "Erreur traitée sur la requête {} : {}";

	@ResponseBody
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> creerReponsePourException(final HttpServletRequest req, final Exception e) {

		// Log de l'erreur
		LOG.info(LOG_MESSAGE, req.getRequestURI(), e);

		// Renvoi de la réponse
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseBody
	@ExceptionHandler({ BusinessException.class, RestException.class })
	public ResponseEntity<Object> creerReponsePourExceptionDuProjet(final HttpServletRequest req, final BaseException e) {

		// Log de l'erreur
		if (ExceptionLevel.INFORMATION.equals(e.getExceptionId().getLevel())) {
			LOG.info(LOG_MESSAGE_AVEC_MESSAGE_EXCEPTION, req.getRequestURI(), e.getMessage());
		} else if (ExceptionLevel.WARNING.equals(e.getExceptionId().getLevel())) {
			LOG.warn(LOG_MESSAGE_AVEC_MESSAGE_EXCEPTION, req.getRequestURI(), e.getMessage());
		} else {
			LOG.error(LOG_MESSAGE, req.getRequestURI(), e);
		}

		// Transformation de l'exception en un objet transportable
		// Ou une String du message à défaut
		final String erreur = e.toJson();

		return new ResponseEntity<>(erreur, HttpStatus.valueOf(e.getExceptionId().getHttpStatusCode()));
	}

	/**
	 * Uniquement pour l'exception EmptyResultDataAccessException.
	 *
	 * Cette erreur ne doit pas survenir. Le code doit s'en prémunir
	 */
	@ResponseBody
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> creerReponsePourExceptionTechniquesConnues(final HttpServletRequest req, final EmptyResultDataAccessException e) {

		// Log car cette erreur ne doit pas survenir !
		LOG.error("Cette erreur ne doit jamais survenir ! Le code doit s'en prémunir en utilisant la méthode delete et non deleteById");

		// Renvoi de la réponse
		final BaseException exceptionDeSubstitution = new BusinessException(BusinessException.OBJET_NON_EXISTANT, "inconnu", "inconnu");
		return this.creerReponsePourExceptionDuProjet(req, exceptionDeSubstitution);
	}

	/** Uniquement pour l'exception MissingServletRequestParameterException */
	@ResponseBody
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Object> creerReponsePourExceptionTechniquesConnues(final HttpServletRequest req,
			final MissingServletRequestParameterException e) {

		// Log et statut de l'erreur
		LOG.info(LOG_MESSAGE_AVEC_MESSAGE_EXCEPTION, req.getRequestURI(), e.getMessage());

		// Renvoi de la réponse
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
