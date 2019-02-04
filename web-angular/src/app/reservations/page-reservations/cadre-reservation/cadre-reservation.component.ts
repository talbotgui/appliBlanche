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
  /** Liste des options */
  options: model.Option[] = [];

  /** Reservation dans le détail */
  reservationSelectionnee: model.Reservation | undefined;
  /** Map des options utilisée dans les ecrans */
  optionsCalculeesPourLaReservationSelectionnee: model.IStringToAnyMap<boolean> = {};

  /** Bus de message pour communiquer avec le composant parent */
  busDeMessage = new EventEmitter<string>();

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.reservationsService.listerChambres().subscribe(
      (chambres) => this.chambres = (chambres && chambres.length > 0) ? chambres : []);
    this.reservationsService.listerFormules().subscribe(
      (formules) => this.formules = (formules && formules.length > 0) ? formules : []);
    this.reservationsService.listerOptions().subscribe(
      (options) => this.options = (options && options.length > 0) ? options : []);
  }

  enregistrerReservationSelectionnee() {
    if (this.reservationSelectionnee) {

      // Application des options sélectionnées
      this.reservationSelectionnee.options = [];
      if (this.options) {
        for (const o of this.options) {
          if (this.optionsCalculeesPourLaReservationSelectionnee[o.reference]) {
            this.reservationSelectionnee.options.push(o);
          }
        }
      }

      // Sauvegarde
      this.reservationsService.sauvegarderReservation(this.reservationSelectionnee).subscribe(() => {
        this.reservationSelectionnee = undefined;
        this.busDeMessage.emit('');
      });
    }
  }

  changerEtatEnCours() {
    if (this.reservationSelectionnee) {
      this.reservationsService.changerEtatReservation(this.reservationSelectionnee.reference, 'EN_COURS').subscribe(() => {
        this.annulerOuFermer();
      });
    }
  }

  /** Méthode appelée par le composant parent (pour ignorer la ligne suivante : @@angular:analyse:ignorerLigneSuivante@@) */
  selectionnerUneReservation(r: model.Reservation) {
    this.reservationSelectionnee = r;
    // Calcul de l'objet portant les options
    this.optionsCalculeesPourLaReservationSelectionnee = {};
    if (this.options && r && r.options) {
      for (const o of this.options) {
        const estSelectionnee = (r.options.findIndex((oSel) => o.reference === oSel.reference) >= 0);
        this.optionsCalculeesPourLaReservationSelectionnee[o.reference] = estSelectionnee;
      }
    }
  }

  /** Annulation ou fermeture du formulaire */
  annulerOuFermer() {
    this.reservationSelectionnee = undefined;
    this.busDeMessage.emit('');
  }
}
