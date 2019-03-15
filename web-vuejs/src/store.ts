import Vue from 'vue';
import Vuex from 'vuex';

import { MessageErreur, Severite } from '@/model/erreur';
import { Utilisateur } from '@/model/model';
import { Reservation, Chambre, Formule } from './model/reservation-model';

Vue.use(Vuex);

export default new Vuex.Store({
    // Pour se forcer à bien utiliser le store
    // Documentation : https://vuex.vuejs.org/guide/strict.html
    strict: true,

    // Les états conservés par Vuex
    state: {
        // Dernier message
        messageErreurHttp: new MessageErreur('Bienvenue dans l\'application', Severite.Info, []),
        // Token pour appeler l'API
        tokenApi: (localStorage.getItem('JWT')) ? '' + localStorage.getItem('JWT') : '',
        // Utilisateur connecté
        utilisateur: null,
        // Flag indiquant que l'utilisateur s'est déconnecté
        aDemandeLaDeconnexion: false,
        // Flag evitant l'appel REST pour valider le token
        tokenDejaValide: false,
        // Reservation en cours d'édition
        reservationEnCoursDedition: new Reservation('', new Date(), undefined, '', new Chambre('', ''), new Formule('', '', 0)),
    },

    // Getter donnant accès aux états
    getters: {
        getDernierMessageErreurHttp(state, getters): MessageErreur {
            return state.messageErreurHttp;
        },
        getTokenApi(state, getters): string {
            return state.tokenApi;
        },
        getDemandeLaDeconnexion(state, getter): boolean {
            return state.aDemandeLaDeconnexion;
        },
        getTokenDejaValide(state, getter): boolean {
            return state.tokenDejaValide;
        },
        getReservationEnCoursDedition(state, getter): Reservation | undefined {
            if (state.reservationEnCoursDedition.reference) {
                return state.reservationEnCoursDedition;
            } else {
                return undefined;
            }
        },
    },

    // Modifications synchrones des états
    mutations: {
        declarerErreurHttp(state: any, message: MessageErreur) {
            state.messageErreurHttp = message;
        },
        viderErreurHttp(state: any) {
            state.messageErreurHttp = undefined;
        },
        declarerUneConnexionUtilisateurToken(state: any, token: string) {
            localStorage.setItem('JWT', token);
            state.tokenDejaValide = true;
            state.tokenApi = token;
        },
        declarerUneConnexionUtilisateur(state: any, utilisateur: Utilisateur) {
            state.utilisateur = utilisateur;
        },
        invaliderConnexionUtilisateur(state: any) {
            state.tokenApi = undefined;
            state.tokenDejaValide = false;
            localStorage.removeItem('JWT');
        },
        demandeDeConnexion(state: any) {
            state.aDemandeLaDeconnexion = false;
            state.tokenDejaValide = false;
        },
        demandeDeDeconnexion(state: any) {
            state.aDemandeLaDeconnexion = true;
        },
        definirReservationEnCoursDedition(state: any, reservation: Reservation) {
            state.reservationEnCoursDedition = reservation;
        },
        retirerReservationEnCoursDedition(state: any) {
            state.reservationEnCoursDedition = new Reservation('', new Date(), undefined, '', new Chambre('', ''), new Formule('', '', 0));
        },
    },

    // Modifications asynchrones des états
    actions: {},
});
