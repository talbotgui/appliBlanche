import { Component, Vue } from 'vue-property-decorator';

import { Page, ElementMonitoring } from '@/model/model';
import { MonitoringService } from '@/services/service-monitoring';

@Component
export default class PageAdministrationMonitoring extends Vue {

    /** Page de données */
    public page: Page<ElementMonitoring> = new Page(10, 0);

    /** Listes alimentant les selectbox */
    public listeNbElementsParPage: number[] = [5, 10, 20, 50, 100];
    public listeIndexesDePage: number[] = [];

    /** Une dépendance */
    private monitoringService: MonitoringService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.monitoringService = new MonitoringService();
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
        this.monitoringService.lireInformations(this.page).subscribe((p) => {

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
