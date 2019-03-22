import { Component, Vue } from 'vue-property-decorator';

import { Option } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';
import AnimationUtils from '@/services/utilitaire/animationUtils';

@Component
export default class CadreOptions extends Vue {

    /** Element en cours d'édition */
    public nouvelleOption: Option | null = null;

    /** Données du tableau */
    public options: Option[] = [];

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

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
        this.nouvelleOption = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creer() {
        this.nouvelleOption = new Option('', '', 0, false, false);
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.nouvelleOption) {
            const mdp = new Option(this.nouvelleOption.reference, this.nouvelleOption.nom, this.nouvelleOption.prix,
                this.nouvelleOption.parNuit, this.nouvelleOption.parPersonne);
            this.reservationService.sauvegarderOption(mdp).subscribe((retour: any) => {
                // Reset du formulaire
                this.annulerCreation();
                // Creation du datasource
                this.chargerDonnees();
            });
        }
    }

    /** A la sélection */
    public selectionner(mdp: Option) {
        this.nouvelleOption = mdp;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(mdp: Option) {
        this.reservationService.supprimerOption(mdp.reference)
            .subscribe((retour: any) => {
                this.chargerDonnees();
                this.annulerCreation();
            });
    }

    private chargerDonnees() {
        this.reservationService.listerOptions().subscribe((options: any) => this.options = options);
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaire', true);
    }
}
