import { Component, Vue } from 'vue-property-decorator';

import { MoyenDePaiement } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';
import AnimationUtils from '@/services/utilitaire/animationUtils';

@Component
export default class CadreMoyendepaiement extends Vue {

    /** Element en cours d'édition */
    public nouveauMoyenDePaiement: MoyenDePaiement | null = null;

    /** Données du tableau */
    public moyensDePaiement: MoyenDePaiement[] = [];

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Regles de validation du formulaire */
    public nomRegles = [(v: any) => (!!v) || 'moyendepaiement_placeholder_nom_validation'];

    /** Une dépendance */
    private reservationService: ReservationService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.reservationService = new ReservationService();
    }

    public mounted() {
        // Reset du formulaire
        this.annulerCreation();
        // Creation du datasource
        this.chargerDonnees();
    }

    /** On annule la creation en masquant le formulaire */
    public annulerCreation() {
        this.nouveauMoyenDePaiement = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creer() {
        this.nouveauMoyenDePaiement = new MoyenDePaiement('', 0, '');
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.nouveauMoyenDePaiement) {
            const mdp = new MoyenDePaiement(this.nouveauMoyenDePaiement.reference, this.nouveauMoyenDePaiement.montantAssocie, this.nouveauMoyenDePaiement.nom);
            this.reservationService.sauvegarderMoyenDePaiement(mdp).subscribe((retour: any) => {
                // Reset du formulaire
                this.annulerCreation();
                // Creation du datasource
                this.chargerDonnees();
            });
        }
    }

    /** A la sélection */
    public selectionner(mdp: MoyenDePaiement) {
        this.nouveauMoyenDePaiement = mdp;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(mdp: MoyenDePaiement) {
        this.reservationService.supprimerMoyenDePaiement(mdp.reference)
            .subscribe((retour: any) => {
                this.chargerDonnees();
                this.annulerCreation();
            });
    }

    private chargerDonnees() {
        this.reservationService.listerMoyensDePaiement().subscribe((moyensDePaiement: any) => this.moyensDePaiement = moyensDePaiement);
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaire', true);
    }
}
