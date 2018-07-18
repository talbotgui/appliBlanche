import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { MatPaginator, MatSort } from '@angular/material';

import { ClientService } from './client.service';
import * as model from '../model/model';

export class DataSourceComponent<T> implements DataSource<T> {

  private dataSubject = new BehaviorSubject<T[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  public page: model.Page<T> = new model.Page<T>(5, 0);

  constructor(private methodeDeChargement: (page: model.Page<T>) => Observable<{} | model.Page<T>>) { }

  // Pour permettre au tableau de récupérer les données quand elles seront disponibles
  connect(collectionViewer: CollectionViewer): Observable<T[]> {
    return this.dataSubject.asObservable();
  }

  // Pour tout fermer
  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
  }

  // Méthode récupérant les éléments de pagination d'un MatPaginator
  preparerPagination(paginator: MatPaginator) {
    this.page.number = paginator.pageIndex;
    this.page.size = paginator.pageSize;
  }

  // Méthode récupérant les éléments de tri d'un MatSort
  preparerTri(sorter: MatSort) {
    if (!!sorter.direction && !!sorter.active) {
      this.page.sort = new model.Sort();
      this.page.sort.sortColonne = sorter.active;
      this.page.sort.sortOrder = sorter.direction;
    } else {
      this.page.sort = undefined;
    }
  }

  load() {

    // Chargement en cours
    this.loadingSubject.next(true);

    // Appel à l'API pour récupérer des données
    this.methodeDeChargement(this.page).pipe(
      // Traitement des erreurs (TODO)
      catchError(() => of([])),
      // Fin du chargement
      finalize(() => this.loadingSubject.next(false))
    )
      // Traitement de la réponse
      .subscribe((pageResultat: model.Page<T>) => {
        this.page = pageResultat;
        this.dataSubject.next(pageResultat.content);
      });
  }
}