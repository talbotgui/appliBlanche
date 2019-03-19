package com.guillaumetalbot.applicationblanche.metier.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

/**
 * Composant permettant de générer des données juste après le démarrage de l'application.
 *
 * Ce composant est dans le répertoire src/test/java du projet REST. Donc il n'est pas embarqué dans l'application packagée.
 *
 * Mais il est pris en compte au démarrage de l'application en mode développement.
 */
@Component
@Transactional
public class GenerateurDeDonneesService implements ApplicationListener<ApplicationReadyEvent> {
	/** Durée maximale d'un séjour dans le jeu de données généré. */
	private static final int DUREE_MAX_RESERVATION = 7;

	/** Durée maximale du trou entre deux séjours dans le jeu de données généré. */
	private static final int DUREE_MAX_TROU = 2;

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(GenerateurDeDonneesService.class);

	/** Nombre maximal de consommation. */
	private static final int NB_CONSOMMATIONS_MAX = 25;

	@Autowired
	private DataSource datasource;

	@Autowired
	private FactureService factureService;

	@Autowired
	private ReservationParametresService parametresService;

	@Autowired
	private ReservationService reservationService;

	/**
	 * Pour ne pas développer un service pour ce besoin, utilisation de JdbcTemplate
	 *
	 * @return
	 */
	private long compterReservationExistantes() {
		return new JdbcTemplate(this.datasource).queryForObject("select count(*) from RESERVATION", Long.class);
	}

	private void initialiserUnJeuDeDonneesDeTest() {
		// Récupération des informations nécessaires
		final Collection<Chambre> chambres = this.parametresService.listerChambres();
		final List<Formule> formules = new ArrayList<>(this.parametresService.listerFormules());
		final List<Option> options = new ArrayList<>(this.parametresService.listerOptions());
		final List<Produit> produits = new ArrayList<>(this.parametresService.listerProduits());

		// Création de réservation à partir d'aujourd'hui dans toutes les chambres en alternant les formules mais en laissant une nuit de temps en
		// temps vide (1/10 délalée par chambre)
		// Le tout sur les 60 prochains jours et depuis les 10 derniers jours
		final LocalDate dateDebutMin = LocalDate.now().minus(10, ChronoUnit.DAYS);
		final LocalDate dateFinMax = LocalDate.now().plus(60, ChronoUnit.DAYS);

		// Pour toutes les chambres
		LocalDate debut = dateDebutMin;
		LocalDate fin = debut;
		for (final Chambre c : chambres) {

			// Sur la période
			do {

				// recalcul des date
				final int trouEntreDeuxReservation = 1 + (int) (Math.random() * DUREE_MAX_TROU);
				debut = fin.plus(trouEntreDeuxReservation, ChronoUnit.DAYS);
				final int duree = 1 + (int) (Math.random() * DUREE_MAX_RESERVATION);
				fin = debut.plus(duree, ChronoUnit.DAYS);

				// Calcul des données de la réservation
				final Formule laFormule = this.selectionnerAleatoirementUneFormule(formules);
				final List<Option> lesOptions = this.selectionnerAleatoirementDesOptions(options);
				final List<Produit> lesProduits = this.selectionnerAleatoirementDesProduits(produits);
				final String nomClient = debut.getDayOfMonth() + "-" + fin.getDayOfMonth() + "-" + c.getId() + "-" + laFormule.getId() + "-"
						+ lesOptions.size();

				// Creation de la réservation
				final Reservation reservation = new Reservation(nomClient, c, debut, fin);
				reservation.setFormule(laFormule);
				reservation.addOption(lesOptions);
				reservation.setNombrePersonnes((long) (1 + Math.random() * 2));

				// Sauvegarde de la réservation
				final String referenceReservation = this.reservationService.sauvegarderReservation(reservation);

				// Si la date de début est passée
				if (debut.isBefore(LocalDate.now())) {

					// Etat en cours
					this.reservationService.changerEtatReservation(referenceReservation, EtatReservation.EN_COURS);

					// Ajout de consommations
					for (final Produit produit : lesProduits) {
						final Consommation consommation = new Consommation(reservation, produit, produit.getPrix(), 1);
						this.reservationService.sauvegarderConsommation(consommation);
					}

					// Facturation si la réservation est passée
					if (fin.isBefore(LocalDate.now())) {
						this.factureService.facturer(referenceReservation);
					}
				}

				// Jusqu'à la fin prévue
			} while (fin.isBefore(dateFinMax));

			// reset de la date de début pour la chambre suivante
			debut = dateDebutMin;
			fin = debut;
		}

	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent app) {
		// Pas de création de jeu de données si l'application est démarrée pour un test
		if (Mockito.mockingDetails(this.reservationService).isMock()) {
			return;
		}

		// Pour ne créer un jeu de données que si aucune donnée n'est présente en base
		if (this.compterReservationExistantes() == 0) {
			LOG.info("Démarrage de la création d'un jeu de données");
			this.initialiserUnJeuDeDonneesDeTest();
			LOG.info("Fin de la création d'un jeu de données");
		}
	}

	/**
	 * Sélection d'un nombre aléatoire d'options parmi les options possibles. Pas d'option en double (Set).
	 *
	 * Le random est généré à partir de Math.random (qui renvoie entre 0.0 et 1.0) multiplié par le nombre d'option et tronqué à la partie entière
	 * (donc de 0 à options.size()-1).
	 *
	 * @param options Les options disponibles
	 * @return Les options sélectionnnées
	 */
	private List<Option> selectionnerAleatoirementDesOptions(final List<Option> options) {
		final int nbOptions = (int) (Math.random() * options.size());
		final Set<Option> selection = new HashSet<>();
		for (int i = 0; i < nbOptions; i++) {
			final int index = (int) (Math.random() * options.size());
			selection.add(options.get(index));
		}
		return new ArrayList<>(selection);
	}

	/**
	 * Sélection d'un nombre aléatoire de produits parmi les produits possibles. Doublons possibles !
	 *
	 * Le random est généré à partir de Math.random (qui renvoie entre 0.0 et 1.0) multiplié par le nombre d'option et tronqué à la partie entière
	 *
	 * @param produits Les produits disponibles
	 * @return Les produits sélectionnnées
	 */
	private List<Produit> selectionnerAleatoirementDesProduits(final List<Produit> produits) {
		final int nbConsommations = (int) (Math.random() * NB_CONSOMMATIONS_MAX);
		final List<Produit> selection = new ArrayList<>();
		for (int i = 0; i < nbConsommations; i++) {
			final int index = (int) (Math.random() * produits.size());
			selection.add(produits.get(index));
		}
		return selection;
	}

	private Formule selectionnerAleatoirementUneFormule(final List<Formule> formules) {
		final int index = (int) (Math.random() * formules.size());
		return formules.get(index);
	}

}
