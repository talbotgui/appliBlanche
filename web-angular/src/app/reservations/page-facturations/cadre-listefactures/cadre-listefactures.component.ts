import { Component, OnInit, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';

/** Page de gestion des factures */
@Component({ selector: 'cadre-listefactures', templateUrl: './cadre-listefactures.component.html', styleUrls: ['./cadre-listefactures.component.css'] })
export class CadreListeFacturesComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Bus de message pour communiquer avec le composant parent */
  busDeMessage = new EventEmitter<model.Reservation>();

  /** Liste des réservations facturables */
  reservationsFacturables: model.Reservation[];

  /** Liste des réservations facturées */
  reservationsFacturees: model.Reservation[];

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.reservationsService.listerReservationsFacturables().subscribe((reservations) => this.reservationsFacturables = reservations);
    this.reservationsService.listerReservationsFacturees().subscribe((reservations) => this.reservationsFacturees = reservations);
  }

  /** A la selection, on prévient les composants intéressés */
  selectionnerReservation(r: model.Reservation) {
    this.busDeMessage.emit(r);
  }

}
