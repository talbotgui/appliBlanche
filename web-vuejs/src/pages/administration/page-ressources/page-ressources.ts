import { Component, Vue, Watch } from 'vue-property-decorator';

import { RessourceService } from '@/services/administration/service-ressource';
import { Page, Ressource, Sort } from '@/model/model';
import Pagination from '@/composants/composant-pagination/composant-pagination';
import { Observable } from 'rxjs';

@Component({ components: { Pagination } })
export default class PageAdministrationRessources extends Vue {

    get lignesDuTableau() { return this.page.content; }
    get nombreTotalElements() { return this.page.totalElements; }
    get listeOptionNombreElementsParPage() { return [5, 6, 8, 10, 300]; }

    public pagination: any = {
        deep: true,
    };

    /** Flag indiquant le chargement en cours */
    public chargementEnCours = false;

    /** Structure de la table à afficher */
    // text et align sont utilisés pour l'affichage
    // sortable et value sont utilisés pour le tri des données
    public entetes = [
        { text: 'Clef', align: 'center', value: 'clef' },
        { text: 'Description', align: 'center', value: 'description', sortable: false },
    ];

    /** Page de données */
    private page: Page<Ressource> = new Page(0, 0);

    /** Une dépendance */
    private ressourcesService: RessourceService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.ressourcesService = new RessourceService();
    }

    @Watch('pagination')
    public auChangementDePagination(val: any, oldVal: any) {
        // Si la page n'a jamais été chargée (à l'arrivée sur la page), on ignore cet évènement
        if (!this.page.content) {
            return;
        }

        // Application des paramètres de pagination
        this.page.number = this.pagination.page - 1;
        this.page.size = this.pagination.rowsPerPage;

        // Application des paramètres de tri
        console.log(this.pagination.sortBy + '//' + this.pagination.descending);
        this.page.sort = new Sort();
        if (this.pagination.sortBy !== null) {
            this.page.sort.sortOrder = (!!this.pagination.descending) ? 'asc' : 'desc';
            this.page.sort.sortColonne = this.pagination.sortBy;
        }

        // Rechargement des données
        this.chargerDonnees(this.page);
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() { this.chargerDonnees(new Page(10, 0)); }

    /** Chargement des données */
    public chargerDonnees(nouvellePage: Page<any>) {
        this.chargementEnCours = true;
        this.ressourcesService.listerRessources(nouvellePage).subscribe((p) => {
            // Sauvegarde de la page pour en afficher le contenu
            this.page = p;
            // Envoi de la page au composant de pagination pour prise en compte
            // (this.$refs.pagination as Pagination).prendreEnComptePage(p);
            // Fin du chargement
            this.chargementEnCours = false;
        });
    }
}
