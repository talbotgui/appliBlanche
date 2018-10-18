import { Component, OnInit, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';

/** Page de gestion des reservations */
@Component({ selector: 'cadre-reservation', templateUrl: './cadre-reservation.component.html', styleUrls: ['./cadre-reservation.component.css'] })
export class CadreReservationComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des chambres */
  chambres: model.Chambre[] = [];

  /** Liste des formules */
  formules: model.Formule[] = [];

  /** Reservation dans le détail */
  reservationSelectionnee: model.Reservation | undefined;

  /** Bus de message pour communiquer avec le composant parent */
  busDeMessage = new EventEmitter<string>();

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.reservationsService.listerChambres().subscribe(
      (chambres) => {
        if (chambres && chambres.length > 0) {
          this.chambres = chambres;
        } else {
          this.chambres = [];
        }
      });
    this.reservationsService.listerFormules().subscribe(
      (formules) => {
        if (formules && formules.length > 0) {
          this.formules = formules;
        } else {
          this.formules = [];
        }
      });
  }

  enregistrerReservationSelectionnee() {
    if (this.reservationSelectionnee) {
      this.reservationsService.sauvegarderReservation(this.reservationSelectionnee).subscribe(() => {
        this.reservationSelectionnee = undefined;
        this.busDeMessage.emit('');
      });
    }
  }
}
