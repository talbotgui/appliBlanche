import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';
import * as model from '../model/model';

@Component({ selector: 'page-connexion', templateUrl: './page-connexion.component.html', styleUrls: ['./page-connexion.component.css'] })
export class PageConnexionComponent {

  login: string = 'adminAsupprimer';
  mdp: string = 'adminAsupprimer';

  // Un constructeur pour se faire injecter les dépendances
  constructor(private router: Router, private utilisateurService: UtilisateurService) { }

  connecter(): void {
    this.utilisateurService.connecter(this.login, this.mdp, () => {
      this.router.navigate(['page-accueil-route']);
    });
  }
}
