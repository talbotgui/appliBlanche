import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs';

import { RestUtilsService } from '../../shared/service/restUtils.service';
import { HttpProxy } from '../../shared/service/httpProxy.component';

import { environment } from '../../../environments/environment';

import * as model from '../model/model';

/** Composant TS d'interface avec les API Back de manipulation des réservations */
@Injectable()
export class ReservationService {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService) { }

  /** Liste des chambres */
  listerChambres(): Observable<model.Chambre[]> {
    const url = environment.baseUrl + '/v1/chambres';
    return this.http.get<model.Chambre[]>(url, this.restUtils.creerHeader());
  }

  /** Liste des produits */
  listerProduits(): Observable<model.Produit[]> {
    const url = environment.baseUrl + '/v1/produits';
    return this.http.get<model.Produit[]>(url, this.restUtils.creerHeader());
  }

  /** Liste les consommations d'une reservation */
  listerConsommation(referenceReservation: string): Observable<model.Consommation[]> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations';
    return this.http.get<model.Consommation[]>(url, this.restUtils.creerHeader());
  }

  /** Liste des réservations entre deux dates. */
  rechercherReservations(dateDebut: Date, dateFin: Date): Observable<model.Reservation[]> {
    const debut = this.http.formaterDate(dateDebut);
    const fin = this.http.formaterDate(dateFin);
    const url = environment.baseUrl + '/v1/reservations?dateDebut=' + debut + '&dateFin=' + fin;
    return this.http.get<model.Reservation[]>(url, this.restUtils.creerHeader());
  }

  /** Sauvegarde d'une consommation via l'API */
  sauvegarderConsommation(referenceReservation: string, consommation: model.Consommation): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations';
    return this.http.post<void>(url, consommation, this.restUtils.creerHeaderPost());
  }


  /** Sauvegarde d'une chambre via l'API */
  sauvegarderChambre(chambre: model.Chambre): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/chambres';
    return this.http.post<void>(url, chambre, this.restUtils.creerHeaderPost());
  }

  /** Sauvegarde d'un produit via l'API */
  sauvegarderProduit(produit: model.Produit): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/produits/';
    return this.http.post<void>(url, produit, this.restUtils.creerHeaderPost());
  }

  /** Sauvegarde d'une reservation via l'API */
  sauvegarderReservation(reservation: model.Reservation): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/';
    return this.http.post<void>(url, reservation, this.restUtils.creerHeaderPost());
  }

  /** Suppression d'une consommation via l'API */
  supprimerConsommation(referenceReservation: string, referenceConsommation: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations/' + referenceConsommation;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Suppression d'un produit via l'API */
  supprimerProduit(referenceProduit: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/produits/' + referenceProduit;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }
}
