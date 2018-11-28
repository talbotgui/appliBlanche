import { Component, OnInit, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

// Import des libs et classes pour faire du PDF
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';

import * as model from '../../model/model';
import { ReservationService } from '../../service/reservation.service';

/** Page de gestion des reservations */
@Component({ selector: 'cadre-detailsfacture', templateUrl: './cadre-detailsfacture.component.html', styleUrls: ['./cadre-detailsfacture.component.css'] })
export class CadreDetailsFactureComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  reservationSelectionnee: model.Reservation;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
  }

  /** A la sélection d'une réservation dans un autre composant  (pour ignorer la ligne suivante : @@angular:analyse:ignorerLigneSuivante@@) */
  selectionnerUneReservation(r: model.Reservation) {
    this.reservationSelectionnee = r;
  }

  /** Acte de facturation */
  facturer() {

    // Changement d'etat

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
        const dateDebut = this.reservationsService.formaterDate(this.reservationSelectionnee.dateDebut);
        const dateFin = this.reservationsService.formaterDate(this.reservationSelectionnee.dateFin);
        pdf.save('Note-' + this.reservationSelectionnee.client + '-' + dateDebut + '-' + dateFin + '.pdf');
      });
    }
  }
}
