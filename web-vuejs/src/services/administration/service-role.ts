import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';
import { Page, Role } from '@/model/model';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class RoleService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    /**
     * Liste des roles de manière paginée et triée.
     *
     * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
     */
    public listerRoles(page: Page<any>): Observable<{} | Page<Role>> {

        // Seul un tri par défaut est possible
        let triParNom: string = '';
        if (page.sort) {
            if (page.sort.sortOrder === 'asc') {
                triParNom = 'true';
            } else {
                triParNom = 'false';
            }
        }

        // Appel à l'API
        const url = process.env.VUE_APP_URL_API + '/v1/roles?pageNumber=' + page.number + '&pageSize=' + page.size + '&triParNom=' + triParNom;
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Sauvegarde d'un role via l'API */
    public sauvegarderRole(role: Role): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/roles?nom=' + role.nom;
        return from(axios.post(url, {}, this.restUtils.creerHeader()));
    }

    /** Suppression d'un role via l'API */
    public supprimerRole(role: Role): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/roles/' + role.nom;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }
}
