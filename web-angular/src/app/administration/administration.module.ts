// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import du module partagé
import { SharedModule } from '../shared/shared.module';

// Tous les composants applicatifs de l'application
import { PageUtilisateurComponent } from './page-utilisateur/page-utilisateur.component';
import { PageRoleComponent } from './page-role/page-role.component';
import { PageRessourceComponent } from './page-ressource/page-ressource.component';
import { PageMonitoringComponent } from './page-monitoring/page-monitoring.component';

// Les composants injectables
import { UtilisateurService } from './service/utilisateur.service';
import { RoleService } from './service/role.service';
import { RessourceService } from './service/ressource.service';
import { MonitoringService } from './service/monitoring.service';

@NgModule({
  imports: [
    // Le module partagé
    SharedModule
  ],

  // Tous les composants applicatifs du module
  declarations: [
    PageUtilisateurComponent, PageRoleComponent, PageRessourceComponent, PageMonitoringComponent
  ],

  // Les services
  providers: [
    UtilisateurService, RoleService, RessourceService, MonitoringService
  ]
})
export class AdministrationModule { }
