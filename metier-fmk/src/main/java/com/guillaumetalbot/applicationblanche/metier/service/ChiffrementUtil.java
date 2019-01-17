package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;

public final class ChiffrementUtil {
	public static String encrypt(final String mdp) {
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(mdp.getBytes(StandardCharsets.UTF_8));
			return new String(md.digest(), StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new BusinessException(BusinessException.ERREUR_SHA, e);
		}
	}

	private ChiffrementUtil() {
		// pour bloquer l'instanciation
	}
}
