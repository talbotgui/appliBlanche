import { Component, OnInit } from '@angular/core';

import { LocaleService } from 'angular-l10n';

@Component({ selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css'] })
export class AppComponent implements OnInit {

  constructor(public locale: LocaleService) { }

  ngOnInit(): void {
  }

  // Méthode permettant de changer de langue
  changerLaLangueDesLibelles(language: string): void {
    this.locale.setCurrentLanguage(language);
  }
}
