import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';

import { UtilisateurService } from '../service/utilisateur.service';
import * as model from '../model/model';

@Component({ selector: 'page-connexion', templateUrl: './page-connexion.component.html', styleUrls: ['./page-connexion.component.css'] })
export class PageConnexionComponent implements OnInit {

  login: string = 'adminAsupprimer';
  mdp: string = 'adminAsupprimer';
  messageErreurConnexion: boolean = false;

  // Url de redirection à utiliser à la sortie de la connexion
  redirectionPostConnexion: string | undefined = undefined;

  // Un constructeur pour se faire injecter les dépendances
  constructor(private router: Router, private utilisateurService: UtilisateurService, private route: ActivatedRoute) { }

  ngOnInit() {
    // Lecture des paramètres de l'URL
    this.route.queryParamMap.pipe(map(params => params.get('redirect') || undefined))
      .subscribe(redirect => this.redirectionPostConnexion = redirect);
  }

  connecter(): void {

    // Suppression du message d'erreur
    this.messageErreurConnexion = false;

    // Connexion
    this.utilisateurService.connecter(this.login, this.mdp,
      () => {
        // redirection vers la page demandée ou la page d'accueil
        if (this.redirectionPostConnexion) {
          this.router.navigate([this.redirectionPostConnexion]);
        } else {
          this.router.navigate(['page-accueil-route']);
        }
      },
      () => {
        this.messageErreurConnexion = true;
      });
  }


}
