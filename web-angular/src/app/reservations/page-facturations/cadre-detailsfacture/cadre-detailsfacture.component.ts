import { Component, OnInit, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

// Import des libs et classes pour faire du PDF
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';

import { ReservationService } from '../../service/reservation.service';

/** Page de gestion des reservations */
@Component({ selector: 'cadre-detailsfacture', templateUrl: './cadre-detailsfacture.component.html', styleUrls: ['./cadre-detailsfacture.component.css'] })
export class CadreDetailsFactureComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
  }

  testerCreationPdf() {
    const data = document.getElementById('detailsFacture');
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
        // pdf.text('petit texte', 10, 10);

        // Sauvegarde
        pdf.save('MYPdf.pdf');
      });
    }
  }
}
