import { Component, OnInit } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../service/reservation.service';
import * as model from '../model/model';

/** Page de gestion des reservations */
@Component({ selector: 'page-consommations', templateUrl: './page-consommations.component.html', styleUrls: ['./page-consommations.component.css'] })
export class PageConsommationsComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des reservations en cours */
  reservations: model.Reservation[];
  /** Liste des produits */
  produits: model.Produit[];
  /** Consommations saisies */
  consommationsDeLaReservationSelectionnee: model.Consommation[];

  /** Réservation sélectionnée */
  reservationSelectionee: model.Reservation;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.chargerReservations();
    this.chargerProduits();
    this.consommationsDeLaReservationSelectionnee = [];
  }

  calculerQuantitePourProduit(referenceProduit: string): number {
    const conso = this.rechercherConsommation(referenceProduit);
    if (conso) {
      return conso.quantite;
    } else {
      return 0;
    }
  }

  /** A la selection d'une réservation */
  selectionnerReservation(resa: model.Reservation) {
    this.reservationSelectionee = resa;
    this.rechercherLesConsommationsDeLaReservation();
  }

  /** Ajout d'une consommation */
  ajouterConsomation(reservation: model.Reservation, produit: model.Produit) {
    const consommation = new model.Consommation('', new Date(), undefined, 1, reservation, produit);
    this.reservationsService.sauvegarderConsommation(this.reservationSelectionee.reference, consommation)
      .subscribe(() => this.rechercherLesConsommationsDeLaReservation());
  }

  /** Suppression d'une consommation */
  supprimerConsommation(referenceProduit: string) {
    const conso = this.rechercherConsommation(referenceProduit);
    if (conso) {
      this.reservationsService.supprimerConsommation(this.reservationSelectionee.reference, conso.reference)
        .subscribe(() => conso.quantite = 0);
    }
  }

  /** Réduire la quantite d'une consommation */
  reduireConsommation(referenceProduit: string) {
    const conso = this.rechercherConsommation(referenceProduit);
    if (conso) {
      this.reservationsService.reduireConsommation(this.reservationSelectionee.reference, conso.reference)
        .subscribe(() => conso.quantite--);
    }
  }

  private rechercherConsommation(referenceProduit: string): model.Consommation | undefined {
    return this.consommationsDeLaReservationSelectionnee.find((c) => c.produit.reference === referenceProduit);
  }

  /** Raffrichissement de la liste des consommations de la réservation sélectionnée */
  private rechercherLesConsommationsDeLaReservation() {
    this.reservationsService.listerConsommation(this.reservationSelectionee.reference)
      .subscribe((consommations) => this.consommationsDeLaReservationSelectionnee = consommations);
  }

  /** Chargement des reservations */
  private chargerReservations() { this.reservationsService.listerReservationsEnCours().subscribe((reservations) => this.reservations = reservations); }

  /** Chargement des produits */
  private chargerProduits() { this.reservationsService.listerProduits().subscribe((produits) => this.produits = produits); }
}
