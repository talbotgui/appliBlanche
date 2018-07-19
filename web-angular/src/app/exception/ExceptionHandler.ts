import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar } from '../../../node_modules/@angular/material';
import { TranslationService } from 'angular-l10n';

/** Gestionnaire d'erreur par défaut */
@Injectable()
export class ExceptionHandler implements ErrorHandler {

  // Because the ErrorHandler is created before the providers, we’ll have to use the Injector to get them.
  constructor(private injector: Injector) { }

  handleError(error: any): void {

    // Récupération d'un composant (sans déclencher de dépendance cyclique)
    const router = this.injector.get(Router);
    const snackBar = this.injector.get(MatSnackBar);
    const i18n = this.injector.get(TranslationService);

    // Log de l'erreur
    console.info('Traitement d\'une erreur');
    console.error(error);

    // Si c'est une erreur HTTP
    let codeMessage;
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        codeMessage = 'erreur_aucuneConnexionInternet';
      } else if (error.status === 0) {
        codeMessage = 'erreur_securiteParNavigateur';
      } else if (error.status === 404) {
        codeMessage = 'erreur_apiNonDisponible';
      } else if (error.status === 403 || error.status === 401) {
        // Si ce n'est pas une erreur à la connexion, on ne fait rien
        if (error.url && !error.url.endsWith('/login')) {
          codeMessage = 'erreur_securite';
        }
      } else {
        codeMessage = 'erreur_http';
      }
    }
    // si c'est une erreur de code, message par défaut
    else {
      codeMessage = 'erreur_pgm';
    }

    // Affichage du message
    if (codeMessage) {
      const message = i18n.translate(codeMessage);
      snackBar.open(message);
    }
  }
}