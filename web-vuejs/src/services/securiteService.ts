import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';

import { Utilisateur } from '@/model/model';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export default class SecuriteService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    public connecter(login: string, mdp: string): Observable<any> {

        // Appel au login
        const donnees = { login, mdp };
        return from(axios.post(process.env.VUE_APP_URL_API + '/login', donnees, this.restUtils.creerHeader()));
    }
}
