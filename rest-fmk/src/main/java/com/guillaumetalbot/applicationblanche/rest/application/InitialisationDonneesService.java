package com.guillaumetalbot.applicationblanche.rest.application;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.securite.IntercepteurDesRessourcesAutorisees;

import io.swagger.annotations.ApiOperation;

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
	 * Extraction du chemin s'il y en a un.
	 * 
	 * @param methode
	 * @return
	 */
	private static String extraireCheminDeLannotation(final Method methode) {
		String chemin = null;
		final RequestMapping annotation = methode.getAnnotation(RequestMapping.class);
		final GetMapping annotationGet = methode.getAnnotation(GetMapping.class);
		final PostMapping annotationPost = methode.getAnnotation(PostMapping.class);
		final DeleteMapping annotationDelete = methode.getAnnotation(DeleteMapping.class);
		final PutMapping annotationPut = methode.getAnnotation(PutMapping.class);
		if (annotation != null) {
			chemin = annotation.method()[0].name() + " " + String.join("", annotation.value());
		} else if (annotationGet != null) {
			chemin = "Get " + String.join("", annotationGet.value());
		} else if (annotationPost != null) {
			chemin = "Post " + String.join("", annotationPost.value());
		} else if (annotationDelete != null) {
			chemin = "Delete " + String.join("", annotationDelete.value());
		} else if (annotationPut != null) {
			chemin = "Put " + String.join("", annotationPut.value());
		}
		return chemin;
	}

	/**
	 * Extraction de la description s'il y en a une.
	 *
	 * @param methode
	 * @return
	 */
	private static String extraireDescriptionDeLanotation(final Method methode) {
		String description = "";
		final ApiOperation annotationDocumentaire = methode.getAnnotation(ApiOperation.class);
		if (annotationDocumentaire != null && StringUtils.isNotEmpty(annotationDocumentaire.value())) {
			description = annotationDocumentaire.value();
			if (StringUtils.isNoneEmpty(annotationDocumentaire.notes())) {
				description += " (" + annotationDocumentaire.notes() + ")";
			}
		}
		return description;
	}

	/**
	 * Recherche de tous les controleurs REST et les méthodes qu'ils exposent.
	 *
	 * @param applicationContext        Contexte Spring
	 * @param controleursRestSuffix     Suffixe des controleurs REST
	 * @param packageDesControleursRest Liste des packages contenant des controleurs
	 *
	 * @return
	 */
	public static Collection<Ressource> listerMethodesDeControleurs(final ApplicationContext applicationContext, final String controleursRestSuffix,
			final String... packageDesControleursRest) {

		// Liste des packages contenant des controleurs
		final List<String> listepackageDesControleursRest = Arrays.asList(packageDesControleursRest);

		// Recherche des classes de controleur
		final Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Controller.class);

		// Pour chaque classe de controleur ...
		final Collection<Ressource> clefsRessources = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : beans.entrySet()) {
			final Object bean = entry.getValue();
			final Class<?> classeReelle = AopUtils.getTargetClass(bean);

			traiterLesMethodesDeLaClasse(controleursRestSuffix, listepackageDesControleursRest, clefsRessources, entry, classeReelle);
		}
		return clefsRessources;
	}

	/**
	 * Traitement d'un des bean du contexte à la recherche de méthodes de controleur REST
	 *
	 * @param controleursRestSuffix
	 * @param listepackageDesControleursRest
	 * @param clefsRessources
	 * @param entry
	 * @param classeReelle
	 */
	private static void traiterLesMethodesDeLaClasse(final String controleursRestSuffix, final List<String> listepackageDesControleursRest,
			final Collection<Ressource> clefsRessources, final Map.Entry<String, Object> entry, final Class<?> classeReelle) {

		// ... s'il est présent dans les packages qui nous intéresse ...
		if (!listepackageDesControleursRest.contains(classeReelle.getPackage().getName())) {
			return;
		}

		// ... parcours des méthodes
		for (final Method methode : classeReelle.getDeclaredMethods()) {

			// Recherche de l'URI en fonction du type d'annotation utilisée
			final String chemin = extraireCheminDeLannotation(methode);

			if (chemin != null) {
				// Recherche de la documentation
				final String description = extraireDescriptionDeLanotation(methode);

				// Création d'une clef
				final String clef = IntercepteurDesRessourcesAutorisees.calculerClefDeSecuriteDepuisMethode(entry.getKey(), methode,
						controleursRestSuffix);

				// Ajout de la clef à la liste
				clefsRessources.add(new Ressource(clef, chemin, description));
			}
		}
	}

	@Value("${security.restcontroleur.suffixe}")
	private String controleursRestSuffix;

	@Value("${security.restcontroleur.packages}")
	private String packagesDesControleursRest;

	@Autowired
	private SecuriteService securiteService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent app) {

		final Collection<Ressource> clefsRessources = listerMethodesDeControleurs(app.getApplicationContext(), this.controleursRestSuffix,
				this.packagesDesControleursRest.split(","));

		this.securiteService.initialiserOuCompleterConfigurationSecurite(clefsRessources, ADMIN_PAR_DEFAUT_LOGIN_MDP, ADMIN_PAR_DEFAUT_LOGIN_MDP,
				ADMIN_PAR_DEFAUT_ROLE);
	}
}
