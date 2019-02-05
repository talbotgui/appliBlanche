import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Menu extends Vue {
    public message: string = 'I am using TypeScript classes with Vue';
}
