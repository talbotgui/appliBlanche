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

    // Pour lever le flag sur le cache de la méthode estConnecte()
    this.aDemandeLaDeconnexion = false;

    // Appel au login
    const donnees = { login: login, mdp: mdp };
    this.http.post<void>('http://localhost:9090/applicationBlanche/login', donnees, { observe: 'response' })
      .pipe(catchError<any>(error => {
        // Suppression du token si le login est une erreur
        localStorage.removeItem('JWT');

        // Dans le cas d'une erreur 403 (paramètres de connexion par exemple)
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

  private aDemandeLaDeconnexion = false;
  deconnecter(): void {
    this.aDemandeLaDeconnexion = true;
    localStorage.removeItem('JWT');
  }

  private tokenDejaValide = false;
  estConnecte(): Observable<{} | boolean> {
    if (this.aDemandeLaDeconnexion) {
      return Observable.of(false);
    } else if (this.tokenDejaValide) {
      const token = localStorage.getItem('JWT');
      return Observable.of(!!token);
    } else {
      return this.invaliderTokenSiPresentEtExpire();
    }
  }

  listerUtilisateurs(): Observable<{} | model.Utilisateur[]> {
    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs';
    return this.http.get<model.Utilisateur[]>(url, this.restUtils.creerHeader());
  }

  invaliderTokenSiPresentEtExpire(): Observable<{} | model.Utilisateur> {
    const url = 'http://localhost:9090/applicationBlanche/v1/utilisateurs/moi';
    return this.http.get<model.Utilisateur>(url, this.restUtils.creerHeader()).pipe(catchError<any, boolean>(() => {
      localStorage.removeItem('JWT');
      return Observable.of(false);
    }));
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
