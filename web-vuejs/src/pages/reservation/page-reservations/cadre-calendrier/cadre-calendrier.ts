import { Component, Vue } from 'vue-property-decorator';
import { IStringToAnyMap } from '@/model/model';
import { Reservation, Chambre, Formule } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';
import DatePickerCalendarDto from '@/model/vuetifyDto';
import { DateUtils } from '@/services/utilitaire/dateUtils';
import { MutationPayload } from 'vuex';

/** Page de gestion des reservations */
@Component
export default class CadreCalendrier extends Vue {

    /** Flag permettant la saisie de dates sur une période personnalisée */
    public flagSaisieDatesPersonalisees: boolean = false;

    /** Largeur de la colonne d'une chambre */
    public nbColParChambre = 2;

    /** Liste des réservations à afficher. */
    public reservations: IStringToAnyMap<IStringToAnyMap<{ style: string, texte: string, reservation: Reservation }>> = {};

    /** Liste des chambres */
    public chambres: Chambre[] = [];

    /** Liste des jours */
    public jours: Date[] = [];

    /** Filtre d'affichage - debut */
    public dateDebut: DatePickerCalendarDto = new DatePickerCalendarDto();

    /** Filtre d'affichage - fin */
    public dateFin: DatePickerCalendarDto = new DatePickerCalendarDto();

    /** Map contenant les couleurs des réservations déjà affichées pour ne pas en changer à chaque déplacement du calendrier */
    private couleursReservation: IStringToAnyMap<string> = {};

    /** Une dépendance */
    private reservationService: ReservationService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.reservationService = new ReservationService();
    }

    /** A l'initialisation */
    public mounted() {

        // Initialisation des dates de filtrage (de J-3 à J+12)
        this.deplacerDateParDefaut();

        // A la fin de l'édition d'une réservation, on recharge les données
        this.$store.subscribe((mutation: MutationPayload, state: any) => {
            if (mutation.type === 'retirerReservationEnCoursDedition') {
                this.chargerDonnees();
            }
        });
    }

    /** revenir aux dates par défaut */
    public deplacerDateParDefaut() {
        const dateDuJour = new Date();
        this.dateDebut.date = new Date(dateDuJour.getTime() - (3 * 1000 * 3600 * 24));
        this.dateFin.date = new Date(dateDuJour.getTime() + (12 * 1000 * 3600 * 24));

        // Chargement des données
        this.chargerDonnees();
    }

    /** Déplacement des dates du filtre en jour */
    public deplacerDateParJour(n: number) {
        // Déplacement des dates du filtre
        if (this.dateDebut && this.dateFin) {
            this.dateDebut.date = new Date(this.dateDebut.date.getTime() + (n * 1000 * 3600 * 24));
            this.dateFin.date = new Date(this.dateFin.date.getTime() + (n * 1000 * 3600 * 24));
        }

        // Chargement des données
        this.chargerDonnees();
    }

    /** Affichage du détail dans le formualaire */
    public afficherDetail(reservation: Reservation | undefined) {
        // Pour prévenir le composant parent qu'une réservation est sélectionnée
        this.$store.commit('definirReservationEnCoursDedition', reservation);
    }

    /** Initialisation d'une nouvelle réservation */
    public initaliserNouvelleReservation() {
        // Pour prévenir le composant parent qu'une réservation est sélectionnée
        const debut = new Date();
        const fin = undefined;
        const reservation = new Reservation('', debut, fin, '', new Chambre('', ''), new Formule('', '', 0));
        reservation.nombrePersonnes = 2;
        this.$store.commit('definirReservationEnCoursDedition', reservation);
    }

    /** Chargement de la liste des chambres, puis des réservations et calcul du tableau de données */
    public chargerDonnees() {

        // reset des données
        this.reservations = {};
        this.couleursReservation = {};

        // Chargement des chambres
        this.reservationService.listerChambres().subscribe(
            (reponse) => {
                const chambres = reponse as Chambre[];
                if (chambres && chambres.length > 0) {
                    this.chambres = chambres;
                    this.nbColParChambre = Math.floor((12 - 2) / chambres.length);
                } else {
                    this.chambres = [];
                    this.nbColParChambre = 1;
                }

                // Chargement des réservations
                if (this.dateDebut && this.dateFin) {
                    this.reservationService.rechercherReservations(this.dateDebut.date, this.dateFin.date).subscribe(
                        (reponse2) => {
                            const reservations = reponse2 as Reservation[];

                            // Parcours des réservations pour transformer les string en vériables dates
                            const dateUtils = new DateUtils();
                            for (const r of reservations) {
                                r.dateDebut = dateUtils.parserDateYYYYMMDD(r.dateDebut);
                                if (r.dateFin) {
                                    r.dateFin = dateUtils.parserDateYYYYMMDD(r.dateFin);
                                }
                            }

                            // Calcul de la liste des jours entre la date de début et la date de fin
                            this.jours = [];
                            if (this.dateDebut && this.dateFin) {
                                const d = new Date(this.dateDebut.date);
                                while (d <= this.dateFin.date) {
                                    this.jours.push(new Date(d));
                                    d.setDate(d.getDate() + 1);
                                }
                            }

                            // Calcul du tableau de données
                            for (const j of this.jours) {
                                for (const c of this.chambres) {
                                    if (!this.reservations[c.reference]) {
                                        this.reservations[c.reference] = {};
                                    }
                                    for (const r of reservations) {
                                        if (r.dateFin && r.chambre.reference === c.reference && r.dateDebut <= j && j <= r.dateFin) {

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
                                            const styleCss = 'background-color:' + bgCouleur;

                                            // Mise en place de la réservation à cette date et dans cette chambre
                                            this.reservations[c.reference][j.toISOString()] = { style: styleCss, texte, reservation: r };
                                        }
                                    }
                                }
                            }
                        },
                    );
                }
            },
        );
    }
}
