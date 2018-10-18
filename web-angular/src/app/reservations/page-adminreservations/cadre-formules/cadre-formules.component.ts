import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';

/** Page d'administration du module des reservations */
@Component({
  selector: 'cadre-formules', templateUrl: './cadre-formules.component.html',
  styleUrls: ['./cadre-formules.component.css']
})
export class CadreFormulesComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des formules */
  formules: model.Formule[];

  /** Nouvelle formule */
  nouvelleFormule: model.Formule | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerFormules();
  }

  /** Affichage du formulaire d'ajout de formule */
  proposerAjoutFormule() { this.nouvelleFormule = new model.Formule('', '', 0); }

  /** Edition d'une formule existante */
  modifierFormule(f: model.Formule) { this.nouvelleFormule = new model.Formule(f.reference, f.nom, f.prixParNuit); }

  /** Annulation de la modification de la formule */
  annulerModificationFormule() { this.nouvelleFormule = undefined; }

  /** Sauvegarde de la formule */
  validerAjoutFormule() {
    if (this.nouvelleFormule) {
      this.reservationsService.sauvegarderFormule(this.nouvelleFormule).subscribe(() => {
        this.nouvelleFormule = undefined;
        this.chargerFormules();
      });
    }
  }

  /** Suppression formule */
  supprimerFormule(c: model.Formule) {
    this.reservationsService.supprimerFormule(c.reference).subscribe(() => {
      this.nouvelleFormule = undefined;
      this.chargerFormules();
    });
  }

  /** Chargement des formules */
  private chargerFormules() { this.reservationsService.listerFormules().subscribe((formules) => this.formules = formules); }
}
