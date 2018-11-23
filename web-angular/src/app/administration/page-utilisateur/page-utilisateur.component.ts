import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Language } from 'angular-l10n';

import { UtilisateurService } from '../service/utilisateur.service';
import * as model from '../../model/model';
import { DataSourceSimpleComponent } from '../../shared/service/datasourceSimple.component';
import { RoleService } from '../service/role.service';

/**
 * Page de visualisation et création des utilisateurs
 */
@Component({ selector: 'page-utilisateur', templateUrl: './page-utilisateur.component.html', styleUrls: ['./page-utilisateur.component.css'] })
export class PageUtilisateurComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Utilisateur en cours d'édition */
  utilisateurSelectionne: model.Utilisateur | undefined;

  /** Flag permettant de savoir si c'est une création ou une modification (pour bloquer le login) */
  creation: boolean = false;

  /** Liste des colonnes à afficher dans le tableau */
  displayedColumns: string[] = ['login', 'roles', 'actions'];

  /** DataSource du tableau (initialisé dans le onInit) */
  dataSource: DataSourceSimpleComponent<model.Utilisateur>;

  /** Liste complète de tous les roles */
  tousLesRoles: model.Role[];

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private route: ActivatedRoute, private utilisateurService: UtilisateurService, private roleService: RoleService) { }

  /** Appel au service à l'initialisation du composant */
  ngOnInit(): void {
    // Reset du formulaire
    this.annulerCreationUtilisateur();

    // Creation du datasource
    this.dataSource = new DataSourceSimpleComponent<model.Utilisateur>(() => this.utilisateurService.listerUtilisateurs());

    // Chargement des données avec les paramètres par défaut (nb éléments par page par défaut défini dans le DataSource)
    this.dataSource.load();

    // Chargement de la liste des roles
    const pageRecherche = new model.Page<model.Role>(50, 0);
    this.roleService.listerRoles(pageRecherche).subscribe((page: model.Page<model.Role>) => this.tousLesRoles = page.content);
  }

  /** On annule la creation en masquant le formulaire */
  annulerCreationUtilisateur() {
    this.utilisateurSelectionne = undefined;
  }

  /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
  creerUtilisateur() {
    this.utilisateurSelectionne = new model.Utilisateur();
    this.creation = true;
  }

  /** Appel au service de sauvegarde puis rechargement des données */
  sauvegarderUtilisateur() {
    if (this.utilisateurSelectionne) {
      this.utilisateurService.sauvegarderUtilisateur(this.utilisateurSelectionne).subscribe((retour) => {
        this.dataSource.load();
        this.annulerCreationUtilisateur();
      });
    }
  }

  /** A la sélection */
  selectionnerUtilisateur(utilisateur: model.Utilisateur) {
    this.utilisateurSelectionne = utilisateur;
    this.creation = false;
  }

  /** A la suppression  */
  supprimerUtilisateur(utilisateur: model.Utilisateur) {
    this.utilisateurService.supprimerUtilisateur(utilisateur)
      .subscribe((retour) => {
        this.dataSource.load();
        this.annulerCreationUtilisateur();
      });
  }

  /** Ajout suppression du role à cet utilisateur */
  ajouterRetirerRole(utilisateur: model.Utilisateur, role: model.Role, statut: boolean) {
    this.utilisateurService.ajouterRetirerAutorisation(utilisateur, role, statut).subscribe(() => {
      this.dataSource.load();
      this.annulerCreationUtilisateur();
    });
  }
}
