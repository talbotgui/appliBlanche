import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';
import { Reservation } from '../model/model';

/** Page de gestion des reservations */
@Component({ selector: 'page-consommations', templateUrl: './page-consommations.component.html', styleUrls: ['./page-consommations.component.css'] })
export class PageConsommationsComponent {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des réservations en cours */
  reservations: Reservation;
}
