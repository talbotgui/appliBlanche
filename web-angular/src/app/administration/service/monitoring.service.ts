import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { RestUtilsService } from '../../shared/service/restUtils.service';
import { HttpProxy } from '../../shared/service/httpProxy.component';

import * as model from '../model/model';
import { Page } from '../../model/model';

/** Composant TS d'interface avec les API Back de lecture des informations de monitoring */
@Injectable()
export class MonitoringService {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService) { }

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
}
