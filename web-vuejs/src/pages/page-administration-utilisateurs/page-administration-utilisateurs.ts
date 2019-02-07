import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/service-securite';
import routeur from '@/router';

@Component
export default class PageAdministrationUtilisateurs extends Vue {

    /** Une dépendance */
    private securiteService: SecuriteService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.securiteService = new SecuriteService();
    }

}
