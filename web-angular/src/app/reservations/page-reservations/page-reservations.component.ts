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
  }

  /** Chargement des reservations */
  rechercherReservations() {
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
