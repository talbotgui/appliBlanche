import Vue from 'vue';
import Vuex from 'vuex';
import { MessageErreur, Severite } from './model/erreur';

Vue.use(Vuex);

export default new Vuex.Store({
    // Pour se forcer à bien utiliser le store
    // Documentation : https://vuex.vuejs.org/guide/strict.html
    strict: true,

    // Les états conservés par Vuex
    state: {
        messageErreurHttp: new MessageErreur('Bienvenue dans l\'application', Severite.Info),
        tokenApi: '',
    },

    // Getter donnant accès aux états
    getters: {
        getDernierMessageErreurHttp(state, getters): MessageErreur {
            return state.messageErreurHttp;
        },
        getTokenApi(state, getters): string {
            return state.tokenApi;
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
        declarerUneConnexionUtilisateur(state: any, token: string) {
            state.tokenApi = token;
        },
        invaliderConnexionUtilisateur(state: any) {
            state.tokenApi = undefined;
        },
    },

    // Modifications asynchrones des états
    actions: {},
});
