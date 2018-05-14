package com.guillaumetalbot.applicationblanche.metier.entite;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

public class EntiteTest {

	@Test
	public void test01Ok() {
		//
		final Class<? extends IdentifiableParReference> clazz = Client.class;
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
		final Class<? extends IdentifiableParReference> clazz = Client.class;
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
		final Class<? extends IdentifiableParReference> clazz1 = Client.class;
		final Class<? extends IdentifiableParReference> clazz2 = Dossier.class;
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
		final Class<? extends IdentifiableParReference> clazz = Client.class;
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

}
