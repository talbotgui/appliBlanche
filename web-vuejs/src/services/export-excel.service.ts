import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { DateUtils } from './utilitaire/dateUtils';

/** Type MIME d'un tableau en format OPEN */
const EXCEL_MIME_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

/** Extension MS Excel */
const EXCEL_EXTENSION = '.xlsx';

/** Composant permettant l'export de document */
export class ExportService {

    /**
     * Méthode exportant le tableau de données dans un fichier Excel avec un seul onglet.
     * @param json Le tableau contenant des objets. Le nom de l'attribut dans les objets est l'entête des colonnes.
     * @param nomOnglet Le nom de l'unique onglet
     * @param nomFichierExcel Le nom du fichier Excel qui sera suffixé de la date et de l'extention
     */
    public exporterTableauEnExcel(json: any[], nomOnglet: string, nomFichierExcel: string): void {
        const excelBuffer: any = this.creerExcelDepuisTableau(json, nomOnglet);
        this.declencherTelechargement(excelBuffer, nomFichierExcel);
    }

    /**
     * Méthode créant l'Excel avec les données
     * @param json Le tableau contenant des objets. Le nom de l'attribut dans les objets est l'entête des colonnes.
     * @param nomOnglet Le nom de l'unique onglet
     */
    private creerExcelDepuisTableau(json: any[], nomOnglet: string): any {
        const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
        const workbook: XLSX.WorkBook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, nomOnglet);
        return XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    }

    /**
     * Méthode déclenchant le téléchargement dans le navigateur
     * @param buffer Buffer de données
     * @param nomFichier Nom du fichier
     */
    private declencherTelechargement(buffer: any, nomFichier: string): void {
        const data: Blob = new Blob([buffer], { type: EXCEL_MIME_TYPE });
        const dateDuJour = (new DateUtils()).formaterDate(new Date());
        FileSaver.saveAs(data, nomFichier + '_' + dateDuJour + EXCEL_EXTENSION);
    }
}
