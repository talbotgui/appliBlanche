import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, NavigationExtras } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private utilisateurService: UtilisateurService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.utilisateurService.estConnecte().map(b => {
      if (!!b) {
        return true;
      } else {
        const navigationExtras: NavigationExtras = { queryParams: { 'redirect': state.url } };
        this.router.navigate(['page-connexion-route'], navigationExtras);
        return false;
      }
    });
  }
}