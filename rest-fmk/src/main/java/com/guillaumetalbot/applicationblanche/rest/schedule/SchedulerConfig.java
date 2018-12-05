package com.guillaumetalbot.applicationblanche.rest.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe de configuration activant les mécaniques de Scheduling.
 *
 * Ce package doit être intégré dans @ComponentScan de l'application pour activer cette configuration.
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

}
