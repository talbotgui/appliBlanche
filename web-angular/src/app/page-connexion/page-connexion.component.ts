import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SecuriteService } from '../service/securite.service';
import * as model from '../model/model';

/** Page de connexion */
@Component({ selector: 'page-connexion', templateUrl: './page-connexion.component.html', styleUrls: ['./page-connexion.component.css'] })
export class PageConnexionComponent implements OnInit {

  /** Login de l'utilisateur */
  login: string = 'adminAsupprimer';

  /** Mot de passe de l'utilisateur */
  mdp: string = 'adminAsupprimer';

  /** Flag erreur de connexion */
  messageErreurConnexion: boolean = false;

  /** Url de redirection à utiliser à la sortie de la connexion */
  redirectionPostConnexion: string | undefined = undefined;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private router: Router, private securiteService: SecuriteService, private route: ActivatedRoute) { }

  /** A l'initialisation */
  ngOnInit() {
    // Lecture des paramètres de l'URL
    this.route.queryParamMap.pipe(map((params) => params.get('redirect') || undefined))
      .subscribe((redirect) => this.redirectionPostConnexion = redirect);
  }

  /** Tentative de connexion */
  connecter(): void {

    // Suppression du message d'erreur
    this.messageErreurConnexion = false;

    // Connexion
    this.securiteService.connecter(this.login, this.mdp, () => {
      // redirection vers la page demandée ou la page d'accueil
      if (this.redirectionPostConnexion) {
        this.router.navigate([this.redirectionPostConnexion]);
      } else {
        this.router.navigate(['page-accueil-route']);
      }
    }, () => { this.messageErreurConnexion = true; });
  }
}
