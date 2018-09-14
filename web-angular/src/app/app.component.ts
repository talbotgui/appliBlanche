import { Component } from '@angular/core';

import { LocaleService } from 'angular-l10n';

/** Composant ROOT de l'application */
@Component({ selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css'] })
export class AppComponent {

  /** Constructeur avec injection */
  constructor(public locale: LocaleService) { }

  /** Méthode permettant de changer de langue */
  changerLaLangueDesLibelles(language: string): void {
    this.locale.setCurrentLanguage(language);
  }
}
