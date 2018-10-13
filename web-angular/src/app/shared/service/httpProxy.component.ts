import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
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
    return this.http.get<T>(url, options).pipe(map((r) => this.transformerLesDatesDansLesObjetsEnVraiDate(r)));
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
    const bodyTransforme = this.transformerLesDatesEnString(body);
    return this.http.post<T>(url, bodyTransforme, options);
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
    const bodyTransforme = this.transformerLesDatesEnString(body);
    return this.http.post<T>(url, bodyTransforme, options);
  }

  /** Format une Date en chaine de caractères */
  formaterDate(laDate: Date): string {
    if (laDate) {
      return laDate.getFullYear() + '-' + this.formatNombre(laDate.getMonth() + 1) + '-' + this.formatNombre(laDate.getDate());
    } else {
      return '';
    }
  }

  /** Retourne un nombre sur 2 caractères */
  private formatNombre(n: number): string {
    if (n < 10) {
      return '0' + n;
    } else {
      return '' + n;
    }
  }

  /** Parcours l'objet et transforme les dates en String */
  private transformerLesDatesEnString(obj: any) {

    // Parcours des attributs
    for (const attr in obj) {

      // Si c'est une string
      if (obj[attr] && obj[attr] instanceof Date) {
        obj[attr] = this.formaterDate(obj[attr] as Date);
      }

      // Si c'est un objet
      else if (obj[attr] && obj[attr] instanceof Object) {
        this.transformerLesDatesDansLesObjetsEnVraiDate(obj[attr]);
      }
    }
    return obj;
  }

  /** Parcours l'objet et transforme les string contenant des dates en Date */
  private transformerLesDatesDansLesObjetsEnVraiDate(obj: any) {

    // Parcours des attributs
    for (const attr in obj) {

      // Si c'est une string
      if (obj[attr] && typeof obj[attr] === 'string') {
        const s: string = (obj[attr] as string);
        if (s.charAt(4) === '-' && s.charAt(7) === '-') {
          obj[attr] = new Date(s);
        }
      }

      // Si c'est un objet
      else if (obj[attr] && obj[attr] instanceof Object) {
        this.transformerLesDatesDansLesObjetsEnVraiDate(obj[attr]);
      }
    }
    return obj;
  }
}
