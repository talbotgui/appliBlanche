import { Component, Vue } from 'vue-property-decorator';
import { MutationPayload } from 'vuex';

import routeur from '@/router';
import { ModuleApplicatif, PageApplicative } from '@/model/model';
import SecuriteService from '@/services/service-securite';
import Langues from '@/composants/composant-langues/composant-langues';

@Component({ components: { Langues } })
export default class Menu extends Vue {

    /** Liste des modules du menu autorisés à l'utilisateur connecté */
    public modules: ModuleApplicatif[] = [];
    public moduleSelectionne: ModuleApplicatif = new ModuleApplicatif('', '', []);

    /** Composant de service */
    private securiteService: SecuriteService;

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
        this.securiteService = new SecuriteService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {

        const tousLesModules: ModuleApplicatif[] = [];
        // Menu ADMINISTRATION
        let pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_utilisateur', 'user', 'utilisateur.listerUtilisateur', '/administration-utilisateurs'));
        pagesDuModule.push(new PageApplicative('menu_role', 'users', 'roleEtRessource.listerRoles', ''));
        pagesDuModule.push(new PageApplicative('menu_ressource', 'magic', 'roleEtRessource.listerRessource', '/administration-ressources'));
        pagesDuModule.push(new PageApplicative('menu_monitoring', 'binoculars', 'monitoring.lireDonneesDuMonitoring', ''));
        tousLesModules.push(new ModuleApplicatif('menu_titre_administration', 'shield-alt', pagesDuModule));
        // Menu RESERVATION
        pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_reservations', 'calendar-alt', 'reservation.rechercherReservations', ''));
        pagesDuModule.push(new PageApplicative('menu_adminreservations', 'hotel', 'reservationParametres.listerChambres', ''));
        tousLesModules.push(new ModuleApplicatif('menu_titre_reservation', 'person-booth', pagesDuModule));
        // Menu CONSOMMATION
        pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_consommation', 'wine-bottle', 'reservation.rechercherConsommationsDuneReservation', ''));
        pagesDuModule.push(new PageApplicative('menu_adminconsommation', 'dolly', 'reservationParametres.listerProduits', ''));
        tousLesModules.push(new ModuleApplicatif('menu_titre_consommation', 'concierge-bell', pagesDuModule));
        // Menu FACTURATION
        pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_facturation', 'donate', 'reservation.rechercherReservations', ''));
        tousLesModules.push(new ModuleApplicatif('menu_titre_facturation', 'dollar-sign', pagesDuModule));

        // Résumé des clefs nécessaires aux pages
        let clefs: string[] = [];
        tousLesModules.forEach((m) => {
            clefs = clefs.concat(m.pages.map((p) => p.clefApi));
        });

        // A la connexion/déconnexion d'un utilisateur
        this.$store.subscribe((mutation: MutationPayload, state: any) => {

            // A la connexion
            if (mutation.type === 'declarerUneConnexionUtilisateur') {

                // Validation des éléments du menu autorisés à cet utilisateur
                const modulesAutorises: ModuleApplicatif[] = [];
                const clefsAutorisees = this.securiteService.validerAutorisations(clefs);
                tousLesModules.forEach((m) => {
                    const pagesAutorisees = m.pages.filter((p) => clefsAutorisees.indexOf(p.clefApi) !== -1);
                    if (pagesAutorisees.length > 0) {
                        modulesAutorises.push(new ModuleApplicatif(m.nom, m.icone, pagesAutorisees));
                    }
                });

                // Affectation des modules autorisés
                this.modules = modulesAutorises;
            }

            // A la déconnexion
            else if (mutation.type === 'demandeDeDeconnexion') {
                this.modules = [];
            }
        });
    }

    /** méthode de déconnexion */
    public deconnecter() {
        this.securiteService.deconnecter();
        routeur.push('/login');
    }
}
