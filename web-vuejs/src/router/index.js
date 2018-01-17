import Vue from 'vue'
import Router from 'vue-router'
import Connexion from '@/components/connexion/Connexion'
import VueResource from 'vue-resource'

Vue.use(Router)
Vue.use(VueResource)

export default new Router({
  routes: [
    { path: '/', name: 'Connexion', component: Connexion }
  ]
})
