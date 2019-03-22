import { Component, Vue } from 'vue-property-decorator';

import { Formule } from '@/model/reservation-model';
import { ReservationService } from '@/services/reservations/reservation.service';
import AnimationUtils from '@/services/utilitaire/animationUtils';

@Component
export default class CadreFormules extends Vue {

    /** Element en cours d'édition */
    public nouvelleFormule: Formule | null = null;

    /** Données du tableau */
    public formules: Formule[] = [];

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
        this.nouvelleFormule = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creer() {
        this.nouvelleFormule = new Formule('', '', 0);
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.nouvelleFormule) {
            const mdp = new Formule(this.nouvelleFormule.reference, this.nouvelleFormule.nom, this.nouvelleFormule.prixParNuit);
            this.reservationService.sauvegarderFormule(mdp).subscribe((retour: any) => {
                // Reset du formulaire
                this.annulerCreation();
                // Creation du datasource
                this.chargerDonnees();
            });
        }
    }

    /** A la sélection */
    public selectionner(mdp: Formule) {
        this.nouvelleFormule = mdp;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(mdp: Formule) {
        this.reservationService.supprimerFormule(mdp.reference)
            .subscribe((retour: any) => {
                this.chargerDonnees();
                this.annulerCreation();
            });
    }

    private chargerDonnees() {
        this.reservationService.listerFormules().subscribe((formules: any) => this.formules = formules);
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaire', true);
    }
}
