import { Component, Vue } from 'vue-property-decorator';

import CadreCalendrier from './cadre-calendrier/cadre-calendrier';
import CadreReservation from './cadre-reservation/cadre-reservation';

/** Page de gestion des reservations */
@Component({ components: { CadreCalendrier, CadreReservation } })
export default class PageReservationsComponent extends Vue {

}
