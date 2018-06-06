import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';

@Component({ selector: 'cadre-menu', templateUrl: './cadre-menu.component.html', styleUrls: ['./cadre-menu.component.css'] })
export class CadreMenuComponent {

  // Un constructeur pour se faire injecter les dépendances
  constructor(private router: Router, private utilisateurService: UtilisateurService) { }

  deconnecter(): void {
    this.utilisateurService.deconnecter();
    this.router.navigate(['page-connexion-route']);
  }
}
