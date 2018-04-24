import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { catchError } from 'rxjs/operators';

import * as model from '../model/model';

@Injectable()
export class UtilisateurService {

  private headerSecurite: { headers: HttpHeaders } | undefined;
  private headerSecuritePost: { headers: HttpHeaders } | undefined;

  constructor(private http: HttpClient) { }


  connecter(login: string, mdp: string): Observable<void> {
    this.headerSecurite = { headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa(login + ':' + mdp) }) };
    this.headerSecuritePost = { headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(login + ':' + mdp) }) };
    return this.http.get<void>('http://localhost:9090/applicationBlanche/v1/utilisateurs', this.headerSecurite)
      .pipe(catchError(error => {
        this.headerSecurite = undefined;
        return this.handleError(error);
      }))
  }

  estConnecte(): boolean {
    return !!this.headerSecurite;
  }

  listerUtilisateurs(): Observable<model.Utilisateur[]> {
    return this.http.get<model.Utilisateur[]>('http://localhost:9090/applicationBlanche/v1/utilisateurs', this.headerSecurite)
      .pipe(catchError(this.handleError));
  }

  sauvegarderUtilisateur(utilisateur: model.Utilisateur): Observable<void> {
    const donnees = new HttpParams()
      .set('login', utilisateur.login)
      .set('mdp', utilisateur.mdp);

    return this.http.post<void>('http://localhost:9090/applicationBlanche/v1/utilisateurs', donnees, this.headerSecuritePost)
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
    return new ErrorObservable('Une erreur est arrivée. Si le problème persiste, merci de contacter l\'administrateur');
  };
}
