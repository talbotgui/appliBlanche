import { by } from 'protractor';

export class APP {
  static DRAPEAU_FR = by.css("div.drapeaux a.fr");
  static DRAPEAU_EN = by.css("div.drapeaux a.en");
}

export class PageLogin {
  static TITRE = by.css(".container-fluid > h2");

  static CHAMP_LOGIN = by.xpath("//input[@name='login']");
  static CHAMP_MDP = by.xpath("//input[@name='mdp']");

  static BOUTON_CONNECTER = by.css("button.connecter");

  static MESSAGE_ERREUR_CONNEXION = by.css("div.messageErreur");
}
