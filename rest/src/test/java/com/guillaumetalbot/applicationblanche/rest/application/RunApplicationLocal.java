package com.guillaumetalbot.applicationblanche.rest.application;

/**
 * Lanceur pour l'application réelle en local.
 *
 * Cette classe étant dans src/test/java, on démarre avec l'accès aux fichiers de src/test/resources et aux classes présentes dans src/test/java
 * (GenerateurDeDonneesService par exemple).
 */
public class RunApplicationLocal {

	public static void main(final String[] args) {
		RestApplication.main(args);
	}

}
