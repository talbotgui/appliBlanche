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

  /** Liste des réservations à afficher. */
  reservations: model.IStringToAnyMap<model.IStringToAnyMap<model.Reservation>> = {};

  /** Liste des chambres */
  chambres: model.Chambre[];

  /** Liste des jours */
  jours: Date[];

  /** Reservation dans le détail */
  reservationSelectionnee: model.Reservation;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {

    // Initialisation des dates de filtrage
    const dateDuJour = new Date();
    this.dateDebut = new Date(dateDuJour.getFullYear(), dateDuJour.getMonth(), 1);
    this.dateFin = new Date(dateDuJour.getFullYear(), dateDuJour.getMonth() + 1, 1);

    // Chargement des données
    this.chargerDonnees();
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

    // Chargement des chambres
    this.reservationsService.listerChambres().subscribe(
      (chambres) => {
        this.chambres = chambres;

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
              for (const r of reservations) {
                for (const c of this.chambres) {
                  if (r.chambre.reference === c.reference && r.dateDebut <= j && j <= r.dateFin) {
                    if (!this.reservations[c.reference]) {
                      this.reservations[c.reference] = {};
                    }
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
}
