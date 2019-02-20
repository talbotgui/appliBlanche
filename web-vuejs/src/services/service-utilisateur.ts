import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';
import { Utilisateur, Role } from '@/model/model';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class UtilisateurService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    /** Lit la liste complète des utilisateurs */
    public listerUtilisateurs(): Observable<{} | Utilisateur[]> {
        const url = process.env.VUE_APP_URL_API + '/v1/utilisateurs';
        return from(axios.get(url, this.restUtils.creerHeader({ clef: 'Accept', valeur: 'application/json;details' })));
    }

    /** Création d'un utilisateur */
    public sauvegarderUtilisateur(utilisateur: Utilisateur): Observable<{} | void> {
        const donnees = { login: utilisateur.login, mdp: utilisateur.mdp };

        const url = process.env.VUE_APP_URL_API + '/v1/utilisateurs?login=' + utilisateur.login + '&mdp=' + utilisateur.mdp;
        return from(axios.post(url, donnees, this.restUtils.creerHeader()));
    }

    /** Suppression d'un utilisateur */
    public supprimerUtilisateur(utilisateur: Utilisateur): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/utilisateurs/' + utilisateur.login;
        return from(axios.delete(url, this.restUtils.creerHeader()));
    }

    /** Ajout/retrait d'un role à un utilisateur */
    public ajouterRetirerAutorisation(utilisateur: Utilisateur, role: Role, statut: boolean): Observable<{} | void> {
        const url = process.env.VUE_APP_URL_API + '/v1/utilisateurs/' + utilisateur.login + '/roles/' + role.nom;
        return from(axios.put(url, statut, this.restUtils.creerHeader()));
    }
}
