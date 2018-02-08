// Import de VueJS et ses modules
import Vue from 'vue'
import Router from 'vue-router'

// Import des composants de l'application
import Connexion from '@/components/connexion/Connexion'
import Administration from '@/components/administration/Administration'
import Utilisateur from '@/components/administration/Utilisateur'
import rest from '../services/rest'

// Déclaration des modules de VueJS
Vue.use(Router)

// Règles de protection des routes
function connexionNecessaire (to, from, next) {
  if (rest.isUtilisateurConnecte()) {
    next()
  } else {
    console.info('utilsateur non connecté, retour à la page de connexion')
    next({ path: '/', query: { redirect: to.fullPath } })
  }
}

// Définition des routes
export default new Router({
  routes: [
    { path: '/', name: 'Connexion', component: Connexion },
    { path: '/login', name: 'Connexion', component: Connexion },
    {
      path: '/administration',
      name: 'Administration',
      component: Administration,
      beforeEnter: connexionNecessaire,
      children: [
        { path: 'utilisateur', name: 'Utilisateur', component: Utilisateur }
      ]
    }
  ]
})
