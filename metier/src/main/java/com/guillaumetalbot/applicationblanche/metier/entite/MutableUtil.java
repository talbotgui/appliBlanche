package com.guillaumetalbot.applicationblanche.metier.entite;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MutableUtil {

	public static Date getMutable(final Date date) {
		if (date == null) {
			return null;
		} else {
			return new Date(date.getTime());
		}
	}

	public static <T> Set<T> getMutable(final Set<T> set) {
		if (set == null) {
			return null;
		} else {
			return new HashSet<T>(set);
		}
	}

	private MutableUtil() {
		// Pour que cette classe ne soit pas instanciée
	}
}
