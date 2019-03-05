import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';
import { Reservation, Formule, Option, Produit, Chambre, Consommation, Facture, MoyenDePaiement, Paiement } from '@/model/reservation-model';
import { DateUtils } from '../utilitaire/dateUtils';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class ReservationService {

    /** Dépendance */
    private restUtils: RestUtils;
    private dateUtils: DateUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
        this.dateUtils = new DateUtils();
    }

    /** Liste des chambres */
    public listerChambres(): Observable<{} | Chambre[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/chambres';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste des produits */
    public listerProduits(): Observable<{} | Produit[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/produits';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste des formules */
    public listerFormules(): Observable<{} | Formule[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/formules';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste des options */
    public listerOptions(): Observable<{} | Option[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/options';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste les consommations d'une reservation */
    public listerConsommation(referenceReservation: string): Observable<{} | Consommation[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/consommations';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste les reservations en cours */
    public listerReservationsEnCours(): Observable<{} | Reservation[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations?etat=EN_COURS';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste des réservations facturables */
    public listerReservationsFacturables(): Observable<{} | Reservation[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations?etat=EN_COURS';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Liste des réservations facturées */
    public listerReservationsFacturees(): Observable<{} | Reservation[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations?etat=FACTUREE';
        return from(axios.get(url, this.restUtils.creerHeader({ clef: 'Accept', valeur: 'application/json;details' })));
    }

    /** Liste des réservations entre deux dates. */
    public rechercherReservations(dateDebut: Date, dateFin: Date): Observable<{} | Reservation[]> {
        const debut = this.dateUtils.formaterDate(dateDebut);
        const fin = this.dateUtils.formaterDate(dateFin);
        const url = process.env.VUE_APP_URL_API + '/v1/reservations?dateDebut=' + debut + '&dateFin=' + fin;
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'une consommation via l'API */
    public sauvegarderConsommation(referenceReservation: string, consommation: Consommation): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/consommations';
        return from(axios.post(url, consommation, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'une chambre via l'API */
    public sauvegarderChambre(chambre: Chambre): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/chambres';
        return from(axios.post(url, chambre, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un produit via l'API */
    public sauvegarderProduit(produit: Produit): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/produits/';
        return from(axios.post(url, produit, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un formule via l'API */
    public sauvegarderFormule(formule: Formule): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/formules/';
        return from(axios.post(url, formule, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un option via l'API */
    public sauvegarderOption(option: Option): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/options/';
        return from(axios.post(url, option, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'une reservation via l'API */
    public sauvegarderReservation(reservation: Reservation): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/';
        return from(axios.post(url, reservation, this.restUtils.creerHeader()));
    }

    /** Changement du statut d'une reservation via l'API */
    public changerEtatReservation(referenceReservation: string, etat: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/etat?etat=' + etat;
        return from(axios.put(url, etat, this.restUtils.creerHeader()));
    }

    /** Suppression d'une consommation via l'API */
    public supprimerConsommation(referenceReservation: string, referenceConsommation: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/consommations/' + referenceConsommation;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Réduire la quantité d'une consommation via l'API */
    public reduireConsommation(referenceReservation: string, referenceConsommation: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/consommations/' + referenceConsommation + '?quantite=-1';
        return from(axios.put(url, undefined, this.restUtils.creerHeader()));
    }

    /** Suppression d'un produit via l'API */
    public supprimerProduit(referenceProduit: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/produits/' + referenceProduit;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Suppression d'un formule via l'API */
    public supprimerFormule(referenceFormule: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/formules/' + referenceFormule;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Suppression d'un option via l'API */
    public supprimerOption(referenceOption: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/options/' + referenceOption;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Suppression d'un chambre via l'API */
    public supprimerChambre(referenceChambre: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/chambres/' + referenceChambre;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'une reservation via l'API */
    public facturer(refReservation: string): Observable<{} | Facture> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + refReservation + '/facturer';
        return from(axios.post(url, undefined, this.restUtils.creerHeader()));
    }

    /** Liste des moyens de paiement */
    public listerMoyensDePaiement(): Observable<{} | MoyenDePaiement[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/moyensDePaiement';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Suppression d'un moyen de paiement via l'API */
    public supprimerMoyenDePaiement(referenceMoyenDePaiement: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/moyensDePaiement/' + referenceMoyenDePaiement;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un MoyenDePaiement via l'API */
    public sauvegarderMoyenDePaiement(mdp: MoyenDePaiement): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/moyensDePaiement/';
        return from(axios.post(url, mdp, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un Paiement via l'API */
    public sauvegarderPaiement(referenceReservation: string, paiement: Paiement): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/paiements/';
        return from(axios.post(url, paiement, this.restUtils.creerHeader()));
    }

    /** Charge une référence */
    public chargerReservation(referenceReservation: string): Observable<{} | Reservation> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation;
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Appel à l'API pour obtenir le montant total */
    public calculerMontantTotal(referenceReservation: string): Observable<{} | number> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/montantTotal';
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Calcul du montant restant du à partir des paiements chargés et du montant total calculé par l'API */
    public calculerMontantRestantDu(montantTotal: number, paiements: Paiement[]): number {
        let montantPaye = 0;
        paiements.forEach((p) => montantPaye += (p.montant) ? p.montant : p.moyenDePaiement.montantAssocie);
        return montantTotal - montantPaye;
    }

    /** Suppression d'un paiement via l'API */
    public supprimerPaiement(referenceReservation: string, referencePaiement: string): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/reservations/' + referenceReservation + '/paiements/' + referencePaiement;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }
}
