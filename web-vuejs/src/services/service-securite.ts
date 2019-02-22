import axios from 'axios';
import { from, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import RestUtils from '@/services/utilitaire/restUtils';
import { Utilisateur } from '@/model/model';
import store from '@/store';

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

    /** Vérification des paramètres de connexion, récupération du token et des clefs des ressources autorisées */
    public connecter(login: string, mdp: string): Observable<any> {

        // Pour lever le flag sur le cache de la méthode estConnecte()
        store.commit('demandeDeConnexion');

        // Appel au login
        const donnees = { login, mdp };
        return from(axios.post(process.env.VUE_APP_URL_API + '/login', donnees, this.restUtils.creerHeader()))
            .pipe(map((u: any) => {
                this.conserverUtilisateurConnecteEnMemoire(u as Utilisateur);
            }));
    }

    /** Filtre des clefs de sécurités autorisées */
    public validerAutorisations(clefs: string[]): string[] {

        // Lecture des données du storage
        const clefsDuStorage = localStorage.getItem('CLEFS');
        let toutesLesClefsAutorisees: string[] = [];
        if (clefsDuStorage) {
            toutesLesClefsAutorisees = clefsDuStorage.split('|');
        }

        // Validation des clefs fournies en entrée
        return clefs.filter((c) => toutesLesClefsAutorisees.indexOf(c) !== -1);
    }

    /** Informe si l'utilisateur est bien connecté */
    public estConnecte(): Observable<boolean> {

        // Si l'utilisateur a demandé la déconnexion depuis le dernier raffichissement de la page (donc bien déconnecté)
        if (store.getters.getDemandeLaDeconnexion) {
            return of(false);
        }

        // Si le token a déjà été validé depuis le dernier chargement de la page
        else if (store.getters.getTokenDejaValide) {
            const token = localStorage.getItem('JWT');
            return of(!!token);
        }

        // Tentative d'appel REST pour valider/invalider le token
        else {
            return this.invaliderTokenSiPresentEtExpire();
        }
    }

    /** Demande de déconnexion */
    public deconnecter(): void {
        store.commit('demandeDeDeconnexion');
        store.commit('invaliderConnexionUtilisateur');
        localStorage.removeItem('CLEFS');
    }

    /**
     * Appel à l'API REST /utilisateurs/moi et vérification de l'expiration du token
     *
     * S'il est encore bon, rien ne se passe. Sinon, le localStorage est vidé.
     */
    private invaliderTokenSiPresentEtExpire(): Observable<boolean> {
        return from(axios.get(process.env.VUE_APP_URL_API + '/v1/utilisateurs/moi', this.restUtils.creerHeader())
            .then((reponse: any) => {
                if (reponse.status && reponse.status === 204) {
                    this.deconnecter();
                    return false;
                } else {
                    this.conserverUtilisateurConnecteEnMemoire(reponse as Utilisateur);
                    return true;
                }
            })
            // en cas d'erreur du service REST, on supprime le token
            .catch(() => {
                this.deconnecter();
                return false;
            }));
    }

    /** Conservation en memoire de l'utilisateur connecte */
    private conserverUtilisateurConnecteEnMemoire(utilisateur: Utilisateur): void {

        // Conservation des informations
        let clefsAutorisees: string[] = [];
        utilisateur.roles.forEach((r) => {
            clefsAutorisees = clefsAutorisees.concat(r.ressourcesAutorisees.map((ressource) => ressource.clef));
        });
        localStorage.setItem('CLEFS', clefsAutorisees.join('|'));

        // Notification de l'evenement
        store.commit('declarerUneConnexionUtilisateur', utilisateur);
    }
}
