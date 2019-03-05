import { Component, Vue } from 'vue-property-decorator';

import { Chambre } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';
import AnimationUtils from '@/services/utilitaire/animationUtils';

@Component
export default class CadreChambres extends Vue {

    /** Element en cours d'édition */
    public nouvelleChambre: Chambre | null = null;

    /** Données du tableau */
    public chambres: Chambre[] = [];

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Regles de validation du formulaire */
    public nomRegles = [(v: any) => (!!v) || 'todo'];

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
        this.nouvelleChambre = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creer() {
        this.nouvelleChambre = new Chambre('', '');
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.nouvelleChambre) {
            const mdp = new Chambre(this.nouvelleChambre.reference, this.nouvelleChambre.nom);
            this.reservationService.sauvegarderChambre(mdp).subscribe((retour: any) => {
                // Reset du formulaire
                this.annulerCreation();
                // Creation du datasource
                this.chargerDonnees();
            });
        }
    }

    /** A la sélection */
    public selectionner(mdp: Chambre) {
        this.nouvelleChambre = mdp;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(mdp: Chambre) {
        this.reservationService.supprimerChambre(mdp.reference)
            .subscribe((retour: any) => {
                this.chargerDonnees();
                this.annulerCreation();
            });
    }

    private chargerDonnees() {
        this.reservationService.listerChambres().subscribe((chambres: any) => this.chambres = chambres);
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaire', true);
    }
}
