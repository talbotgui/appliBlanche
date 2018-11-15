import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { RestUtilsService } from '../../shared/service/restUtils.service';
import { HttpProxy } from '../../shared/service/httpProxy.component';

import * as model from '../../model/model';

/** Composant TS d'interface avec les API Back de manipulation des ressources */
@Injectable()
export class RessourceService {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService) { }

  /**
   * Liste des ressources de manière paginée et triée.
   *
   * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
   */
  listerRessources(page: model.Page<any>): Observable<{} | model.Page<model.Ressource>> {

    // Seul un tri par défaut est possible
    let triParNom: string = '';
    if (page.sort) {
      if (page.sort.sortOrder === 'asc') {
        triParNom = 'true';
      } else {
        triParNom = 'false';
      }
    }

    // Appel à l'API
    const url = environment.baseUrl + '/v1/ressources?pageNumber=' + page.number + '&pageSize=' + page.size + '&triParNom=' + triParNom;
    return this.http.get<model.Page<model.Ressource>>(url, this.restUtils.creerHeader());
  }
}
