import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs'

import { RestUtilsService } from '../../service/restUtils.service';
import { HttpProxy } from '../../service/httpProxy.component';

import * as model from '../model/model';

/** Composant TS d'interface avec les API Back de manipulation des réservations */
@Injectable()
export class ReservationService {

  private CHAMBRES = [new model.Chambre("ch1", "Chambre1"), new model.Chambre("ch2", "Chambre2"), new model.Chambre("ch3", "Chambre3")];

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService) { }

  listerChambres(): Observable<model.Chambre[]> {
    return of(this.CHAMBRES);
  }

  /** Liste des réservations entre deux dates. */
  rechercherReservations(dateDebut: Date, dateFin: Date): Observable<model.Reservation[]> {
    const liste: model.Reservation[] = [
      new model.Reservation("R1", new Date(2018, 10, 1), new Date(2018, 10, 3), "ClientR1-2N-C1", this.CHAMBRES[0]),
      new model.Reservation("R2", new Date(2018, 10, 2), new Date(2018, 10, 3), "ClientR2-1N-C2", this.CHAMBRES[1]),
      new model.Reservation("R3", new Date(2018, 9, 30), new Date(2018, 11, 1), "ClientR3-1M-C3", this.CHAMBRES[2]),
      new model.Reservation("R4", new Date(2018, 10, 4), new Date(2018, 10, 6), "ClientR4-2N-C1", this.CHAMBRES[0]),
      new model.Reservation("R5", new Date(2018, 10, 5), new Date(2018, 10, 9), "ClientR5-4N-C2", this.CHAMBRES[1])
    ];
    return of(liste);
  }
}
