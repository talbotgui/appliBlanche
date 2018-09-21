// Les modules Angular importés
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID, ErrorHandler } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatInputModule, MatCommonModule, MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule } from '@angular/material';
import { MatGridListModule, MatPaginatorModule, MatSortModule, MatTableModule } from '@angular/material';
import { MatSnackBarModule, MatTooltipModule } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatNativeDateModule, DateAdapter } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { L10nConfig, L10nLoader, TranslationModule, StorageStrategy, ProviderType } from 'angular-l10n';
import { MapValuesPipe, AttributesToMapPipe } from './pipes.component';
import { MyDateAdapter } from './dateformat.component';

// Import de bootstrap
import { AlertModule } from 'ngx-bootstrap';

// La configuration de l'application
import { environment } from '../environments/environment';

// Tous les composants applicatifs de l'application
import { AppComponent } from './app.component';
import { PageAccueilComponent } from './page-accueil/page-accueil.component';
import { PageConnexionComponent } from './page-connexion/page-connexion.component';
import { CadreMenuComponent } from './cadre-menu/cadre-menu.component';
import { PageUtilisateurComponent } from './administration/page-utilisateur/page-utilisateur.component';
import { PageClientComponent } from './page-client/page-client.component';

// Les composants injectables
import { ExceptionHandler } from './exception/ExceptionHandler';
import { IntercepteurHttp } from './exception/IntercepteurHttp';
import { AuthGuard } from './service/auth-guard.service';
import { RestUtilsService } from './service/restUtils.service';
import { UtilisateurService } from './service/utilisateur.service';
import { ClientService } from './service/client.service';
import { PwaService } from './service/pwa.service';

// Le composant contenant les routes
import { AppRoutingModule } from './app-routing.module';

// Pour faire de l'application une PWA
import { ServiceWorkerModule } from '@angular/service-worker';

// Gestion des locales et des formats de date pour Angular 5
// @see https://angular.io/guide/i18n#i18n-pipes
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr, 'fr');

/** Configuration de l'internationnalisation */
const l10nConfig: L10nConfig = {
  locale: {
    // Liste des langues disponibles
    languages: [{ code: 'fr', dir: 'ltr' }, { code: 'en', dir: 'ltr' }],
    // Langue par défaut
    language: 'fr',
    // Lieu de stockage de la dernière langue sélectionnée par l'utilisateur
    storage: StorageStrategy.Cookie
  },
  translation: {
    // Source des libellés
    providers: [{ type: ProviderType.WebAPI, path: environment.baseUrl + '/i18n/' }],
    // cache
    caching: true,
    // Libellé par défaut si manquant
    missingValue: 'xxx'
  }
};

// Déclaration du module
@NgModule({

  // Le composant principal
  bootstrap: [AppComponent],

  // Tous les composants applicatifs de l'application
  declarations: [
    AppComponent, AttributesToMapPipe,
    MapValuesPipe, PageAccueilComponent, PageConnexionComponent, CadreMenuComponent, PageUtilisateurComponent, PageClientComponent
  ],

  // Tous les composants à afficher dans un Dialog
  entryComponents: [
  ],

  providers: [
    // Paramétrage global
    { provide: LOCALE_ID, useValue: 'fr' },
    { provide: DateAdapter, useClass: MyDateAdapter },

    // Composants de gestion des erreurs
    { provide: ErrorHandler, useClass: ExceptionHandler },
    { provide: HTTP_INTERCEPTORS, useClass: IntercepteurHttp, multi: true },

    // Les composants injectables
    RestUtilsService, UtilisateurService, ClientService, RestUtilsService, AuthGuard, PwaService

  ],

  // Les modules importés
  imports: [

    // Des modules classiques
    BrowserModule, FormsModule, HttpClientModule,

    // Les modules Material
    BrowserAnimationsModule, MatInputModule, MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule,
    MatGridListModule, MatNativeDateModule, MatRadioModule, MatSelectModule, MatSidenavModule, MatSnackBarModule,
    MatTooltipModule, MatPaginatorModule, MatSortModule, MatTableModule, MatCommonModule,

    // Déclaration des routes
    AppRoutingModule,

    // Import de bootstrap
    AlertModule.forRoot(),

    // Gestion de l'internationnalisation
    TranslationModule.forRoot(l10nConfig),

    // Pour faire de l'application une PWA
    ServiceWorkerModule.register('/' + environment.baseUri + '/ngsw-worker.js', { enabled: environment.production })
  ]
})
/** Module principal */
export class AppModule {

  /** Constructeur avec injection */
  constructor(public l10nLoader: L10nLoader) {
    // Initialisation de l'i18n au démarrage de l'application
    this.l10nLoader.load();
  }
}
