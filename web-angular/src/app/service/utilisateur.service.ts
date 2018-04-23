import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { catchError } from 'rxjs/operators';

import * as model from '../model/model';

@Injectable()
export class UtilisateurService {

  constructor(private http: HttpClient) { }

  listerUtilisateurs(): Observable<model.Utilisateur[]> {
    const httpOptions = this.creerHeaderSecurite();
    return this.http.get<model.Utilisateur[]>('http://localhost:9090/applicationBlanche/v1/utilisateurs', httpOptions)
      .pipe(catchError(this.handleError));
  }

  /** Creation des entetes de securite */
  private creerHeaderSecurite() {
    return { headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('adminAsupprimer:adminAsupprimer') }) };
  }

  /** Gestionnnaire des erreurs REST */
  private handleError(error: HttpErrorResponse) {
    // A client-side or network error occurred. Handle it accordingly.
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    }

    // The backend returned an unsuccessful response code.
    // The response body may contain clues as to what went wrong,
    else {
      console.error('Backend returned code ${error.status}, body was: ${error.error}');
    }

    // return an ErrorObservable with a user-facing error message
    return new ErrorObservable('Something bad happened; please try again later.');
  };
}
