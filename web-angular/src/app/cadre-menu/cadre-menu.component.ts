import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import * as model from '../model/model';
import { SecuriteService } from '../service/securite.service';
import { Context } from '../shared/service/context';

/** Menu de l'application */
@Component({ selector: 'cadre-menu', templateUrl: './cadre-menu.component.html', styleUrls: ['./cadre-menu.component.css'] })
export class CadreMenuComponent implements OnInit {

  /** Liste des modules du menu autorisés à l'utilisateur connecté */
  modules: model.ModuleApplicatif[] = [];

  /** Utilisateur connecté */
  utilisateurConnecte: model.Utilisateur;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private router: Router, private securiteService: SecuriteService, private context: Context) { }

  /** A l'initialisation */
  ngOnInit() {
    const tousLesModules: model.ModuleApplicatif[] = [];

    // Menu ADMINISTRATION
    let pagesDuModule = [];
    pagesDuModule.push(new model.PageApplicative('menu_utilisateur', 'utilisateur.listerUtilisateur', '/page-utilisateur-route'));
    pagesDuModule.push(new model.PageApplicative('menu_role', 'roleEtRessource.listerRoles', '/page-role-route'));
    pagesDuModule.push(new model.PageApplicative('menu_ressource', 'roleEtRessource.listerRessource', '/page-ressource-route'));
    pagesDuModule.push(new model.PageApplicative('menu_monitoring', 'monitoring.lireDonneesDuMonitoring', '/page-monitoring-route'));
    tousLesModules.push(new model.ModuleApplicatif('menu_titre_administration', pagesDuModule));

    // Menu RESERVATION
    pagesDuModule = [];
    pagesDuModule.push(new model.PageApplicative('menu_reservations', 'reservation.rechercherReservations', '/page-reservations-route'));
    pagesDuModule.push(new model.PageApplicative('menu_adminreservations', 'reservationParametres.listerChambres', '/page-adminreservations-route'));
    tousLesModules.push(new model.ModuleApplicatif('menu_titre_reservation', pagesDuModule));

    // Menu CONSOMMATION
    pagesDuModule = [];
    pagesDuModule.push(new model.PageApplicative('menu_consommation', 'reservation.rechercherConsommationsDuneReservation', '/page-consommations-route'));
    pagesDuModule.push(new model.PageApplicative('menu_adminconsommation', 'reservationParametres.listerProduits', '/page-adminconsommations-route'));
    tousLesModules.push(new model.ModuleApplicatif('menu_titre_consommation', pagesDuModule));

    // Résumé des clefs nécessaires aux pages
    let clefs: string[] = [];
    tousLesModules.forEach((m) => {
      clefs = clefs.concat(m.pages.map((p) => p.clefApi));
    });

    // A la connexion/déconnexion d'un utilisateur
    this.context.notificationsConnexionDunUtilisateur.subscribe((u: model.Utilisateur) => {

      // Validation des éléments du menu autorisés à cet utilisateur
      const clefsAutorisees = this.securiteService.validerAutorisations(clefs);
      const modulesAutorises: model.ModuleApplicatif[] = [];
      tousLesModules.forEach((m) => {
        const pagesAutorisees = m.pages.filter((p) => clefsAutorisees.indexOf(p.clefApi) !== -1);
        if (pagesAutorisees.length > 0) {
          modulesAutorises.push(new model.ModuleApplicatif(m.nom, pagesAutorisees));
        }
      });

      // Affectation des modules autorisés
      this.modules = modulesAutorises;

      // Sauvegarde des données de l'utilisateur
      this.utilisateurConnecte = u;
    });
  }

  /** Déconnexion de l'utilisateur */
  deconnecter(): void {
    this.securiteService.deconnecter();
    this.router.navigate(['page-connexion-route']);
  }
}
