import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Menu extends Vue {
    public message: string = 'I am using TypeScript classes with Vue';

    public items: Array<{ title: string, to: string }> = [{ title: 'el1', to: '/' }, { title: 'el2', to: '/about' }];

    public cliquerSurMenu() {
        console.log('coucou');
    }
}
