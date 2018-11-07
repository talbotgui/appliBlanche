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
  supprimerConsommation(consommation: model.Consommation) {
    this.reservationsService.supprimerConsommation(this.reservationSelectionee.reference, consommation.reference)
      .subscribe(() => this.rechercherLesConsommationsDeLaReservation());
  }

  /** Réduire la quantite d'une consommation */
  reduireConsommation(consommation: model.Consommation) {
    this.reservationsService.reduireConsommation(this.reservationSelectionee.reference, consommation.reference)
      .subscribe(() => this.rechercherLesConsommationsDeLaReservation());
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
