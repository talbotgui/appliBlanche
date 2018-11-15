import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { RestUtilsService } from '../../shared/service/restUtils.service';
import { HttpProxy } from '../../shared/service/httpProxy.component';

import * as model from '../../model/model';

/** Composant TS d'interface avec les API Back de manipulation des roles */
@Injectable()
export class RoleService {

  /** Constructeur avec injection */
  constructor(private http: HttpProxy, private restUtils: RestUtilsService) { }

  /**
   * Liste des roles de manière paginée et triée.
   *
   * @param page La page demandée (nb éléments par page, index de la page et ordre de tri)
   */
  listerRoles(page: model.Page<any>): Observable<{} | model.Page<model.Role>> {

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
    const url = environment.baseUrl + '/v1/roles?pageNumber=' + page.number + '&pageSize=' + page.size + '&triParNom=' + triParNom;
    return this.http.get<model.Page<model.Role>>(url, this.restUtils.creerHeader());
  }

  /** Sauvegarde d'un role via l'API */
  sauvegarderRole(role: model.Role): Observable<{} | void> {
    const donnees = new HttpParams().set('nom', role.nom);
    const url = environment.baseUrl + '/v1/roles';
    return this.http.post<void>(url, donnees, this.restUtils.creerHeaderPost());
  }

  /** Suppression d'un role via l'API */
  supprimerRole(role: model.Role): Observable<{} | void> {
    const url = environment.baseUrl + '/v1/roles/' + role.nom;
    return this.http.delete<void>(url, this.restUtils.creerHeader());
  }
}
