package com.guillaumetalbot.applicationblanche.metier.entite;

public interface IdentifiableParReference {

	/**
	 * Retourne une référence unique à cette instance à partir de sa classe et de son identifiant unique.
	 *
	 * Cette méthode ne doit pas utiliser d'attributs modifiables pour permettre de réutiliser une même référence pour pointer sur un même objet avec
	 * plusieurs semaines d'écart entre les deux appels.
	 *
	 * @return
	 */
	String getReference();

	/**
	 * Initialise l'identifiant unique à partir de la référence.
	 *
	 * Cette méthode ne doit pas utiliser d'attributs modifiables pour permettre de réutiliser une même référence pour pointer sur un même objet avec
	 * plusieurs semaines d'écart entre les deux appels.
	 *
	 * @param reference
	 */
	void setReference(String reference);
}
