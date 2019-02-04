import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatSort } from '@angular/material';
import { tap } from 'rxjs/operators';
import { merge } from 'rxjs';
import { Language } from 'angular-l10n';

import * as model from '../../model/model';

import { RoleService } from '../service/role.service';
import { DataSourcePagineTrieComponent } from '../../shared/service/datasourcePagineTrie.component';
import { AnimationComponent } from '../../shared/service/animation.component';

/** Page listant les roles et permettant leur création, modification et suppression */
@Component({ selector: 'page-role', templateUrl: './page-role.component.html', styleUrls: ['./page-role.component.css'] })
export class PageRoleComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Liste des colonnes à afficher dans le tableau */
  displayedColumns: string[] = ['nom', 'actions'];

  /** DataSource du tableau (initialisé dans le onInit) */
  dataSource: DataSourcePagineTrieComponent<model.Role>;

  /** Composant de pagination */
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /** Composant de tri */
  @ViewChild(MatSort) sorter: MatSort;

  /** Role en cours d'édition */
  roleSelectionne: model.Role | undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private route: ActivatedRoute, private roleService: RoleService, private animationComponent: AnimationComponent) { }

  /** Initialisation des composants de la page */
  ngOnInit(): void {
    // Creation du datasource
    this.dataSource = new DataSourcePagineTrieComponent<model.Role>((page) => this.roleService.listerRoles(page));

    // Chargement des données avec les paramètres par défaut (nb éléments par page par défaut défini dans le DataSource)
    this.dataSource.load();

    // Reset du formulaire
    this.annulerCreation();
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

  /** Reset et masquage du formulaire de modification/création */
  annulerCreation() {
    this.roleSelectionne = undefined;
  }

  /** Initialiser le formulaire de création */
  creer() {
    this.roleSelectionne = new model.Role();
    this.animationComponent.deplacerLaVueSurLeComposant('formulaireAjoutRole', true);
  }

  /** sauvegarder un role et recharger les données sans changer la pagination */
  sauvegarder() {
    if (this.roleSelectionne) {
      this.roleService.sauvegarderRole(this.roleSelectionne)
        .subscribe(() => {
          this.dataSource.load();
          this.annulerCreation();
        });
    }
  }

  /**
   * supprimer un role
   * et recharger les données en retournant en première page
   * (pour éviter de rester sur une page vide)
   */
  supprimer(role: model.Role) {
    this.roleService.supprimerRole(role)
      .subscribe(() => {
        // retour à la première page si on est pas en page 1
        if (this.paginator.hasPreviousPage()) {
          this.paginator.firstPage();
        }
        // rechargement des données si on est déjà sur la première page
        else {
          this.dataSource.load();
        }
      });
  }
}
