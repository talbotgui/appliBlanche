import { ApplicationPage } from './app.po';
import * as selectors from './selectors';
import * as path from 'path';

/**
 * Pour que chaque test soit autonome, chaque test contient un scénario qui redémarre de l'ouverture de la page.
 * Les étapes de préparation du test sont dans la partie Arrange (de Arrange/Act/Assert) si et seulement si ces étapes ont été testées dans un autre test.
 *
 * Pour démarrer le test en DEBUG avec VSCode, il faut lancer "ng serve" depuis un terminal puis depuis la vue DEBUG de lancer le bon test
 *
 * Pour n'exécuter qu'un seul test depuis la ligne de commande : "npm run debug-e2e" après avoir modifier le script à lancer dans package.json
 */
describe('Accueil de l\'application', () => {
  let page: ApplicationPage;

  beforeEach(() => {
    page = new ApplicationPage();
  });

  it('Accès à l\'accueil', () => {
    //
    //
    page.navigateToRoot();
    //
    expect(page.getText(selectors.APP.TITRE)).toEqual('Mon application');
  });

});
