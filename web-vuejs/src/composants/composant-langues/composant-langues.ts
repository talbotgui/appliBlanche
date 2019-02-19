import { Component, Vue } from 'vue-property-decorator';
import { I18nService } from '@/services/service-i18n';

@Component
export default class Langues extends Vue {

    private i18nService: I18nService;

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
        this.i18nService = new I18nService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        this.changerLaLangueDesLibelles('fr');
    }

    /** Chargement des libelles depuis l'API */
    public changerLaLangueDesLibelles(codeLangue: string) {
        this.i18nService.chargerUneLangue(codeLangue, this.$i18n);
    }
}
