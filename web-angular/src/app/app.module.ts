// Les modules Angular importés
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule, MatGridListModule } from '@angular/material';
import { MatSnackBarModule, MatTooltipModule } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatNativeDateModule, MAT_PLACEHOLDER_GLOBAL_OPTIONS, DateAdapter } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CKEditorModule } from 'ng2-ckeditor';
import { TreeModule } from 'angular-tree-component';
import { MapValuesPipe, AttributesToMapPipe } from './pipes.component';
import { MyDateAdapter } from './dateformat.component';

// Import de bootstrap
import { AlertModule } from 'ngx-bootstrap';

// Tous les composants applicatifs de l'application
import { AppComponent } from './app.component';
import { TabAccueilComponent } from './tab-accueil/tab-accueil.component';

// Les composants injectables
import { UtilisateurService } from './service/utilisateur.service';

// Le composant contenant les routes
import { AppRoutingModule } from './app-routing.module';

// Gestion des locales et des formats de date pour Angular 5
// @see https://angular.io/guide/i18n#i18n-pipes
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr, 'fr');

// Déclaration du module
@NgModule({

  // Le composant principal
  bootstrap: [AppComponent],

  // Tous les composants applicatifs de l'application
  declarations: [
    AppComponent, AttributesToMapPipe,
    MapValuesPipe, TabAccueilComponent
  ],

  // Tous les composants à afficher dans un Dialog
  entryComponents: [
  ],

  providers: [
    // Paramétrage global
    { provide: LOCALE_ID, useValue: 'fr' },
    { provide: DateAdapter, useClass: MyDateAdapter },
    { provide: MAT_PLACEHOLDER_GLOBAL_OPTIONS, useValue: { float: 'never' } },

    // Les composants injectables
    UtilisateurService

  ],

  // Les modules importés
  imports: [

    // Des modules classiques
    BrowserModule, FormsModule, HttpClientModule,

    // Les modules Material
    BrowserAnimationsModule, MatButtonModule, MatCardModule, MatChipsModule, MatDatepickerModule, MatGridListModule,
    MatNativeDateModule, MatRadioModule, MatSelectModule, MatSidenavModule, MatSnackBarModule, MatTooltipModule,

    // les composants WEB riches externes
    CKEditorModule, TreeModule,

    // Déclaration des routes
    AppRoutingModule,

    // Import de bootstrap
    AlertModule.forRoot()
  ]
})
export class AppModule { }
