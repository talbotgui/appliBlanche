import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../service/reservation.service';
import * as model from '../model/model';

/** Page d'administration du module des reservations */
@Component({
  selector: 'page-adminreservations', templateUrl: './page-adminreservations.component.html',
  styleUrls: ['./page-adminreservations.component.css']
})
export class PageAdminReservationsComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des chambres */
  chambres: model.Chambre[];

  /** Nouvelle chambre */
  nouvelleChambre: model.Chambre | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerDonnees();
  }

  /** Chargement de la liste des chambres, puis des réservations et calcul du tableau de données */
  chargerDonnees() {
    this.chargerChambres();
  }

  /** Affichage du formulaire d'ajout de chambre */
  proposerAjoutChambre() {
    this.nouvelleChambre = new model.Chambre('', '');
  }

  /** Sauvegarde de la chambre */
  validerAjoutChambre() {
    if (this.nouvelleChambre) {
      this.reservationsService.sauvegarderChambre(this.nouvelleChambre).subscribe(() => {
        this.nouvelleChambre = undefined;
        this.chargerChambres();
      });
    }
  }

  /** Chargement des chambres */
  private chargerChambres() {
    this.reservationsService.listerChambres().subscribe((chambres) => this.chambres = chambres);
  }
}
