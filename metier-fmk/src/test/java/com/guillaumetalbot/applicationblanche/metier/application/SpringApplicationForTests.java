package com.guillaumetalbot.applicationblanche.metier.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Une configuration Spring-boot pour le test. Cette classe remplace le traditionnel fichier XML.
 */
@SpringBootApplication
@EntityScan("com.guillaumetalbot.applicationblanche.metier.entite")
@ComponentScan({ "com.guillaumetalbot.applicationblanche.metier.dao", "com.guillaumetalbot.applicationblanche.metier.service" })
@EnableJpaRepositories("com.guillaumetalbot.applicationblanche.metier.dao")
public class SpringApplicationForTests {

}
