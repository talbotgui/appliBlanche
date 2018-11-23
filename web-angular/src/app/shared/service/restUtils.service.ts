import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

/**
 *  Composant utilitaire pour traiter les appels aux API rest en général : entête de sécurité
 */
@Injectable()
export class RestUtilsService {

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private http: HttpClient) { }

  /** Création des options d'appels REST */
  public creerHeader(enteteSupplementaire?: { clef: string, valeur: string }): { headers: HttpHeaders } | undefined {

    // Token par défaut
    const headers: any = { 'Content-Type': 'application/json' };

    // ajout du token de l'utilisateur connecté
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }

    // Ajout de l'entete supplémentaire si demandé
    if (enteteSupplementaire) {
      headers[enteteSupplementaire.clef] = enteteSupplementaire.valeur;
    }
    return { headers: new HttpHeaders(headers) };
  }

  /** Création des options d'appels REST pour les POST */
  public creerHeaderPost(): { headers: HttpHeaders } | undefined {
    const headers: any = {};
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
    }
    return { headers: new HttpHeaders(headers) };
  }
}
