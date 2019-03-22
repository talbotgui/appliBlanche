import { DateUtils } from '@/services/utilitaire/dateUtils';
import { Page, Sort } from './model';

export default class DatePickerCalendarDto {

    get dateCourte() {
        return this.dateUtils.formaterDateDDMM(this.date);
    }
    get dateComplete() {
        return this.dateUtils.formaterDateYYMMDD(this.date);
    }
    set dateComplete(value: string) {
        this.date = this.dateUtils.parserDateYYMMDD(value);
    }
    public datePick: boolean = false;
    public date: Date = new Date();

    private dateUtils: DateUtils = new DateUtils();
}

/**
 * Classe utilitaire pour faciliter l'usage de DataTable.
 *
 * Coté html :
 *
 * <v-data-table :headers="entetes" :items="dtDto.lignesDuTableau" :loading="dtDto.chargementEnCours" class="elevation-1" :pagination.sync="dtDto.pagination"
 * :total-items="dtDto.nombreTotalElements" :rows-per-page-items="dtDto.listeOptionNombreElementsParPage" :must-sort="true">
 *
 * Coté TS :
 *
 * // Pas de méthode 'mounted' chargeant les données. Ce sera fait à l'initialisation du tableau.
 *
 * // DTO contenant tous les éléments de pagination
 * public dtDto: PaginationDto<Ressource> = new PaginationDto(this.chargerDonnees);
 *
 * // A chaque modification du membre 'dtDto', prise en compte dans le DTO et appel à "chargerDonnees"
 * @Watch('dtDto.pagination')
 * public auChangementDePagination(val: any, oldVal: any) { this.dtDto.auChangementDePagination(); }
 *
 * // Méthode chargeant les données
 * public chargerDonnees(nouvellePage: Page<any>) {
 *   this.ressourcesService.listerRessources(nouvellePage).subscribe((p) => {
 *     // Sauvegarde de la page pour en afficher le contenu
 *     this.dtDto.sauvegarderPage(p);
 *   });
 * }
 */
export class PaginationDto<T> {

    /** Objet utilisé par le composant DataTable pour stocker les informations de tri et de pagination */
    public pagination: any = { deep: true };

    /** Flag indiquant le chargement en cours */
    public chargementEnCours = false;

    /** Page de données */
    private page: Page<T> = new Page(0, 0);

    /** GETTER pour obtenir les lignes dans le tableau */
    get lignesDuTableau() { return this.page.content; }
    /** GETTER pour obtenir le nombre total d'éléments */
    get nombreTotalElements() { return this.page.totalElements; }
    /** GETTER pour obtenir la liste des tailles de page possibles */
    get listeOptionNombreElementsParPage() { return [5, 6, 8, 10, 300]; }
    /** GETTER pour obtenir la page courante */
    get pageCourante() { return this.page; }

    /**
     * Constructeur
     * @param chargerDonneesCallback La méthode du controleur permettant le chargement des données
     */
    public constructor(private chargerDonneesCallback: (p: Page<T>) => void) { }

    /** Méthode appelée par chargerDonneesCallback après le chargement des données */
    public sauvegarderPage(page: Page<T>) {
        // sauvegarde de la page
        this.page = page;
        // Fin du chargement
        this.chargementEnCours = false;
    }

    /** Méthode appelée au moindre changement dans le tri ou la pagination */
    public auChangementDePagination() {
        // Si la page n'a jamais été chargée (à l'arrivée sur la page), on charge la première page
        if (!this.page.content) {
            this.page.size = this.listeOptionNombreElementsParPage[0];
        }

        // Sinon, on applique les données venant de datatable
        else {
            // Application des paramètres de pagination
            this.page.number = this.pagination.page - 1;
            this.page.size = this.pagination.rowsPerPage;

            // Application des paramètres de tri
            this.page.sort = new Sort();
            if (this.pagination.sortBy !== null) {
                this.page.sort.sortOrder = (!!this.pagination.descending) ? 'asc' : 'desc';
                this.page.sort.sortColonne = this.pagination.sortBy;
            }
        }

        // Appel à la méthode de chargement des données
        this.chargementEnCours = true;
        this.chargerDonneesCallback(this.pageCourante);
    }
}
