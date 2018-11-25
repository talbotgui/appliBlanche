import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Le composant de sécurité
import { AuthGuard } from '../service/auth-guard.service';

// Tous les composants applicatifs de l'application
import { PageUtilisateurComponent } from './page-utilisateur/page-utilisateur.component';
import { PageRoleComponent } from './page-role/page-role.component';
import { PageRessourceComponent } from './page-ressource/page-ressource.component';
import { PageMonitoringComponent } from './page-monitoring/page-monitoring.component';

/** Toutes les routes */
const routes: Routes = [
  { path: 'page-utilisateur-route', component: PageUtilisateurComponent, canActivate: [AuthGuard] },
  { path: 'page-role-route', component: PageRoleComponent, canActivate: [AuthGuard] },
  { path: 'page-ressource-route', component: PageRessourceComponent, canActivate: [AuthGuard] },
  { path: 'page-monitoring-route', component: PageMonitoringComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
/** Module de déclaration des routes */
export class AdministrationRoutingModule { }
