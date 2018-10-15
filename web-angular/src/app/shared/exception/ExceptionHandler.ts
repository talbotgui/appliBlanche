import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { TranslationService, LocaleService } from 'angular-l10n';

/** Gestionnaire d'erreur par défaut */
@Injectable()
export class ExceptionHandler implements ErrorHandler {

  /** Erreur traitée précédemment pour ne pas traiter deux fois la même erreur */
  private precedenteErreur: any | undefined;

  /** Composant informatif */
  private snackbarConfig: MatSnackBarConfig = { duration: 3000 };

  /**
   * Un constructeur pour se faire injecter les dépendances
   *
   * Because the ErrorHandler is created before the providers, we’ll have to use the Injector to get them
   */
  constructor(private injector: Injector) { }

  /** Traitement de l'erreur */
  handleError(error: any): void {

    // Pour ne pas traiter deux fois la même erreur
    if (this.precedenteErreur && this.precedenteErreur === error) {
      return;
    }
    this.precedenteErreur = error;

    // Récupération d'un composant (sans déclencher de dépendance cyclique)
    const snackBar = this.injector.get(MatSnackBar);
    const i18n = this.injector.get(TranslationService);
    const locale = this.injector.get(LocaleService);

    // Log de l'erreur (nécessaire pour tracer l'erreur)
    /* tslint:disable-next-line */
    console.info('Traitement d\'une erreur');
    /* tslint:disable-next-line */
    console.error(error);

    // Si c'est une erreur HTTP
    let codeMessage;
    let parametresMessage: string[] = [];
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
      } else if (error.error && error.error.codeException) {
        codeMessage = error.error.codeException;
        parametresMessage = error.error.details;
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
      const message = i18n.translate(codeMessage, parametresMessage, locale.getCurrentLanguage());
      snackBar.open(message, undefined, this.snackbarConfig);
    }
  }
}
