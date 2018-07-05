import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatSort } from '@angular/material';
import { tap } from 'rxjs/operators';
import { merge } from 'rxjs';

import { DataSourceComponent } from '../service/datasource.component';
import { ClientService } from '../service/client.service';
import * as model from '../model/model';

@Component({ selector: 'page-client', templateUrl: './page-client.component.html', styleUrls: ['./page-client.component.css'] })
export class PageClientComponent implements OnInit {

  // Liste des colonnes à afficher dans le tableau
  displayedColumns: string[] = ['nomClient', 'ville', 'nbDossiers', 'nbDemandes', 'dateCreationDernierDossier', 'actions'];

  // Data source à utiliser
  // initialisé dans le onInit
  dataSource: DataSourceComponent<model.ClientDto>;

  // Composant de pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;

  // Composant de tri
  @ViewChild(MatSort) sorter: MatSort;

  // Client en cours d'édition
  clientSelectionne: model.ClientDto | undefined;

  // Un constructeur pour se faire injecter les dépendances
  constructor(private route: ActivatedRoute, private clientService: ClientService) { }

  // Initialisation des composants de la page
  ngOnInit(): void {
    // Creation du datasource
    this.dataSource = new DataSourceComponent<model.ClientDto>(page => this.clientService.listerClientsDto(page));

    // Chargement des données avec les paramètres par défaut (nb éléments par page par défaut défini dans le DataSource)
    this.dataSource.load();

    // Reset du formulaire
    this.annulerCreation();
  }

  // Binding des évènements des composants de la page
  ngAfterViewInit() {

    // au changement de tri, on recharge les données en revenant en première page
    this.sorter.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    // au changemnt de page (index ou taille), on recharge les données
    merge(this.paginator.page, this.sorter.sortChange)
      .pipe(tap(() => {
        this.dataSource.preparerPagination(this.paginator);
        this.dataSource.preparerTri(this.sorter);
        this.dataSource.load()
      })
      )
      .subscribe();
  }

  // Reset et masquage du formulaire de modification/création
  annulerCreation() {
    this.clientSelectionne = undefined;
  }

  // Initialiser le formulaire de création
  creer() {
    this.clientSelectionne = new model.ClientDto();
  }

  // sauvegarder un client et recharger les données sans changer la pagination
  sauvegarder() {
    if (this.clientSelectionne) {
      this.clientService.sauvegarderClient(this.clientSelectionne)
        .subscribe(retour => {
          this.dataSource.load();
          this.annulerCreation();
        });
    }
  }

  // supprimer un client
  // et recharger les données en retournant en première page
  // (pour éviter de rester sur une page vide)
  supprimer(client: model.ClientDto) {
    this.clientService.supprimerClient(client)
      .subscribe(retour => {
        this.paginator.firstPage();
      });
  }

  // A la sélection d'un élève
  selectionner(client: model.ClientDto) {
    this.clientSelectionne = client;
  }
}
