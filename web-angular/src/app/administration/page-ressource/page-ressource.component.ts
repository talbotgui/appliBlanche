import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatSort } from '@angular/material';
import { tap } from 'rxjs/operators';
import { merge } from 'rxjs';
import { Language } from 'angular-l10n';

import { DataSourceComponent } from '../../shared/service/datasource.component';
import { RessourceService } from '../service/ressource.service';
import * as model from '../../model/model';

/** Page listant les ressources et permettant leur création, modification et suppression */
@Component({ selector: 'page-ressource', templateUrl: './page-ressource.component.html', styleUrls: ['./page-ressource.component.css'] })
export class PageRessourceComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des colonnes à afficher dans le tableau */
  displayedColumns: string[] = ['clef', 'description'];

  /** DataSource du tableau (initialisé dans le onInit) */
  dataSource: DataSourceComponent<model.Ressource>;

  /** Composant de pagination */
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /** Composant de tri */
  @ViewChild(MatSort) sorter: MatSort;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private route: ActivatedRoute, private ressourceService: RessourceService) { }

  /** Initialisation des composants de la page */
  ngOnInit(): void {
    // Creation du datasource
    this.dataSource = new DataSourceComponent<model.Ressource>((page) => this.ressourceService.listerRessources(page));

    // Chargement des données avec les paramètres par défaut (nb éléments par page par défaut défini dans le DataSource)
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
}
