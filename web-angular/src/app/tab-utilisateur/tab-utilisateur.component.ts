import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { UtilisateurService } from '../service/utilisateur.service';
import * as model from '../model/model';

@Component({ selector: 'tab-utilisateur', templateUrl: './tab-utilisateur.component.html', styleUrls: ['./tab-utilisateur.component.css'] })
export class TabUtilisateurComponent implements OnInit {

  // Liste des utilisateurs à afficher
  utilisateurs: model.Utilisateur[];

  // Utilisateur en cours d'édition
  utilisateurSelectionne: model.Utilisateur;

  // Un constructeur pour se faire injecter les dépendances
  constructor(private route: ActivatedRoute, private utilisateurService: UtilisateurService) { }

  // Appel au service à l'initialisation du composant
  ngOnInit(): void {

    // Chargemnt de la liste des utilisateurs
    this.utilisateurService.listerUtilisateurs().subscribe(liste => {
      this.utilisateurs = liste;
    });

    // Si des paramètres sont présents, initialisation du formulaire avec les données de l'objet indiqué
    this.route.params.subscribe((params: { [key: string]: any }) => {
      const loginUtilisateur = params['loginUtilisateur'];
      if (loginUtilisateur) {
        this.utilisateurs.forEach(u => {
          if (u.login == loginUtilisateur) {
            this.utilisateurSelectionne = u;
          }
        })
      }
    });
  }

  // A la sélection d'un élève
  selectionnerUtilisateur(utilisateur: model.Utilisateur) {
    this.utilisateurSelectionne = utilisateur;
  }

}
