import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { SecuriteService } from '../service/securite.service';

/** Menu de l'application */
@Component({ selector: 'cadre-menu', templateUrl: './cadre-menu.component.html', styleUrls: ['./cadre-menu.component.css'] })
export class CadreMenuComponent {

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private router: Router, private securiteService: SecuriteService) { }

  /** Déconnexion de l'utilisateur */
  deconnecter(): void {
    this.securiteService.deconnecter();
    this.router.navigate(['page-connexion-route']);
  }
}
