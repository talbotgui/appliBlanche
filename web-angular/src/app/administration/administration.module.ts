// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import du module partagé
import { SharedModule } from '../shared.module';

// Tous les composants applicatifs de l'application
import { PageUtilisateurComponent } from './page-utilisateur/page-utilisateur.component';

// Les composants injectables
import { UtilisateurService } from './service/utilisateur.service';

@NgModule({
  imports: [
    // Le module partagé
    SharedModule
  ],

  // Tous les composants applicatifs du module
  declarations: [
    PageUtilisateurComponent
  ],

  // Les services
  providers: [
    UtilisateurService
  ]
})
export class AdministrationModule { }