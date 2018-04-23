import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Tous les composants applicatifs de l'application
import { PageAccueilComponent } from './page-accueil/page-accueil.component';
import { PageUtilisateurComponent } from './administration/page-utilisateur/page-utilisateur.component';

const routes: Routes = [
  // pour rediriger par défaut sur le dashboard
  { path: '', redirectTo: '/page-accueil-route', pathMatch: 'full' },
  { path: 'page-accueil-route', component: PageAccueilComponent },
  { path: 'page-utilisateur-route', component: PageUtilisateurComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
