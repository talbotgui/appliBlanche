package com.guillaumetalbot.applicationblanche.exception;

import java.security.InvalidParameterException;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseExceptionTest {

	@Test
	public void test01MessageSansParametre01PasDeParametreFourni() {

		//

		//
		final Exception e = new BusinessException(BusinessException.ERREUR_LOGIN);

		//
		Assert.assertNotNull(e);
	}

	@Test
	public void test01MessageSansParametre02UnParametreFourni() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.ERREUR_LOGIN, "");
		});

		//
		Assert.assertNotNull(t);
		Assert.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre01PasDeParametreFourni() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT);
		});

		//
		Assert.assertNotNull(t);
		Assert.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre02TropPeuDeParametresFournis() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1");
		});

		//
		Assert.assertNotNull(t);
		Assert.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre03TropDeParametresFournis() {

		//

		//
		final Throwable t = Assertions.catchThrowable(() -> {
			new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2", "3");
		});

		//
		Assert.assertNotNull(t);
		Assert.assertEquals(InvalidParameterException.class, t.getClass());
	}

	@Test
	public void test02MessageAvecParametre04BonNombreDeParametresFournis() {

		//

		//
		final Exception e = new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2");

		//
		Assert.assertNotNull(e);
	}

	@Test
	public void test03ToJson() {

		//

		//
		final BusinessException e = new BusinessException(BusinessException.OBJET_NON_EXISTANT, "1", "2");

		//
		Assert.assertNotNull(e.toJson());
	}

}
