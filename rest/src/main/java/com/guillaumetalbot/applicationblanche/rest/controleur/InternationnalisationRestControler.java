package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class InternationnalisationRestControler {

	@RequestMapping(value = "/i18n/{langue}", method = GET)
	public Map<String, String> chargerLibelles(@PathVariable(value = "langue") final String langue) {
		final Map<String, String> libelles = new HashMap<>();
		if ("FR".equals(langue.toUpperCase())) {
			libelles.put("connexion_titre", "Connexion");
			libelles.put("connexion_identifiant", "Identifiant :");
			libelles.put("connexion_motDePasse", "Mot de passe :");
			libelles.put("connexion_boutonConnexion", "Se connecter");
		} else {
			libelles.put("connexion_titre", "Login");
			libelles.put("connexion_identifiant", "Login :");
			libelles.put("connexion_motDePasse", "Password :");
			libelles.put("connexion_boutonConnexion", "Log in");
		}
		return libelles;
	}
}
