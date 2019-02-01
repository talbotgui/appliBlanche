import { Component } from '@angular/core';

import { PwaService } from '../service/pwa.service';

/** Menu de l'application */
@Component({ selector: 'cadre-pwa', templateUrl: './cadre-pwa.component.html', styleUrls: ['./cadre-pwa.component.css'] })
export class CadrePwaComponent {

  /** Flag indiquant que l'utilisateur a fermé le message PWA */
  private flagMessagePwa = true;

  /** Constructeur avec injection */
  constructor(private pwaService: PwaService) { }

  /** L'application, dans ses conditions d'utilisation, est-elle éligible à l'installation semi-automatique */
  get installationPwaSemiAutomatiqueAutorisee(): boolean {
    return this.flagMessagePwa && this.pwaService.installationPwaSemiAutomatiqueAutorisee();
  }

  /** L'application, dans ses conditions d'utilisation, est-elle éligible à l'installation sur IOS */
  get installationPwaManuelleIosAutorisee(): boolean {
    return this.flagMessagePwa && this.pwaService.installationPwaManuelleIosAutorisee();
  }

  /** Le navigateur ne supporte pas les PWA */
  get installationPwaImpossible(): boolean {
    return this.flagMessagePwa && this.pwaService.installationPwaImpossible();
  }

  /** Installer l'application comme une application mobile */
  installerPwa(): void {
    this.pwaService.installerPwa();
  }

  masquerMessagePwa() {
    this.flagMessagePwa = false;
  }
}
