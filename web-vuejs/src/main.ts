import Vue from 'vue';
import VueI18n from 'vue-i18n';

// Ces imports déclenche les mécaniques des plugins - debut
import 'roboto-fontface/css/roboto/roboto-fontface.css';
import '@fortawesome/fontawesome-free/css/all.css';
import '@/plugins/vuetify';
// Ces imports déclenche les mécaniques des plugins - fin

import App from './App.vue';
import router from './router';
import store from './store';
import './registerServiceWorker';

// i18n
Vue.use(VueI18n);
const i18n = new VueI18n({
    locale: 'fr', fallbackLocale: 'en',
    messages: {
        en: { message: { login: 'login', password: 'pwd', doLogin: 'Login' } },
        fr: { message: { login: 'identifiant', password: 'mdp', doLogin: 'Connexion' } },
    },
});

Vue.config.productionTip = false;
new Vue({ router, store, i18n, render: (h) => h(App) }).$mount('#app');
