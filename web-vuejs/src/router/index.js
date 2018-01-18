// Import de VueJS et ses modules
import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'

// Import des composants de l'application
import Connexion from '@/components/connexion/Connexion'
import Administration from '@/components/administration/Administration'

// Déclaration des modules de VueJS
Vue.use(Router)
Vue.use(VueResource)

// Définition des routes
export default new Router({
  routes: [
    { path: '/', name: 'Connexion', component: Connexion },
    { path: '/login', name: 'Connexion', component: Connexion },
    { path: '/utilisateur', name: 'Utilisateur', component: Administration }
  ]
})
