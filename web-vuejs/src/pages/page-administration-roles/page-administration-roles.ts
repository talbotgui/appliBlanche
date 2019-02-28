import { Component, Vue } from 'vue-property-decorator';

import { Role, Page } from '@/model/model';
import { RoleService } from '@/services/service-role';
import AnimationUtils from '@/services/utilitaire/animationUtils';
import Pagination from '@/composants/composant-pagination/composant-pagination';

@Component({ components: { Pagination } })
export default class PageAdministrationRoles extends Vue {

    /** Objet en cours d'édition */
    public elementSelectionne: Role | null = null;

    /** Flag permettant de savoir si c'est une création ou une modification (pour bloquer le login) */
    public creation: boolean = false;

    /** Page de données */
    public page: Page<Role> = new Page(0, 0);

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Regles de validation du formulaire */
    public nomRegles = [(v: any) => (!!v) || 'role_placeholder_nom_validation'];

    /** Une dépendance */
    private roleService: RoleService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.roleService = new RoleService();
    }

    public mounted() {
        // Reset du formulaire
        this.annulerCreation();

        // Initialisation des données
        this.chargerDonnees(new Page(10, 0));
    }

    /** On annule la creation en masquant le formulaire */
    public annulerCreation() {
        this.elementSelectionne = null;
    }

    /** Initialisation de l'objet pour accueillir les données du formulaire */
    public creer() {
        this.elementSelectionne = new Role();
        this.creation = true;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarder() {
        if (this.elementSelectionne) {
            const elementAsauvegarder = new Role();
            elementAsauvegarder.nom = this.elementSelectionne.nom;
            this.roleService.sauvegarderRole(elementAsauvegarder).subscribe((retour) => {
                this.chargerDonnees(this.page);
                this.annulerCreation();
            });
        }
    }

    /** A la sélection */
    public selectionner(element: Role) {
        this.elementSelectionne = element;
        this.creation = false;
        this.deplacerLaVueSurLeFormulaire();
    }

    /** A la suppression  */
    public supprimer(element: Role) {
        this.roleService.supprimerRole(element)
            .subscribe((retour) => {
                this.chargerDonnees(this.page);
                this.annulerCreation();
            });
    }

    private chargerDonnees(nouvellePage: Page<any>) {
        this.roleService.listerRoles(nouvellePage).subscribe((p: any) => {
            // Sauvegarde de la page pour en afficher le contenu
            this.page = p;
            // Envoi de la page au composant de pagination pour prise en compte
            (this.$refs.pagination as Pagination).prendreEnComptePage(p);
        });
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaireRole', true);
    }
}
