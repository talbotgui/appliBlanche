import { Component, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

// Import des libs et classes pour faire du PDF
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';

import * as model from '../../model/model';
import { ReservationService } from '../../service/reservation.service';
import { MatDialog } from '@angular/material';
import { DialogPaiementComponent } from './dialog-paiement/dialog-paiement.component';

/** Page de gestion des reservations */
@Component({ selector: 'cadre-detailsfacture', templateUrl: './cadre-detailsfacture.component.html', styleUrls: ['./cadre-detailsfacture.component.css'] })
export class CadreDetailsFactureComponent {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Réservation sélectionnée */
  reservationSelectionnee: model.Reservation | undefined;

  /** Facture courante */
  factureDeLaReservationSelectionee: model.Facture | undefined;

  /** Bus de message pour communiquer avec le composant parent */
  busDeMessage = new EventEmitter<string>();

  /** Montant calculé */
  montantRestantDu: number;

  /** Montant calculé */
  montantTotal: number;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService, private dialogPaiement: MatDialog) { }

  /** A la sélection d'une réservation dans un autre composant  (pour ignorer la ligne suivante car ce n'est pas une méthode appelée de l'HTML : @@angular:analyse:ignorerLigneSuivante@@) */
  selectionnerUneReservation(r: model.Reservation) {
    this.reservationSelectionnee = r;
    this.factureDeLaReservationSelectionee = undefined;

    this.reservationsService.calculerMontantTotal(r.reference).subscribe((m) => {
      this.montantTotal = m;
      this.montantRestantDu = this.reservationsService.calculerMontantRestantDu(this.montantTotal, r.paiements);
    });
  }

  /** Acte de facturation */
  facturer() {

    // Changement d'etat, calcul du montant total et génération du PDF
    // Refresh des données à la fin
    if (this.reservationSelectionnee) {
      this.reservationsService.facturer(this.reservationSelectionnee.reference).subscribe((facture: model.Facture) => {

        // Raffraichissement des données de la reservation
        this.raffraichirDonneesDeLaPage();

        // Sauvegarde de la facture dans les données du composant
        this.factureDeLaReservationSelectionee = facture;
      });
    }
  }

  telechargerLaFacture() {
    if (this.factureDeLaReservationSelectionee) {
      const contenu = new Blob([this.factureDeLaReservationSelectionee.pdf], { type: 'application/pdf' });
      window.open(URL.createObjectURL(contenu));
    }
  }

  defacturer() {
    if (this.reservationSelectionnee) {
      this.reservationsService.changerEtatReservation(this.reservationSelectionnee.reference, 'EN_COURS').subscribe(() => {
        this.raffraichirDonneesDeLaPage();
      });
    }
  }

  /** Génère le PDF */
  editerUneNote() {
    const data = document.getElementById('contenuNote');
    if (data) {
      html2canvas(data).then((canvas: any) => {
        // Pour changer les dimensions de l'image en conservant le ratio
        const imgWidth = 208;
        const imgHeight = canvas.height * imgWidth / canvas.width;

        // Creation de l'image
        const contentDataURL = canvas.toDataURL('image/png');

        // création du document en A4 avec l'image en position 0:0
        const pdf = new jspdf('p', 'mm', 'a4');
        pdf.addImage(contentDataURL, 'PNG', 0, 0, imgWidth, imgHeight);

        // Sauvegarde
        if (this.reservationSelectionnee) {
          const dateDebut = this.reservationsService.formaterDate(this.reservationSelectionnee.dateDebut);
          const dateFin = this.reservationsService.formaterDate(this.reservationSelectionnee.dateFin);
          pdf.save('Note-' + this.reservationSelectionnee.client + '-' + dateDebut + '-' + dateFin + '.pdf');
        }
      });
    }
  }

  /** Affichage du popup de gestion des paiements */
  afficherPopupPaiement() {

    // Ouverture de la popup
    const dialogRef = this.dialogPaiement.open(DialogPaiementComponent, { data: { reservation: this.reservationSelectionnee, montantTotal: this.montantTotal } });

    // A sa fermeture
    dialogRef.afterClosed().subscribe((dataRetour: any) => {
      if (this.reservationSelectionnee) {
        this.reservationSelectionnee.paiements = dataRetour;
        this.montantRestantDu = this.reservationsService.calculerMontantRestantDu(this.montantTotal, this.reservationSelectionnee.paiements);
      }
    });
  }

  /** Raffraichit les données du backend */
  private raffraichirDonneesDeLaPage() {
    if (this.reservationSelectionnee) {

      // Raffraichit les données de la réservation depuis le backend
      this.reservationsService.chargerReservation(this.reservationSelectionnee.reference).subscribe((r) => {
        this.reservationSelectionnee = r;
      });

      // Raffraichissement des données dans le composant d'à coté
      this.busDeMessage.emit('');
    }
  }
}
