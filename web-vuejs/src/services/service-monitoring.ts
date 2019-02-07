import axios from 'axios';
import { from, empty, Observable } from 'rxjs';
import { expand, map, reduce } from 'rxjs/operators';

import RestUtils from '@/services/utilitaire/restUtils';

import { Page } from '@/model/model';
import { ElementMonitoring } from '@/model/administration-model';

/**
 * Composant responsable des appels aux APIs.
 * Documentation Axios : https://fr.vuejs.org/v2/cookbook/using-axios-to-consume-apis.html
 */
export class MonitoringService {

    /** Dépendance */
    private restUtils: RestUtils;

    /** Constructeur instanciant les dépendances */
    constructor() {
        this.restUtils = new RestUtils();
    }

    /**
     * Liste des éléments de monitoring de manière paginée et triée.
     *
     * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
     */
    public lireInformations(page: Page<ElementMonitoring>): Observable<{} | Page<ElementMonitoring>> {

        // Toutes les colonnes sont triables mais, par défaut, c'est par clef
        let triPar: string = 'clef';
        let ordreTri: boolean = true;
        if (page.sort && page.sort.sortColonne && page.sort.sortOrder) {
            ordreTri = (page.sort.sortOrder === 'asc');
            triPar = page.sort.sortColonne;
        }

        // Appel à l'API
        const url = process.env.VUE_APP_URL_API + '/monitoring?pageNumber=' + page.number
            + '&pageSize=' + page.size + '&triPar=' + triPar + '&ordreTri=' + ordreTri;
        return from(axios.get(url, this.restUtils.creerHeader()));
    }

    /**
     * Lecture de toutes les données, génération d'un fichier Excel et déclenchement du téléchargement
     */
    public exporterInformations(): void {

        // Toutes les colonnes sont triables mais, par défaut, c'est par clef
        const pageSize = 10;
        const triPar = 'clef';
        const ordreTri = true;

        //  méthode faisant appel à l'API avec le numéro de page en paramètre
        const appelApi = (noPage: number) => {
            const url = process.env.VUE_APP_URL_API + '/monitoring?pageNumber=' + noPage
                + '&pageSize=' + pageSize + '&triPar=' + triPar + '&ordreTri=' + ordreTri;
            return from(axios.get(url, this.restUtils.creerHeader()));
        };

        // Premier appel à la méthode avec la page 0
        const obsResultats = appelApi(0)
            // enchainé par
            .pipe(

                // des appels systématiques aux autres pages (i s'incrémente à chaque fois et commence à 0)
                // tant que le retour n'est pas empty
                expand((reponse: any, i: number) => {
                    if (reponse.data.numberOfElements === reponse.data.size) {
                        return appelApi(i + 1);
                    } else {
                        return empty();
                    }
                }),

                // transformation des données en tableau d'élément (la notion de page n'ayant plus de sens à la fin)
                map((reponse) => reponse.data.content),

                // Reduce pour n'avoir qu'un seul appel au suscribe à la fin
                // Le reduce concatene le contenu des différents tableaux
                reduce((acc, x) => acc.concat(x), []),
            );

        // Creation de l'excel et telechargement
        obsResultats.subscribe((tableau: ElementMonitoring[]) => {

            // Transformation en données Excel
            const data: any[] = [];

            // Creation de l'excel
            tableau.forEach((em) => {
                data.push({
                    'clef': em.clef, 'nb d\'appels': em.nbAppels,
                    'temps cumule (en ms)': em.tempsCumule, 'temps max (en ms)': em.tempsMax,
                    'temps min (en ms)': em.tempsMin, 'temps moyen (en ms)': em.tempsMoyen,
                });
            });

            // Export
            // TODO: finir ce code
            // this.exportService.exporterTableauEnExcel(data, 'monitoring', 'monitoring');
        });
    }
}
