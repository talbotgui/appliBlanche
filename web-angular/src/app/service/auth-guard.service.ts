import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Router } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private utilisateurService: UtilisateurService) { }

  canActivate() {
    if (this.utilisateurService.estConnecte()) {
      return true;
    } else {
      this.router.navigate(['page-connexion-route']);
      return false;
    }
  }
}