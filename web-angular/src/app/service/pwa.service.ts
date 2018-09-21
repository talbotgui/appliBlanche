// Sources : https://medium.com/poka-techblog/turn-your-angular-app-into-a-pwa-in-4-easy-steps-543510a9b626

import { Injectable } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';

@Injectable()
export class PwaService {

  private promptEvent: any;

  /** Contructeur avec injection des dépendances */
  constructor(private swUpdate: SwUpdate) {

    // Ajout d'un listener sur l'évènement envoyé par le navigateur si l'application
    // répond aux critères d'installation d'une PWA sur le terminal
    window.addEventListener('beforeinstallprompt', (event) => {
      this.promptEvent = event;
    });
  }

  /** L'application, dans ses conditions d'utilisation, est-elle éligible à l'installation */
  estInstallationPwaAutorisee(): boolean {
    return !!this.promptEvent;
  }

  /** Installation de l'application PWA */
  installerPwa() {
    this.promptEvent.prompt();
  }
}
