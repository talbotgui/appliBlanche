import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { TranslationProvider } from 'angular-l10n';

import { HttpProxy } from './httpProxy.component';

// La configuration de l'application
import { environment } from '../../../environments/environment';

/** Fournisseur de traduction pour le plugin l10n */
@Injectable() export class MyTranslationProvider implements TranslationProvider {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy) { }

  /**
   * This method must contain the logic of data access.
   * @param language The current language
   * @param args The object set during the configuration of 'providers'
   * @return An observable of an object of translation data: {key: value}
   */
  public getTranslation(language: string, args: any): Observable<any> {
    return this.http.get<{ [key: string]: string; }>(environment.baseUrl + '/i18n/' + language);
  }
}
