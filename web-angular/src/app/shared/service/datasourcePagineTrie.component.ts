import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { of, BehaviorSubject, Observable } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { MatPaginator, MatSort } from '@angular/material';

import * as model from '../../model/model';
import { AbstractDataSourceComponent } from './abstractDatasource.component';

/** Datasource utilisé pour alimenter un tableau paginé */
export class DataSourcePagineTrieComponent<T> extends AbstractDataSourceComponent<T> {

  /** Page envoyée au service et retournée par le service */
  public page: model.Page<T> = new model.Page<T>(5, 0);

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private methodeDeChargement: (page: model.Page<T>) => Observable<{} | model.Page<T>>) {
    super();
  }

  /** Méthode récupérant les éléments de pagination d'un MatPaginator */
  preparerPagination(paginator: MatPaginator) {
    this.page.number = paginator.pageIndex;
    this.page.size = paginator.pageSize;
  }

  /** Méthode récupérant les éléments de tri d'un MatSort */
  preparerTri(sorter: MatSort) {
    if (!!sorter.direction && !!sorter.active) {
      this.page.sort = new model.Sort();
      this.page.sort.sortColonne = sorter.active;
      this.page.sort.sortOrder = sorter.direction;
    } else {
      this.page.sort = undefined;
    }
  }

  /** Charger le données */
  load() {

    // Chargement en cours
    super.notifierDebutChargement();

    // Appel à l'API pour récupérer des données
    this.methodeDeChargement(this.page).pipe(
      // Traitement des erreurs (TODO)
      catchError(() => of([])),
      // Fin du chargement
      finalize(() => super.notifierFinChargement())
    )
      // Traitement de la réponse
      .subscribe((pageResultat: model.Page<T>) => {
        this.page = pageResultat;
        super.publierDonneees(pageResultat.content);
      });
  }
}
