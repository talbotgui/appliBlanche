// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import VueResource from 'vue-resource'

import 'bootstrap/dist/css/bootstrap.min.css'
import '../static/style.css'

Vue.use(VueResource)

Vue.config.productionTip = false
// Vue.config.debug = true
// Vue.config.silent = false
// Vue.config.devtools = true

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
