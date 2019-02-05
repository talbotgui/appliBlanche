import axios from 'axios';

export default class RestUtils {

    /** Constructeur initialisant le gestionnaire d'erreur */
    constructor() {

        // Creation d'un intercepteur HTTP à la réception de la réponse
        axios.interceptors.response.use(
            // qui ne renvoie que  les données (méthode onFulfilled de l'intercepteur)
            (response: any) => {
                return response.data;
            },
            // qui traite les erreur (méthode onRejected de l'intercepteur)
            this.errorHandler,
        );
    }

    /** Creation des entetes d'appel à une méthode REST */
    public creerHeader(): { headers: any } | undefined {
        const entete: any = { 'Content-Type': 'application/json' };
        return { headers: entete };
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

        // Renvoi d'une erreur
        // Ce code déclenche dans la console la ligne 'Uncaught {data:......}
        // Mais sans elle, on passe dans la méthode 'onFulfilled' de l'intercepteur
        // puis dans la callback du suscribe de l'appelant.
        return Promise.reject(error.response);
    }
}
