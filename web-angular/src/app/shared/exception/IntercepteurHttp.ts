import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

/** Intercepteur HTTP pour traiter systématiquement les cas d'erreurs. */
@Injectable()
export class IntercepteurHttp implements HttpInterceptor {

  /**
   * Un constructeur pour se faire injecter les dépendances
   *
   * Because the ErrorHandler is created before the providers, we’ll have to use the Injector to get them
   */
  constructor(private injector: Injector) { }

  /** Interception de l'appel et traitement du cas d'erreur */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // exécution de la requête
    return next.handle(req).pipe(

      // Interception des erreurs HTTP
      catchError<any>((error) => {

        // Log (nécessaire pour tracer l'erreur)
        /* tslint:disable-next-line */
        console.info('Erreur durant un appel HTTP');

        // Récupération d'un composant (sans déclencher de dépendance cyclique)
        const exceptionHandler = this.injector.get(ErrorHandler);

        // Appel au ErrorHandler qui fait tout le travail
        exceptionHandler.handleError(error);

        // Pour que l'erreur soit gérable aussi dans le code appelant
        throw error;
      }));
  }
}
