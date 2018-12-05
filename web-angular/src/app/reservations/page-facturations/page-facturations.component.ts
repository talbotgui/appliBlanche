import { Component, ViewChild, AfterViewInit } from '@angular/core';

import * as model from '../model/model';
import { CadreDetailsFactureComponent } from './cadre-detailsfacture/cadre-detailsfacture.component';
import { CadreListeFacturesComponent } from './cadre-listefactures/cadre-listefactures.component';

/** Page de gestion des factures */
@Component({ selector: 'page-facturations', templateUrl: './page-facturations.component.html', styleUrls: ['./page-facturations.component.css'] })
export class PageFacturationsComponent implements AfterViewInit {

  /** Instance du composant enfant */
  @ViewChild(CadreListeFacturesComponent) listeFactures: CadreListeFacturesComponent;

  /** Instance du composant enfant */
  @ViewChild(CadreDetailsFactureComponent) detailsFacture: CadreDetailsFactureComponent;

  /** Après le onInit et après que la vue se soit initialisée */
  ngAfterViewInit() {

    // A la sélection d'un élément dans la liste, on prévient le cadre de détail
    this.listeFactures.busDeMessage.subscribe((r: model.Reservation | undefined) => {
      // sélection d'une réservation
      if (r) {
        this.detailsFacture.selectionnerUneReservation(r);
      }
    });
  }
}