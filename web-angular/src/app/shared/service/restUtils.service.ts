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
  public creerHeader(): { headers: HttpHeaders } | undefined {
    const headers: any = { 'Content-Type': 'application/json' };
    const jwt = localStorage.getItem('JWT');
    if (jwt) {
      headers.Authorization = jwt;
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
