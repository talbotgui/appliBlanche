import { Injectable } from '@angular/core';

import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { LocaleService, TranslationService } from 'angular-l10n';
import { RxStompService } from '@stomp/ng2-stompjs';
import { RxStompConfig } from '@stomp/rx-stomp';
import { Message } from '@stomp/stompjs';

import { environment } from '../../../environments/environment';

import { Reservation } from '../../reservations/model/model';
import { Context } from './context';

/** Composant TS permettant de recevoir/envoyer des messages sur des WEB sockets */
@Injectable()
export class NotificationsService {

  /** Client webSocket */
  private stompService: RxStompService;

  /** Composant informatif */
  private snackbarConfig: MatSnackBarConfig = { duration: 3000, panelClass: 'snackbarInfo' };

  /** Constructeur du composant */
  constructor(private snackBar: MatSnackBar, private translationService: TranslationService, private localService: LocaleService, private context: Context) {

    // A la connexion d'un utilisateur, on initialise le socket
    this.context.notificationsConnexionDunUtilisateur.subscribe((u) => {
      if (u) {
        this.initialiserSocketPostConnexion();
      } else {
        this.deconnexion();
      }
    });
  }

  /** Déconnexion */
  deconnexion() {
    if (this.stompService) {
      console.log('Fermeture de la socket au backend');
      this.stompService.deactivate();
    }
  }

  /** Initialisation de la socket */
  initialiserSocketPostConnexion() {
    console.log('Initialisation de la socket au backend');

    // calcul de l'URL de la socket
    const url = environment.baseUrl.replace('http://', 'ws://').replace('https://', 'ws://') + '/websocket';

    // Lecture du token
    const token = localStorage.getItem('JWT');
    if (!token) {
      return;
    }

    // Configuration
    const config: RxStompConfig = {
      brokerURL: url, connectHeaders: { Authorization: token },
      heartbeatIncoming: 0, heartbeatOutgoing: 20000, reconnectDelay: 5000
    };
    this.stompService = new RxStompService();
    this.stompService.configure(config);

    // Log à chaque connexion/reconnexion
    this.stompService.connected$.subscribe(() => { console.log('Reconnexion à la socket du backend'); });

    // Démarrage
    this.stompService.activate();

    // Ecoute des messages envoyés par le backend sur le topic 'nouvelleReservation'
    this.stompService.watch('/topic/nouvelleReservation').subscribe(
      (m: Message) => {
        let message = this.translationService.translate('notification_nouvelleReservation', undefined, this.localService.getCurrentLanguage());
        if (m.body) {
          const r: Reservation = JSON.parse(m.body);
          message += ' ' + r.client + ' (' + r.dateDebut + ' => ' + r.dateFin + ')';
        }
        this.snackBar.open(message, undefined, this.snackbarConfig);
      }
    );
  }
}
