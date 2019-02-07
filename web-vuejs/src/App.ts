import { Component, Vue } from 'vue-property-decorator';

import Menu from '@/composants/composant-menu/composant-menu';
import Snackbar from '@/composants/composant-snackbar/composant-snackbar';

@Component({ components: { Menu, Snackbar } })
export default class Accueil extends Vue {

}
