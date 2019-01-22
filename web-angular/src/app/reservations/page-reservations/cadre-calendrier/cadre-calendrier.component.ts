import { Component, OnInit, EventEmitter } from '@angular/core';
import { Language } from 'angular-l10n';

import { ReservationService } from '../../service/reservation.service';
import * as model from '../../model/model';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';

/** Page de gestion des reservations */
@Component({ selector: 'cadre-calendrier', templateUrl: './cadre-calendrier.component.html', styleUrls: ['./cadre-calendrier.component.css'] })
export class CadreCalendrierComponent implements OnInit {

  /** Decorateur nécessaire aux libellés internationnalisés dans des tooltips */
  @Language() lang: string;

  /** Bus de message pour communiquer avec le composant parent */
  busDeMessage = new EventEmitter<model.Reservation>();

  /** Filtre d'affichage - debut */
  dateDebut: Date;

  /** Filtre d'affichage - fin */
  dateFin: Date;

  /** Flag permettant la saisie de dates sur une période personnalisée */
  flagSaisieDatesPersonalisees: boolean = false;

  /** Largeur de la colonne d'une chambre */
  nbColParChambre = 2;

  /** Liste des réservations à afficher. */
  reservations: model.IStringToAnyMap<model.IStringToAnyMap<{ style: SafeStyle, texte: string, reservation: model.Reservation }>> = {};

  /** Liste des chambres */
  chambres: model.Chambre[] = [];

  /** Liste des jours */
  jours: Date[];

  /** Map contenant les couleurs des réservations déjà affichées pour ne pas en changer à chaque déplacement du calendrier */
  private couleursReservation: model.IStringToAnyMap<string> = {};

  /** Un constructeur pour se faire injecter les dépendances */
  constructor(private reservationsService: ReservationService, private sanitizer: DomSanitizer) { }

  /** A l'initialisation */
  ngOnInit() {

    // Initialisation des dates de filtrage (de J-3 à J+12)
    this.deplacerDateParDefaut();

  }

  /** revenir aux dates par défaut */
  deplacerDateParDefaut() {
    const dateDuJour = new Date();
    this.dateDebut = new Date(dateDuJour.getTime() - (3 * 1000 * 3600 * 24));
    this.dateFin = new Date(dateDuJour.getTime() + (12 * 1000 * 3600 * 24));

    // Chargement des données
    this.chargerDonnees();
  }

  /** Déplacement des dates du filtre en jour */
  deplacerDateParJour(n: number) {
    // Déplacement des dates du filtre
    this.dateDebut = new Date(this.dateDebut.getTime() + (n * 1000 * 3600 * 24));
    this.dateFin = new Date(this.dateFin.getTime() + (n * 1000 * 3600 * 24));

    // Chargement des données
    this.chargerDonnees();
  }

  /** Affichage du détail dans le formualaire */
  afficherDetail(reservation: model.Reservation | undefined) {
    // Pour prévenir le composant parent qu'une réservation est sélectionnée
    this.busDeMessage.emit(reservation);
  }

  /** Initialisation d'une nouvelle réservation */
  initaliserNouvelleReservation() {
    // Pour prévenir le composant parent qu'une réservation est sélectionnée
    const debut = new Date();
    const fin = new Date(debut.getTime() + (1 * 1000 * 3600 * 24));
    const reservation = new model.Reservation('', debut, fin, '', new model.Chambre('', ''), new model.Formule('', '', 0));
    reservation.nombrePersonnes = 2;
    this.busDeMessage.emit(reservation);
  }

  /** Chargement de la liste des chambres, puis des réservations et calcul du tableau de données */
  chargerDonnees() {

    // reset des données
    this.reservations = {};
    this.couleursReservation = {};

    // Chargement des chambres
    this.reservationsService.listerChambres().subscribe(
      (chambres) => {
        if (chambres && chambres.length > 0) {
          this.chambres = chambres;
          this.nbColParChambre = Math.floor((12 - 2) / chambres.length);
        } else {
          this.chambres = [];
          this.nbColParChambre = 1;
        }

        // Chargement des réservations
        this.reservationsService.rechercherReservations(this.dateDebut, this.dateFin).subscribe(
          (reservations: model.Reservation[]) => {

            // Calcul de la liste des jours entre la date de début et la date de fin
            this.jours = [];
            const d = new Date(this.dateDebut);
            while (d <= this.dateFin) {
              this.jours.push(new Date(d));
              d.setDate(d.getDate() + 1);
            }

            // Calcul du tableau de données
            for (const j of this.jours) {
              for (const c of this.chambres) {
                if (!this.reservations[c.reference]) {
                  this.reservations[c.reference] = {};
                }
                for (const r of reservations) {
                  if (r.chambre.reference === c.reference && r.dateDebut <= j && j <= r.dateFin) {

                    // Affectation d'une couleur de fond pour toute la réservation
                    // Si la couleur de fond existe déjà pour cette réservation, on masque le texte
                    let bgCouleur = this.couleursReservation[r.reference];
                    let texte = '';
                    if (!bgCouleur) {
                      // tslint:disable-next-line
                      this.couleursReservation[r.reference] = '#' + ((1 << 24) * Math.random() | 0).toString(16) + '80';
                      bgCouleur = this.couleursReservation[r.reference];
                      texte = r.client;
                    }
                    const styleCss = this.sanitizer.bypassSecurityTrustStyle('background-color:' + bgCouleur);

                    // Mise en place de la réservation à cette date et dans cette chambre
                    this.reservations[c.reference][j.toISOString()] = { style: styleCss, texte, reservation: r };

                  }
                }
              }
            }
          }
        );
      }
    );
  }
}
