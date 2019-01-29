// Les modules Angular importés
import { NgModule } from '@angular/core';

// Import du module partagé
import { SharedModule } from '../shared/shared.module';

// Tous les composants applicatifs de l'application
import { CadreMoyenDePaiementComponent } from './page-adminconsommations/cadre-moyendepaiement/cadre-moyendepaiement.component';
import { CadreProduitsComponent } from './page-adminconsommations/cadre-produits/cadre-produits.component';
import { PageAdminConsommationsComponent } from './page-adminconsommations/page-adminconsommations.component';
import { CadreChambresComponent } from './page-adminreservations/cadre-chambres/cadre-chambres.component';
import { CadreFormulesComponent } from './page-adminreservations/cadre-formules/cadre-formules.component';
import { CadreOptionsComponent } from './page-adminreservations/cadre-options/cadre-options.component';
import { PageAdminReservationsComponent } from './page-adminreservations/page-adminreservations.component';
import { PageConsommationsComponent } from './page-consommations/page-consommations.component';
import { CadreDetailsFactureComponent } from './page-facturations/cadre-detailsfacture/cadre-detailsfacture.component';
import { DialogPaiementComponent } from './page-facturations/cadre-detailsfacture/dialog-paiement/dialog-paiement.component';
import { CadreListeFacturesComponent } from './page-facturations/cadre-listefactures/cadre-listefactures.component';
import { PageFacturationsComponent } from './page-facturations/page-facturations.component';
import { CadreCalendrierComponent } from './page-reservations/cadre-calendrier/cadre-calendrier.component';
import { CadreReservationComponent } from './page-reservations/cadre-reservation/cadre-reservation.component';
import { PageReservationsComponent } from './page-reservations/page-reservations.component';

// Les composants injectables
import { ReservationService } from './service/reservation.service';

@NgModule({
  imports: [
    // Le module partagé
    SharedModule
  ],

  // Tous les composants applicatifs du module
  declarations: [
    PageReservationsComponent, CadreCalendrierComponent, CadreReservationComponent,
    PageConsommationsComponent,
    PageAdminReservationsComponent, CadreChambresComponent, CadreFormulesComponent, CadreOptionsComponent,
    PageAdminConsommationsComponent, CadreProduitsComponent, CadreMoyenDePaiementComponent,
    PageFacturationsComponent, CadreListeFacturesComponent, CadreDetailsFactureComponent, DialogPaiementComponent
  ],

  // Toutes les Dialog
  entryComponents: [
    DialogPaiementComponent
  ],

  // Les services
  providers: [
    ReservationService
  ]
})
export class ReservationsModule { }
