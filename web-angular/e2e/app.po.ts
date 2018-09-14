import { browser, by, element, WebElement } from 'protractor';
import { By, FileDetector, promise as wdpromise } from 'selenium-webdriver';
import * as fs from 'fs';

export class ApplicationPage {
  private identifiant: string;

  /** Constructeur simple sans paramètres */
  constructor() {
    this.identifiant = '' + (new Date()).getTime();
  }

  navigateToRoot(): void {
    browser.get('/');
    browser.driver.manage().window().maximize();
  }
  getText(selector: By) {
    return element(selector).getText();
  }
  getValue(selector: By) {
    return element(selector).getAttribute('value');
  }
  click(selector: By): void {
    element(selector).click();
  }
  clickAll(selector: By): void {
    element.all(selector).click();
  }
  isVisible(selector: By) {
    return element(selector).isPresent();
  }
  type(selector: By, text: string): void {
    const e = element(selector);
    e.clear();
    e.sendKeys(text);
  }
  patiente(temps?: number) {
    if (temps) {
      browser.driver.sleep(temps);
    } else {
      browser.waitForAngular();
    }
  }
  compterElements(selector: By): wdpromise.Promise<number> {
    return element.all(selector).count();
  }
  executeScript(script: string): void {
    browser.executeScript(script);
  }
  imprimeEcran(nom: string): void {
    browser.takeScreenshot().then((png) => {
      const dirName = 'build/e2e-screenshot';
      if (!fs.existsSync(dirName)) {
        fs.mkdirSync(dirName);
      }

      const fileName = dirName + '/' + this.identifiant + nom + '.png';
      const stream = fs.createWriteStream(fileName);
      stream.write(new Buffer(png, 'base64'));
      stream.end();
    });
  }
  /**
   * Sélectionne une option d'un selectBox
   * @param selectorDuSelect Selecteur du select
   * @param optionValues Liste d'options (ré-évaluation du select entre chaque option)
   */
  select(selectorDuSelect: By, ...optionValues: string[]): void {
    for (const optionValue of optionValues) {
      element(selectorDuSelect).click();
      browser.waitForAngular();
      element(By.xpath('//option[@value="' + optionValue + '"]')).click();
    }
  }
}
