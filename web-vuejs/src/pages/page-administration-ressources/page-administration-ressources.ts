import { Component, Vue } from 'vue-property-decorator';

import { RessourceService } from '@/services/service-ressource';
import { Page, Ressource } from '@/model/model';

@Component
export default class PageAdministrationRessources extends Vue {

    /** Page de données */
    public page!: Page<Ressource>;

    /** Une dépendance */
    private ressourcesService: RessourceService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.ressourcesService = new RessourceService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        this.page = new Page(10, 0);
        this.ressourcesService.listerRessources(this.page).subscribe((p) => { this.page = p; console.log(p); });
    }
}
