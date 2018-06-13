import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import * as model from '../model/model';

@Injectable()
export class UtilisateurService {

  constructor(private http: HttpClient) { }

  connecter(login: string, mdp: string, callback: Function): void {
    const donnees = { login: login, mdp: mdp };
    this.http.post<void>('http://localhost:9090/applicationBlanche/login', donnees, { observe: 'response' })
      .pipe(catchError(error => {
        localStorage.removeItem('JWT');
        return this.handleError(error);
      }))
      .subscribe((reponse: HttpResponse<void>) => {

        // Lecture du token
        const token = reponse.headers.get('Authorization');

        // Sauvegarde du token dans le localstorage
        if (token) {
          localStorage.setItem('JWT', token);
        }

        // Appel à la callback
        callback();
      })
  }

  deconnecter(): void {
    localStorage.removeItem('JWT');
  }

  estConnecte(): boolean {
    const token = localStorage.getItem('JWT');
    return !!token;
  }

  listerUtilisateurs(): Observable<{} | model.Utilisateur[]> {
    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs';
    return this.http.get<model.Utilisateur[]>(url, this.creerHeader())
      .pipe(catchError(this.handleError));
  }

  sauvegarderUtilisateur(utilisateur: model.Utilisateur): Observable<{} | void> {
    const donnees = new HttpParams()
      .set('login', utilisateur.login)
      .set('mdp', utilisateur.mdp);

    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs';
    return this.http.post<void>(url, donnees, this.creerHeaderPost())
      .pipe(catchError(this.handleError));
  }

  /** Gestionnnaire des erreurs REST */
  private handleError(error: HttpErrorResponse) {
    // A client-side or network error occurred. Handle it accordingly.
    if (error.error instanceof ErrorEvent) {
      console.error('Une erreur est arrivée :', error.error.message);
    }

    // The backend returned an unsuccessful response code.
    // The response body may contain clues as to what went wrong,
    else {
      console.error('Reponse de l\'API : ' + error.status + ' - ' + error.error);
    }

    // return an ErrorObservable with a user-facing error message
    return throwError('Une erreur est arrivée. Si le problème persiste, merci de contacter l\'administrateur');
  };

  // Création des options d'appels REST
  private creerHeader(): { headers: HttpHeaders } | undefined {
    const headers: any = { 'Content-Type': 'application/json' }
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }
    return { headers: new HttpHeaders(headers) };
  }

  // Création des options d'appels REST pour les POST
  private creerHeaderPost(): { headers: HttpHeaders } | undefined {
    const headers: any = {}
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }
    return { headers: new HttpHeaders(headers) };
  }
}
