import { Component, Vue } from 'vue-property-decorator';

import { RessourceService } from '@/services/service-ressource';
import { Page, Ressource } from '@/model/model';

@Component
export default class PageAdministrationRessources extends Vue {

    /** Page de données */
    public page: Page<Ressource> = new Page(10, 0);

    /** Listes alimentant les selectbox */
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
    public mounted() { this.chargerDonnees(); }

    /** Au changement du nombre d'élément par page */
    public selectionnerNbElements(size: number) {
        this.page.size = size;
        this.page.number = 0;
        this.chargerDonnees();
    }

    /** Au changement d'index de page */
    public selectionnerIndexPage(pageNumber: number) {
        this.page.number = pageNumber - 1;
        this.chargerDonnees();
    }

    /** Chargement des données */
    private chargerDonnees() {
        this.ressourcesService.listerRessources(this.page).subscribe((p) => {

            // Sauvegarde de la page
            this.page = p;
            this.page.number = this.page.number + 1;

            // Constutition des indexes de page pour la liste déroulante
            const listeIndexes = [];
            for (let i = 0; i < this.page.totalPages; i++) {
                listeIndexes.push(i + 1);
            }
            this.listeIndexesDePage = listeIndexes;
        });
    }
}
