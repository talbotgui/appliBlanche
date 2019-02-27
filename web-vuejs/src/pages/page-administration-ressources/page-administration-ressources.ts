import { Component, Vue } from 'vue-property-decorator';

import { RessourceService } from '@/services/service-ressource';
import { Page, Ressource } from '@/model/model';
import Pagination from '@/composants/composant-pagination/composant-pagination';

@Component({ components: { Pagination } })
export default class PageAdministrationRessources extends Vue {

    /** Page de données */
    public page: Page<Ressource> = new Page(0, 0);

    /** Une dépendance */
    private ressourcesService: RessourceService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.ressourcesService = new RessourceService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() { this.chargerDonnees(new Page(10, 0)); }

    /** Chargement des données */
    public chargerDonnees(nouvellePage: Page<any>) {
        this.ressourcesService.listerRessources(nouvellePage).subscribe((p) => {
            // Sauvegarde de la page pour en afficher le contenu
            this.page = p;
            // Envoi de la page au composant de pagination pour prise en compte
            (this.$refs.pagination as Pagination).prendreEnComptePage(p);
        });
    }
}
