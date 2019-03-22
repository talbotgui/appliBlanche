import { Component, Vue, Watch } from 'vue-property-decorator';

import { Page } from '@/model/model';
import { ElementMonitoring } from '@/model/administration-model';
import { MonitoringService } from '@/services/administration/service-monitoring';
import { PaginationDto, EnteteDatatable } from '@/model/vuetifyDto';

@Component
export default class PageAdministrationMonitoring extends Vue {

    /** DTO contenant tous les éléments de pagination */
    public dtDto: PaginationDto<ElementMonitoring> = new PaginationDto(
        // Méthode de chargement des données
        this.chargerDonnees,
        // entêtes à utiliser
        [
            new EnteteDatatable('monitoring_entete_clef', 'clef', 'center', true),
            new EnteteDatatable('monitoring_entete_nbAppels', 'nbAppels', 'center', true),
            new EnteteDatatable('monitoring_entete_tempsCumule', 'tempsCumule', 'center', true),
            new EnteteDatatable('monitoring_entete_tempsMoyen', 'tempsMoyen', 'center', true),
            new EnteteDatatable('monitoring_entete_tempsMax', 'tempsMax', 'center', true),
            new EnteteDatatable('monitoring_entete_tempsMin', 'tempsMin', 'center', true),
        ],
        // tri par défaut
        { colonne: 'clef', ascendant: false },
    );

    /** Une dépendance */
    private monitoringService: MonitoringService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.monitoringService = new MonitoringService();
    }

    /** A chaque modification du membre 'pagination', prise en compte dans le DTO et appel à "chargerDonnees" */
    @Watch('dtDto.pagination')
    public auChangementDePagination(val: any, oldVal: any) {
        this.dtDto.auChangementDePagination();
    }

    /** Chargement des données */
    public chargerDonnees(nouvellePage: Page<any>) {
        this.monitoringService.lireInformations(nouvellePage).subscribe((p) => {
            this.dtDto.sauvegarderPage(p);
        });
    }

    /** Export en excel */
    public exporterInformations() {
        this.monitoringService.exporterInformations();
    }
}
