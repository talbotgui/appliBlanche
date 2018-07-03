import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material';

import { ClientService } from '../service/client.service';
import * as model from '../model/model';

@Component({ selector: 'page-client', templateUrl: './page-client.component.html', styleUrls: ['./page-client.component.css'] })
export class PageClientComponent implements OnInit {

  displayedColumns: string[] = ['nomClient', 'ville', 'nbDossiers', 'nbDemandes', 'dateCreationDernierDossier', 'actions'];
  dataSource: model.ClientDto[] = [];

  // Liste des clients à afficher
  page: model.Page<model.ClientDto> = new model.Page(10, 0);

  // Client en cours d'édition
  clientSelectionne: model.ClientDto | undefined;

  // Un constructeur pour se faire injecter les dépendances
  constructor(private route: ActivatedRoute, private clientService: ClientService) { }

  // Appel au service à l'initialisation du composant
  ngOnInit(): void {
    this.chargerDonnees();

    // Reset du formulaire
    this.annulerCreation();
  }

  changerPage(index: number) {
    console.debug("aze=" + index);
    this.page.number = index;
    this.chargerDonnees();
  }

  chargerDonnees() {
    this.clientService.listerClientsDto(this.page)
      .subscribe((page: model.Page<model.ClientDto>) => {
        this.page = page;
        this.dataSource = page.content;
      });

  }

  annulerCreation() {
    this.clientSelectionne = undefined;
  }

  creer() {
    this.clientSelectionne = new model.ClientDto();
  }

  sauvegarder() {
    if (this.clientSelectionne) {
      this.clientService.sauvegarderClient(this.clientSelectionne)
        .subscribe(retour => {
          this.chargerDonnees();
          this.annulerCreation();
        });
    }
  }

  supprimer(client: model.ClientDto) {
    this.clientService.supprimerClient(client)
      .subscribe(retour => { this.chargerDonnees(); });
  }

  // A la sélection d'un élève
  selectionner(client: model.ClientDto) {
    this.clientSelectionne = client;
  }

  listeDeNombres(total: number): number[] {
    return Array.from(Array(total).keys());
  }

}
