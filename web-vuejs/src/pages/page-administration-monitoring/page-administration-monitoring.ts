import { Component, Vue } from 'vue-property-decorator';

import { Page } from '@/model/model';
import { ElementMonitoring } from '@/model/administration-model';
import { MonitoringService } from '@/services/service-monitoring';
import Pagination from '@/composants/composant-pagination/composant-pagination';

@Component({ components: { Pagination } })
export default class PageAdministrationMonitoring extends Vue {

    /** Page dont le contenu est affiché */
    public page: Page<ElementMonitoring> = new Page(10, 0);

    /** Une dépendance */
    private monitoringService: MonitoringService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.monitoringService = new MonitoringService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        this.chargerDonnees(this.page);
    }

    /** Chargement des données */
    public chargerDonnees(page: Page<any>) {
        this.monitoringService.lireInformations(page).subscribe((p) => {
            // Sauvegarde de la page pour en afficher le contenu
            this.page = p;
            // Envoi de la page au composant de pagination pour prise en compte
            (this.$refs.pagination as Pagination).prendreEnComptePage(p);
        });
    }
}
