import { Component, OnInit } from '@angular/core';

import { ReservationService } from '../service/reservation.service';
import * as model from '../model/model';

/** Page de gestion des reservations */
@Component({ selector: 'page-reservations', templateUrl: './page-reservations.component.html', styleUrls: ['./page-reservations.component.css'] })
export class PageReservationsComponent implements OnInit {

  /** Filtre d'affichage - debut */
  dateDebut: Date;

  /** Filtre d'affichage - fin */
  dateFin: Date;

  /** Liste des jours */
  jours: Date[];

  /** Liste des réservations à afficher **/
  reservations: model.Reservation[];

  /** Liste des chambres */
  chambres: model.Chambre[];

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {

    // Initialisation des dates de filtrage
    const dateDuJour = new Date();
    this.dateDebut = new Date(dateDuJour.getFullYear(), dateDuJour.getMonth(), 1);
    this.dateFin = new Date(dateDuJour.getFullYear(), dateDuJour.getMonth() + 1, 1);

    // Chargement des données
    this.rechercherReservations();
    this.listerChambres();
    this.calculerLesJours();
  }

  /** Recherche d'une réservation à la date donnée pour cette chambre */
  getReservation(c: model.Chambre, d: Date): string | undefined {
    if (this.reservations) {
      for (let r of this.reservations) {
        if (r.chambre.reference === c.reference && r.dateDebut <= d && d <= r.dateFin) {
          return r.client;
        }
      }
    }
    return undefined;
  }

  /** Calcul de la liste des jours entre la date de début et la date de fin */
  private calculerLesJours() {
    this.jours = [];
    const d = new Date(this.dateDebut);
    while (d <= this.dateFin) {
      this.jours.push(new Date(d));
      d.setDate(d.getDate() + 1);
    }
  }

  /** Chargement des reservations */
  private rechercherReservations() {
    this.reservationsService.rechercherReservations(this.dateDebut, this.dateFin).subscribe(
      (reservations) => this.reservations = reservations
    );
  }

  /** Chargement de la liste des chambres */
  private listerChambres() {
    this.reservationsService.listerChambres().subscribe(
      (chambres) => this.chambres = chambres
    );
  }
}
