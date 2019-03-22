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

export class PaginationDto<T> {

    /** Objet utilisé par le composant DataTable pour stocker les informations de tri et de pagination */
    public pagination: any = { deep: true };

    /** Page de données */
    private page: Page<T> = new Page(0, 0);

    get lignesDuTableau() { return this.page.content; }
    get nombreTotalElements() { return this.page.totalElements; }
    get listeOptionNombreElementsParPage() { return [5, 6, 8, 10, 300]; }
    get pageCourante() { return this.page; }

    public constructor(private chargerDonneesCallback: (p: Page<T>) => void) { }

    public sauvegarderPage(page: Page<T>) { this.page = page; }

    public auChangementDePagination() {
        // Si la page n'a jamais été chargée (à l'arrivée sur la page), on ignore cet évènement
        if (!this.page.content) {
            return;
        }

        // Application des paramètres de pagination
        this.page.number = this.pagination.page - 1;
        this.page.size = this.pagination.rowsPerPage;

        // Application des paramètres de tri
        this.page.sort = new Sort();
        if (this.pagination.sortBy !== null) {
            this.page.sort.sortOrder = (!!this.pagination.descending) ? 'asc' : 'desc';
            this.page.sort.sortColonne = this.pagination.sortBy;
        }

        // Appel à la méthode de chargement des données
        this.chargerDonneesCallback(this.pageCourante);
    }
}
