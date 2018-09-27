import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

/** Composant passe-plat de la classe HttpClient permettant de bouchoner durant les tests. */
@Injectable()
export class HttpProxy {

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private http: HttpClient) { }

  delete<T>(
    url: string, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> {
    return this.http.delete<T>(url, options);
  }

  get<T>(
    url: string, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> {
    return this.http.get<T>(url, options);
  }

  post<T>(
    url: string, body: any | null, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> | Observable<HttpResponse<T>> {
    return this.http.post<T>(url, body, options);
  }

  postForResponse<T>(
    url: string, body: any | null, options: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe: 'response';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<HttpResponse<T>> {
    return this.http.post<T>(url, body, options);
  }
}
