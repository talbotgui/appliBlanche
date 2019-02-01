import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Language } from 'angular-l10n';

import * as model from '../../../model/model';
import { ReservationService } from '../../../service/reservation.service';

@Component({
  selector: 'dialog-paiement',
  templateUrl: './dialog-paiement.component.html',
  styleUrls: ['./dialog-paiement.component.scss']
})
export class DialogPaiementComponent implements OnInit {

  /** Validation du formulaire */
  get formulaireValide(): boolean {
    // Données obligatoires
    return !!this.nouveauPaiement && !!this.nouveauPaiement.moyenDePaiement && !!this.nouveauPaiement.moyenDePaiement.reference
      // soit le moyen de paiement présente un montant soit le montant est saisi
      && (!!this.nouveauPaiement.moyenDePaiement.montantAssocie || !!this.nouveauPaiement.montant);
  }

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips (ici, nécessaire car c'est un Dialog) */
  @Language() lang: string;

  /** Liste des paiements déjà enregistrés */
  paiements: model.Paiement[] = [];

  /** Nouveau paiement à enregistrer */
  nouveauPaiement: model.Paiement | undefined;

  /** Moyens de paiement disponibles */
  moyensDePaiement: model.MoyenDePaiement[] = [];

  /** Montant total de la réservation */
  private montantTotal: number;

  /** Référence de la réservation (nécessaire pour la sauvegarde du paiement) */
  private referenceReservation: string;

  /** Injection de dépendances */
  constructor(
    private dialogRef: MatDialogRef<DialogPaiementComponent>,
    @Inject(MAT_DIALOG_DATA) private dataInput: { reservation: model.Reservation, montantTotal: number },
    private service: ReservationService) { }

  /** A l'initialisation */
  ngOnInit() {
    // sauvegarde des données déjà enregistrées
    this.paiements = this.dataInput.reservation.paiements;
    this.referenceReservation = this.dataInput.reservation.reference;
    this.montantTotal = this.dataInput.montantTotal;

    // Chargement de la liste des moyenDePaiement
    this.service.listerMoyensDePaiement().subscribe((liste) => this.moyensDePaiement = liste);
  }

  /** Enregistrement du paiement */
  enregistrer() {
    if (this.nouveauPaiement) {
      if (this.nouveauPaiement.moyenDePaiement.montantAssocie) {
        this.nouveauPaiement.montant = undefined;
      }
      this.service.sauvegarderPaiement(this.referenceReservation, this.nouveauPaiement).subscribe(() => {
        this.paiements.push(this.nouveauPaiement as model.Paiement);
        this.nouveauPaiement = undefined;
      });
    }
  }

  /** Annulation */
  annuler() {
    this.dialogRef.close(this.paiements);
  }

  /** Initialisation du formulaire */
  initialiserFormulaire() {
    // objet pour le formulaire
    this.nouveauPaiement = new model.Paiement();
    this.nouveauPaiement.moyenDePaiement = new model.MoyenDePaiement('', 0, '');
    this.nouveauPaiement.montant = this.service.calculerMontantRestantDu(this.montantTotal, this.paiements);
  }

  /** Suppression d'un paiement */
  supprimerPaiement(referencePaiement: string) {
    this.service.supprimerPaiement(this.dataInput.reservation.reference, referencePaiement).subscribe(() => {
      this.paiements = this.paiements.filter((p) => p.reference !== referencePaiement);
    });
  }
}
