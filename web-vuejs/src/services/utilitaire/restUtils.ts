import axios from 'axios';
import * as store from '@/store';
import { MessageErreur, Severite } from '@/model/erreur';

export default class RestUtils {

    /** Constructeur initialisant le gestionnaire d'erreur */
    constructor() {
        this.declarerIntercepteurGestionDerreur();
    }

    /** Creation des entetes d'appel à une méthode REST */
    public creerHeader(enteteSupplementaire?: { clef: string, valeur: string }): { headers: any } | undefined {
        // Entete de base
        const entete: any = { 'Content-Type': 'application/json' };

        // Ajout d'un entete supplémentaire
        if (enteteSupplementaire) {
            entete[enteteSupplementaire.clef] = enteteSupplementaire.valeur;
        }

        // Ajout du token de l'API (si disponible)
        if (store.default.getters.getTokenApi) {
            entete.authorization = store.default.getters.getTokenApi;
        }

        return { headers: entete };
    }

    // Creation d'un intercepteur HTTP à la réception de la réponse HTTP
    private declarerIntercepteurGestionDerreur() {
        axios.interceptors.response.use(
            // méthode onFulfilled de l'intercepteur
            (response: any) => {
                // Si c'est une requete de connexion, on sauvegarde le token
                if (response && response.request && response.request.responseURL && response.request.responseURL.endsWith('login')
                    && response.headers && response.headers.authorization) {
                    store.default.commit('declarerUneConnexionUtilisateur', response.headers.authorization);
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

    /**
     * Méthode traitant les erreurs HTTP.
     * Source : https://gist.github.com/fgilio/230ccd514e9381fafa51608fcf137253
     */
    private errorHandler(error: any) {

        // Log de l'erreur
        /* tslint:disable-next-line */
        console.info('Traitement d\'une erreur');

        // Définition d'un code de message
        let codeMessage = 'erreur_http';
        let parametresMessage: string[] = [];
        let severite: Severite = Severite.Error;

        // Si on est offline
        if (!navigator.onLine) {
            codeMessage = 'erreur_aucuneConnexionInternet';
        } else if (error.response) {
            /* tslint:disable-next-line */
            console.log(error.response);
            if (error.response.status === 0) {
                codeMessage = 'erreur_securiteParNavigateur';
            } else if (error.response.status === 404) {
                codeMessage = 'erreur_apiNonDisponible';
            } else if (error.response.status === 403 || error.response.status === 401) {
                // Si ce n'est pas une erreur à la connexion, on ne fait rien
                if (error.config.url && !error.config.url.endsWith('/login')) {
                    codeMessage = 'erreur_securite';
                }
            } else if (error.data.error && error.data.error.codeException) {
                // TODO: tester le cas d'une erreur RestException ou BusinessException
                codeMessage = error.data.error.codeException;
                parametresMessage = error.data.error.details;
                severite = error.data.error.level;
            }
        } else if (error.request) {
            // TODO: tester le cas d'une erreur RestException ou BusinessException
            /* tslint:disable-next-line */
            console.log('cas d\'erreur non traité');
        } else {
            // TODO: traiter ce cas
            /* tslint:disable-next-line */
            console.log('cas d\'erreur non traité pour le moment');
        }

        // TODO: i18n
        /* tslint:disable-next-line */
        console.log('codeMessage=' + codeMessage);
        /* tslint:disable-next-line */
        console.log('parametresMessage=' + parametresMessage);

        // Notification du store avec le message d'erreur
        store.default.commit('declarerErreurHttp', new MessageErreur(codeMessage, severite));

        // Si c'est une requête de login, on supprime le token présent dans le store
        if (error && error.request && error.request.responseURL
            && error.request.responseURL.endsWith('/login') && error.response.status === 403) {
            store.default.commit('invaliderConnexionUtilisateur');
        }

        // Renvoi d'une erreur
        // Ce code déclenche dans la console la ligne 'Uncaught {data:......}
        // Mais sans elle, on passe dans la méthode 'onFulfilled' de l'intercepteur
        // puis dans la callback du suscribe de l'appelant.
        return Promise.reject(error.response);
    }
}
