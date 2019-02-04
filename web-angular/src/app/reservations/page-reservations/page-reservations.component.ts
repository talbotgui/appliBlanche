import { Component, ViewChild, AfterViewInit } from '@angular/core';

import * as model from '../model/model';
import { CadreReservationComponent } from './cadre-reservation/cadre-reservation.component';
import { CadreCalendrierComponent } from './cadre-calendrier/cadre-calendrier.component';
import { AnimationComponent } from '../../shared/service/animation.component';

/** Page de gestion des reservations */
@Component({ selector: 'page-reservations', templateUrl: './page-reservations.component.html', styleUrls: ['./page-reservations.component.css'] })
export class PageReservationsComponent implements AfterViewInit {

  /** Instance du composant enfant */
  @ViewChild(CadreCalendrierComponent) cadreCalendrier: CadreCalendrierComponent;

  /** Instance du composant enfant */
  @ViewChild(CadreReservationComponent) cadreReservation: CadreReservationComponent;

  /** Constructeur avec injection. */
  constructor(private animationComponent: AnimationComponent) { }

  /** Après le onInit et après que la vue se soit initialisée */
  ngAfterViewInit() {

    // A l'envoi d'un message du composant Calendrier
    this.cadreCalendrier.busDeMessage.subscribe((r: model.Reservation | undefined) => {
      if (r) {
        // sélection d'une réservation
        this.cadreReservation.selectionnerUneReservation(r);
        this.animationComponent.deplacerLaVueSurLeComposant('cadre-reservation');
      }
    });

    // A l'envoi d'un message du composant Reservation
    this.cadreReservation.busDeMessage.subscribe(() => {
      // raffraichissement du calendrier
      this.cadreCalendrier.chargerDonnees();
    });
  }
}
