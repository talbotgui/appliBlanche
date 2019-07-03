package com.guillaumetalbot.applicationblanche.exception;

import java.security.InvalidParameterException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseExceptionTest {

	@Test
	public void test01MessageSansParametre01PasDeParametreFourni() {

		//

		//
		final Exception e = new BusinessException(BusinessException.ERREUR_LOGIN);

		//
		org.junit.jupiter.api.Assertions.assertNotNull(e);
	}

	@Test
	public void test01MessageSansParametre02UnParametreFourni() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.ERREUR_LOGIN, "");
		});

		//
		org.junit.jupiter.api.Assertions.assertNotNull(t);
		org.junit.jupiter.api.Assertions.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre01PasDeParametreFourni() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT);
		});

		//
		org.junit.jupiter.api.Assertions.assertNotNull(t);
		org.junit.jupiter.api.Assertions.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre02TropPeuDeParametresFournis() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1");
		});

		//
		org.junit.jupiter.api.Assertions.assertNotNull(t);
		org.junit.jupiter.api.Assertions.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre03TropDeParametresFournis() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2", "3");
		});

		//
		org.junit.jupiter.api.Assertions.assertNotNull(t);
		org.junit.jupiter.api.Assertions.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre04BonNombreDeParametresFournis() {

		//

		//
		final Exception e = new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2");

		//
		org.junit.jupiter.api.Assertions.assertNotNull(e);
	}

	@Test
	public void test03ToJson() {

		//

		//
		final BusinessException e = new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2");

		//
		org.junit.jupiter.api.Assertions.assertNotNull(e.toJson());
	}

}
