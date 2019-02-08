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
      this.stompService.deactivate();
    }
  }

  /** Initialisation de la socket */
  initialiserSocketPostConnexion() {

    // calcul de l'URL de la socket
    const url = environment.baseUrl.replace('http://', 'ws://').replace('https://', 'wss://') + '/websocket';

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

    // Démarrage
    this.stompService.activate();

    // Ecoute des messages envoyés par le backend sur le topic 'nouvelleReservation'
    this.stompService.watch('/topic/nouvelleReservation').subscribe(this.traiterNotificationNouvelleReservation.bind(this));
  }

  private traiterNotificationNouvelleReservation(m: Message) {

    // Recupération des données
    let message = this.translationService.translate('notification_nouvelleReservation', undefined, this.localService.getCurrentLanguage());

    // Lecture des données et complément au message
    if (!m.body) {
      const r: Reservation = JSON.parse(m.body);
      message += ' ' + r.client + ' (' + r.dateDebut + ' => ' + r.dateFin + ')';
    }

    // Affichage d'un message dans la snackbar
    this.snackBar.open(message, undefined, this.snackbarConfig);

    // Notification par l'API NOTIFICATION
    // Documentation https://developer.mozilla.org/en-US/docs/Web/API/Notifications_API/Using_the_Notifications_API
    this.notifierDansLos(message);
  }

  /** Notification dans l'OS (Windows, OSx, Android) */
  // Documentation https://developer.mozilla.org/en-US/docs/Web/API/Notifications_API/Using_the_Notifications_API
  private notifierDansLos(message: string) {
    // Verification que le navigateur supporte les notifications
    if (!('Notification' in window)) {
      return;
    }

    // Si les notifications sont autorisées, on notifie
    else if (Notification.permission === 'granted') {
      const notification = new Notification(message);
    }

    // Sinon, on demande la permission puis on notifie une fois autorisé
    else if (Notification.permission !== 'denied') {
      Notification.requestPermission(function(permission) {
        if (permission === 'granted') {
          const notification = new Notification('Hi there!');
        }
      });
    }
  }
}
