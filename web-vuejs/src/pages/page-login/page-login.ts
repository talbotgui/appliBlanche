import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/service-securite';
import routeur from '@/router';

@Component
export default class PageLogin extends Vue {

    /** Flag indiquant l'état de validation du formulaire */
    public valid: boolean = true;

    /** Identifiant */
    public login: string = 'adminAsupprimer';

    /** Mot de passe */
    public mdp: string = 'adminAsupprimer';

    /** Une dépendance */
    private securiteService: SecuriteService;

    /** Constructeur avec création des dép ndances */
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
