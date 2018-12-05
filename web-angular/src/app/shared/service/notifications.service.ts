import { Injectable } from '@angular/core';

import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { LocaleService, TranslationService } from 'angular-l10n';
import { StompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';

import { environment } from '../../../environments/environment';

import { Reservation } from '../../reservations/model/model';

/** Composant TS permettant de recevoir/envoyer des messages sur des WEB sockets */
@Injectable()
export class NotificationsService {

  /** Service permettant la connexion au socket du backend */
  stompService: StompService;

  /** Composant informatif */
  private snackbarConfig: MatSnackBarConfig = { duration: 3000, panelClass: 'snackbarInfo' };

  /** Constructeur du composant */
  constructor(private snackBar: MatSnackBar, private translationService: TranslationService, private localService: LocaleService) {
    this.initialiserSocket();
  }

  /** Initialisation de la socket */
  initialiserSocket() {
    console.log('Initialisation de la socket du backend');

    // calcul de l'URL de la socket
    const url = environment.baseUrl.replace('http://', 'ws://').replace('https://', 'ws://') + '/websocket';

    // Lecture du token
    const token = localStorage.getItem('JWT');
    if (!token) {
      return;
    }

    // Connexion
    this.stompService = new StompService({
      url,
      headers: { Authorization: token, TOTO: 'coucou' },
      heartbeat_in: 0, heartbeat_out: 20000, reconnect_delay: 5000,
      debug: false
    });

    // Log à chaque connexion/reconnexion
    this.stompService.connected$.subscribe(() => { console.log('Reconnexion à la socket du backend'); });

    // Ecoute des messages envoyés par le backend sur le topic 'nouvelleReservation'
    this.stompService.subscribe('/topic/nouvelleReservation').subscribe((m: Message) => {
      let message = this.translationService.translate('notification_nouvelleReservation', undefined, this.localService.getCurrentLanguage());
      if (m.body) {
        const r: Reservation = JSON.parse(m.body);
        message += ' ' + r.client + ' (' + r.dateDebut + ' => ' + r.dateFin + ')';
      }
      this.snackBar.open(message, undefined, this.snackbarConfig);
    });
  }
}
