package com.guillaumetalbot.applicationblanche.metier.entite;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class MutableUtilTest {

	@Test
	public void test01Date01CasNull() {
		final Date date1 = null;
		final Date date2 = MutableUtil.getMutable(date1);
		Assert.assertNull(date2);
	}

	@Test
	public void test01Date02CasNonNull() {
		final Date date1 = new Date();
		final Date date2 = MutableUtil.getMutable(date1);
		Assert.assertNotNull(date2);
		Assert.assertNotSame(date1, date2);
		Assert.assertEquals(date1.getTime(), date2.getTime());
	}

	@Test
	public void test02Set01CasNull() {
		final Set<String> set1 = null;
		final Set<String> set2 = MutableUtil.getMutable(set1);
		Assert.assertNull(set2);
	}

	@Test
	public void test02Set02CasNonNull() {
		final Set<String> set1 = new HashSet<>(Arrays.asList("string1", "string2"));
		final Set<String> set2 = MutableUtil.getMutable(set1);
		Assert.assertNotNull(set2);
		Assert.assertNotSame(set1, set2);
		Assert.assertEquals(set1.size(), set2.size());
	}
}
