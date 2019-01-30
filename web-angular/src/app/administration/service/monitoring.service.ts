import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { RestUtilsService } from '../../shared/service/restUtils.service';
import { HttpProxy } from '../../shared/service/httpProxy.component';

import * as model from '../model/model';
import { Page } from '../../model/model';
import { ExportService } from '../../shared/service/export-excel.service';

/** Composant TS d'interface avec les API Back de lecture des informations de monitoring */
@Injectable()
export class MonitoringService {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService, private exportService: ExportService) { }

  /**
   * Liste des éléments de monitoring de manière paginée et triée.
   *
   * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
   */
  lireInformations(page: Page<model.ElementMonitoring>): Observable<{} | Page<model.ElementMonitoring>> {

    // Toutes les colonnes sont triables mais, par défaut, c'est par clef
    let triPar: string = 'clef';
    let ordreTri: boolean = true;
    if (page.sort && page.sort.sortColonne && page.sort.sortOrder) {
      ordreTri = (page.sort.sortOrder === 'asc');
      triPar = page.sort.sortColonne;
    }

    // Appel à l'API
    const url = environment.baseUrl + '/monitoring?pageNumber=' + page.number + '&pageSize=' + page.size + '&triPar=' + triPar + '&ordreTri=' + ordreTri;
    return this.http.get<Page<model.ElementMonitoring>>(url, this.restUtils.creerHeader());
  }

  /**
   * Lecture de toutes les données, génération d'un fichier Excel et déclenchement du téléchargement
   */
  exporterInformations(): void {

    // Toutes les colonnes sont triables mais, par défaut, c'est par clef
    const pageNumber = 0;
    const pageSize = 10000;
    const triPar = 'clef';
    const ordreTri = true;

    // Appel à l'API
    const url = environment.baseUrl + '/monitoring?pageNumber=' + pageNumber + '&pageSize=' + pageSize + '&triPar=' + triPar + '&ordreTri=' + ordreTri;
    const obsResultats = this.http.get<Page<model.ElementMonitoring>>(url, this.restUtils.creerHeader());

    // Creation de l'excel et telechargement
    obsResultats.subscribe((p: Page<model.ElementMonitoring>) => {

      // Transformation en données Excel
      const data: any[] = [];

      // Creation de l'excel
      p.content.forEach((em) => {
        data.push({
          'clef': em.clef, 'nb d\'appels': em.nbAppels,
          'temps cumule (en ms)': em.tempsCumule, 'temps max (en ms)': em.tempsMax,
          'temps min (en ms)': em.tempsMin, 'temps moyen (en ms)': em.tempsMoyen
        });
      });

      // Export
      this.exportService.exporterTableauEnExcel(data, 'monitoring', 'monitoring');
    });
  }
}
