import { Component, Vue } from 'vue-property-decorator';
import { Chambre, Formule, Option, Reservation } from '@/model/reservation-model';
import { IStringToAnyMap } from '@/model/model';
import { ReservationService } from '@/services/reservations/reservation.service';
import { MutationPayload } from 'vuex';
import DatePickerCalendarDto from '@/model/vuetifyDto';

/** Page de gestion des reservations */
@Component
export default class CadreReservation extends Vue {

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Liste des chambres */
    public chambres: Chambre[] = [];
    /** Liste des formules */
    public formules: Formule[] = [];
    /** Liste des options */
    public options: Option[] = [];

    /** Reservation dans le détail */
    public reservationSelectionnee: Reservation = new Reservation('', new Date(), undefined, '', new Chambre('', ''), new Formule('', '', 0));
    public creation: boolean = false;
    /** Map des options utilisée dans les ecrans */
    public optionsCalculeesPourLaReservationSelectionnee: IStringToAnyMap<boolean> = {};
    /** Date selectionnée par l'utilisateur dans le datePicker */
    public dateDebut: DatePickerCalendarDto = new DatePickerCalendarDto();
    /** Date selectionnée par l'utilisateur dans le datePicker */
    public dateFin: DatePickerCalendarDto = new DatePickerCalendarDto();

    /** Regles de validation du formulaire */
    public obligatoireRegles = [(v: any) => (!!v) || 'commmun_champ_obligatoire'];

    /** Une dépendance */
    private reservationService: ReservationService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.reservationService = new ReservationService();
    }

    /** A l'initialisation */
    public mounted() {
        this.reservationService.listerChambres().subscribe(
            (reponse) => {
                const chambres = reponse as Chambre[];
                this.chambres = (chambres && chambres.length > 0) ? chambres : [];
            },
        );
        this.reservationService.listerFormules().subscribe(
            (reponse) => {
                const formules = reponse as Formule[];
                this.formules = (formules && formules.length > 0) ? formules : [];
            },
        );
        this.reservationService.listerOptions().subscribe(
            (reponse) => {
                const options = reponse as Option[];
                this.options = (options && options.length > 0) ? options : [];
            },
        );

        // A la sélection d'une réservation
        this.$store.subscribe((mutation: MutationPayload, state: any) => {
            if (mutation.type === 'definirReservationEnCoursDedition') {
                this.selectionnerUneReservation(state.reservationEnCoursDedition);
            }
        });
    }

    public enregistrerReservationSelectionnee() {
        if (this.reservationSelectionnee) {

            // Application des options sélectionnées
            this.reservationSelectionnee.options = [];
            if (this.options) {
                for (const o of this.options) {
                    if (this.optionsCalculeesPourLaReservationSelectionnee[o.reference]) {
                        this.reservationSelectionnee.options.push(o);
                    }
                }
            }

            // Sauvegarde
            this.reservationService.sauvegarderReservation(this.reservationSelectionnee).subscribe(() => {
                this.reservationSelectionnee = new Reservation('', new Date(), undefined, '', new Chambre('', ''), new Formule('', '', 0));
                this.creation = false;
                this.$store.commit('retirerReservationEnCoursDedition');
            });
        }
    }

    public changerEtatEnCours() {
        if (this.reservationSelectionnee) {
            this.reservationService.changerEtatReservation(this.reservationSelectionnee.reference, 'EN_COURS').subscribe(() => {
                this.annulerOuFermer();
            });
        }
    }

    /** Annulation ou fermeture du formulaire */
    public annulerOuFermer() {
        this.reservationSelectionnee = new Reservation('', new Date(), undefined, '', new Chambre('', ''), new Formule('', '', 0));
        this.creation = false;
        this.$store.commit('retirerReservationEnCoursDedition');
    }

    /** Méthode appelée par le composant parent (pour ignorer la ligne suivante : @@angular:analyse:ignorerLigneSuivante@@) */
    private selectionnerUneReservation(r: Reservation) {
        this.reservationSelectionnee = r;
        this.creation = false;
        this.dateDebut.date = r.dateDebut;
        if (r.dateFin) {
            this.dateFin.date = r.dateFin;
        }
        // Calcul de l'objet portant les options
        this.optionsCalculeesPourLaReservationSelectionnee = {};
        if (this.options && r && r.options) {
            for (const o of this.options) {
                const estSelectionnee = (r.options.findIndex((oSel) => o.reference === oSel.reference) >= 0);
                this.optionsCalculeesPourLaReservationSelectionnee[o.reference] = estSelectionnee;
            }
        }
    }
}
