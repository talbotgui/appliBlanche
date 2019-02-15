import axios from 'axios';
import { from, Observable } from 'rxjs';

import RestUtils from '@/services/utilitaire/restUtils';
import { Utilisateur, Role } from '@/model/model';
import VueI18n from 'vue-i18n';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class I18nService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    /** Chargement des libelles de la langue si pas déjà chargés */
    public chargerUneLangue(codeLangue: string, i18n: VueI18n) {
        axios.get(process.env.VUE_APP_URL_API + '/i18n/' + codeLangue, this.restUtils.creerHeader())
            .then((reponse: any) => {
                i18n.setLocaleMessage('fr', reponse);
            });
    }
}
