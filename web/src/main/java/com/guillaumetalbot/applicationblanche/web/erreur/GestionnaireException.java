package com.guillaumetalbot.applicationblanche.web.erreur;

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
import com.guillaumetalbot.applicationblanche.exception.RestException;

@ControllerAdvice
public class GestionnaireException {

	private static final Logger LOG = LoggerFactory.getLogger(GestionnaireException.class);

	@ResponseBody
	@ExceptionHandler({ BusinessException.class, RestException.class })
	public ResponseEntity<Object> defaultErrorHandler(final HttpServletRequest req, final BaseException e) {
		LOG.error("Erreur traitée sur la requête {}", req.getRequestURI(), e);
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.valueOf(e.getExceptionId().getHttpStatusCode()));
	}
}
