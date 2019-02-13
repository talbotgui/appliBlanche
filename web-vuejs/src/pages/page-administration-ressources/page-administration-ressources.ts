import { Component, Vue } from 'vue-property-decorator';

import { RessourceService } from '@/services/service-ressource';
import { Page, Ressource } from '@/model/model';

@Component
export default class PageAdministrationRessources extends Vue {

    /** Page de données */
    public page: Page<Ressource> = new Page(10, 0);

    /** Liste alimentant les selectbox */
    public listeNbElementsParPage: number[] = [5, 10, 20, 50, 100];
    public listeIndexesDePage: number[] = [];

    /** Une dépendance */
    private ressourcesService: RessourceService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.ressourcesService = new RessourceService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        this.ressourcesService.listerRessources(this.page).subscribe((p) => {
            this.page = p;

            const listeIndexes = [];
            for (let i = 0; i < this.page.totalPages; i++) {
                listeIndexes.push(i);
            }
            this.listeIndexesDePage = listeIndexes;
        });
    }
}
