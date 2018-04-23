import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Tous les composants applicatifs de l'application
import { TabAccueilComponent } from './tab-accueil/tab-accueil.component';
import { TabUtilisateurComponent } from './tab-utilisateur/tab-utilisateur.component';

const routes: Routes = [
  // pour rediriger par défaut sur le dashboard
  { path: '', redirectTo: '/tab-accueil-route', pathMatch: 'full' },
  { path: 'tab-accueil-route', component: TabAccueilComponent },
  { path: 'tab-utilisateur-route', component: TabUtilisateurComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
