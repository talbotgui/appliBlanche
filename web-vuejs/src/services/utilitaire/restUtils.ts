import axios from 'axios';
import store from '@/store';

import { MessageErreur, Severite } from '@/model/erreur';

export default class RestUtils {

    /** Constructeur initialisant le gestionnaire d'erreur */
    constructor() {
        this.declarerIntercepteurGestionDerreur();
    }

    /** Creation des entetes d'appel à une méthode REST */
    public creerHeader(enteteSupplementaire?: { clef: string, valeur: string }): { headers: any } | undefined {
        // Entete de base
        const entetes: any = { 'Content-Type': 'application/json' };

        // Ajout d'un entete supplémentaire
        if (enteteSupplementaire) {
            entetes[enteteSupplementaire.clef] = enteteSupplementaire.valeur;
        }

        // Ajout du token de l'API (si disponible)
        if (store.getters.getTokenApi) {
            entetes.authorization = store.getters.getTokenApi;
        }

        return { headers: entetes };
    }

    // Creation d'un intercepteur HTTP à la réception de la réponse HTTP
    public declarerIntercepteurGestionDerreur() {
        // Pour éviter de déclarer plusieurs fois le gestionnaire d'erreur
        const clef = 'declarerIntercepteurGestionDerreur';
        if (!document.body.className.includes(clef)) {
            document.body.className += ' ' + clef;

            axios.interceptors.response.use(
                // méthode onFulfilled de l'intercepteur
                (response: any) => {
                    // Si c'est une requete de connexion, on sauvegarde le token
                    if (response && response.request && response.request.responseURL && response.request.responseURL.endsWith('login')
                        && response.headers && response.headers.authorization) {
                        store.commit('declarerUneConnexionUtilisateurToken', response.headers.authorization);
                    }

                    // Renvoi uniquement des données de la réponse et pas de la requête elle même
                    if (response.data) {
                        return response.data;
                    } else {
                        return response;
                    }
                },
                // qui traite les erreur (méthode onRejected de l'intercepteur)
                this.errorHandler,
            );
        }
    }

    /**
     * Méthode traitant les erreurs HTTP.
     * Source : https://gist.github.com/fgilio/230ccd514e9381fafa51608fcf137253
     */
    private errorHandler(error: any) {

        // Définition d'un code de message
        let codeMessage = 'erreur_http';
        let parametresMessage: string[] = [];
        let severite: Severite = Severite.Error;

        if (!navigator.onLine) {
            // Si on est offline
            codeMessage = 'erreur_aucuneConnexionInternet';

        } else if (error && error.response) {
            // Si on a un statut d'erreur
            if (error.response.status === 0) {
                codeMessage = 'erreur_securiteParNavigateur';
            } else if (error.response.data && error.response.data.codeException) {
                codeMessage = error.response.data.codeException;
                parametresMessage = error.response.data.details;
                severite = error.response.data.level;
            } else if (error.response.status === 404) {
                codeMessage = 'erreur_apiNonDisponible';
            } else if (error.response.status === 403 || error.response.status === 401) {
                // Si ce n'est pas une erreur à la connexion, on ne fait rien
                if (error.config.url && !error.config.url.endsWith('/login')) {
                    codeMessage = 'erreur_securite';
                }
            }
        } else {
            // Cas non traitable
            /* tslint:disable-next-line */
            console.log('cas d\'erreur non traité pour le moment');
        }

        // Notification du store avec le message d'erreur
        store.commit('declarerErreurHttp', new MessageErreur(codeMessage, severite, parametresMessage));

        // Si c'est une requête de login, on supprime le token présent dans le store
        if (error && error.request && error.request.responseURL
            && error.request.responseURL.endsWith('/login') && error.response.status === 403) {
            store.commit('invaliderConnexionUtilisateur');
        }

        // Renvoi d'une erreur
        // Ce code déclenche dans la console la ligne 'Uncaught {data:......}
        // Mais sans elle, on passe dans la méthode 'onFulfilled' de l'intercepteur
        // puis dans la callback du suscribe de l'appelant.
        return Promise.reject(error.response);
    }
}
