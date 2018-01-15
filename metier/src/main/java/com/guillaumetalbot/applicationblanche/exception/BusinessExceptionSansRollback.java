package com.guillaumetalbot.applicationblanche.exception;

import java.io.Serializable;

public class BusinessExceptionSansRollback extends BusinessException {
	/** Default UID. */
	private static final long serialVersionUID = 1L;

	public BusinessExceptionSansRollback(final ExceptionId exceptionId) {
		super(exceptionId);
	}

	public BusinessExceptionSansRollback(final ExceptionId pExceptionId, final Serializable... pParameters) {
		super(pExceptionId, pParameters);
	}

	public BusinessExceptionSansRollback(final ExceptionId exceptionId, final Throwable nestedException) {
		super(exceptionId, nestedException);
	}

	public BusinessExceptionSansRollback(final ExceptionId pExceptionId, final Throwable pNestedException, final Serializable... pParameters) {
		super(pExceptionId, pNestedException, pParameters);
	}

}
