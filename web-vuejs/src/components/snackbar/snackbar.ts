import { Component, Vue } from 'vue-property-decorator';

import { MessageErreur, Severite } from '@/model/erreur';

@Component
export default class Snackbar extends Vue {

    /** Donnée venant du store */
    public get message(): MessageErreur | undefined {
        return this.$store.getters.getDernierMessageErreurHttp;
    }

    /** Méthode calculée */
    public get flagAfficherSnackbar() { return !!this.message; }
    public set flagAfficherSnackbar(valeur: boolean) { this.$store.commit('viderErreurHttp'); }

    /** Méthode calculée */
    public get color(): string {
        if (!this.message) {
            return 'white';
        } else if (this.message.severite === Severite.Info) {
            return 'blue';
        } else if (this.message.severite === Severite.Warn) {
            return 'orange';
        } else {
            return 'red';
        }
    }

    /** Valeur fixe */
    public flagMultiline: boolean = false;

    /** Valeur fixe */
    public timeout: number = 3000;

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
    }
}
