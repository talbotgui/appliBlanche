// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import du module partagé
import { SharedModule } from '../shared/shared.module';

// Tous les composants applicatifs de l'application
import { PageReservationsComponent } from './page-reservations/page-reservations.component';
import { CadreCalendrierComponent } from './page-reservations/cadre-calendrier/cadre-calendrier.component';
import { CadreReservationComponent } from './page-reservations/cadre-reservation/cadre-reservation.component';
import { PageAdminReservationsComponent } from './page-adminreservations/page-adminreservations.component';
import { CadreProduitsComponent } from './page-adminreservations/cadre-produits/cadre-produits.component';
import { CadreChambresComponent } from './page-adminreservations/cadre-chambres/cadre-chambres.component';

// Les composants injectables
import { ReservationService } from './service/reservation.service';

@NgModule({
  imports: [
    // Le module partagé
    SharedModule
  ],

  // Tous les composants applicatifs du module
  declarations: [
    PageAdminReservationsComponent, CadreProduitsComponent, CadreChambresComponent,
    PageReservationsComponent, CadreCalendrierComponent, CadreReservationComponent
  ],

  // Les services
  providers: [
    ReservationService
  ]
})
export class ReservationsModule { }
