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

  /** Liste des produits */
  produits: model.Produit[];

  /** Nouveau produit */
  nouveauProduit: model.Produit | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerDonnees();
  }

  /** Chargement de la liste des chambres, puis des réservations et calcul du tableau de données */
  chargerDonnees() {
    this.chargerChambres();
    this.chargerProduits();
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

  /** Chargement des chambres */
  private chargerChambres() { this.reservationsService.listerChambres().subscribe((chambres) => this.chambres = chambres); }
  /** Chargement des produits */
  private chargerProduits() { this.reservationsService.listerProduits().subscribe((produits) => this.produits = produits); }
}
