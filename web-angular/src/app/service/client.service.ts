import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import { RestUtilsService } from './restUtils.service';

import * as model from '../model/model';

@Injectable()
export class ClientService {

  constructor(private http: HttpClient, private restUtils: RestUtilsService) { }

  listerClientsDto(page: model.Page<any>): Observable<{} | model.Page<model.ClientDto>> {

    // Seul un tri par défaut est possible
    let triParNom: string = '';
    if (page.sort) {
      console.debug(page.sort.sortOrder);
      if (page.sort.sortOrder === 'asc') {
        triParNom = 'true';
      } else {
        triParNom = 'false';
      }
    }

    // Appel à l'API
    const url = 'http://localhost:9090/applicationBlanche/v1/clients?pageNumber=' + page.number + '&pageSize=' + page.size + '&triParNom=' + triParNom;
    return this.http.get<model.Page<model.ClientDto>>(url, this.restUtils.creerHeader())
      .pipe(catchError(this.restUtils.handleError));
  }

  sauvegarderClient(client: model.ClientDto): Observable<{} | void> {
    let donnees = new HttpParams().set('nom', client.nomClient);
    if (client.reference) {
      donnees = new HttpParams().set('nom', client.nomClient).set('reference', client.reference);
    }

    const url = 'http://localhost:9090/applicationBlanche/v1/clients';
    return this.http.post<void>(url, donnees, this.restUtils.creerHeaderPost())
      .pipe(catchError(this.restUtils.handleError));
  }

  supprimerClient(client: model.ClientDto): Observable<{} | void> {
    const url = 'http://localhost:9090/applicationBlanche/v1/clients/' + client.reference;
    return this.http.delete<void>(url, this.restUtils.creerHeader())
      .pipe(catchError(this.restUtils.handleError));
  }
}
