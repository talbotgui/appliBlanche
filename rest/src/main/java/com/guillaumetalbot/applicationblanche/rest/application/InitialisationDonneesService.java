package com.guillaumetalbot.applicationblanche.rest.application;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

/**
 * Ce composant s'exécute au démarrage de l'application (ApplicationListener) et initialise ou met à jour la base de données :
 *
 * création d'un utilisateur adminAsupprimer si aucun utilisateur n'existe
 *
 * et mise à jour de la liste des ressources en fonction des méthodes exposées par les controleurs REST.
 */
@Component
public class InitialisationDonneesService implements ApplicationListener<ApplicationReadyEvent> {

	/** Configuration de la sécurité par défaut. */
	public static final String ADMIN_PAR_DEFAUT_LOGIN_MDP = "adminAsupprimer";
	public static final String ADMIN_PAR_DEFAUT_ROLE = "administrateur";

	/**
	 * Recherche de tous les controleurs REST et des méthodes qu'ils exposent.
	 *
	 * @param applicationContext
	 *
	 * @return
	 */
	private static Collection<String> listerMethodesDeControleurs(final ApplicationContext applicationContext) {
		final Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Controller.class);
		final Collection<String> clefsRessources = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : beans.entrySet()) {
			final String nomClasse = entry.getKey() + ".";
			final Object bean = entry.getValue();
			if (!RestApplication.PACKAGE_REST_CONTROLEUR.equals(bean.getClass().getPackage().getName())) {
				continue;
			}
			for (final Method methode : entry.getValue().getClass().getDeclaredMethods()) {
				clefsRessources.add(nomClasse + methode.getName());
			}
		}
		return clefsRessources;
	}

	@Autowired
	private SecuriteService securiteService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent app) {

		final Collection<String> clefsRessources = listerMethodesDeControleurs(app.getApplicationContext());

		this.securiteService.initialiserOuCompleterConfigurationSecurite(clefsRessources, ADMIN_PAR_DEFAUT_LOGIN_MDP, ADMIN_PAR_DEFAUT_LOGIN_MDP,
				ADMIN_PAR_DEFAUT_ROLE);
	}
}
