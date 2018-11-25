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

    // Seul un tri par défaut est possible
    let triParClef: string = '';
    if (page.sort) {
      if (page.sort.sortOrder === 'asc') {
        triParClef = 'true';
      } else {
        triParClef = 'false';
      }
    }

    // Appel à l'API
    const url = environment.baseUrl + '/monitoring?pageNumber=' + page.number + '&pageSize=' + page.size + '&triParClef=' + triParClef;
    return this.http.get<Page<model.ElementMonitoring>>(url, this.restUtils.creerHeader());
  }
}
