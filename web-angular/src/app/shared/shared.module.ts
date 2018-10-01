// Les modules Angular importés
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID, ErrorHandler } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatInputModule, MatCommonModule, MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule } from '@angular/material';
import { MatGridListModule, MatPaginatorModule, MatSortModule, MatTableModule } from '@angular/material';
import { MatSnackBarModule, MatTooltipModule, MatNativeDateModule, DateAdapter } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Import du module bootstrap
import { AlertModule } from 'ngx-bootstrap';

// Import du module ServiceWorker pour faire de l'application une PWA
import { ServiceWorkerModule } from '@angular/service-worker';

// Les composants l10n
import { TranslationModule } from 'angular-l10n';

// Les composants l10n
import { L10nConfig, L10nLoader, StorageStrategy, ProviderType } from 'angular-l10n';

// Les composants propres à l'application
import { MapValuesPipe, AttributesToMapPipe } from './pipes/pipes.component';
import { MyDateAdapter } from './pipes/dateformat.component';

// La configuration de l'application
import { environment } from '../../environments/environment';

// Les composants injectables
import { ExceptionHandler } from './exception/ExceptionHandler';
import { IntercepteurHttp } from './exception/IntercepteurHttp';
import { HttpProxy } from './service/httpProxy.component';
import { RestUtilsService } from './service/restUtils.service';
import { BrowserComponent } from './service/browser.component';
import { MyTranslationProvider } from './service/myTranslationProvider.component';

// Gestion des locales et des formats de date pour Angular 5
// @see https://angular.io/guide/i18n#i18n-pipes
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr, 'fr');

/** Configuration de l'internationnalisation */
export const l10nConfig: L10nConfig = {
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

    // Déclaration des composants présents dans le code et réutilisables
    declarations: [
        // Les pipes réutilisables
        AttributesToMapPipe, MapValuesPipe,
    ],

    providers: [
        // Paramétrage global
        { provide: LOCALE_ID, useValue: 'fr' },
        { provide: DateAdapter, useClass: MyDateAdapter },

        // Composants de gestion des erreurs
        { provide: ErrorHandler, useClass: ExceptionHandler },
        { provide: HTTP_INTERCEPTORS, useClass: IntercepteurHttp, multi: true },

        // Les composants injectables
        RestUtilsService, BrowserComponent, HttpProxy, MyTranslationProvider

    ],

    // Les modules importés
    imports: [
        // Module commun
        CommonModule,

        // Import de bootstrap
        AlertModule.forRoot(),

        // Gestion de l'internationnalisation (la configuration est dans le SharedModule )
        TranslationModule.forRoot(l10nConfig, { translationProvider: MyTranslationProvider }),

        // Import du module ServiceWorker pour faire de l'application une PWA
        ServiceWorkerModule.register('/' + environment.baseUri + '/ngsw-worker.js', { enabled: environment.production }),
    ],

    // Modules exportés par le module partagé et utilisable dans ceux qui importe le module partagé
    exports: [
        // Les composants de l'applicatino
        AttributesToMapPipe, MapValuesPipe,

        // Des modules classiques
        BrowserModule, FormsModule, HttpClientModule,

        // Les modules Material
        BrowserAnimationsModule, MatInputModule, MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule,
        MatGridListModule, MatNativeDateModule, MatRadioModule, MatSelectModule, MatSidenavModule, MatSnackBarModule,
        MatTooltipModule, MatPaginatorModule, MatSortModule, MatTableModule, MatCommonModule,

        // Le module l10n
        TranslationModule

    ]
})
/** Module principal */
export class SharedModule {

    /** Constructeur avec injection */
    constructor(public l10nLoader: L10nLoader) {
        // Initialisation de l'i18n au démarrage de l'application
        this.l10nLoader.load();
    }
}
