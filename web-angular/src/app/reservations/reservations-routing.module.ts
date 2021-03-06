import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Le composant de sécurité
import { AuthGuard } from '../service/auth-guard.service';

// Tous les composants applicatifs de l'application
import { PageReservationsComponent } from './page-reservations/page-reservations.component';
import { PageConsommationsComponent } from './page-consommations/page-consommations.component';
import { PageAdminReservationsComponent } from './page-adminreservations/page-adminreservations.component';
import { PageAdminConsommationsComponent } from './page-adminconsommations/page-adminconsommations.component';
import { PageFacturationsComponent } from './page-facturations/page-facturations.component';

/** Toutes les routes */
const routes: Routes = [
  { path: 'page-reservations-route', component: PageReservationsComponent, canActivate: [AuthGuard] },
  { path: 'page-adminreservations-route', component: PageAdminReservationsComponent, canActivate: [AuthGuard] },
  { path: 'page-consommations-route', component: PageConsommationsComponent, canActivate: [AuthGuard] },
  { path: 'page-adminconsommations-route', component: PageAdminConsommationsComponent, canActivate: [AuthGuard] },
  { path: 'page-facturations-route', component: PageFacturationsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
/** Module de déclaration des routes */
export class ReservationsRoutingModule { }
