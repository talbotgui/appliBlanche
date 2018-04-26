// Import de VueJS et ses modules
import Vue from 'vue'
import Router from 'vue-router'

// Import des composants de l'application
import Connexion from '@/components/connexion/Connexion'
import Accueil from '@/components/Accueil'
import Utilisateur from '@/components/administration/Utilisateur'
import Role from '@/components/administration/Role'
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
function dejaConnecte (to, from, next) {
  if (!rest.isUtilisateurConnecte()) {
    next()
  } else {
    console.info('utilsateur déjà connecté, direction accueil')
    next({ path: '/accueil' })
  }
}

// Définition des routes
export default new Router({
  routes: [
    { path: '/', name: 'Connexion', component: Connexion, beforeEnter: dejaConnecte },
    { path: '/login', name: 'Connexion', component: Connexion, beforeEnter: dejaConnecte },
    { path: '/accueil', name: 'Accueil', component: Accueil, beforeEnter: connexionNecessaire },
    { path: '/administration/utilisateur', name: 'Utilisateur', component: Utilisateur, beforeEnter: connexionNecessaire },
    { path: '/administration/role', name: 'Role', component: Role, beforeEnter: connexionNecessaire }
  ]
})
