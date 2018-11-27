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

  }
}
