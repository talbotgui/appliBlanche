import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import * as model from '../model/model';

@Injectable()
export class RestUtilsService {

  constructor(private http: HttpClient) { }

  /** Gestionnnaire des erreurs REST */
  public handleError(error: HttpErrorResponse) {
    // A client-side or network error occurred. Handle it accordingly.
    if (error.error instanceof ErrorEvent) {
      console.error('Une erreur est arrivée :', error.error.message);
    }

    // The backend returned an unsuccessful response code.
    // The response body may contain clues as to what went wrong,
    else {
      console.error('Reponse de l\'API : ' + error.status);
      console.error(error);
    }

    // return an ErrorObservable with a user-facing error message
    return throwError('Une erreur est arrivée. Si le problème persiste, merci de contacter l\'administrateur');
  };

  // Création des options d'appels REST
  public creerHeader(): { headers: HttpHeaders } | undefined {
    const headers: any = { 'Content-Type': 'application/json' }
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }
    return { headers: new HttpHeaders(headers) };
  }

  // Création des options d'appels REST pour les POST
  public creerHeaderPost(): { headers: HttpHeaders } | undefined {
    const headers: any = {}
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }
    return { headers: new HttpHeaders(headers) };
  }
}
