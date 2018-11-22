import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { of, BehaviorSubject, Observable } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

/** Datasource utilisé pour alimenter un tableau non paginé ni trié */
export abstract class AbstractDataSourceComponent<T> implements DataSource<T> {

  /** BehaviorSubject informant d'un chargement en cours */
  protected loadingSubject = new BehaviorSubject<boolean>(false);

  /** Vecteur permettant de manipuler les chargements de données */
  private dataSubject = new BehaviorSubject<any>([]);

  /** Observable informant d'un chargement en cours (utilisé par un spinner par exemple) */
  get loading() { return this.loadingSubject.asObservable(); }

  /** Pour permettre au tableau de récupérer les données quand elles seront disponibles */
  connect(collectionViewer: CollectionViewer): Observable<T[]> {
    return this.dataSubject.asObservable();
  }

  /** Pour tout fermer */
  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
  }

  /** Début du chargement */
  notifierDebutChargement() {
    this.loadingSubject.next(true);
  }

  /** Fin du chargement */
  notifierFinChargement() {
    this.loadingSubject.next(false);
  }

  /** Publication des données dans le subject */
  publierDonneees(donnees: any) {
    this.dataSubject.next(donnees);
  }
}
