import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import * as model from '../../model/model';

/**
 * Composant TS permettant de notifier des évènements depuis un composant et de prévenir les autres
 * composants intéressés par ce type de notification
 */
@Injectable()
export class Context {

  /** Connexion d'un utilisateur */
  private utilisateur = new BehaviorSubject<model.Utilisateur | undefined>(undefined);

  notifierConnexionDunUtilisateur(u: model.Utilisateur | undefined) {
    this.utilisateur.next(u);
  }

  get notificationsConnexionDunUtilisateur(): Observable<model.Utilisateur | undefined> {
    return this.utilisateur.asObservable();
  }
}
