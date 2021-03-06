import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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

  /** Liste des formules */
  listerFormules(): Observable<model.Formule[]> {
    const url = environment.baseUrl + '/v1/formules';
    return this.http.get<model.Formule[]>(url, this.restUtils.creerHeader());
  }

  /** Liste des options */
  listerOptions(): Observable<model.Option[]> {
    const url = environment.baseUrl + '/v1/options';
    return this.http.get<model.Option[]>(url, this.restUtils.creerHeader());
  }

  /** Liste les consommations d'une reservation */
  listerConsommation(referenceReservation: string): Observable<model.Consommation[]> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations';
    return this.http.get<model.Consommation[]>(url, this.restUtils.creerHeader());
  }

  /** Liste les reservations en cours */
  listerReservationsEnCours(): Observable<model.Reservation[]> {
    const url = environment.baseUrl + '/v1/reservations?etat=EN_COURS';
    return this.http.get<model.Reservation[]>(url, this.restUtils.creerHeader());
  }

  /** Liste des réservations facturables */
  listerReservationsFacturables(): Observable<model.Reservation[]> {
    const url = environment.baseUrl + '/v1/reservations?etat=EN_COURS';
    return this.http.get<model.Reservation[]>(url, this.restUtils.creerHeader({ clef: 'Accept', valeur: 'application/json;details' }));
  }

  /** Liste des réservations facturées */
  listerReservationsFacturees(): Observable<model.Reservation[]> {
    const url = environment.baseUrl + '/v1/reservations?etat=FACTUREE';
    return this.http.get<model.Reservation[]>(url, this.restUtils.creerHeader({ clef: 'Accept', valeur: 'application/json;details' }));
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

  /** Sauvegarde d'un formule via l'API */
  sauvegarderFormule(formule: model.Formule): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/formules/';
    return this.http.post<void>(url, formule, this.restUtils.creerHeaderPost());
  }

  /** Sauvegarde d'un option via l'API */
  sauvegarderOption(option: model.Option): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/options/';
    return this.http.post<void>(url, option, this.restUtils.creerHeaderPost());
  }

  /** Sauvegarde d'une reservation via l'API */
  sauvegarderReservation(reservation: model.Reservation): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/';
    return this.http.post<void>(url, reservation, this.restUtils.creerHeaderPost());
  }

  /** Changement du statut d'une reservation via l'API */
  changerEtatReservation(referenceReservation: string, etat: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/etat?etat=' + etat;
    return this.http.put<void>(url, etat, this.restUtils.creerHeader());
  }

  /** Suppression d'une consommation via l'API */
  supprimerConsommation(referenceReservation: string, referenceConsommation: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations/' + referenceConsommation;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Réduire la quantité d'une consommation via l'API */
  reduireConsommation(referenceReservation: string, referenceConsommation: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/consommations/' + referenceConsommation + '?quantite=-1';
    return this.http.put<void>(url, undefined, this.restUtils.creerHeader());
  }

  /** Suppression d'un produit via l'API */
  supprimerProduit(referenceProduit: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/produits/' + referenceProduit;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Suppression d'un formule via l'API */
  supprimerFormule(referenceFormule: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/formules/' + referenceFormule;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Suppression d'un option via l'API */
  supprimerOption(referenceOption: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/options/' + referenceOption;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Suppression d'un chambre via l'API */
  supprimerChambre(referenceChambre: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/chambres/' + referenceChambre;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** pour exposer la méthode de formattage aux controleurs */
  formaterDate(date: Date): string {
    return this.http.formaterDate(date);
  }

  /** Sauvegarde d'une reservation via l'API */
  facturer(refReservation: string): Observable<{} | model.Facture> {
    const url = environment.baseUrl + '/v1/reservations/' + refReservation + '/facturer';
    return this.http.post<model.Facture>(url, undefined, this.restUtils.creerHeaderPost());
  }

  /** Liste des moyens de paiement */
  listerMoyensDePaiement(): Observable<model.MoyenDePaiement[]> {
    const url = environment.baseUrl + '/v1/moyensDePaiement';
    return this.http.get<model.MoyenDePaiement[]>(url, this.restUtils.creerHeader());
  }

  /** Suppression d'un moyen de paiement via l'API */
  supprimerMoyenDePaiement(referenceMoyenDePaiement: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/moyensDePaiement/' + referenceMoyenDePaiement;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }

  /** Sauvegarde d'un MoyenDePaiement via l'API */
  sauvegarderMoyenDePaiement(mdp: model.MoyenDePaiement): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/moyensDePaiement/';
    return this.http.post<void>(url, mdp, this.restUtils.creerHeaderPost());
  }

  /** Sauvegarde d'un Paiement via l'API */
  sauvegarderPaiement(referenceReservation: string, paiement: model.Paiement): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/paiements/';
    return this.http.post<void>(url, paiement, this.restUtils.creerHeaderPost());
  }

  /** Charge une référence */
  chargerReservation(referenceReservation: string): Observable<model.Reservation> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation;
    return this.http.get<model.Reservation>(url, this.restUtils.creerHeader());
  }

  /** Appel à l'API pour obtenir le montant total */
  calculerMontantTotal(referenceReservation: string): Observable<number> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/montantTotal';
    return this.http.get<number>(url, this.restUtils.creerHeader());
  }

  /** Calcul du montant restant du à partir des paiements chargés et du montant total calculé par l'API */
  calculerMontantRestantDu(montantTotal: number, paiements: model.Paiement[]): number {
    let montantPaye = 0;
    paiements.forEach((p) => montantPaye += (p.montant) ? p.montant : p.moyenDePaiement.montantAssocie);
    return montantTotal - montantPaye;
  }

  /** Suppression d'un paiement via l'API */
  supprimerPaiement(referenceReservation: string, referencePaiement: string): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/reservations/' + referenceReservation + '/paiements/' + referencePaiement;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }
}
