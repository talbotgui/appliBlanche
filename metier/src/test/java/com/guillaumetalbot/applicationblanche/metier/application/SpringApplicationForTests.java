package com.guillaumetalbot.applicationblanche.metier.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guillaumetalbot.applicationblanche.application.PackageForApplicationHelper;

/**
 * Une configuration Spring-boot pour le test. Cette classe remplace le traditionnel fichier XML.
 */
@SpringBootApplication
@EntityScan(PackageForApplicationHelper.PACKAGE_METIER_ENTITE)
@ComponentScan({ PackageForApplicationHelper.PACKAGE_METIER_SERVICE, PackageForApplicationHelper.PACKAGE_METIER_DAO })
@EnableJpaRepositories(PackageForApplicationHelper.PACKAGE_METIER_DAO)
public class SpringApplicationForTests {

}
