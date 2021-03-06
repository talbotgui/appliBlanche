import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../environments/environment';

import { RestUtilsService } from '../shared/service/restUtils.service';
import { HttpProxy } from '../shared/service/httpProxy.component';
import { Context } from '../shared/service/context';

import * as model from '../model/model';

/** Composant TS de gestion de la sécurité */
@Injectable()
export class SecuriteService {

  /** Flag indiquant que l'utilisateur s'est déconnecté */
  private aDemandeLaDeconnexion = false;

  /** Flag evitant l'appel REST pour valider le token */
  private tokenDejaValide = false;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService, private context: Context) { }

  /** Tentative de connexion d'un utilisateur */
  connecter(login: string, mdp: string, callback: () => void, callbackErreurParametresConnexion: (error: any) => void): void {

    // Pour lever le flag sur le cache de la méthode estConnecte()
    this.aDemandeLaDeconnexion = false;

    // Appel au login
    const donnees = { login, mdp };
    this.http.postForResponse<void>(environment.baseUrl + '/login', donnees, { observe: 'response' })
      .pipe(catchError<any>((error: any) => {
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

        // Sauvegarde du token dans le localstorage
        const token = reponse.headers.get('Authorization');
        if (token) {
          localStorage.setItem('JWT', token);
          this.tokenDejaValide = true;
        }

        // Sauvegarde des informations de l'utilisateur connecté
        if (reponse.body) {
          this.conserverUtilisateurConnecteEnMemoire(reponse.body);
        }

        // Appel à la callback
        callback();
      });
  }

  /** Demande de déconnexion */
  deconnecter(): void {
    this.aDemandeLaDeconnexion = true;
    this.nettoyerDonneesDeConnexion();
  }

  /** Informe si l'utilisateur est bien connecté */
  estConnecte(): Observable<{} | boolean> {

    // Si l'utilisateur a demandé la déconnexion depuis le dernier raffichissement de la page (donc bien déconnecté)
    if (this.aDemandeLaDeconnexion) {
      return of(false);
    }

    // Si le token a déjà été validé depuis le dernier chargement de la page
    else if (this.tokenDejaValide) {
      const token = localStorage.getItem('JWT');
      return of(!!token);
    }

    // Tentative d'appel REST pour valider/invalider le token
    else {
      return this.invaliderTokenSiPresentEtExpire();
    }
  }

  /**
   * Appel à l'API REST /utilisateurs/moi et vérification de l'expiration du token
   *
   * S'il est encore bon, rien ne se passe. Sinon, le localStorage est vidé.
   */
  invaliderTokenSiPresentEtExpire(): Observable<{} | model.Utilisateur> {
    const url = environment.baseUrl + '/v1/utilisateurs/moi';
    return this.http.get<model.Utilisateur>(url, this.restUtils.creerHeader()).pipe(

      // sauvegarde des données de l'utilisateur
      map((u: model.Utilisateur) => {
        this.conserverUtilisateurConnecteEnMemoire(u);
        return u;
      }),

      // en cas d'erreur du service REST, on supprime le token
      catchError<any, boolean>(() => {
        this.nettoyerDonneesDeConnexion();
        return of(false);
      }));
  }

  validerAutorisations(clefs: string[]): string[] {
    // Lecture des données du storage
    const clefsDuStorage = localStorage.getItem('CLEFS');
    let toutesLesClefsAutorisees: string[] = [];
    if (clefsDuStorage) {
      toutesLesClefsAutorisees = clefsDuStorage.split('|');
    }
    // Validation des clefs fournies en entrée
    return clefs.filter((c) => toutesLesClefsAutorisees.indexOf(c) !== -1);
  }

  /** Conservation en memoire de l'utilisateur connecte */
  private conserverUtilisateurConnecteEnMemoire(utilisateur: model.Utilisateur): void {

    // Conservation des informations
    let clefsAutorisees: string[] = [];
    utilisateur.roles.forEach((r) => {
      clefsAutorisees = clefsAutorisees.concat(r.ressourcesAutorisees.map((ressource) => ressource.clef));
    });
    localStorage.setItem('CLEFS', clefsAutorisees.join('|'));

    // Notification de l'evenement
    this.context.notifierConnexionDunUtilisateur(utilisateur);
  }

  /** Supprime les informations conservées de l'utilisateur connecté */
  private nettoyerDonneesDeConnexion() {

    // Nettoyage des informations
    localStorage.removeItem('CLEFS');
    localStorage.removeItem('JWT');

    // Notification de l'evenement
    this.context.notifierConnexionDunUtilisateur(undefined);
  }
}
