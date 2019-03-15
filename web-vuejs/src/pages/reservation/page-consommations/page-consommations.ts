import { Component, Vue } from 'vue-property-decorator';
import { Reservation, Produit, Consommation } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';

/** Page de gestion des consommations */
@Component
export default class PageConsommations extends Vue {

    /** Liste des reservations en cours */
    public reservations: Reservation[] = [];
    /** Liste des produits */
    public produits: Produit[] = [];
    /** Consommations saisies */
    public consommationsDeLaReservationSelectionnee: Consommation[] = [];

    /** Réservation sélectionnée */
    public reservationSelectionee: Reservation | null = null;

    /** Une dépendance */
    private reservationService: ReservationService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.reservationService = new ReservationService();
    }

    /** A l'initialisation */
    public mounted() {
        this.chargerReservations();
        this.chargerProduits();
        this.consommationsDeLaReservationSelectionnee = [];
    }

    public calculerQuantitePourProduit(referenceProduit: string): number {
        const conso = this.rechercherConsommation(referenceProduit);
        if (conso) {
            return conso.quantite;
        } else {
            return 0;
        }
    }

    /** A la selection d'une réservation */
    public selectionnerReservation(resa: Reservation) {
        this.reservationSelectionee = resa;
        this.rechercherLesConsommationsDeLaReservation();
    }

    /** Ajout d'une consommation */
    public ajouterConsomation(reservation: Reservation, produit: Produit) {
        const consommation = new Consommation('', new Date(), undefined, 1, reservation, produit);
        if (this.reservationSelectionee) {
            this.reservationService.sauvegarderConsommation(this.reservationSelectionee.reference, consommation)
                .subscribe(() => this.rechercherLesConsommationsDeLaReservation());
        }
    }

    /** Suppression d'une consommation */
    public supprimerConsommation(referenceProduit: string) {
        const conso = this.rechercherConsommation(referenceProduit);
        if (conso && this.reservationSelectionee) {
            this.reservationService.supprimerConsommation(this.reservationSelectionee.reference, conso.reference)
                .subscribe(() => conso.quantite = 0);
        }
    }

    /** Réduire la quantite d'une consommation */
    public reduireConsommation(referenceProduit: string) {
        const conso = this.rechercherConsommation(referenceProduit);
        if (conso && this.reservationSelectionee) {
            this.reservationService.reduireConsommation(this.reservationSelectionee.reference, conso.reference)
                .subscribe(() => conso.quantite--);
        }
    }

    private rechercherConsommation(referenceProduit: string): Consommation | undefined {
        return this.consommationsDeLaReservationSelectionnee.find((c) => c.produit.reference === referenceProduit);
    }

    /** Raffrichissement de la liste des consommations de la réservation sélectionnée */
    private rechercherLesConsommationsDeLaReservation() {
        if (this.reservationSelectionee) {
            this.reservationService.listerConsommation(this.reservationSelectionee.reference)
                .subscribe((consommations) => this.consommationsDeLaReservationSelectionnee = consommations as Consommation[]);
        }
    }

    /** Chargement des reservations */
    private chargerReservations() {
        this.reservationService.listerReservationsEnCours().subscribe((reservations) => this.reservations = reservations as Reservation[]);
    }

    /** Chargement des produits */
    private chargerProduits() { this.reservationService.listerProduits().subscribe((produits) => this.produits = produits as Produit[]); }
}
