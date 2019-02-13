import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/service-securite';
import { ModuleApplicatif, PageApplicative } from '@/model/model';

@Component
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
        pagesDuModule.push(new PageApplicative('menu_utilisateur', 'user', 'utilisateur.listerUtilisateur', '/page-utilisateur-route'));
        pagesDuModule.push(new PageApplicative('menu_role', 'users', 'roleEtRessource.listerRoles', '/page-role-route'));
        pagesDuModule.push(new PageApplicative('menu_ressource', 'magic', 'roleEtRessource.listerRessource', '/page-ressource-route'));
        pagesDuModule.push(new PageApplicative('menu_monitoring', 'binoculars', 'monitoring.lireDonneesDuMonitoring', '/page-monitoring-route'));
        tousLesModules.push(new ModuleApplicatif('menu_titre_administration', 'shield-alt', pagesDuModule));
        // Menu RESERVATION
        pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_reservations', 'calendar-alt', 'reservation.rechercherReservations', '/page-reservations-route'));
        pagesDuModule.push(new PageApplicative('menu_adminreservations', 'hotel', 'reservationParametres.listerChambres', '/page-adminreservations-route'));
        tousLesModules.push(new ModuleApplicatif('menu_titre_reservation', 'person-booth', pagesDuModule));
        // Menu CONSOMMATION
        pagesDuModule = [];
        pagesDuModule.push(
            new PageApplicative('menu_consommation', 'wine-bottle', 'reservation.rechercherConsommationsDuneReservation', '/page-consommations-route'),
        );
        pagesDuModule.push(new PageApplicative('menu_adminconsommation', 'dolly', 'reservationParametres.listerProduits', '/page-adminconsommations-route'));
        tousLesModules.push(new ModuleApplicatif('menu_titre_consommation', 'concierge-bell', pagesDuModule));
        // Menu FACTURATION
        pagesDuModule = [];
        pagesDuModule.push(new PageApplicative('menu_facturation', 'donate', 'reservation.rechercherReservations', '/page-facturations-route'));
        tousLesModules.push(new ModuleApplicatif('menu_titre_facturation', 'dollar-sign', pagesDuModule));

        // TODO: sécuriser les menus
        this.modules = tousLesModules;
    }
}
