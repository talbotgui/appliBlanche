import { Component, Vue } from 'vue-property-decorator';
import { Page } from '@/model/model';

/** Composant permettant de mutualiser les listes déroulantes servant à la pagination de tableau */
@Component
export default class Pagination extends Vue {

    /** Listes alimentant les selectbox */
    public listeNbElementsParPage: number[] = [5, 10, 20, 50, 100];
    public listeIndexesDePage: number[] = [];

    /** Page de données */
    private page: Page<any> = new Page(10, 0);

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
    }

    /** Methode mettant à jour les listes déroulantes de pagination en fonction de la page*/
    public prendreEnComptePage(p: Page<any>) {

        // Traitement du numéro de page qui commence à 0
        p.number = p.number + 1;

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
