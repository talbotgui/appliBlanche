import { Component, Vue } from 'vue-property-decorator';

import CadreChambres from './cadre-chambres/cadre-chambres';
import CadreFormules from './cadre-formules/cadre-formules';
import CadreOptions from './cadre-options/cadre-options';

@Component({ components: { CadreChambres, CadreFormules, CadreOptions } })
export default class PageAdminReservations extends Vue {

}
