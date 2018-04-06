// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/router'

// Import des CSS
import 'bootstrap/dist/css/bootstrap.min.css'
import '../static/style.css'

// Composant permettant les appels REST
import VueResource from 'vue-resource'

// Composant permettant l'internationnalisation
import VueI18n from 'vue-i18n'

// Activation des composants
Vue.use(VueResource)
Vue.use(VueI18n)

Vue.config.productionTip = false
// Vue.config.debug = true
// Vue.config.silent = false
// Vue.config.devtools = true

// Configuration de l'internationnalisation
const i18n = new VueI18n({ locale: 'fr', messages: { fr: { 'connexion_identifiant': 'login', 'connexion_titre': 'Connexion', 'connexion_motDePasse': 'Mot de passe :', 'connexion_boutonConnexion': 'Se connecter' } } })

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  i18n
})
