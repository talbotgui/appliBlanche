import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

/** Composant passe-plat de la classe HttpClient permettant de bouchoner durant les tests. */
@Injectable()
export class HttpProxy {

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private http: HttpClient) { }

  /**
     * Construct a DELETE request which interprets the body as JSON and returns it.
     *
     * @return an `Observable` of the body as type `T`.
     */
  delete<T>(
    url: string, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> {
    return this.http.delete<T>(url, options);
  }

  /**
     * Construct a GET request which interprets the body as JSON and returns it.
     *
     * @return an `Observable` of the body as type `T`.
     */
  get<T>(
    url: string, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> {
    return this.http.get<T>(url, options);
  }

  /**
     * Construct a POST request which interprets the body as JSON and returns it.
     *
     * @return an `Observable` of the body as type `T`.
     */
  post<T>(
    url: string, body: any | null, options?: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe?: 'body';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<T> | Observable<HttpResponse<T>> {
    return this.http.post<T>(url, body, options);
  }

  /**
     * Construct a POST request which interprets the body as JSON and returns the full response.
     *
     * @return an `Observable` of the `HttpResponse` for the request, with a body type of `T`.
     */
  postForResponse<T>(
    url: string, body: any | null, options: {
      headers?: HttpHeaders | { [header: string]: string | string[]; }; observe: 'response';
      params?: HttpParams | { [param: string]: string | string[]; }; reportProgress?: boolean; responseType?: 'json'; withCredentials?: boolean;
    }
  ): Observable<HttpResponse<T>> {
    return this.http.post<T>(url, body, options);
  }
}
