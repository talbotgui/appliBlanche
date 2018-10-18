import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';

/** Page d'administration du module des reservations */
@Component({
  selector: 'cadre-produits', templateUrl: './cadre-produits.component.html',
  styleUrls: ['./cadre-produits.component.css']
})
export class CadreProduitsComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des produits */
  produits: model.Produit[];

  /** Nouveau produit */
  nouveauProduit: model.Produit | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerProduits();
  }

  /** Affichage du formulaire d'ajout de produit */
  proposerAjoutProduit() { this.nouveauProduit = new model.Produit('', '', '', 0); }

  /** Edition d'un produit existant */
  modifierProduit(p: model.Produit) { this.nouveauProduit = new model.Produit(p.reference, p.couleur, p.nom, p.prix); }

  /** Annulation de la modification du produit */
  annulerModificationProduit() { this.nouveauProduit = undefined; }

  /** Sauvegarde du produit */
  validerAjoutProduit() {
    if (this.nouveauProduit) {
      this.reservationsService.sauvegarderProduit(this.nouveauProduit).subscribe(() => {
        this.nouveauProduit = undefined;
        this.chargerProduits();
      });
    }
  }

  /** Suppression produit */
  supprimerProduit(p: model.Produit) {
    this.reservationsService.supprimerProduit(p.reference).subscribe(() => {
      this.nouveauProduit = undefined;
      this.chargerProduits();
    });
  }

  /** Chargement des produits */
  private chargerProduits() { this.reservationsService.listerProduits().subscribe((produits) => this.produits = produits); }
}
