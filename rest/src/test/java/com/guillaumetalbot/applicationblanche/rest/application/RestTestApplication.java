package com.guillaumetalbot.applicationblanche.rest.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guillaumetalbot.applicationblanche.application.PackageForApplication;

/**
 * Une configuration Spring-boot pour l'application. Cette classe remplace le traditionnel fichier XML.
 *
 * Cette configuration est différente de RestApplication sur :
 * <ul>
 * <li>Pas de Swagger actif</li>
 * <li>Pas de filtre de sécurité actif</li>
 * <li>Le propertySource pointe sur un fichier de configuration dédié aux tests</li>
 * </ul>
 */
@SpringBootApplication
@EntityScan({ PackageForApplication.PACKAGE_METIER_ENTITE })
@ComponentScan({ RestApplication.PACKAGE_REST_CONTROLEUR, RestApplication.PACKAGE_REST_ERREUR, PackageForApplication.PACKAGE_METIER_SERVICE,
		PackageForApplication.PACKAGE_METIER_DAO })
@EnableJpaRepositories(PackageForApplication.PACKAGE_METIER_DAO)
public class RestTestApplication {

}
