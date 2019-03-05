import Vue from 'vue';
import Router, { Route } from 'vue-router';

import Accueil from '@/pages/page-accueil/page-accueil.vue';
import SecuriteService from '@/services/service-securite';

Vue.use(Router);

/**
 * Routeur de l'application.
 *
 * L'usage d'une méthode pour l'attribut 'component' permet le chargement à la volée du composant
 */
const routeur = new Router({
    routes: [
        { path: '/', name: 'accueil', component: Accueil },
        { path: '/login', name: 'login', component: () => import(/* webpackChunkName: "login" */ './pages/page-login/page-login.vue') },
        {
            path: '/administration-utilisateurs', name: 'administration-utilisateurs',
            component: () => import(/* webpackChunkName: "login" */ './pages/administration/page-utilisateurs/page-utilisateurs.vue'),
        },
        {
            path: '/administration-ressources', name: 'administration-ressources',
            component: () => import(/* webpackChunkName: "login" */ './pages/administration/page-ressources/page-ressources.vue'),
        },
        {
            path: '/administration-monitoring', name: 'administration-monitoring',
            component: () => import(/* webpackChunkName: "login" */ './pages/administration/page-monitoring/page-monitoring.vue'),
        },
        {
            path: '/administration-roles', name: 'administration-roles',
            component: () => import(/* webpackChunkName: "login" */ './pages/administration/page-roles/page-roles.vue'),
        },
        {
            path: '/consommation-admin', name: 'consommation-admin',
            component: () => import(/* webpackChunkName: "login" */ './pages/reservation/page-adminconsommations/page-adminconsommations'),
        },
        {
            path: '/reservation-admin', name: 'reservation-admin',
            component: () => import(/* webpackChunkName: "login" */ './pages/reservation/page-adminreservations/page-adminreservations'),
        },
        {
            path: '/consommation', name: 'consommation',
            component: () => import(/* webpackChunkName: "login" */ './pages/reservation/page-consommations/page-consommations'),
        },
    ],
});

// Définition de la garde
routeur.beforeEach((to: Route, from: Route, next: any) => {

    // Instanciation du service
    const securiteService = new SecuriteService();

    // vérification de l'état de connexion
    securiteService.estConnecte().subscribe((b) => {
        // Si la route n'est ni l'accueil ni le login et que le token est absent => redirection vers login
        if (to.matched.some((laRoute) => laRoute.path !== '/' && laRoute.path !== '/login') && !b) {
            next({ name: 'login', query: { redirect: to.fullPath } });
        } else {
            next();
        }
    });
});

export default routeur;
