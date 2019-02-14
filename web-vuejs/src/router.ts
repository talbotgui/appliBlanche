import Vue from 'vue';
import Router, { Route } from 'vue-router';
import Accueil from '@/pages/page-accueil/page-accueil.vue';
import SecuriteService from './services/service-securite';
import { map } from 'rxjs/operators';

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
            component: () => import(/* webpackChunkName: "login" */ './pages/page-administration-utilisateurs/page-administration-utilisateurs.vue'),
        },
        {
            path: '/administration-ressources', name: 'administration-ressources',
            component: () => import(/* webpackChunkName: "login" */ './pages/page-administration-ressources/page-administration-ressources.vue'),
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
