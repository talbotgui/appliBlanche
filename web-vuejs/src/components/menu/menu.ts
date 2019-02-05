import { Component, Vue } from 'vue-property-decorator';

import SecuriteService from '@/services/securiteService';

@Component
export default class Menu extends Vue {

    public message: string = 'I am using TypeScript classes with Vue';

    public items: Array<{ title: string, to: string }> = [{ title: 'el1', to: '/' }, { title: 'el2', to: '/about' }];

    /** Composant de service */
    private securiteService: SecuriteService;

    /** Constructeur instanciant les composants (et uniquement là). */
    constructor() {
        super();
        this.securiteService = new SecuriteService();
    }

    /** Méthode appelée dès que le composant est chargé. */
    public mounted() {
        console.log('monted');
        this.securiteService.getTest().then((data) => {
            console.log(data);
        });
    }

    public cliquerSurMenu() {
        console.log('coucou');
    }
}
