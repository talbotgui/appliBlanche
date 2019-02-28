import { Component, Vue } from 'vue-property-decorator';
import { Page } from '@/model/model';

/** Composant permettant de mutualiser les listes déroulantes servant à la pagination de tableau */
@Component
export default class Pagination extends Vue {

    /** Listes alimentant les selectbox */
    public listeNbElementsParPage: number[] = [5, 10, 20, 50, 100];
    public listeIndexesDePage: number[] = [];

    /** Index courant de la page (réel + 1) */
    get indexPageCourant() {
        if (this.page && this.page.number) {
            return this.page.number + 1;
        } else {
            return 1;
        }
    }

    /** Page de données */
    private page: Page<any> = new Page(10, 0);

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
    }

    /** Methode mettant à jour les listes déroulantes de pagination en fonction de la page */
    public prendreEnComptePage(p: Page<any>) {

        // Sauvegarde de la dernière page pour la passer dans l'évènement de rechargement
        this.page = p;

        // Constutition des indexes de page pour la liste déroulante
        const listeIndexes = [];
        for (let i = 0; i < p.totalPages; i++) {
            listeIndexes.push(i + 1);
        }
        this.listeIndexesDePage = listeIndexes;

    }

    /** Au changement du nombre d'élément par page */
    public selectionnerNbElements(size: number) {
        this.page.size = size;
        this.page.number = 0;
        this.chargerDonnees(this.page);
    }

    /** Au changement d'index de page */
    public selectionnerIndexPage(pageNumber: number) {
        this.page.number = pageNumber - 1;
        this.chargerDonnees(this.page);
    }

    /** Méthode appellée */
    public chargerDonnees(page: Page<any>) {
        this.$emit('rechargement', this.page);
    }
}
