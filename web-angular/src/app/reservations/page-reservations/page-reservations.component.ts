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

  /** Après le onInit et après que la vue se soit initialisée */
  ngAfterViewInit() {

    // A l'envoi d'un message du composant Calendrier
    this.cadreCalendrier.busDeMessage.subscribe((r: model.Reservation | undefined) => {
      if (r) {
        // sélection d'une réservation
        this.cadreReservation.selectionnerUneReservation(r);

        // Scroll vers le bas pour affiche le cadre
        // (avec un petit décalage temporel pour laisser le temps au cadre de s'afficher)
        setTimeout(() => {
          const cadre = document.getElementById('cadre-reservation');
          if (cadre) {
            cadre.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'center' });
          }
        }, 200);
      }
    });

    // A l'envoi d'un message du composant Reservation
    this.cadreReservation.busDeMessage.subscribe(() => {
      // raffraichissement du calendrier
      this.cadreCalendrier.chargerDonnees();
    });
  }
}
