import { Component, Vue } from 'vue-property-decorator';

import Menu from '@/components/menu/menu.vue';
import Snackbar from '@/components/snackbar/snackbar';

@Component({ components: { Menu, Snackbar } })
export default class Accueil extends Vue {

}
