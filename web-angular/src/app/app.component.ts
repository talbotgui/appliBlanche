import { Component, OnInit } from '@angular/core';

import { LocaleService, TranslationService, Language } from 'angular-l10n';

@Component({ selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css'] })
export class AppComponent implements OnInit {

  constructor(public locale: LocaleService, public translation: TranslationService) { }

  ngOnInit(): void {
    // Exemple de code permettant de récupérer un libellé depuis le controleur
    this.translation.translationChanged().subscribe(
      () => { console.debug(this.translation.translate('Title')); }
    );
  }

  // Méthode permettant de changer de langue
  changerLaLangueDesLibelles(language: string): void {
    this.locale.setCurrentLanguage(language);
  }
}
