import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../service/reservation.service';
import * as model from '../model/model';

/** Page de gestion des reservations */
@Component({ selector: 'page-reservations', templateUrl: './page-reservations.component.html', styleUrls: ['./page-reservations.component.css'] })
export class PageReservationsComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Filtre d'affichage - debut */
  dateDebut: Date;

  /** Filtre d'affichage - fin */
  dateFin: Date;

  /** Flag permettant la saisie de dates sur une période personnalisée */
  flagSaisieDatesPersonalisees: boolean = false;

  /** Largeur de la colonne d'une chambre */
  nbColParChambre = 2;

  /** Liste des réservations à afficher. */
  reservations: model.IStringToAnyMap<model.IStringToAnyMap<model.Reservation>> = {};

  /** Liste des chambres */
  chambres: model.Chambre[] = [];

  /** Liste des jours */
  jours: Date[];

  /** Reservation dans le détail */
  reservationSelectionnee: model.Reservation | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {

    // Initialisation des dates de filtrage (de J-3 à J+12)
    const dateDuJour = new Date();
    this.dateDebut = new Date(dateDuJour.getTime() - (3 * 1000 * 3600 * 24));
    this.dateFin = new Date(dateDuJour.getTime() + (12 * 1000 * 3600 * 24));

    // Chargement des données
    this.chargerDonnees();
  }

  /** Initialisation d'une nouvelle réservation */
  initaliserNouvelleReservation() {
    this.reservationSelectionnee = new model.Reservation('', this.dateDebut, this.dateFin, '', new model.Chambre('', ''));
  }

  /** Déplacement des dates du filtre en jour */
  deplacerDateParJour(n: number) {
    // Déplacement des dates du filtre
    this.dateDebut = new Date(this.dateDebut.getTime() + (n * 1000 * 3600 * 24));
    this.dateFin = new Date(this.dateFin.getTime() + (n * 1000 * 3600 * 24));

    // Chargement des données
    this.chargerDonnees();
  }

  afficherDetail(reservation: model.Reservation) {
    this.reservationSelectionnee = reservation;
  }

  /** Chargement de la liste des chambres, puis des réservations et calcul du tableau de données */
  chargerDonnees() {

    this.reservations = {};

    // Chargement des chambres
    this.reservationsService.listerChambres().subscribe(
      (chambres) => {
        if (chambres && chambres.length > 0) {
          this.chambres = chambres;
          this.nbColParChambre = Math.floor((12 - 2) / chambres.length);
        } else {
          this.chambres = [];
          this.nbColParChambre = 1;
        }

        // Chargement des réservations
        this.reservationsService.rechercherReservations(this.dateDebut, this.dateFin).subscribe(
          (reservations) => {

            // Calcul de la liste des jours entre la date de début et la date de fin
            this.jours = [];
            const d = new Date(this.dateDebut);
            while (d <= this.dateFin) {
              this.jours.push(new Date(d));
              d.setDate(d.getDate() + 1);
            }

            // Calcul du tableau de données
            for (const j of this.jours) {
              for (const c of this.chambres) {
                if (!this.reservations[c.reference]) {
                  this.reservations[c.reference] = {};
                }
                for (const r of reservations) {
                  if (r.chambre.reference === c.reference && r.dateDebut <= j && j <= r.dateFin) {
                    this.reservations[c.reference][j.toISOString()] = r;
                  }
                }
              }
            }
          }
        );
      }
    );
  }

  enregistrerReservationSelectionnee() {
    if (this.reservationSelectionnee) {
      this.reservationsService.sauvegarderReservation(this.reservationSelectionnee).subscribe(() => {
        this.reservationSelectionnee = undefined;
        this.chargerDonnees();
      });
    }
  }
}
