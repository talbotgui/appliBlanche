import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

/** Gestionnaire d'erreur par défaut */
@Injectable()
export class ExceptionHandler implements ErrorHandler {

  // Because the ErrorHandler is created before the providers, we’ll have to use the Injector to get them.
  constructor(private injector: Injector) { }

  handleError(error: any): void {

    // Récupération d'un composant (sans déclencher de dépendance cyclique)
    const router = this.injector.get(Router);

    // Log de l'erreur
    console.info('Traitement d\'une erreur');
    console.error(error);

    // Si c'est une erreur HTTP
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        alert('Aucune connexion Internet disponible');
      } else if (error.status === 0) {
        alert('Erreur de sécurité détectée par le navigateur');
      } else if (error.status === 404) {
        alert('API down');
      } else if (error.status === 403 || error.status === 401) {
        // Si ce n'est pas une erreur à la connexion, on ne fait rien
        if (error.url && !error.url.endsWith('/login')) {
          alert('Erreur de sécurité lors d\'un appel à l\'API. Tentez de vous reconnecter');
        }
      } else {
        alert('Erreur HTTP');
      }
    }
    // si c'est une erreur de code, rien à faire
  }
}