import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';

import { Page, Ressource, Role } from '@/model/model';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class RessourceService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    /**
     * Liste des ressources de manière paginée et triée.
     *
     * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
     */
    public listerRessources(page: Page<any>): Observable<any | Page<Ressource>> {

        // Seul un tri par défaut est possible
        let triParClef: string = '';
        if (page.sort) {
            if (page.sort.sortOrder === 'asc') {
                triParClef = 'true';
            } else {
                triParClef = 'false';
            }
        }

        // Appel à l'API
        const url = process.env.VUE_APP_URL_API + '/v1/ressources?pageNumber=' + page.number + '&pageSize='
            + page.size + '&triParClef=' + triParClef;
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /** Ajoute une ressource des autorisations d'un role */
    public ajouterAutorisation(role: Role, ressource: Ressource): Observable<{} | void> { return this.ajouterRetirerAutorisation(role, ressource, true); }
    /** Retire une ressource des autorisations d'un role */
    public retirerAutorisation(role: Role, ressource: Ressource): Observable<{} | void> { return this.ajouterRetirerAutorisation(role, ressource, false); }
    private ajouterRetirerAutorisation(role: Role, ressource: Ressource, statut: boolean): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/roles/' + role.nom + '/ressource/' + ressource.clef;
        return from(axios.put(url, statut, this.restUtils.creerHeader()));
    }
}
