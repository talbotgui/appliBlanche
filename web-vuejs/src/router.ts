import Vue from 'vue';
import Router, { Route } from 'vue-router';
import Accueil from '@/pages/page-accueil/page-accueil.vue';
import * as store from '@/store';

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
    // Lecture du token
    const tokenApi = store.default.getters.getTokenApi;

    // Si la route n'est ni l'accueil ni le login et que le token est absent => redirection vers login
    if (to.matched.some((laRoute) => laRoute.path !== '/' && laRoute.path !== '/login') && !tokenApi) {
        next({ name: 'login', query: { redirect: to.fullPath } });
    } else {
        next();
    }
});

export default routeur;