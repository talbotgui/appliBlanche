import { Component, Vue } from 'vue-property-decorator';

import CadreMoyendepaiement from './cadre-moyendepaiement/cadre-moyendepaiement';
import CadreProduits from './cadre-produits/cadre-produits';

@Component({ components: { CadreMoyendepaiement, CadreProduits } })
export default class PageAdminConsommations extends Vue {

}
