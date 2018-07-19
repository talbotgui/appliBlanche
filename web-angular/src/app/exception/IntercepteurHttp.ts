import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/observable/throw'
import 'rxjs/add/operator/catch';

import { ExceptionHandler } from './ExceptionHandler'

@Injectable()
export class IntercepteurHttp implements HttpInterceptor {

  constructor(private injector: Injector) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // exécution de la requête
    return next.handle(req)

      // Interception des erreurs HTTP
      .catch((error, caught) => {

        // Log
        console.info('Erreur durant un appel HTTP');

        // Récupération d'un composant (sans déclencher de dépendance cyclique)
        const exceptionHandler = this.injector.get(ErrorHandler);

        // Appel au ErrorHandler qui fait tout le travail
        exceptionHandler.handleError(error);

        // Pour que l'erreur soit gérable aussi dans le code appelant
        return Observable.throw(error);
      }) as any;
  }
}
