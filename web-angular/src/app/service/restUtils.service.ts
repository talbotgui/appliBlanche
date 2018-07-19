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
