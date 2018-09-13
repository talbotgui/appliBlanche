package com.guillaumetalbot.applicationblanche.metier.entite;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;

public class EntiteTest {

	@Test
	public void test01Ok() {
		//
		final Class<? extends IdentifiableParReference> clazz = EntiteDeTest1.class;
		final Long id = 1L;

		//
		final String reference = Entite.genererReference(clazz, id);
		final Long idRetourne = Entite.extraireIdentifiant(reference, clazz);

		//
		Assert.assertEquals(id, idRetourne);
	}

	@Test
	public void test02Ko01PasLeBonFormat() {
		//
		final Class<? extends IdentifiableParReference> clazz = EntiteDeTest1.class;
		final String reference = "azertyuiop";

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			Entite.extraireIdentifiant(reference, clazz);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(BusinessException.class, thrown.getClass());
		Assert.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
	}

	@Test
	public void test02Ko02PasLaBonneClasse() {
		//
		final Class<? extends IdentifiableParReference> clazz1 = EntiteDeTest1.class;
		final Class<? extends IdentifiableParReference> clazz2 = EntiteDeTest2.class;
		final Long id = 1L;

		//
		final String reference = Entite.genererReference(clazz1, id);
		final Throwable thrown = Assertions.catchThrowable(() -> {
			Entite.extraireIdentifiant(reference, clazz2);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(BusinessException.class, thrown.getClass());
		Assert.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
	}

	@Test
	public void test02Ko03IdentifiantInvalide() {
		//
		final Class<? extends IdentifiableParReference> clazz = EntiteDeTest1.class;
		final Long id = -1L;

		//
		final String reference = Entite.genererReference(clazz, id);
		final Throwable thrown = Assertions.catchThrowable(() -> {
			Entite.extraireIdentifiant(reference, clazz);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(BusinessException.class, thrown.getClass());
		Assert.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
	}

	@Test
	public void test03InjectionChaineDansReference() {
		//
		final Class<? extends IdentifiableParReference> clazz = EntiteDeTest1.class;
		final Long id = 100L;

		//
		final String referenceModifiee = "aa" + Entite.genererReference(clazz, id) + "aa";
		final Throwable thrown = Assertions.catchThrowable(() -> {
			Entite.extraireIdentifiant(referenceModifiee, clazz);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(BusinessException.class, thrown.getClass());
		Assert.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
	}

}