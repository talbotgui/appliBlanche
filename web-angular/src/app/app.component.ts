import { Component } from '@angular/core';
import { Language } from 'angular-l10n';

import { LocaleService } from 'angular-l10n';
import { PwaService } from './service/pwa.service';

/** Composant ROOT de l'application */
@Component({ selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css'] })
export class AppComponent {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Constructeur avec injection */
  constructor(public locale: LocaleService, private pwaService: PwaService) { }

  /** Méthode permettant de changer de langue */
  changerLaLangueDesLibelles(language: string): void {
    this.locale.setCurrentLanguage(language);
  }

  /** L'application, dans ses conditions d'utilisation, est-elle éligible à l'installation */
  get installationPwaAutorisee(): boolean {
    return this.pwaService.estInstallationPwaAutorisee();
  }

  /** Installer l'application comme une application mobile */
  installerPwa(): void {
    this.pwaService.installerPwa();
  }
}
