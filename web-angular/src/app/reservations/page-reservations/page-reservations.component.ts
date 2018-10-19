import { Component, ViewChild, AfterViewInit } from '@angular/core';

import * as model from '../model/model';
import { CadreReservationComponent } from './cadre-reservation/cadre-reservation.component';
import { CadreCalendrierComponent } from './cadre-calendrier/cadre-calendrier.component';

/** Page de gestion des reservations */
@Component({ selector: 'page-reservations', templateUrl: './page-reservations.component.html', styleUrls: ['./page-reservations.component.css'] })
export class PageReservationsComponent implements AfterViewInit {

  /** Instance du composant enfant */
  @ViewChild(CadreCalendrierComponent) cadreCalendrier: CadreCalendrierComponent;

  /** Instance du composant enfant */
  @ViewChild(CadreReservationComponent) cadreReservation: CadreReservationComponent;

  ngAfterViewInit() {

    // A l'envoi d'un message du composant Calendrier
    this.cadreCalendrier.busDeMessage.subscribe((r: model.Reservation | undefined) => {
      // sélection d'une réservation
      if (r) {
        this.cadreReservation.selectionnerUneReservation(r);
      }
    });

    // A l'envoi d'un message du composant Reservation
    this.cadreReservation.busDeMessage.subscribe(() => {
      // raffraichissement du calendrier
      this.cadreCalendrier.chargerDonnees();
    });
  }
}
