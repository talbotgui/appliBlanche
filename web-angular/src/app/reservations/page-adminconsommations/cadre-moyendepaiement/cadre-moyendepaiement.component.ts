import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import { DataSourceSimpleComponent } from '../../../shared/service/datasourceSimple.component';
import * as model from '../../model/model';

/** Page d'administration du module des reservations */
@Component({ selector: 'cadre-moyendepaiement', templateUrl: './cadre-moyendepaiement.component.html' })
export class CadreMoyenDePaiementComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des colonnes à afficher dans le tableau */
  displayedColumns: string[] = ['nom', 'montantAssocie', 'actions'];

  /** Liste des MDP */
  dataSource: DataSourceSimpleComponent<model.MoyenDePaiement>;

  /** Nouveau MoyenDePaiement */
  nouveauMoyenDePaiement: model.MoyenDePaiement | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.charger();
  }

  /** Affichage du formulaire d'ajout */
  proposerAjout() { this.nouveauMoyenDePaiement = new model.MoyenDePaiement('', 0, ''); }

  /** Edition d'un existant */
  modifier(m: model.MoyenDePaiement) { this.nouveauMoyenDePaiement = new model.MoyenDePaiement(m.reference, m.montantAssocie, m.nom); }

  /** Annulation de la modification */
  annulerModification() { this.nouveauMoyenDePaiement = undefined; }

  /** Sauvegarde  */
  validerAjout() {
    if (this.nouveauMoyenDePaiement) {
      this.reservationsService.sauvegarderMoyenDePaiement(this.nouveauMoyenDePaiement).subscribe(() => {
        this.nouveauMoyenDePaiement = undefined;
        this.charger();
      });
    }
  }

  /** Suppression */
  supprimer(m: model.MoyenDePaiement) {
    this.reservationsService.supprimerMoyenDePaiement(m.reference).subscribe(() => {
      this.nouveauMoyenDePaiement = undefined;
      this.charger();
    });
  }

  /** Chargement des produits */
  private charger() {
    this.dataSource = new DataSourceSimpleComponent<model.MoyenDePaiement>(() => this.reservationsService.listerMoyensDePaiement());
    this.dataSource.load();
  }
}
