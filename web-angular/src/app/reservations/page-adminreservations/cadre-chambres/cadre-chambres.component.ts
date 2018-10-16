import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';

/** Page d'administration du module des reservations */
@Component({
  selector: 'cadre-chambres', templateUrl: './cadre-chambres.component.html',
  styleUrls: ['./cadre-chambres.component.css']
})
export class CadreChambresComponent implements OnInit {

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
    this.chargerChambres();
  }

  /** Affichage du formulaire d'ajout de chambre */
  proposerAjoutChambre() { this.nouvelleChambre = new model.Chambre('', ''); }

  /** Edition d'une chambre existante */
  modifierChambre(c: model.Chambre) { this.nouvelleChambre = new model.Chambre(c.reference, c.nom); }

  /** Annulation de la modification de la chambre */
  annulerModificationChambre() { this.nouvelleChambre = undefined; }

  /** Sauvegarde de la chambre */
  validerAjoutChambre() {
    if (this.nouvelleChambre) {
      this.reservationsService.sauvegarderChambre(this.nouvelleChambre).subscribe(() => {
        this.nouvelleChambre = undefined;
        this.chargerChambres();
      });
    }
  }

  /** Suppression chambre */
  supprimerChambre(c: model.Chambre) {
    this.reservationsService.supprimerChambre(c.reference).subscribe(() => {
      this.nouvelleChambre = undefined;
      this.chargerChambres();
    });
  }

  /** Chargement des chambres */
  private chargerChambres() { this.reservationsService.listerChambres().subscribe((chambres) => this.chambres = chambres); }
}
