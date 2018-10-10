import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Le composant de sécurité
import { AuthGuard } from '../service/auth-guard.service';

// Tous les composants applicatifs de l'application
import { PageReservationsComponent } from './page-reservations/page-reservations.component';
import { PageAdminReservationsComponent } from './page-adminreservations/page-adminreservations.component';

/** Toutes les routes */
const routes: Routes = [
  { path: 'page-reservations-route', component: PageReservationsComponent, canActivate: [AuthGuard] },
  { path: 'page-adminreservations-route', component: PageAdminReservationsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
/** Module de déclaration des routes */
export class ReservationsRoutingModule { }
