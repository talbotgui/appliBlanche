// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import du module partagé
import { SharedModule } from '../shared/shared.module';

// Tous les composants applicatifs de l'application
import { PageUtilisateurComponent } from './page-utilisateur/page-utilisateur.component';
import { PageRoleComponent } from './page-role/page-role.component';

// Les composants injectables
import { UtilisateurService } from './service/utilisateur.service';
import { RoleService } from './service/role.service';

@NgModule({
  imports: [
    // Le module partagé
    SharedModule
  ],

  // Tous les composants applicatifs du module
  declarations: [
    PageUtilisateurComponent, PageRoleComponent
  ],

  // Les services
  providers: [
    UtilisateurService, RoleService
  ]
})
export class AdministrationModule { }
