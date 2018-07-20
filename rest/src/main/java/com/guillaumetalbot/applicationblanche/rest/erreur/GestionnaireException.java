package com.guillaumetalbot.applicationblanche.rest.erreur;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guillaumetalbot.applicationblanche.exception.BaseException;
import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.exception.ExceptionId.ExceptionLevel;
import com.guillaumetalbot.applicationblanche.exception.RestException;

@ControllerAdvice
public class GestionnaireException {

	private static final Logger LOG = LoggerFactory.getLogger(GestionnaireException.class);

	@ResponseBody
	@ExceptionHandler({ BusinessException.class, RestException.class })
	public ResponseEntity<Object> creerReponsePourExceptionDuProjet(final HttpServletRequest req, final BaseException e) {

		// Log de l'erreur
		if (ExceptionLevel.INFORMATION.equals(e.getExceptionId().getLevel())) {
			LOG.info("Erreur traitée sur la requête {}", req.getRequestURI());
		} else if (ExceptionLevel.WARNING.equals(e.getExceptionId().getLevel())) {
			LOG.warn("Erreur traitée sur la requête {}", req.getRequestURI(), e);
		} else {
			LOG.error("Erreur traitée sur la requête {}", req.getRequestURI(), e);
		}

		// Transformation de l'exception en un objet transportable
		// Ou une String du message à défaut
		final String erreur = e.toJson();

		return new ResponseEntity<Object>(erreur, HttpStatus.valueOf(e.getExceptionId().getHttpStatusCode()));
	}
}
