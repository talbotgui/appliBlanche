import axios from 'axios';
import VueI18n from 'vue-i18n';

import RestUtils from '@/services/utilitaire/restUtils';

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

        // Vérifie si la langue a déjà été chargée
        const clefMessage = 'menu_accueil';
        const messageDansNouvelleLangue = i18n.t(clefMessage, codeLangue);

        // Chargement des libellés de la langue
        if (clefMessage === messageDansNouvelleLangue) {
            axios.get(process.env.VUE_APP_URL_API + '/i18n/' + codeLangue, this.restUtils.creerHeader())
                .then((reponse: any) => {
                    i18n.setLocaleMessage(codeLangue, reponse);
                    i18n.locale = codeLangue;
                });
        }

        // Simple changement de langue
        else {
            i18n.locale = codeLangue;
        }
    }
}
