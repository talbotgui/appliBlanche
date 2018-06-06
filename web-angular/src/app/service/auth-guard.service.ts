import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, NavigationExtras } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private utilisateurService: UtilisateurService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.utilisateurService.estConnecte()) {
      return true;
    } else {
      const navigationExtras: NavigationExtras = { queryParams: { 'redirect': state.url } };
      this.router.navigate(['page-connexion-route'], navigationExtras);
      return false;
    }
  }
}