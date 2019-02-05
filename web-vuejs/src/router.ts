import Vue from 'vue';
import Router from 'vue-router';
import Accueil from './views/accueil/accueil.vue';

Vue.use(Router);

/**
 * Routeur de l'application.
 *
 * L'usage d'une méthode pour l'attribut 'component' permet le chargement à la volée du composant
 */
export default new Router({
    routes: [
        { path: '/', name: 'accueil', component: Accueil },
        { path: '/about', name: 'about', component: () => import(/* webpackChunkName: "about" */ './views/About.vue') },
    ],
});
