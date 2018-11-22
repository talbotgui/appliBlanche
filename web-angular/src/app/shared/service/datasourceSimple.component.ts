import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { of, BehaviorSubject, Observable } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

/** Datasource utilisé pour alimenter un tableau non paginé ni trié */
export class DataSourceSimpleComponent<T> implements DataSource<T> {

  /** BehaviorSubject informant d'un chargement en cours */
  public loadingSubject = new BehaviorSubject<boolean>(false);

  /** Observable informant d'un chargement en cours */
  public loading$ = this.loadingSubject.asObservable();

  /** Liste retournée par le service */
  public liste: T[] = [];

  /** Vecteur permettant de manipuler les chargements de données */
  private dataSubject = new BehaviorSubject<T[]>([]);

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private methodeDeChargement: () => Observable<{} | T[]>) { }

  /** Pour permettre au tableau de récupérer les données quand elles seront disponibles */
  connect(collectionViewer: CollectionViewer): Observable<T[]> {
    return this.dataSubject.asObservable();
  }

  /** Pour tout fermer */
  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
  }

  /** Charger le données */
  load() {

    // Chargement en cours
    this.loadingSubject.next(true);

    // Appel à l'API pour récupérer des données
    this.methodeDeChargement().pipe(
      // Traitement des erreurs (TODO)
      catchError(() => of([])),
      // Fin du chargement
      finalize(() => this.loadingSubject.next(false))
    )
      // Traitement de la réponse
      .subscribe((listeResultat: T[]) => {
        this.liste = listeResultat;
        this.dataSubject.next(listeResultat);
      });
  }
}
