import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import { RestUtilsService } from './restUtils.service';

import * as model from '../model/model';

@Injectable()
export class UtilisateurService {

  constructor(private http: HttpClient, private restUtils: RestUtilsService) { }

  connecter(login: string, mdp: string, callback: Function, callbackErreurParametresConnexion: Function): void {
    const donnees = { login: login, mdp: mdp };
    this.http.post<void>('http://localhost:9090/applicationBlanche/login', donnees, { observe: 'response' })
      .pipe(catchError<any>(error => {
        // Pour traiter l'erreur comme un cas particulier
        localStorage.removeItem('JWT');

        // Dans le cas d'une erreur de paramètres de connexion
        // appel à la callback pour que le composant traite son erreur
        if (error.status && error.status === 403) {
          callbackErreurParametresConnexion(error);
        }

        // On laisse se faire le traitement global d'erreur
        throw error;
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
    return this.http.get<model.Utilisateur[]>(url, this.restUtils.creerHeader());
  }

  sauvegarderUtilisateur(utilisateur: model.Utilisateur): Observable<{} | void> {
    const donnees = new HttpParams()
      .set('login', utilisateur.login)
      .set('mdp', utilisateur.mdp);

    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs';
    return this.http.post<void>(url, donnees, this.restUtils.creerHeaderPost());
  }

  supprimerUtilisateur(utilisateur: model.Utilisateur): Observable<{} | void> {
    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs/' + utilisateur.login;
    return this.http.delete<void>(url, this.restUtils.creerHeaderPost());
  }
}
