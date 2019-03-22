import { Component, Vue, Watch } from 'vue-property-decorator';

import { RessourceService } from '@/services/administration/service-ressource';
import { PaginationDto, EnteteDatatable } from '@/model/vuetifyDto';
import { Ressource, Page } from '@/model/model';

@Component
export default class PageAdministrationRessources extends Vue {

    /** DTO contenant tous les éléments de pagination */
    public dtDto: PaginationDto<Ressource> = new PaginationDto(
        // Méthode de chargement des données
        this.chargerDonnees,
        // entêtes à utiliser
        [
            new EnteteDatatable('ressource_entete_clef', 'clef', 'center', true),
            new EnteteDatatable('ressource_entete_description', 'description', 'center', false),
        ],
        // tri par défaut
        { colonne: 'clef', ascendant: false },
    );

    /** Une dépendance */
    private ressourcesService: RessourceService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.ressourcesService = new RessourceService();
    }

    /** A chaque modification du membre 'pagination', prise en compte dans le DTO et appel à "chargerDonnees" */
    @Watch('dtDto.pagination')
    public auChangementDePagination(val: any, oldVal: any) {
        this.dtDto.auChangementDePagination();
    }

    /** Chargement des données */
    public chargerDonnees(nouvellePage: Page<any>) {
        this.ressourcesService.listerRessources(nouvellePage).subscribe((p) => {
            this.dtDto.sauvegarderPage(p);
        });
    }
}
