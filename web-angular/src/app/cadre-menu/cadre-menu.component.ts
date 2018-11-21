import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import * as model from '../model/model';
import { SecuriteService } from '../service/securite.service';

/** Menu de l'application */
@Component({ selector: 'cadre-menu', templateUrl: './cadre-menu.component.html', styleUrls: ['./cadre-menu.component.css'] })
export class CadreMenuComponent implements OnInit {

  modules: model.ModuleApplicatif[] = [];

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private router: Router, private securiteService: SecuriteService) { }

  /** A l'initialisation */
  ngOnInit() {
    this.modules = [];

    // Menu ADMINISTRATION
    let pages = [];
    pages.push(new model.PageApplicative('menu_utilisateur', '/page-utilisateur-route'));
    pages.push(new model.PageApplicative('menu_role', '/page-role-route'));
    pages.push(new model.PageApplicative('menu_ressource', '/page-ressource-route'));
    this.modules.push(new model.ModuleApplicatif('menu_titre_administration', pages));

    // Menu RESERVATION
    pages = [];
    pages.push(new model.PageApplicative('menu_reservations', '/page-reservations-route'));
    pages.push(new model.PageApplicative('menu_adminreservations', '/page-adminreservations-route'));
    this.modules.push(new model.ModuleApplicatif('menu_titre_reservation', pages));

    // Menu CONSOMMATION
    pages = [];
    pages.push(new model.PageApplicative('menu_consommation', '/page-consommations-route'));
    pages.push(new model.PageApplicative('menu_adminconsommation', '/page-adminconsommations-route'));
    this.modules.push(new model.ModuleApplicatif('menu_titre_consommation', pages));
  }

  /** Déconnexion de l'utilisateur */
  deconnecter(): void {
    this.securiteService.deconnecter();
    this.router.navigate(['page-connexion-route']);
  }
}
