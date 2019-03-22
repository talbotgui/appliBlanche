import { Component, Vue, Watch } from 'vue-property-decorator';

import { Role, Page } from '@/model/model';
import { RoleService } from '@/services/administration/service-role';
import AnimationUtils from '@/services/utilitaire/animationUtils';
import { PaginationDto, EnteteDatatable } from '@/model/vuetifyDto';

@Component
export default class PageAdministrationRoles extends Vue {

    /** Objet en cours d'édition */
    public elementSelectionne: Role | null = null;

    /** Flag permettant de savoir si c'est une création ou une modification (pour bloquer le login) */
    public creation: boolean = false;

    /** DTO contenant tous les éléments de pagination */
    public dtDto: PaginationDto<Role> = new PaginationDto(
        // Méthode de chargement des données
        this.chargerDonnees,
        // entêtes à utiliser
        [
            new EnteteDatatable('role_entete_nom', 'nom', 'center', true),
            new EnteteDatatable('commun_entete_actions', 'action', 'center', false),
        ],
        // tri par défaut
        { colonne: 'nom', ascendant: false },
    );

    /** Flag indiquant l'état de validation du formulaire */
    public valide: boolean = true;

    /** Une dépendance */
    private roleService: RoleService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.roleService = new RoleService();
    }

    // Reset du formulaire
    public mounted() { this.annulerCreation(); }

    /** On annule la creation en masquant le formulaire */
    public annulerCreation() { this.elementSelectionne = null; }

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
                this.dtDto.forcerRechargement();
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
                this.dtDto.forcerRechargement();
                this.annulerCreation();
            });
    }

    /** A chaque modification du membre 'pagination', prise en compte dans le DTO et appel à "chargerDonnees" */
    @Watch('dtDto.pagination')
    public auChangementDePagination(val: any, oldVal: any) {
        this.dtDto.auChangementDePagination();
    }

    private chargerDonnees(nouvellePage: Page<any>) {
        this.roleService.listerRoles(nouvellePage).subscribe((p: any) => {
            this.dtDto.sauvegarderPage(p);
        });
    }

    private deplacerLaVueSurLeFormulaire() {
        (new AnimationUtils()).deplacerLaVueSurLeComposant('formulaireRole', true);
    }
}
