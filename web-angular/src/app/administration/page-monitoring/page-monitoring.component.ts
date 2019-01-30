import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatSort } from '@angular/material';
import { tap } from 'rxjs/operators';
import { merge } from 'rxjs';
import { Language } from 'angular-l10n';

import * as model from '../model/model';
import { DataSourcePagineTrieComponent } from '../../shared/service/datasourcePagineTrie.component';
import { MonitoringService } from '../service/monitoring.service';

/** Page listant les éléments de monitoring */
@Component({ selector: 'page-monitoring', templateUrl: './page-monitoring.component.html' })
export class PageMonitoringComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des colonnes à afficher */
  columnsToDisplay = ['clef', 'nbAppels', 'tempsCumule', 'tempsMoyen', 'tempsMax', 'tempsMin'];

  /** DataSource du tableau (initialisé dans le onInit) */
  dataSource: DataSourcePagineTrieComponent<model.ElementMonitoring>;

  /** Composant de pagination */
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /** Composant de tri */
  @ViewChild(MatSort) sorter: MatSort;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private route: ActivatedRoute, private monitoringService: MonitoringService) { }

  /** Initialisation des composants de la page */
  ngOnInit(): void {

    // Chargement des ressources
    this.dataSource = new DataSourcePagineTrieComponent<model.ElementMonitoring>((page) => this.monitoringService.lireInformations(page));
    this.dataSource.load();
  }

  /** Binding des évènements des composants de la page */
  ngAfterViewInit() {

    // au changement de tri, on recharge les données en revenant en première page
    this.sorter.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    // au changemnt de page (index ou taille), on recharge les données
    merge(this.paginator.page, this.sorter.sortChange)
      .pipe(tap(() => {
        this.dataSource.preparerPagination(this.paginator);
        this.dataSource.preparerTri(this.sorter);
        this.dataSource.load();
      })
      )
      .subscribe();
  }

  /** Export en excel */
  exporterInformations() {
    this.monitoringService.exporterInformations();
  }
}
