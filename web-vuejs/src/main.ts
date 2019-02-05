import Vue from 'vue';

// Ces imports déclenche les mécaniques des plugins - debut
import 'roboto-fontface/css/roboto/roboto-fontface.css';
import '@fortawesome/fontawesome-free/css/all.css';
import '@/plugins/vuetify';
// Ces imports déclenche les mécaniques des plugins - fin

import App from './App.vue';
import router from './router';
import store from './store';
import './registerServiceWorker';

Vue.config.productionTip = false;
new Vue({ router, store, render: (h) => h(App) }).$mount('#app');
