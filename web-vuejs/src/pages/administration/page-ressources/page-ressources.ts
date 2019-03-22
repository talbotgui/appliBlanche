import { Component, Vue, Watch } from 'vue-property-decorator';

import { RessourceService } from '@/services/administration/service-ressource';
import Pagination from '@/composants/composant-pagination/composant-pagination';
import { PaginationDto } from '@/model/vuetifyDto';
import { Ressource, Page } from '@/model/model';

@Component({ components: { Pagination } })
export default class PageAdministrationRessources extends Vue {

    /** DTO contenant tous les éléments de pagination */
    public dtDto: PaginationDto<Ressource> = new PaginationDto(this.chargerDonnees);

    /** Structure de la table à afficher */
    // text et align sont utilisés pour l'affichage
    // sortable et value sont utilisés pour le tri des données
    public entetes = [
        { text: 'Clef', align: 'center', value: 'clef' },
        { text: 'Description', align: 'center', value: 'description', sortable: false },
    ];

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
