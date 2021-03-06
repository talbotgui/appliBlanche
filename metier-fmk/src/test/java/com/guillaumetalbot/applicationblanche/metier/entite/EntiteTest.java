package com.guillaumetalbot.applicationblanche.metier.entite;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
		org.junit.jupiter.api.Assertions.assertEquals(id, idRetourne);
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
		org.junit.jupiter.api.Assertions.assertNotNull(thrown);
		org.junit.jupiter.api.Assertions.assertEquals(BusinessException.class, thrown.getClass());
		org.junit.jupiter.api.Assertions.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
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
		org.junit.jupiter.api.Assertions.assertNotNull(thrown);
		org.junit.jupiter.api.Assertions.assertEquals(BusinessException.class, thrown.getClass());
		org.junit.jupiter.api.Assertions.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
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
		org.junit.jupiter.api.Assertions.assertNotNull(thrown);
		org.junit.jupiter.api.Assertions.assertEquals(BusinessException.class, thrown.getClass());
		org.junit.jupiter.api.Assertions.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
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
		org.junit.jupiter.api.Assertions.assertNotNull(thrown);
		org.junit.jupiter.api.Assertions.assertEquals(BusinessException.class, thrown.getClass());
		org.junit.jupiter.api.Assertions.assertTrue(BusinessException.equals((BusinessException) thrown, BusinessException.REFERENCE_NON_VALIDE));
	}

	@Test
	public void test04ChaineVide() {
		//

		//
		final Long id = Entite.extraireIdentifiant("", EntiteDeTest1.class);

		//
		org.junit.jupiter.api.Assertions.assertNull(id);
	}

	@Test
	public void test05GenererAvecNull1() {
		//
		//
		final String ref = Entite.genererReference(EntiteDeTest1.class, null);
		//
		org.junit.jupiter.api.Assertions.assertNull(ref);
	}

	@Test
	public void test06GenererAvecNull2() {
		//
		//
		final String ref = Entite.genererReference(null, 1L);
		//
		org.junit.jupiter.api.Assertions.assertNull(ref);
	}

	@Test
	public void test07BoutAboutParReference() {
		//
		final Long id = 1L;
		final String reference = Entite.genererReference(EntiteDeTest1.class, id);

		//
		final EntiteDeTest1 e = new EntiteDeTest1();
		e.setReference(reference);

		//
		org.junit.jupiter.api.Assertions.assertEquals(id, e.getId());
	}
}
