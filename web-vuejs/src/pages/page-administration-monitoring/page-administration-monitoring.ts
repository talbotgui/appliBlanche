import { Component, Vue } from 'vue-property-decorator';

import { Page } from '@/model/model';
import { ElementMonitoring } from '@/model/administration-model';
import { MonitoringService } from '@/services/administration/service-monitoring';
import Pagination from '@/composants/composant-pagination/composant-pagination';

@Component({ components: { Pagination } })
export default class PageAdministrationMonitoring extends Vue {

    /** Page dont le contenu est affiché */
    public page: Page<ElementMonitoring> = new Page(0, 0);

    /** Une dépendance */
    private monitoringService: MonitoringService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.monitoringService = new MonitoringService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        this.chargerDonnees(new Page(10, 0));
    }

    /** Chargement des données */
    public chargerDonnees(nouvellePage: Page<any>) {
        this.monitoringService.lireInformations(nouvellePage).subscribe((p) => {
            // Sauvegarde de la page pour en afficher le contenu
            this.page = p;
            // Envoi de la page au composant de pagination pour prise en compte
            (this.$refs.pagination as Pagination).prendreEnComptePage(p);
        });
    }

    /** Export en excel */
    public exporterInformations() {
        this.monitoringService.exporterInformations();
    }

}
