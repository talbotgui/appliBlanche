import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/securiteService';
import routeur from '@/router';

@Component
export default class Login extends Vue {

    public login: string = 'adminAsupprimer';
    public mdp: string = 'adminAsupprimer';

    /** Une dépendance */
    private securiteService: SecuriteService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.securiteService = new SecuriteService();
    }

    /** Connexion */
    public connecter() {
        this.securiteService.connecter(this.login, this.mdp).subscribe(() => {
            routeur.push('/');
        });
    }
}
