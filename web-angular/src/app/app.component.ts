import { Component } from '@angular/core';
import { Language } from 'angular-l10n';

import { LocaleService } from 'angular-l10n';
import { PwaService } from './service/pwa.service';
import { NotificationsService } from './shared/service/notifications.service';

/** Composant ROOT de l'application */
@Component({ selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css'] })
export class AppComponent {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /**
   * Constructeur avec injection
   * (l'import de NotificationsService n'est là que pour forcer son initialisation)
   */
  constructor(public locale: LocaleService, private notificationsService: NotificationsService) { }

  /** Méthode permettant de changer de langue */
  changerLaLangueDesLibelles(language: string): void {
    this.locale.setCurrentLanguage(language);
  }

}
