import axios from 'axios';
import RestUtils from './utilitaire/restUtils';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export default class SecuriteService {
    private restUtils = new RestUtils();

    public getTest(): Promise<any> {
        return axios.get(process.env.VUE_APP_URL_API + '/i18n/fr', this.restUtils.creerHeader()).;
    }
}
