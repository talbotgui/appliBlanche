import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';
import { AnimationComponent } from '../../../shared/service/animation.component';

/** Page d'administration du module des reservations */
@Component({
  selector: 'cadre-options', templateUrl: './cadre-options.component.html',
  styleUrls: ['./cadre-options.component.css']
})
export class CadreOptionsComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des options */
  options: model.Option[];

  /** Nouvelle option */
  nouvelleOption: model.Option | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService, private animationComponent: AnimationComponent) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerOptions();
  }

  /** Affichage du formulaire d'ajout de option */
  proposerAjoutOption() {
    this.nouvelleOption = new model.Option('', '', 0, false, false);
    this.animationComponent.deplacerLaVueSurLeComposant('formulaireOption');
  }

  /** Edition d'une option existante */
  modifierOption(o: model.Option) {
    this.nouvelleOption = new model.Option(o.reference, o.nom, o.prix, o.parNuit, o.parPersonne);
    this.animationComponent.deplacerLaVueSurLeComposant('formulaireOption');
  }

  /** Annulation de la modification de la option */
  annulerModificationOption() { this.nouvelleOption = undefined; }

  /** Sauvegarde de la option */
  validerAjoutOption() {
    if (this.nouvelleOption) {
      this.reservationsService.sauvegarderOption(this.nouvelleOption).subscribe(() => {
        this.nouvelleOption = undefined;
        this.chargerOptions();
      });
    }
  }

  /** Suppression option */
  supprimerOption(c: model.Option) {
    this.reservationsService.supprimerOption(c.reference).subscribe(() => {
      this.nouvelleOption = undefined;
      this.chargerOptions();
    });
  }

  /** Chargement des options */
  private chargerOptions() { this.reservationsService.listerOptions().subscribe((options) => this.options = options); }
}
