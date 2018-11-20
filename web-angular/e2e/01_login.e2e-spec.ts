import { ApplicationPage } from './app.po';
import * as selectors from './selectors';

/**
 * Pour que chaque test soit autonome, chaque test contient un scénario qui redémarre de l'ouverture de la page.
 * Les étapes de préparation du test sont dans la partie Arrange (de Arrange/Act/Assert) si et seulement si ces étapes ont été testées dans un autre test.
 *
 * Pour démarrer le test en DEBUG avec VSCode, il faut lancer "ng serve" depuis un terminal puis depuis la vue DEBUG de lancer le bon test
 *
 * Pour n'exécuter qu'un seul test depuis la ligne de commande : "npm run debug-e2e" après avoir modifier le script à lancer dans package.json
 */
describe('Page de login', () => {
  let page: ApplicationPage;

  beforeEach(() => {
    page = new ApplicationPage();
  });

  it('Premier accès', () => {
    //
    //
    page.navigateToRoot();
    //
    expect(page.isVisible(selectors.PageLogin.TITRE)).toBeTruthy();
    expect(page.isVisible(selectors.PageLogin.CHAMP_LOGIN)).toBeTruthy();
    expect(page.isVisible(selectors.PageLogin.CHAMP_MDP)).toBeTruthy();
    expect(page.isVisible(selectors.PageLogin.BOUTON_CONNECTER)).toBeTruthy();
    expect(page.isVisible(selectors.APP.DRAPEAU_EN)).toBeTruthy();
    expect(page.isVisible(selectors.APP.DRAPEAU_FR)).toBeTruthy();
  });

  it('i18n EN', () => {
    //
    //
    page.navigateToRoot();
    page.click(selectors.APP.DRAPEAU_EN);
    //
    expect(page.getText(selectors.PageLogin.TITRE)).toEqual('Login');
  });

  it('i18n FR', () => {
    //
    //
    page.navigateToRoot();
    page.click(selectors.APP.DRAPEAU_FR);
    //
    expect(page.getText(selectors.PageLogin.TITRE)).toEqual('Connexion');
  });

  it('Erreur de connexion', () => {
    //
    //
    page.navigateToRoot();
    page.type(selectors.PageLogin.CHAMP_LOGIN, 'mauvaisLogin');
    page.type(selectors.PageLogin.CHAMP_MDP, 'mauvaisMdp');
    page.click(selectors.PageLogin.BOUTON_CONNECTER);
    //
    expect(page.isVisible(selectors.PageLogin.MESSAGE_ERREUR_CONNEXION)).toBeTruthy();
  });

});
