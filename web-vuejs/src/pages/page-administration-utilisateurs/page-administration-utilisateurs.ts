import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/service-securite';
import { Role, Utilisateur, Page } from '@/model/model';
import { RoleService } from '@/services/service-role';
import { UtilisateurService } from '@/services/service-utilisateur';

@Component
export default class PageAdministrationUtilisateurs extends Vue {

    /** Listes alimentant les selectbox */
    public listeNbElementsParPage: number[] = [5, 10, 20, 50, 100];
    public listeIndexesDePage: number[] = [];

    /** Utilisateur en cours d'édition */
    public utilisateurSelectionne: Utilisateur | null = null;

    /** Flag permettant de savoir si c'est une création ou une modification (pour bloquer le login) */
    public creation: boolean = false;

    /** DataSource du tableau (initialisé dans le onInit) */
    public utilisateurs: Utilisateur[] = [];

    /** Liste complète de tous les roles */
    public tousLesRoles: Role[] = [];

    /** Une dépendance */
    private utilisateurService: UtilisateurService;
    private roleService: RoleService;

    /** Constructeur avec création des dépendances */
    constructor() {
        super();
        this.utilisateurService = new UtilisateurService();
        this.roleService = new RoleService();
    }

    public mounted() {
        // Reset du formulaire
        this.annulerCreationUtilisateur();

        // Creation du datasource
        this.chargerDonnees();

        // Chargement de la liste des roles
        const pageRecherche = new Page<Role>(50, 0);
        this.roleService.listerRoles(pageRecherche).subscribe((page: any) => this.tousLesRoles = page.content);
    }

    /** On annule la creation en masquant le formulaire */
    public annulerCreationUtilisateur() {
        this.utilisateurSelectionne = null;
    }

    /** Initialisation de l'objet Utilisateur pour accueillir les données du formulaire */
    public creerUtilisateur() {
        this.utilisateurSelectionne = new Utilisateur();
        this.creation = true;
        // TODO: deplacerLaVueSurLeComposant
    }

    /** Appel au service de sauvegarde puis rechargement des données */
    public sauvegarderUtilisateur() {
        if (this.utilisateurSelectionne) {
            const utilisateur = new Utilisateur();
            utilisateur.login = this.utilisateurSelectionne.login;
            utilisateur.mdp = this.utilisateurSelectionne.mdp;
            this.utilisateurService.sauvegarderUtilisateur(utilisateur).subscribe((retour) => {
                this.chargerDonnees();
                this.annulerCreationUtilisateur();
            });
        }
    }

    /** A la sélection */
    public selectionnerUtilisateur(utilisateur: Utilisateur) {
        this.utilisateurSelectionne = utilisateur;
        this.creation = false;
        // TODO: deplacerLaVueSurLeComposant
    }

    /** A la suppression  */
    public supprimerUtilisateur(utilisateur: Utilisateur) {
        this.utilisateurService.supprimerUtilisateur(utilisateur)
            .subscribe((retour) => {
                this.chargerDonnees();
                this.annulerCreationUtilisateur();
            });
    }

    /** Ajout suppression du role à cet utilisateur */
    public ajouterRetirerRole(utilisateur: Utilisateur, role: Role, statut: boolean) {
        this.utilisateurService.ajouterRetirerAutorisation(utilisateur, role, statut).subscribe(() => {
            this.chargerDonnees();
            this.annulerCreationUtilisateur();
        });
    }

    private chargerDonnees() {
        this.utilisateurService.listerUtilisateurs().subscribe((utilisateurs: any) => this.utilisateurs = utilisateurs);
    }
}
