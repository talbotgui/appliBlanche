// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import de bootstrap
import { AlertModule } from 'ngx-bootstrap';

// Les composants l10n
import { TranslationModule } from 'angular-l10n';
import { MyTranslationProvider } from './service/myTranslationProvider.component';

// La configuration de l'application
import { environment } from '../environments/environment';

// Pour faire de l'application une PWA
import { ServiceWorkerModule } from '@angular/service-worker';

// Tous les composants applicatifs de ce module
import { AppComponent } from './app.component';
import { PageAccueilComponent } from './page-accueil/page-accueil.component';
import { PageConnexionComponent } from './page-connexion/page-connexion.component';
import { CadreMenuComponent } from './cadre-menu/cadre-menu.component';
import { PageUtilisateurComponent } from './administration/page-utilisateur/page-utilisateur.component';
import { PageClientComponent } from './page-client/page-client.component';

// Les composants injectabables de ce module
import { UtilisateurService } from './service/utilisateur.service';
import { ClientService } from './service/client.service';

// Import des modules
import { SharedModule, l10nConfig } from './shared.module';
import { ReservationsModule } from './reservations/reservations.module';

// Le composant contenant les routes
import { AppRoutingModule } from './app-routing.module';

// Déclaration du module
@NgModule({

  // Le composant principal
  bootstrap: [AppComponent],

  // Tous les composants applicatifs de ce module
  declarations: [AppComponent, PageAccueilComponent, PageConnexionComponent, CadreMenuComponent, PageUtilisateurComponent, PageClientComponent],

  // Les modules importés
  imports: [


    // Import de bootstrap
    AlertModule.forRoot(),

    // Gestion de l'internationnalisation
    TranslationModule.forRoot(l10nConfig, { translationProvider: MyTranslationProvider }),

    // Pour faire de l'application une PWA
    ServiceWorkerModule.register('/' + environment.baseUri + '/ngsw-worker.js', { enabled: environment.production }),

    // Le module partagé
    SharedModule,

    // Déclaration des modules applicatifs
    ReservationsModule,

    // Déclaration des routes
    AppRoutingModule,
  ],

  // Les composants injectables
  providers: [
    UtilisateurService, ClientService
  ]
})

/** Module principal */
export class AppModule {

}
