import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { of, BehaviorSubject, Observable } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { AbstractDataSourceComponent } from './abstractDatasource.component';

/** Datasource utilisé pour alimenter un tableau non paginé ni trié */
export class DataSourceSimpleComponent<T> extends AbstractDataSourceComponent<T> {

  /** Liste retournée par le service */
  public liste: T[] = [];

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private methodeDeChargement: () => Observable<{} | T[]>) {
    super();
  }

  /** Charger le données */
  load() {

    // Chargement en cours
    super.notifierDebutChargement();

    // Appel à l'API pour récupérer des données
    this.methodeDeChargement().pipe(
      // Traitement des erreurs (TODO)
      catchError(() => of([])),
      // Fin du chargement
      finalize(() => super.notifierFinChargement())
    )
      // Traitement de la réponse
      .subscribe((listeResultat: T[]) => {
        this.liste = listeResultat;
        super.publierDonneees(listeResultat);
      });
  }
}
