import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatSort } from '@angular/material';
import { tap } from 'rxjs/operators';
import { merge } from 'rxjs';
import { Language } from 'angular-l10n';

import { DataSourceComponent } from '../../shared/service/datasource.component';
import { RessourceService } from '../service/ressource.service';
import * as model from '../../model/model';
import { RoleService } from '../service/role.service';

/** Page listant les ressources et permettant leur création, modification et suppression */
@Component({ selector: 'page-ressource', templateUrl: './page-ressource.component.html', styleUrls: ['./page-ressource.component.css'] })
export class PageRessourceComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des colonnes fixes du tableau à générer (générée ne veut pas dire affichée) */
  colonnesFixes = ['clef', 'description'];

  /** Liste complete des colonnes à afficher */
  get columnsToDisplay(): string[] { return this.colonnesFixes.concat(this.roles.map((r) => r.nom)); }

  /** DataSource du tableau (initialisé dans le onInit) */
  dataSource: DataSourceComponent<model.Ressource>;

  /** Liste des roles existants */
  roles: model.Role[] = [];

  /** Composant de pagination */
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /** Composant de tri */
  @ViewChild(MatSort) sorter: MatSort;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private route: ActivatedRoute, private ressourceService: RessourceService, private roleService: RoleService) { }

  /** Initialisation des composants de la page */
  ngOnInit(): void {

    // Chargement des ressources
    this.dataSource = new DataSourceComponent<model.Ressource>((page) => this.ressourceService.listerRessources(page));
    this.dataSource.load();

    // Chargement des roles
    const pageDeTousLesRoles = new model.Page<model.Role>(50, 0);
    this.roleService.listerRoles(pageDeTousLesRoles).subscribe((roles: model.Page<model.Role>) => {

      // Sauvegarde de la liste des roles
      this.roles = roles.content;
    });
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

  /** Vérifie que la ressource est autorisée pour ce role */
  estAutorise(role: model.Role, ressource: model.Ressource): boolean { return !!role.ressourcesAutorisees.find((re) => re.clef === ressource.clef); }
}
